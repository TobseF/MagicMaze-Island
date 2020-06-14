package tfr.korge.jam.roguymaze

import com.soywiz.klogger.Logger
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.GridLayer
import tfr.korge.jam.roguymaze.model.Tile
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.renderer.WorldComponent

/**
 * Actions and checks for the provided [GridLayer]
 */
class GameMechanics(val world: World, val worldComponent: WorldComponent) {

    val ground = world.rooms.first().ground

    companion object {
        val log = Logger("GameMechanics")

        suspend operator fun invoke(injector: AsyncInjector): GameMechanics {
            injector.run {
                return GameMechanics(get(), get()).apply {
                    injector.mapInstance(this)
                }
            }
        }
    }

    private val zoomStep = 0.1
    private val moveStep = 128.0

    fun zoomIn() {
        worldComponent.scale += zoomStep
    }

    fun zoomOut() {
        worldComponent.scale -= zoomStep
    }

    fun moveMapLeft() {
        worldComponent.x += moveStep
    }

    fun moveMapRight() {
        worldComponent.x -= moveStep
    }

    fun moveMapUp() {
        worldComponent.y += moveStep
    }

    fun moveMapDown() {
        worldComponent.y -= moveStep
    }


    fun swapTiles(a: Position, b: Position) {
        /*
        val tileA = player[a]
        val tileB = player[b]
        player[a] = tileB
        player[b] = tileA*/
    }

    fun isSwapAllowed(start: Position, end: Position): Boolean {
        //return gridLayer.get(end) == Tile.Land
        return true
    }


    fun removeTile(position: Position) {
        ground[position] = Tile.Grass
    }

    data class Move(val target: Position, val tile: Position) {
        fun distance() = target.distance(tile)
    }

    data class InsertMove(val target: Position, val tile: Tile) : Comparable<GameMechanics.InsertMove> {
        override fun compareTo(other: GameMechanics.InsertMove) = other.target.y - this.target.y
    }

    fun insert(moves: List<GameMechanics.InsertMove>) = moves.forEach { insert(it) }

    fun insert(move: GameMechanics.InsertMove) {
        ground[move.target] = move.tile
    }

    fun move(move: GameMechanics.Move) {
        ground[move.target] = ground[move.tile]
        ground[move.tile] = Tile.Grass
    }

    private fun Position.isTile() = ground[this].isTile()

    override fun toString(): String {
        return ground.toString()
    }

}