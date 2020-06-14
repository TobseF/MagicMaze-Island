package tfr.korge.jam.roguymaze

import tfr.korge.jam.roguymaze.math.PositionGrid.Position
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

/**
 * Triggered after the user drags a tile
 */
data class DragTileEvent(val start: Position, val end: Position)

class GameOverEvent

class ResetGameEvent

class NextLevelEvent


data class FoundMaskEvent(val playerNumber: Int = 0)

data class FoundHomeEvent(val playerNumber: Int = 0)

data class FoundNewRoomEvent(val nextRoom: Int = 0)

data class InputEvent(val action: InputEvent.Action, val playerNumber: Int = 0) {
    enum class Action { MapMoveUp, MapMoveDown, MapMoveLeft, MapMoveRight, MapZoomIn, MapZoomOut, SelectPlayer, PlayerLeft, PlayerRight, PlayerUp, PlayerDown, ActionSearch }
}