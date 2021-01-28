package tfr.korge.jam.roguymaze.audio

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs

/**
 * Plays game sounds.
 */
class SoundMachine : AsyncDependency {

    /**
     * Removing tiles from the field
     */
    private var clear: Sound? = null

    /**
     * Wrong tile move, which will be toggled back
     */
    private var wrongMove: Sound? = null

    /**
     * Tile git's the ground. Used for now.
     */
    private var dopGround: Sound? = null

    val playSounds = true

    companion object {
        operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { SoundMachine() }
        }
    }

    private suspend fun newSound(fileName: String) = resourcesVfs["sounds/$fileName"].readSound()

    override suspend fun init() {
        if (playSounds) {
            clear = newSound("clear.mp3")
            wrongMove = newSound("wrong_move.mp3")
            dopGround = newSound("drop_ground.mp3")
        }
    }

    fun playClear() {
        if (playSounds) {
            clear?.play()
        }
    }

    fun playWrongMove() {
        if (playSounds) {
            wrongMove?.play()
        }
    }

    fun playDropGround() {
        if (playSounds) {
            dopGround?.play()
        }
    }


}