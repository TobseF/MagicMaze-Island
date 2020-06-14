package tfr.korge.jam.roguymaze

import com.soywiz.korinject.AsyncInjector
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.Tile
import tfr.korge.jam.roguymaze.model.TileCell

class LevelCheck(val room: Room, private val bus: EventBus) {

    companion object {
        suspend operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { LevelCheck(get(), get()) }
        }
    }

    private var totalScore = 0
    private var moves = 0

    private val tileCounters: MutableMap<Tile, Int> = mutableMapOf()

    init {
        bus.register<NewScoreEvent> { onScore(it) }
        bus.register<TileDeletionEvent> { countTiles(it.tiles) }
        bus.register<SwapTileEvent> { onTileSwapTileEvent() }
    }


    fun failed(): Boolean {
        return false
    }


    private fun countTiles(tiles: List<TileCell>) {
        tiles.map { it.tile }.forEach { tile ->
            tileCounters[tile] = getCount(tile) + 1
        }
    }

    fun getCount(tile: Tile) = tileCounters.getOrElse(tile) { 0 }


    fun onScore(score: NewScoreEvent) {
    }

    fun onTileSwapTileEvent() {
        moves++
    }

    fun reset() {
        moves = 0
        totalScore = 0
        tileCounters.clear()
    }

}