import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korim.bitmap.NinePatchBitmap32
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Size
import tfr.korge.jam.roguymaze.GameOverEvent
import tfr.korge.jam.roguymaze.NextLevelEvent
import tfr.korge.jam.roguymaze.ResetGameEvent
import tfr.korge.jam.roguymaze.lib.*


class GameOverComponent(bus: EventBus, res: Resources, resolution: Resolution) : Container() {

    init {
        bus.register<GameOverEvent> { visible = true }
        visible = false
        val shadowCorrection = 10
        val center = resolution.center().moveRight(shadowCorrection)

        val messageBox = MessageBox(Size(450, 400), center, res.messageBox)
        addChild(messageBox)
        val textPos = Point(center.x - shadowCorrection, messageBox.pos.y)
        textCentered(text = "Game Over", textSize = 64.0, font = res.fontBubble, center = textPos.moveDown(80))

        addChild(CandyButton("Restart", ResetGameEvent(), bus, res, textPos.moveDown(200), this::hide))
        addChild(CandyButton("Next", NextLevelEvent(), bus, res, textPos.moveDown(310), this::hide))
    }

    companion object {
        suspend operator fun invoke(injector: AsyncInjector): GameOverComponent {
            return GameOverComponent(injector.get(), injector.get(), injector.get())
        }
    }

    fun hide() {
        visible = false
    }

    class MessageBox(size: Size, center: Point, texture: NinePatchBitmap32) : Container() {
        init {
            position(center.copy().sub(size.half().p))
            ninePatch(ninePatch = texture, size = size)
        }
    }

    class CandyButton(text: String, event: Any, bus: EventBus, res: Resources, point: Point, run: () -> Unit) :
            Container() {

        init {
            val texture = res.imageButton
            val point1 = texture.centered(point)
            position(point1)
            image(texture = texture) {
                val center = getLocalBounds().center()
                textCentered(text = text, textSize = 55.0, font = res.fontBubble, center = center)
                onClick {
                    run.invoke()
                    bus.send(event)
                }
            }
        }
    }


}