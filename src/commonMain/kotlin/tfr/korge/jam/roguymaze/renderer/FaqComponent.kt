package tfr.korge.jam.roguymaze.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge.view.centerOn
import com.soywiz.korge.view.image
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.OpenFaqEvent
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.World

class FaqComponent(val world: World, val res: Resources, val rootView: View, val bus: EventBus) : Container() {

    companion object {
        val log = Logger("FaqComponent")

        suspend operator fun invoke(injector: AsyncInjector): FaqComponent {
            injector.mapSingleton {
                FaqComponent(get(), get(), get(), get())
            }
            return injector.get()
        }
    }


    init {


        val page2 = image(res.helpPage2) {
            centerOn(rootView)
            visible = false
            onClick {
                visible = false
            }
        }
        val page1 = image(res.helpPage1) {
            centerOn(rootView)
            visible = false
            onClick {
                visible = false
                page2.visible = true
            }
        }

        bus.register<OpenFaqEvent> {
            if (page1.visible || page2.visible) {
                page1.visible = false
                page2.visible = false
            } else {
                page1.visible = true
                page2.visible = false
            }
        }


    }


}