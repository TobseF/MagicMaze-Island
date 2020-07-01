package tfr.korge.jam.roguymaze

import com.soywiz.klogger.Logger
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.InputEvent.Action
import tfr.korge.jam.roguymaze.audio.SoundMachine
import tfr.korge.jam.roguymaze.level.WorldFactory
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.Players.Player
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.Tile
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.model.network.Update
import tfr.korge.jam.roguymaze.renderer.WorldComponent
import tfr.korge.jam.roguymaze.renderer.animation.TileAnimator

/**
 * Global game cycle which reacts on swapped tiles [onDragTileEvent].
 */
class GameFlow(private val world: World,
        val worldFactory: WorldFactory,
        private val worldComponent: WorldComponent,
        private val bus: EventBus,
        private val mechanics: GameMechanics,
        private val animator: TileAnimator,
        private val soundMachine: SoundMachine) {

    init {
        bus.register<DragTileEvent> { it.onDragTileEvent() }
        bus.register<InputEvent> { handleInput(it) }
        bus.register<ChangePlayerEvent> { handleChangePlayerId(it) }
        bus.register<ChangeRoomEvent> {
            log.info { "Change room to ${it.roomName}" }
            world.roomName = it.roomName
        }
    }


    private fun handleChangePlayerId(event: ChangePlayerEvent) {
        log.info { "Selected Player: ${event.playerId}" }
        world.selectedPlayer = event.playerId
    }

    private fun handleInput(inputEvent: InputEvent) {
        val playerId = inputEvent.heroNumber
        if (playerId != 0) {
            executeInput(inputEvent, playerId)
        }
    }

    private fun executeInput(inputEvent: InputEvent, playerId: Int) {
        when (inputEvent.action) {
            Action.MapMoveUp -> mechanics.moveMapUp()
            Action.MapMoveDown -> mechanics.moveMapDown()
            Action.MapMoveLeft -> mechanics.moveMapLeft()
            Action.MapMoveRight -> mechanics.moveMapRight()
            Action.MapZoomIn -> mechanics.zoomIn()
            Action.MapZoomOut -> mechanics.zoomOut()
            Action.SelectHero -> selectHero(playerId)
            Action.HeroLeft -> moveHero(playerId, Direction.Left)
            Action.HeroRight -> moveHero(playerId, Direction.Right)
            Action.HeroUp -> moveHero(playerId, Direction.Up)
            Action.HeroDown -> moveHero(playerId, Direction.Down)
            Action.ActionSearch -> findNewRoom()
            Action.FoundNextRoom -> {
                if (inputEvent.roomId != null && playerId != 0) {
                    findNewRoom(playerId, inputEvent.roomId)
                }
            }
        }
    }

    fun moveHero(direction: Direction) {
        moveHero(world.selectedHero, direction)
    }

    fun selectHero(selectedHero: Int) {
        world.selectedHero = selectedHero
    }

    companion object {
        val log = Logger("GameFlow")

        suspend operator fun invoke(injector: AsyncInjector): GameFlow {
            injector.mapSingleton {
                GameFlow(get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    private var rush = 1

    private fun DragTileEvent.onDragTileEvent() {
        if (animator.isAnimationRunning()) {
            log.debug { "Skipping drag event because of moving tiles ($start. $end)" }
        }
        /*else if (field[posA].isNotTile() || field[posB].isNotTile()) {
            log.debug { "Skipping drag event because one tile wasn't a tile ($posA. $posB)" }
        }*/
        else if (mechanics.isSwapAllowed(start, end)) {
            swapTiles(start, end)
            var player = 0
            val startField = world.rooms.first().ground[start]
            if (startField.isPlayer()) {
                player = startField.name.substringAfter("Player").toInt()
            }
            val update = Update(listOf(Update.Player(player, Update.Pos(end.x, end.y))))
            bus.send(update)
        } else {
            animator.animateIllegalSwap(start, end)
            soundMachine.playWrongMove()
        }
    }

    /**
     * Swaps two tiles and triggers the removal of and refill of connected tiles. An illegal swap, will be swapped back.
     */
    private fun swapTiles(posA: Position, posB: Position) {
        // bus.send(SwapTileEvent(posA, posB))
        //rush = 1
        mechanics.swapTiles(posA, posB)
        animator.animateSwap(posA, posB).invokeOnCompletion {
            soundMachine.playClear()
        }
    }

    enum class Direction {
        Up, Down, Left, Right;

        fun opposite() = when (this) {
            Up -> Down
            Down -> Up
            Left -> Right
            Right -> Left
        }
    }

    var roomNumber = 0

    fun Position.move(direction: Direction) = when (direction) {
        Direction.Left -> Position(x - 1, y)
        Direction.Right -> Position(x + 1, y)
        Direction.Up -> Position(x, y - 1)
        Direction.Down -> Position(x, y + 1)
    }

    fun moveHero(playerNumber: Int, direction: Direction) {
        val playerModel: Player = world.getPlayer(playerNumber)
        val playerComponent = worldComponent.getPlayer(playerModel)
        val playerPos = playerModel.pos()
        val nextAbsolutePos = playerPos.move(direction)
        val nextField = world.getGroundTileCellAbsolute(nextAbsolutePos)
        val currentRoom = world.getRoom(playerPos)
        val nextRoom = world.getRoom(nextAbsolutePos)
        val blockedByWall = isBlockedByWall(direction, currentRoom, playerPos, nextRoom, nextAbsolutePos)

        val takenByPlayer = world.players.isTaken(nextAbsolutePos)
        if (currentRoom != null && nextField.tile == Tile.Grass && !takenByPlayer && !blockedByWall && !playerModel.inHome) {
            playerModel.setPos(nextAbsolutePos)
            playerComponent?.move(direction)
            val currentItem = currentRoom.getItemTileCellRelative(nextField.position)
            if (currentItem.tile.isMask() && playerNumber == currentItem.tile.getPlayerNumber()) {
                playerModel.collectedMask = true
                log.info { "Player $playerNumber found mask ${currentItem.tile}!" }
                currentRoom.removeItem(nextField.position)
                bus.send(FoundMaskEvent(playerNumber))
            }
            if (currentItem.tile.isHome() && playerNumber == currentItem.tile.getPlayerNumber() && playerModel.collectedMask) {
                playerModel.inHome = true
                log.info { "Player $playerNumber found a sweet home on ${currentItem.tile}!" }
                currentRoom.setFinish(nextField.position, playerModel)
                bus.send(FoundHomeEvent(playerNumber))
            }
        } else {
            val taken = if (takenByPlayer) "taken by player" else ""
            log.info { "Illegal Move: $nextAbsolutePos -> $nextField (taken: $taken, border: $blockedByWall" }
        }
    }

    private fun isBlockedByWall(direction: Direction,
            currentRoom: Room?,
            playerPos: Position,
            nextRoom: Room?,
            nextAbsolutePos: Position): Boolean {
        return when (direction) {
            Direction.Left -> currentRoom?.getBorderLeftAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderRightAbsolute(nextAbsolutePos) == Tile.Border
            Direction.Right -> currentRoom?.getBorderRightAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderLeftAbsolute(nextAbsolutePos) == Tile.Border
            Direction.Up -> currentRoom?.getBorderTopAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderBottomAbsolute(nextAbsolutePos) == Tile.Border
            Direction.Down -> currentRoom?.getBorderBottomAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderTopAbsolute(nextAbsolutePos) == Tile.Border
        }
    }


    private fun Room.removeItem(relativePosition: Position) {
        worldComponent.getRoom(this.id)?.items?.removeImage(relativePosition)
        this.items[relativePosition] = Tile.Empty
    }

    private fun Room.setItem(relativePosition: Position, tile: Tile) {
        worldComponent.getRoom(this.id)?.items?.addTile(relativePosition, tile)
        this.items[relativePosition] = tile
    }

    private fun Room.setFinish(relativePosition: Position, player: Player) {
        removeItem(relativePosition)
        setItem(relativePosition, Tile.getFinish(player.number))
    }

    fun reset() {
        rush = 1
    }

    fun findNewRoom(playerNumber: Int = world.selectedHero, nextRoomId: Int? = null) {
        val playerModel: Player = world.getPlayer(playerNumber)
        val playerPos = playerModel.pos()
        val playerItem = world.getItemTileCellAbsolute(playerPos)
        val playerRelativePos = playerItem.position

        if (playerItem.tile.isDoorForPlayer(playerNumber)) {
            val currentRoom: Room = world.getRoom(playerPos) ?: throw IllegalStateException("Player outside room")
            currentRoom.removeItem(playerRelativePos)

            if (playerItem.tile.isExit()) {
                val exit = currentRoom.getExit(playerPos)
                val direction = exit.direction()!!

                val nextAbsolutePos = playerPos.move(direction)
                val nextTile = world.getGroundTileAbsolute(nextAbsolutePos)

                if (nextTile == Tile.OutOfSpace) {
                    val isNetworkEvent = nextRoomId != null
                    log.info { "Is Next Room a network event: $isNetworkEvent ($nextRoomId)" }
                    val nextRoom = if (nextRoomId == null) worldFactory.getUndiscoveredRoom(
                            direction.opposite()) else worldFactory.getUndiscoveredById(nextRoomId)
                    if (nextRoom != null) {
                        addNewRoom(nextRoom, currentRoom, direction, playerNumber)
                    } else {
                        log.info { "Failed finding next room for direction: $direction" }
                    }
                } else {
                    log.info { "Next room location is already occupied: $nextAbsolutePos" }
                }
            } else {
                log.info { "No Exit on the current tile: $playerPos" }
            }
        } else {
            log.info { "Tried to unlock door with wrong player: $playerNumber -> ${playerItem.tile}" }
        }
    }

    private fun addNewRoom(nextRoom: Room, currentRoom: Room, direction: Direction, playerNumber: Int) {
        log.info { "Adding new room to map: ${nextRoom.id}" }
        nextRoom.offsetX = currentRoom.offsetX
        nextRoom.offsetY = currentRoom.offsetY
        nextRoom.removeExit(direction.opposite())
        when (direction) {
            Direction.Up -> {
                nextRoom.offsetY -= Room.size
            }
            Direction.Down -> {
                nextRoom.offsetY += Room.size
            }
            Direction.Left -> {
                nextRoom.offsetX -= Room.size
            }
            Direction.Right -> {
                nextRoom.offsetX += Room.size
            }
        }
        world.rooms += nextRoom
        worldComponent.addRoom(nextRoom)
        bus.send(InputEvent(Action.FoundNextRoom, heroNumber = playerNumber, roomId = nextRoom.id))
    }

}
