package tfr.korge.jam.roguymaze

import com.soywiz.klogger.Logger
import com.soywiz.korev.Key
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.InputEvent.Action
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.renderer.WorldComponent
import tfr.korge.jam.roguymaze.renderer.animation.TileAnimator

class KeyBindings(private val stage: Stage,
        private val bus: EventBus,
        private val worldComponent: WorldComponent,
        private val animator: TileAnimator,
        private val gameFlow: GameFlow,
        private val levelCheck: LevelCheck,
        private val room: Room) : AsyncDependency {


    companion object {
        val log = Logger("KeyBindings")

        suspend operator fun invoke(injector: AsyncInjector): KeyBindings {
            injector.mapSingleton {
                KeyBindings(get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    override suspend fun init() {
        bindKeys()
        bus.register<ResetGameEvent> { reloadLevel() }
        bus.register<NextLevelEvent> { shuffle() }
    }

    private fun bindKeys() {
        stage.onKeyDown {
            onKeyDown(it.key)
        }
    }

    private fun resetState() {
        animator.reset()
        gameFlow.reset()
        levelCheck.reset()
    }

    private fun shuffle() {
        KeyBindings.Companion.log.debug { "Shuffle & Reset" }
        resetState()
        room.ground.shuffle()
    }

    private fun reloadLevel() {
        KeyBindings.Companion.log.debug { "Reload level" }
        resetState()
        room.reset()
    }

    private fun onKeyDown(key: Key) {
        val move = 128.0
        when (key) {
            Key.PLUS, Key.KP_ADD -> {
                bus.send(InputEvent(Action.MapZoomIn))
            }
            Key.MINUS, Key.KP_SUBTRACT -> {
                bus.send(InputEvent(Action.MapZoomOut))
            }
            Key.P -> {
                KeyBindings.Companion.log.debug { "Print Field Data" }
                println(room.ground)
            }
            Key.W -> {
                bus.send(InputEvent(Action.PlayerUp))
            }
            Key.A -> {
                bus.send(InputEvent(Action.PlayerLeft))
            }
            Key.S -> {
                bus.send(InputEvent(Action.PlayerDown))
            }
            Key.D -> {
                bus.send(InputEvent(Action.PlayerRight))
            }
            Key.SPACE -> {
                gameFlow.findNewRoom()
            }
            Key.D -> {
                KeyBindings.Companion.log.debug { "Show Debug Letters" }
            }
            Key.S -> {
                shuffle()
            }
            Key.R -> {
                reloadLevel()
            }
            Key.N1 -> {
                bus.send(InputEvent(Action.SelectPlayer, playerNumber = 1))
            }
            Key.N2 -> {
                bus.send(InputEvent(Action.SelectPlayer, playerNumber = 2))
            }
            Key.N3 -> {
                bus.send(InputEvent(Action.SelectPlayer, playerNumber = 3))
            }
            Key.N4 -> {
                bus.send(InputEvent(Action.SelectPlayer, playerNumber = 4))
            }
            Key.LEFT -> {
                bus.send(InputEvent(Action.MapMoveLeft))
            }
            Key.RIGHT -> {
                bus.send(InputEvent(Action.MapMoveRight))
            }
            Key.UP -> {
                bus.send(InputEvent(Action.MapMoveUp))
            }
            Key.DOWN -> {
                bus.send(InputEvent(Action.MapMoveDown))
            }
            Key.I -> {
                KeyBindings.Companion.log.debug { "Print Image Data" }
                println(worldComponent)
                //println("Renderer data is equal to field data: " + worldComponent.isEqualWithField())
            }
            else -> {
                KeyBindings.Companion.log.debug { "Pressed unmapped key: $key" }
            }
        }
    }


}