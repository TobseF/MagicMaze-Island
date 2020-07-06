package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Stage
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.audio.SoundMachine
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Team
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.renderer.animation.HeroAnimator

/**
 * A team out of 4 [HeroComponent]s
 */
class HeroTeamComponent(val injector: AsyncInjector,
        val bus: EventBus,
        val view: Stage,
        val world: World,
        val worldComponent: WorldComponent,
        val resources: Resources,
        val soundMachine: SoundMachine) : Container() {

    val players = mutableMapOf<Team.Hero, HeroComponent>()
    private val animator = HeroAnimator(view, world, worldComponent, soundMachine)

    companion object {

        suspend operator fun invoke(injector: AsyncInjector): HeroTeamComponent {
            injector.mapSingleton {
                HeroTeamComponent(get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    init {
        for (heroModel in world.team.heroes) {
            val image: BmpSlice = resources.getPlayer(heroModel.number)
            val imageSelected: BmpSlice = resources.getPlayerSelected(heroModel.number)
            val player = HeroComponent(bus, heroModel, world, view, animator, image, imageSelected)
            val pos = worldComponent.getAbsoluteWorldCoordinate(PositionGrid.Position(heroModel.x, heroModel.y))
            player.x = pos.x
            player.y = pos.y
            players.put(heroModel, player)
            addChild(player)
        }
    }


}