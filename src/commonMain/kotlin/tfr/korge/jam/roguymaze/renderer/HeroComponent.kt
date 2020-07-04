package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.BmpSlice
import tfr.korge.jam.roguymaze.FoundHomeEvent
import tfr.korge.jam.roguymaze.GameFlow.Direction
import tfr.korge.jam.roguymaze.InputEvent
import tfr.korge.jam.roguymaze.InputEvent.Action
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.model.Team
import tfr.korge.jam.roguymaze.model.World

class HeroComponent(val bus: EventBus,
        private val hero: Team.Hero,
        /**
         * Number of pixels to move with one step
         */
        private val step: Int,
        val world: World,
        val view: View,
        bitmap: BmpSlice,
        imageSelected: BmpSlice) : Container() {

    private val defaultImage: Image
    private val selectedImage: Image

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

    fun move(direction: Direction) {
        when (direction) {
            Direction.Left -> x -= step
            Direction.Right -> x += step
            Direction.Up -> y -= step
            Direction.Down -> y += step
        }
    }


}