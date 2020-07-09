package tfr.korge.jam.roguymaze

import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.Tile
import tfr.korge.jam.roguymaze.model.TileCell

/**
 * Triggered after the deletion of tiles
 */
data class TileDeletionEvent(val rush: Int, val tiles: List<TileCell>)

/**
 * Triggered after a new score value
 */
data class NewScoreEvent(val score: Int, val multiplicator: Int = 1, val pos: Position)

/**
 * Triggered after the user swaps two tiles
 */
data class SwapTileEvent(val start: Position, val end: Position)

object GameOverEvent

object ResetGameEvent

object NextLevelEvent


data class ChangePlayerEvent(val playerId: Int = 0)

object OpenSettingsEvent

object OpenFaqEvent

data class ChangePlayersCountEvent(val playersCount: Int = 0)

data class ChangeRoomEvent(val roomName: String)

data class FoundMaskEvent(val playerNumber: Int = 0)

data class FoundHomeEvent(val playerNumber: Int = 0)

data class TileClickedEvent(val tile: Tile, val gridPos: Position, val position: Point)


data class InputEvent(val action: Action,
        val heroNumber: Int = 0,
        val roomId: Int? = null,
        val isNetworkEvent: Boolean = false) {

    enum class Action {
        MapMoveUp, MapMoveDown, MapMoveLeft, MapMoveRight, MapZoomIn, MapZoomOut, SelectHero, HeroLeft, HeroRight, HeroUp, HeroDown, ActionSearch, FoundNextRoom, Unknown;

        companion object {
            fun parseValue(actioName: String): Action {
                return try {
                    valueOf(actioName)
                } catch (e: Exception) {
                    Unknown
                }
            }
        }
    }

    fun isLocalEvent() = !isNetworkEvent
}