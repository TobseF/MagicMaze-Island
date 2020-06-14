package tfr.korge.jam.roguymaze.renderer.animation

import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.klogger.Logger
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.position
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.Easing
import kotlinx.coroutines.*
import tfr.korge.jam.roguymaze.GameMechanics.InsertMove
import tfr.korge.jam.roguymaze.GameMechanics.Move
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.TileCell
import tfr.korge.jam.roguymaze.renderer.GridLayerComponent
import tfr.korge.jam.roguymaze.renderer.WorldComponent
import tfr.korge.jam.roguymaze.renderer.WorldImage

/**
 * Provides animation for tiles in a [GridLayerComponent].
 */
class TileAnimator(val view: Stage, val worldComponent: WorldComponent) {

    companion object {
        val log = Logger("TileAnimator")
        suspend operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { TileAnimator(get(), get()) }
        }
    }

    data class ImagePosition(val image: Image?, val point: IPoint) {
        override fun toString() = point.toString()
    }

    private val jobs = mutableListOf<Job>()

    private val moveForward = AnimationSettings(1.seconds, Easing.EASE_IN_OUT_ELASTIC)
    private val moveForwardFast = AnimationSettings(400.milliseconds, Easing.EASE_IN_OUT_ELASTIC)
    private val moveBackward = AnimationSettings(550.milliseconds, Easing.EASE_IN_OUT_ELASTIC)
    private val moveBackwardFast = AnimationSettings(300.milliseconds, Easing.EASE_IN_OUT_ELASTIC)
    private val hide = AnimationSettings(200.milliseconds, Easing.EASE_IN)

    private fun fallingAnimation(rows: Int) = AnimationSettings((500 * rows).milliseconds, easing = Easing.EASE_IN)

    private fun animateRemoveTiles(tile: TileCell) = animateRemoveTiles(tile.position)

    private fun animateRemoveTiles(tile: Position) {
        if (tile.hasImage()) {
            val image = tile.getImage()
            worldComponent.removeTileFromGrid(tile)
            image?.let { animateRemoveTile(it) }
        } else {
            log.debug { "Skipping remove image, because it was already removed: $tile" }
        }
    }

    private fun animateRemoveTile(image: Image) {

        launch {
            image.hide(hide.time, hide.easing)
        }
        launch {
            val scale = 1.4
            image.scaleTo(scale, scale, hide.time, hide.easing)
        }
        launch {
            image.rotateTo(180.degrees, hide.time, hide.easing)
        }
    }

    fun animateRemoveTiles(positions: List<TileCell>) {
        positions.forEach { animateRemoveTiles(it) }
    }

    fun animateMoves(moves: List<Move>): Job {
        val imageMoves = moves.map { it.prepare() }
        imageMoves.forEach { worldComponent.move(it.move) }
        return launch {
            imageMoves.forEach {
                launch {
                    animateMove(it)
                }
            }
        }
    }

    private fun Move.prepare(): ImageMove {
        return ImageMove(this, this.tile.getImagePosition(), worldComponent.getCenterPosition(this.target))
    }

    private suspend fun animateMove(move: ImageMove) {
        move.tile.image?.move(move.target, fallingAnimation(move.distance().toInt()))
    }

    class ImageMove(val move: Move, val tile: ImagePosition, val target: Point) {
        fun distance() = move.distance()
    }

    fun animateSwap(start: Position, end: Position): Job {
        val startPos: ImagePosition = start.getImagePosition()
        val endPos: Point = end.getImagePoint()
        log.debug { "Animate tile swap: $start-$end: $startPos - $endPos" }
        worldComponent.swapTiles(start, end)
        return async {
            launch {
                startPos.image?.move(endPos, moveForward)
            }
        }
    }

    fun animateIllegalSwap(start: Position, end: Position): Job {
        val startPos: ImagePosition = start.getImagePosition()
        val endPos: Point = end.getImagePoint()
        val half = startPos.point.minus(startPos.point.minus(endPos).div(2))
        log.debug { "Animate illegal swap: $start-$end: $startPos - $endPos" }
        return async {
            launch {
                startPos.image?.move(half, moveForwardFast)
                startPos.image?.move(startPos.point, moveBackwardFast)
            }
        }
    }


    fun animateInsert(moves: List<InsertMove>): Job {
        val moveByColumn: Map<Int, List<InsertMove>> = moves.groupBy { it.target.x }
        return launch {
            moveByColumn.keys.forEach { column ->
                val columnMoves = moveByColumn[column]?.sorted()
                columnMoves?.forEachIndexed { row, move ->
                    launch {
                        animateInsert(move, ((1 + row) * 500).toLong(), this)
                    }
                }
            }
        }
    }

    fun isAnimationRunning(): Boolean {
        val active = jobs.any { !it.isCompleted }
        if (!active) {
            jobs.clear()
        }
        return active
    }

    private suspend fun animateInsert(move: InsertMove, delay: Long, scope: CoroutineScope) {
        val image = worldComponent.addTile(move.target, move.tile)
        image.alpha = 0.0
        val target = move.target.getImagePoint()
        val start = move.target.moveToStart().getImagePoint()
        image.position(start)
        delay(delay)
        scope.launch {
            image.tween(image::alpha[1.0], time = 150.milliseconds, easing = Easing.EASE_IN)
        }
        image.move(target, fallingAnimation(move.target.y))
    }

    fun reset() {
        jobs.forEach {
            it.cancel()
        }
        jobs.clear()
    }

    private fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return addJob(view.launch(block = block))
    }

    private fun async(block: suspend CoroutineScope.() -> Unit): Job {
        return addJob(view.async(block = block))
    }

    private fun addJob(job: Job) = job.also { jobs.add(it) }

    private fun Position.getImage(): WorldImage? = worldComponent.getTile(this)
    private fun Position.hasImage(): Boolean = worldComponent.hasTile(this)

    private fun Position.getImagePosition(): ImagePosition {
        return ImagePosition(getImage(), this.getImagePoint())
    }

    private fun Position.getImagePoint(): Point {
        return worldComponent.getCenterPosition(this)
    }

}