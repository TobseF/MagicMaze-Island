package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Image
import com.soywiz.korge.view.View
import com.soywiz.korge.view.anchor
import com.soywiz.korim.bitmap.BmpSlice
import tfr.korge.jam.roguymaze.FoundHomeEvent
import tfr.korge.jam.roguymaze.GameFlow.Direction
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.model.Players
import tfr.korge.jam.roguymaze.model.World

class PlayerComponent(val bus: EventBus,
        val player: Players.Player,
        val step: Int,
        val world: World,
        val view: View,
        bitmap: BmpSlice) : Image(bitmap) {

    init {
        anchor(0.5, 0.5)
        bus.register<FoundHomeEvent> {
            if (player.number == it.playerNumber) {
                alpha = 0.0
            }
        }
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