package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.FoundHomeEvent
import tfr.korge.jam.roguymaze.GameFlow
import tfr.korge.jam.roguymaze.InputEvent
import tfr.korge.jam.roguymaze.InputEvent.Action
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.model.Team
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.renderer.animation.HeroAnimator

class HeroComponent(val bus: EventBus,
        val hero: Team.Hero,
        val world: World,
        val view: Stage,
        val animator: HeroAnimator,
        bitmap: BmpSlice,
        imageSelected: BmpSlice) : Container() {

    var nextPos: Point? = null

    private val defaultImage: Image
    private val selectedImage: Image

    companion object {

        suspend operator fun invoke(injector: AsyncInjector, bitmap: BmpSlice, imageSelected: BmpSlice): HeroComponent {
            injector.mapSingleton {
                HeroComponent(get(), get(), get(), get(), get(), bitmap, imageSelected)
            }
            return injector.get()
        }
    }

    init {
        val scale = 0.55
        val anchorX = 0.5
        val anchorY = 0.6
        onClick {
            bus.send(InputEvent(Action.SelectHero, heroNumber = hero.number))
        }

        defaultImage = image(bitmap) {
            anchor(anchorX, anchorY)
            scale(scale, scale)
            visible = false
        }
        selectedImage = image(imageSelected) {
            scale(scale, scale)
            anchor(anchorX, anchorY)
        }
        bus.register<FoundHomeEvent> {
            if (hero.number == it.playerNumber) {
                alpha = 0.0
            }
        }
        bus.register<InputEvent> {
            if (it.action == Action.SelectHero) {
                updateSelectionState(it.heroNumber)
            }
        }
        updateSelectionState()
    }

    private fun updateSelectionState(selectedHero: Int = world.selectedHero) {
        val selected = selectedHero == hero.number
        selectedImage.visible = selected
        defaultImage.visible = !selected
    }

    fun move(direction: GameFlow.Direction) = animator.move(direction, this)

    fun moveIllegal(direction: GameFlow.Direction) = animator.moveIllegal(direction, this)

    override fun toString(): String {
        return "Hero ${hero.number} [${hero.x},${hero.y}] (${x},${y})"
    }
}