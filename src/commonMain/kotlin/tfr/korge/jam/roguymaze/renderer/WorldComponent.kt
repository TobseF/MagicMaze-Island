package tfr.korge.jam.roguymaze.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onMouseDrag
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.audio.SoundMachine
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resolution
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.Team
import tfr.korge.jam.roguymaze.model.World

class WorldComponent(val injector: AsyncInjector,
        val bus: EventBus,
        val world: World,
        val resolution: Resolution,
        private val worldSprites: WorldSprites,
        private val resources: Resources,
        override val stage: Stage,
        val soundMachine: SoundMachine) : Container(), AsyncDependency {

    /**
     * The size of one tile in px
     */
    val tileSize: Int = 64

    fun roomWidth() = tileSize * Room.size

    companion object {
        val log = Logger("WorldComponent")

        suspend operator fun invoke(injector: AsyncInjector): WorldComponent {
            injector.mapSingleton {
                WorldComponent(get(), get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    private lateinit var players: HeroTeamComponent

    fun getHero(heroNumber: Team.Hero): HeroComponent = players.players[heroNumber]!!

    private val rooms = mutableListOf<RoomComponent>()

    override suspend fun init() {
        players = HeroTeamComponent(injector, bus, stage, world, this, resources, soundMachine)
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
        val roomComponent = RoomComponent(room, this, bus, resources, worldSprites, stage)
        val pos = getRelativeWorldCoordinate(room.pos())
        roomComponent.x = pos.x
        roomComponent.y = pos.y
        rooms.add(roomComponent)
        addChildAt(roomComponent, 0)
    }


    fun getAbsoluteWorldCoordinate(pos: PositionGrid.Position): Point = Point(
            this.x + pos.x * tileSize, this.y + pos.y * tileSize)

    fun getRelativeWorldCoordinate(pos: PositionGrid.Position): Point = Point(pos.x * tileSize, pos.y * tileSize)

}