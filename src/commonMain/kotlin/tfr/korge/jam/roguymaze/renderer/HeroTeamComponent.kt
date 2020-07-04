package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korim.bitmap.BmpSlice
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Team
import tfr.korge.jam.roguymaze.model.World

/**
 * A team out of 4 [HeroComponent]s
 */
class HeroTeamComponent(val bus: EventBus,
        val view: View,
        val world: World,
        worldComponent: WorldComponent,
        val resources: Resources) : Container() {

    val players = mutableMapOf<Team.Hero, HeroComponent>()

    init {
        for (playerModel in world.team.heroes) {
            val image: BmpSlice = resources.getPlayer(playerModel.number)
            val imageSelected: BmpSlice = resources.getPlayerSelected(playerModel.number)
            val player = HeroComponent(bus, playerModel, worldComponent.tileSize, world, view, image, imageSelected)
            val pos = worldComponent.getAbsoluteWorldCoordinate(PositionGrid.Position(playerModel.x, playerModel.y))
            player.x = pos.x
            player.y = pos.y
            players.put(playerModel, player)
            addChild(player)
        }
    }


}