package tfr.korge.jam.roguymaze.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onClick
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.ChangePlayerEvent
import tfr.korge.jam.roguymaze.ChangePlayersCountEvent
import tfr.korge.jam.roguymaze.ChangeRoomEvent
import tfr.korge.jam.roguymaze.OpenSettingsEvent
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.World

class SettingsComponent(val world: World, val rootView: Stage, res: Resources, val bus: EventBus) : Container() {

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
        bus.register<OpenSettingsEvent> {
            this.visible = !visible
        }

        val settings = image(res.table) {
            //centerOn(rootView)
            position(120, 50)
        }

        fun String.selected() = "[$this]"
        fun Int.selected() = "$this".selected()

        val labelPlayers = uiText("Number of Players", width = 420.0) {
            alignTopToTopOf(settings, 90.0)
            alignLeftToLeftOf(settings, 75.0)
            mouseEnabled = false
        }

        for (playerCount in 1 until 6) {
            fun buttonText(nextPlayerCount: Int) = if (playerCount == nextPlayerCount) {
                playerCount.selected()
            } else {
                "$playerCount"
            }
            uiText(buttonText(world.playersCount), width = 70.0) {
                alignTopToBottomOf(labelPlayers, 18.0)
                alignLeftToLeftOf(labelPlayers, (90.0 * (playerCount - 1)))
                onClick {
                    world.playersCount = playerCount
                    log.info { "Changed players count to $playerCount" }
                    bus.send(ChangePlayersCountEvent(playerCount))
                    if (world.selectedPlayer > playerCount) {
                        world.selectedPlayer = playerCount
                        bus.send(ChangePlayerEvent(playerCount))
                    }
                }
                bus.register<ChangePlayersCountEvent> {
                    text = buttonText(it.playersCount)
                }
            }
        }

        val labelPlayer = uiText("Player", width = 420.0) {
            alignTopToBottomOf(labelPlayers, 100.0)
            alignLeftToLeftOf(labelPlayers)
            mouseEnabled = false
        }

        for (playerId in 1 until 6) {
            fun buttonText(nextPlayerId: Int) = if (playerId == nextPlayerId) {
                playerId.selected()
            } else {
                "$playerId"
            }

            uiText(buttonText(world.selectedPlayer), width = 70.0) {
                enabled = playerId <= world.playersCount
                alignTopToBottomOf(labelPlayer, 18.0)
                alignLeftToLeftOf(labelPlayer, (90.0 * (playerId - 1)))
                onClick {
                    log.info { "Changed local player to $playerId" }
                    bus.send(ChangePlayerEvent(playerId = (playerId)))
                }
                bus.register<ChangePlayerEvent> {
                    text = buttonText(it.playerId)
                }
                bus.register<ChangePlayersCountEvent> {
                    enabled = playerId <= it.playersCount
                }
            }
        }

        val room = uiText("Network Game Channel", width = 420.0) {
            alignTopToBottomOf(labelPlayer, 100.0)
            alignLeftToLeftOf(labelPlayer)
            mouseEnabled = false
        }

        val rooms = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I")
        rooms.forEachIndexed { index, roomName ->
            fun buttonText(nextRoom: String) = if (roomName == nextRoom) {
                roomName.selected()
            } else {
                roomName
            }

            uiText(buttonText(world.roomName), width = 70.0) {
                alignTopToBottomOf(room, 18.0)
                alignLeftToLeftOf(room, (90.0 * index))
                onClick {
                    bus.send(ChangeRoomEvent(roomName = roomName))
                }
                bus.register<ChangeRoomEvent> {
                    text = buttonText(it.roomName)
                }
            }
        }
        visible = false
    }


}