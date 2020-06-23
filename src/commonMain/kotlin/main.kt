import com.soywiz.klogger.Logger
import com.soywiz.korev.MouseButton
import com.soywiz.korge.Korge
import com.soywiz.korge.input.mouse
import com.soywiz.korge.input.onMouseDrag
import com.soywiz.korge.view.View
import com.soywiz.korgw.GameWindow
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import kotlinx.coroutines.CoroutineScope
import tfr.korge.jam.roguymaze.*
import tfr.korge.jam.roguymaze.audio.JukeBox
import tfr.korge.jam.roguymaze.audio.SoundMachine
import tfr.korge.jam.roguymaze.level.RoomFactory
import tfr.korge.jam.roguymaze.level.WorldFactory
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resolution
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.network.NetworkBridge
import tfr.korge.jam.roguymaze.renderer.*
import tfr.korge.jam.roguymaze.renderer.animation.TileAnimator
import kotlin.random.Random


/**
 *  Main entry point for the game. To start it, run the gradle tasks:
 * `runJVM` - to run it with JAVA.
 * `runJS` - to run it as HTML Web App.
 * `runAndroidDebug` - to install and start it on an Android device.
 */

const val debug = false
const val playBackgroundMusic = false

/**
 * Virtual size which gets projected onto the [windowResolution]
 */
val virtualResolution = Resolution(width = 1280, height = 800)

/**
 * Actual window size
 */
val windowResolution = virtualResolution

val backgroundColor = Colors["#2b2b2b"]

val levelData = RoomFactory().createRoom(1)


suspend fun main() = Korge(
        virtualHeight = virtualResolution.height, virtualWidth = virtualResolution.width,
        width = windowResolution.width, height = windowResolution.height, bgcolor = backgroundColor, debug = debug,
        quality = GameWindow.Quality.QUALITY) {

    Logger.defaultLevel = Logger.Level.DEBUG

    val userName = "user" + Random.nextInt(999)

    val worldFactory = WorldFactory()
    val world = worldFactory.createWorld(1)

    val injector = AsyncInjector().run {
        mapInstance(this@Korge)
        mapInstance(this@Korge as View)
        mapInstance(this@Korge as CoroutineScope)
        mapInstance(EventBus(this@Korge))
        mapInstance(worldFactory)
        mapInstance(world)
        mapInstance(levelData)
        mapInstance(levelData.ground)
        mapInstance(levelData.walls)
        mapInstance(virtualResolution)
    }
    Resources(injector)

    val worldSprites = WorldSprites(injector.get())
    injector.mapInstance(worldSprites)

    WorldComponent(injector)
    val worldComponent: WorldComponent = injector.get()
    addChild(worldComponent)

    GameMechanics(injector)

    JukeBox(injector) { activated = playBackgroundMusic }.play()

    SoundMachine(injector)

    val islandRenderer = GridLayerComponent(levelData.ground, injector.get(), injector.get(), injector.get())
    injector.mapInstance(islandRenderer)


    var landPostStart: Point
    //worldComponent.draggableOn() // Doesn't support button check

    onMouseDrag { info ->
        if (mouse.button == MouseButton.RIGHT) {
            if (info.start) {
                landPostStart = worldComponent.pos.copy()
            } else if (info.end) {
                landPostStart = worldComponent.pos.copy()
                worldComponent.x = landPostStart.x + info.dx
                worldComponent.y = landPostStart.y + info.dy
                println("dragged World" + worldComponent.pos)
            } else {
            }

        } else {
            // Live tracking is buggy
            //worldComponent.x = landPostStart.x + info.dx
            //worldComponent.y = landPostStart.y + info.dy
        }

    }

    LevelCheck(injector)
    Scoring(injector)


    TileAnimator(injector)

    addChild(GameOverComponent(injector))

    GameFlow(injector)
    KeyBindings(injector)

    NetworkBridge(injector).userName = userName

    addChild(UiComponent(injector))

    addChild(TimerComponent(injector))
    addComponent(injector.get() as TimerComponent)

    addChild(SettingsComponent(injector))
    addChild(FaqComponent(injector))
    addChild(NetworkSettingsPanelComponent(injector))


}


