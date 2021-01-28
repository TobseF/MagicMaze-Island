package tfr.korge.jam.roguymaze.audio

import com.soywiz.klock.seconds
import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.SoundChannel
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.std.resourcesVfs

/**
 * Jukebox which plays background music in a random order.
 */
class JukeBox(val stage: Stage) : AsyncDependency {

    private var playing: SoundChannel? = null
    private val playList = mutableListOf<Sound>()
    private var started = false
    var activated = false

    companion object {
        suspend operator fun invoke(injector: AsyncInjector, receiver: JukeBox.() -> Unit): JukeBox {
            injector.mapSingleton {
                JukeBox(get()).apply {
                    receiver.invoke(this)
                }
            }
            return injector.get()
        }
    }

    override suspend fun init() {
        if (activated) {
            //playList += newMusic("someMusic.mp3")
        }
    }

    fun play() {
        if (activated && !started) {
            stage.launch {
                loopMusicPlaylist()
            }
        }
    }

    private suspend fun loopMusicPlaylist() {
        started = true
        while (started) {
            val nextSong: Sound = playList.random()
            playing = nextSong.play()
            delay(nextSong.length.coerceAtLeast(2.seconds))
            playing?.stop()
        }
    }

    fun stop() {
        playing?.stop()
        started = false
    }


}

private suspend fun newMusic(fileName: String): Sound = resourcesVfs["music/$fileName"].readSound(
    streaming = true
)