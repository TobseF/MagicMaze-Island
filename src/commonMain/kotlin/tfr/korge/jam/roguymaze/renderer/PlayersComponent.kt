package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korim.bitmap.Bitmap
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Players
import tfr.korge.jam.roguymaze.model.World

class PlayersComponent(val bus: EventBus,
        val view: View,
        val world: World,
        val worldComponent: WorldComponent,
        val resources: Resources) : Container() {

    val players = mutableMapOf<Players.Player, PlayerComponent>()

    init {
        for (playerModel in world.players.players) {
            var image: Bitmap = resources.getPlayer(playerModel.number)
            val player = PlayerComponent(bus, playerModel, worldComponent.tileSize, world, view, image)
            val pos = worldComponent.getAbsoluteWorldCoordinate(PositionGrid.Position(playerModel.x, playerModel.y))
            player.x = pos.x
            player.y = pos.y
            players.put(playerModel, player)
            addChild(player)
        }
    }


}