package tfr.korge.jam.roguymaze.compontens

import com.soywiz.korge.component.Component
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.image
import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.lib.Resources

class Background(override val view: Stage, res: Resources) : Component {

    companion object {
        suspend operator fun invoke(injector: AsyncInjector): Background {
            injector.run {
                return Background(get(), get())
            }
        }
    }

    init {
        view.image(res.imageBackground)
    }

}