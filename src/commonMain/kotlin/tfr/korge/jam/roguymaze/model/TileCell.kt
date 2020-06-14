package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.math.PositionGrid

/**
 * A [Tile] wit additional coordinates (column & row) in the [PositionGrid]
 */
data class TileCell(val tile: Tile, val position: PositionGrid.Position) {
    override fun toString(): String {
        return "${tile.shortName()} (${position.x},${position.y})"
    }
}