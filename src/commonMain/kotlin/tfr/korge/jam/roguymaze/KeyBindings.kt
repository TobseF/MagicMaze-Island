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
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.renderer.WorldComponent
import tfr.korge.jam.roguymaze.renderer.animation.TileAnimator

class KeyBindings(private val stage: Stage,
        private val bus: EventBus,
        private val world: World,
        private val worldComponent: WorldComponent,
        private val animator: TileAnimator,
        private val gameFlow: GameFlow,
        private val levelCheck: LevelCheck,
        private val room: Room) : AsyncDependency {


    companion object {
        val log = Logger("KeyBindings")

        suspend operator fun invoke(injector: AsyncInjector): KeyBindings {
            injector.mapSingleton {
                KeyBindings(get(), get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    override suspend fun init() {
        bindKeys()
        bus.register<ResetGameEvent> { reloadLevel() }
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

    private fun reloadLevel() {
        log.debug { "Reload level" }
        resetState()
        room.reset()
    }

    fun sendPlayerInputEvent(action: Action) {
        bus.send(InputEvent(action, world.selectedPlayer))
    }


    private fun onKeyDown(key: Key) {
        when (key) {
            Key.W -> {
                if (world.getAllowedActions().contains(Action.PlayerUp)) {
                    sendPlayerInputEvent(Action.PlayerUp)
                }
            }
            Key.A -> {
                if (world.getAllowedActions().contains(Action.PlayerLeft)) {
                    sendPlayerInputEvent(Action.PlayerLeft)
                }
            }
            Key.S -> {
                if (world.getAllowedActions().contains(Action.PlayerDown)) {
                    sendPlayerInputEvent(Action.PlayerDown)
                }
            }
            Key.D -> {
                if (world.getAllowedActions().contains(Action.PlayerRight)) {
                    sendPlayerInputEvent(Action.PlayerRight)
                }
            }
            Key.SPACE -> {
                if (world.getAllowedActions().contains(Action.ActionSearch)) {
                    gameFlow.findNewRoom()
                }
            }
            Key.PLUS, Key.KP_ADD -> {
                sendPlayerInputEvent(Action.MapZoomIn)
            }
            Key.MINUS, Key.KP_SUBTRACT -> {
                sendPlayerInputEvent(Action.MapZoomOut)
            }
            Key.P -> {
                log.debug { "Print Field Data" }
                println(room.ground)
            }

            Key.LEFT -> {
                sendPlayerInputEvent(Action.MapMoveLeft)
            }
            Key.RIGHT -> {
                sendPlayerInputEvent(Action.MapMoveRight)
            }
            Key.UP -> {
                sendPlayerInputEvent(Action.MapMoveUp)
            }
            Key.DOWN -> {
                sendPlayerInputEvent(Action.MapMoveDown)
            }


            Key.F1, Key.NUMPAD1 -> {
                bus.send(ChangePlayerEvent(1))
            }
            Key.F2, Key.NUMPAD2 -> {
                bus.send(ChangePlayerEvent(2))
            }
            Key.F3, Key.NUMPAD3 -> {
                bus.send(ChangePlayerEvent(3))
            }
            Key.F4, Key.NUMPAD4 -> {
                bus.send(ChangePlayerEvent(4))
            }
            Key.F5, Key.NUMPAD5 -> {
                bus.send(ChangePlayerEvent(5))
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
            else -> {
                log.debug { "Pressed unmapped key: $key" }
            }
        }
    }


}