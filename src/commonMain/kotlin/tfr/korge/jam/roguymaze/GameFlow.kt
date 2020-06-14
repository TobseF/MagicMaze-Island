package tfr.korge.jam.roguymaze

import com.soywiz.klogger.Logger
import com.soywiz.korinject.AsyncInjector
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
    }

    private fun handleInput(inputEvent: InputEvent) {
        when (inputEvent.action) {
            InputEvent.Action.MapMoveUp -> mechanics.moveMapUp()
            InputEvent.Action.MapMoveDown -> mechanics.moveMapDown()
            InputEvent.Action.MapMoveLeft -> mechanics.moveMapLeft()
            InputEvent.Action.MapMoveRight -> mechanics.moveMapRight()
            InputEvent.Action.MapZoomIn -> mechanics.zoomIn()
            InputEvent.Action.MapZoomOut -> mechanics.zoomOut()
            InputEvent.Action.SelectPlayer -> selectPlayer(inputEvent.playerNumber)
            InputEvent.Action.PlayerLeft -> movePlayer(
                    GameFlow.Direction.Left)
            InputEvent.Action.PlayerRight -> movePlayer(
                    GameFlow.Direction.Right)
            InputEvent.Action.PlayerUp -> movePlayer(
                    GameFlow.Direction.Up)
            InputEvent.Action.PlayerDown -> movePlayer(
                    GameFlow.Direction.Down)
            InputEvent.Action.ActionSearch -> findNewRoom()
        }
    }

    fun movePlayer(direction: GameFlow.Direction) {
        movePlayer(world.selectedPlayer, direction)
    }

    fun selectPlayer(playerNumber: Int) {
        world.selectedPlayer = playerNumber
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
            GameFlow.Companion.log.debug { "Skipping drag event because of moving tiles ($start. $end)" }
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
            GameFlow.Direction.Up -> GameFlow.Direction.Down
            GameFlow.Direction.Down -> GameFlow.Direction.Up
            GameFlow.Direction.Left -> GameFlow.Direction.Right
            GameFlow.Direction.Right -> GameFlow.Direction.Left
        }
    }

    var roomNumber = 0

    fun Position.move(direction: GameFlow.Direction) = when (direction) {
        GameFlow.Direction.Left -> Position(x - 1, y)
        GameFlow.Direction.Right -> Position(x + 1, y)
        GameFlow.Direction.Up -> Position(x, y - 1)
        GameFlow.Direction.Down -> Position(x, y + 1)
    }

    fun movePlayer(playerNumber: Int, direction: GameFlow.Direction) {
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
                GameFlow.Companion.log.info { "Player $playerNumber found mask ${currentItem.tile}!" }
                currentRoom.removeItem(nextField.position)
                bus.send(FoundMaskEvent(playerNumber))
            }
            if (currentItem.tile.isHome() && playerNumber == currentItem.tile.getPlayerNumber() && playerModel.collectedMask) {
                playerModel.inHome = true
                GameFlow.Companion.log.info { "Player $playerNumber found a sweet home on ${currentItem.tile}!" }
                currentRoom.setFinish(nextField.position, playerModel)
                bus.send(FoundHomeEvent(playerNumber))
            }
        } else {
            val taken = if (takenByPlayer) "taken by player" else ""
            GameFlow.Companion.log.info { "Illegal Move: $nextAbsolutePos -> $nextField (taken: $taken, border: $blockedByWall" }
        }
    }

    private fun isBlockedByWall(direction: GameFlow.Direction,
            currentRoom: Room?,
            playerPos: Position,
            nextRoom: Room?,
            nextAbsolutePos: Position): Boolean {
        return when (direction) {
            GameFlow.Direction.Left -> currentRoom?.getBorderLeftAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderRightAbsolute(nextAbsolutePos) == Tile.Border
            GameFlow.Direction.Right -> currentRoom?.getBorderRightAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderLeftAbsolute(nextAbsolutePos) == Tile.Border
            GameFlow.Direction.Up -> currentRoom?.getBorderTopAbsolute(
                    playerPos) == Tile.Border || nextRoom?.getBorderButtomAbsolute(nextAbsolutePos) == Tile.Border
            GameFlow.Direction.Down -> currentRoom?.getBorderButtomAbsolute(
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

    fun findNewRoom(playerNumber: Int = world.selectedPlayer) {
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
                    val nextRoom = worldFactory.getUndiscoveredRoom(direction.opposite())
                    if (nextRoom != null) {
                        addNewRoom(nextRoom, currentRoom, direction)

                    } else {
                        GameFlow.Companion.log.info { "Failed finding next room for direction: $direction" }
                    }
                } else {
                    GameFlow.Companion.log.info { "Next room location is already occupied: $nextAbsolutePos" }
                }
            } else {
                GameFlow.Companion.log.info { "No Exit on the current tile: $playerPos" }
            }
        } else {
            GameFlow.Companion.log.info { "Tried to unlock door with wrong player: $playerNumber -> ${playerItem.tile}" }
        }
    }

    private fun addNewRoom(nextRoom: Room, currentRoom: Room, direction: GameFlow.Direction) {
        GameFlow.Companion.log.info { "Adding new room to map: ${nextRoom.id}" }
        nextRoom.offsetX = currentRoom.offsetX
        nextRoom.offsetY = currentRoom.offsetY
        nextRoom.removeExit(direction.opposite())
        when (direction) {
            GameFlow.Direction.Up -> {
                nextRoom.offsetY -= Room.size
            }
            GameFlow.Direction.Down -> {
                nextRoom.offsetY += Room.size
            }
            GameFlow.Direction.Left -> {
                nextRoom.offsetX -= Room.size
            }
            GameFlow.Direction.Right -> {
                nextRoom.offsetX += Room.size
            }
        }
        world.rooms += nextRoom
        worldComponent.addRoom(nextRoom)
        bus.send(FoundNewRoomEvent(nextRoom.id))
    }

}
