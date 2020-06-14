package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.component.UpdateComponentWithViews
import com.soywiz.korge.view.*
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.Countdown
import tfr.korge.jam.roguymaze.renderer.util.TimerFormatter

class TimerComponent(res: Resources, override val view: View) : Container(), UpdateComponentWithViews {

    val formatter = TimerFormatter()
    val stopWatch = Countdown(minutes = 12, seconds = 0)
    val timerText: Text
    var lastUpdate = 0.0

    init {
        timerText = text(getTime(), font = res.fontBubble, textSize = 46.0) {
            alignRightToRightOf(view, 14.0)
            alignTopToTopOf(view, 12.0)
        }
    }

    companion object {

        suspend operator fun invoke(injector: AsyncInjector): TimerComponent {
            injector.mapSingleton {
                TimerComponent(get(), get())
            }
            return injector.get()
        }
    }

    override fun update(views: Views, ms: Double) {
        lastUpdate += ms
        if (lastUpdate > 500) {
            updateTimer()
        }
    }

    fun updateTimer() {
        timerText.text = getTime()
    }

    fun getTime(): String {
        return formatter.getFormattedTimeAsString(stopWatch.getTime())
    }

}