package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.*
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.ChangePlayerEvent
import tfr.korge.jam.roguymaze.ChangePlayersCountEvent
import tfr.korge.jam.roguymaze.ChangeRoomEvent
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.World

class NetworkSettingsPanelComponent(res: Resources, val world: World, bus: EventBus, val rootView: View) : Container() {

    val players: Text
    val room: Text

    init {
        image(res.uiPanelTopCenter) {}

        players = text("1/1", font = res.fontBubble, textSize = 38.0) {
            position(100, 17)
        }

        room = text("A", font = res.fontBubble, textSize = 38.0) {
            position(248, 17)
        }

        //centerXOn(rootView) // broken on JS
        positionX(468)

        bus.register<ChangeRoomEvent> {
            room.text = it.roomName
        }
        bus.register<ChangePlayerEvent> {
            updatePlayers()
        }
        bus.register<ChangePlayersCountEvent> {
            updatePlayers()
        }

    }

    private fun updatePlayers() {
        players.text = "${world.selectedPlayer}/${world.playersCount}"
    }

    companion object {

        suspend operator fun invoke(injector: AsyncInjector): NetworkSettingsPanelComponent {
            injector.mapSingleton {
                NetworkSettingsPanelComponent(get(), get(), get(), get())
            }
            return injector.get()
        }
    }


}