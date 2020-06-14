package tfr.korge.jam.roguymaze.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onClick
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.ChangePlayerEvent
import tfr.korge.jam.roguymaze.ChangePlayersEvent
import tfr.korge.jam.roguymaze.ChangeRoomEvent
import tfr.korge.jam.roguymaze.OpenSettingsEvent
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.World

class SettingsComponent(val world: World, val res: Resources, val rootView: View, val bus: EventBus) : Container() {

    companion object {
        val log = Logger("SettingsComponent")

        suspend operator fun invoke(injector: AsyncInjector): SettingsComponent {
            injector.mapSingleton {
                SettingsComponent(get(), get(), get(), get())
            }
            return injector.get()
        }
    }


    init {
        visible = false

        bus.register<OpenSettingsEvent> {
            this.visible = !visible
        }

        val settings = image(res.table) {
            centerOn(rootView)
        }

        val labelPlayers = uiText("Players", width = 420.0) {
            alignTopToTopOf(settings, 90.0)
            alignLeftToLeftOf(settings, 75.0)
        }

        for (playerCount in 0 until 5) {
            uiText((playerCount + 1).toString(), width = 70.0) {
                alignTopToBottomOf(labelPlayers, 18.0)
                alignLeftToLeftOf(labelPlayers, (90 * playerCount))
                onClick {
                    bus.send(ChangePlayersEvent(playerCount + 1))
                }
            }
        }

        val labelPlayer = uiText("Player", width = 420.0) {
            alignTopToBottomOf(labelPlayers, 100)
            alignLeftToLeftOf(labelPlayers)
        }

        for (playerId in 0 until 5) {
            uiText((playerId + 1).toString(), width = 70.0) {
                alignTopToBottomOf(labelPlayer, 18.0)
                alignLeftToLeftOf(labelPlayer, (90.0 * playerId))
                onClick {
                    bus.send(ChangePlayerEvent(playerId = (playerId + 1)))
                }
            }
        }


        val room = uiText("Room", width = 420.0) {
            alignTopToBottomOf(labelPlayer, 100)
            alignLeftToLeftOf(labelPlayer)
        }

        val rooms = listOf("A", "B", "C", "D", "E", "F", "G", "H", "H")
        rooms.forEachIndexed { index, roomName ->
            uiText(roomName, width = 70.0) {
                alignTopToBottomOf(room, 18.0)
                alignLeftToLeftOf(room, (90.0 * index))
                onClick {
                    bus.send(ChangeRoomEvent(roomName = roomName))
                }
            }
        }
    }


}