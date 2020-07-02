package tfr.korge.jam.roguymaze.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onMouseDrag
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.IPoint
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.GameMechanics
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resolution
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Players
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.Tile
import tfr.korge.jam.roguymaze.model.World

class WorldComponent(val bus: EventBus,
        val world: World,
        val resolution: Resolution,
        private val worldSprites: WorldSprites,
        private val resources: Resources,
        val view: View) : Container() {

    /**
     * The size of one tile in px
     */
    val tileSize: Int = 64

    fun roomWidth() = tileSize * Room.size

    companion object {
        val log = Logger("WorldComponent")

        suspend operator fun invoke(injector: AsyncInjector): WorldComponent {
            injector.mapSingleton {
                WorldComponent(get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    val players = PlayersComponent(bus, view, world, this, resources)

    fun getPlayer(playerNumber: Players.Player): PlayerComponent = players.players[playerNumber]!!

    val rooms = mutableListOf<RoomComponent>()

    init {
        addChild(players)
        addDragListener()

        val center = resolution.center()
        x = center.x - roomWidth() / 2
        y = center.y - roomWidth() / 2
        for (room in world.rooms) {
            addRoom(room)
        }
    }

    private fun addDragListener() {
        var start: Point = pos.copy()
        onMouseDrag { info ->
            if (info.start && !info.end) {
                start = pos.copy()
            } else if (!info.start && !info.end) {
                x = start.x + info.dx
                y = start.y + info.dy
            }
        }
    }

    fun getRoom(roomId: Int): RoomComponent? {
        return rooms.firstOrNull { it.room.id == roomId }
    }

    fun addRoom(room: Room) {
        val roomComponent = RoomComponent(room, this, resources, worldSprites, view)
        val pos = getRelativeWorldCoordinate(room.pos())
        roomComponent.x = pos.x
        roomComponent.y = pos.y
        rooms.add(roomComponent)
        addChildAt(roomComponent, 0)
    }

    fun getAbsoluteWorldCoordinate(pos: PositionGrid.Position): Point = Point(
            this.x + pos.x * tileSize, this.y + pos.y * tileSize)

    fun getRelativeWorldCoordinate(pos: PositionGrid.Position): Point = Point(pos.x * tileSize, pos.y * tileSize)


    fun getCenterPosition(target: PositionGrid.Position): Point {
        TODO("Not yet implemented")
    }

    fun removeTileFromGrid(tile: PositionGrid.Position) {
        TODO("Not yet implemented")
    }

    fun move(move: GameMechanics.Move) {
        TODO("Not yet implemented")
    }

    fun swapTiles(start: PositionGrid.Position, end: PositionGrid.Position) {
        TODO("Not yet implemented")
    }

    fun getTile(position: PositionGrid.Position): WorldImage? {
        TODO("Not yet implemented")
    }

    fun hasTile(position: PositionGrid.Position): Boolean {
        TODO("Not yet implemented")
    }

    fun addTile(target: PositionGrid.Position, tile: Tile): WorldImage {
        TODO("Not yet implemented")
    }

    fun getField(start: IPoint): PositionGrid.Position {
        TODO("Not yet implemented")
    }

    fun isOnGrid(start: PositionGrid.Position) = true


}