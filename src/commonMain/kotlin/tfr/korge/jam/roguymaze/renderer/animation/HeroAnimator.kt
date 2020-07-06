package tfr.korge.jam.roguymaze.renderer.animation

import com.soywiz.klock.milliseconds
import com.soywiz.korge.tween.moveTo
import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.async.launch
import com.soywiz.korma.geom.Point
import com.soywiz.korma.interpolation.Easing.Companion.EASE_IN_OUT_ELASTIC
import com.soywiz.korma.interpolation.Easing.Companion.EASE_IN_OUT_QUAD
import kotlinx.coroutines.Job
import tfr.korge.jam.roguymaze.GameFlow.Direction
import tfr.korge.jam.roguymaze.audio.SoundMachine
import tfr.korge.jam.roguymaze.model.Team
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.renderer.HeroComponent
import tfr.korge.jam.roguymaze.renderer.WorldComponent

class HeroAnimator(val stage: Stage,
        val world: World,
        val worldComponent: WorldComponent,
        val soundMachine: SoundMachine) {

    companion object {

        suspend operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton {
                HeroAnimator(get(), get(), get(), get())
            }
        }
    }

    private val animations: MutableMap<Int, MutableList<Job>> = mutableMapOf()

    init {
        animations[1] = mutableListOf<Job>()
        animations[2] = mutableListOf<Job>()
        animations[3] = mutableListOf<Job>()
        animations[4] = mutableListOf<Job>()
    }

    /**
     * Number of pixels to move with one step
     */
    private val step = worldComponent.tileSize

    fun move(direction: Direction, hero: HeroComponent) {
        val nextHeroPos = hero.nextPos ?: hero.pos
        val nextPos = nextPosition(direction, nextHeroPos)
        hero.nextPos = nextPos
        addJob(stage.launch {
            hero.moveTo(nextPos.x, nextPos.y, 260.milliseconds, EASE_IN_OUT_QUAD)
        }, hero.hero)
    }

    fun addJob(job: Job, hero: Team.Hero) {
        val mutableList = animations[hero.number]!!
        mutableList.forEach { it.cancel() }
        mutableList.clear()
        mutableList.add(job)
        job.invokeOnCompletion {
            mutableList.clear()
        }
    }


    fun moveIllegal(direction: Direction, hero: HeroComponent) {
        val start = hero.nextPos ?: hero.pos.copy()
        val nextPos = nextPositionWrong(direction, start)
        if (animations[hero.hero.number]!!.isEmpty()) {
            soundMachine.playWrongMove()
            stage.launch {
                hero.moveTo(nextPos.x, nextPos.y, 300.milliseconds, EASE_IN_OUT_ELASTIC)
                hero.moveTo(start.x, start.y, 240.milliseconds, EASE_IN_OUT_ELASTIC)
            }
        }
    }

    private fun nextPositionWrong(direction: Direction, hero: Point, step: Int = this.step): Point {
        return nextPosition(direction, hero, (step * 0.4).toInt())
    }

    private fun nextPosition(direction: Direction, hero: Point, step: Int = this.step): Point {
        val pos = hero.copy()
        when (direction) {
            Direction.Left -> pos.x -= step
            Direction.Right -> pos.x += step
            Direction.Up -> pos.y -= step
            Direction.Down -> pos.y += step
        }
        return pos
    }

}