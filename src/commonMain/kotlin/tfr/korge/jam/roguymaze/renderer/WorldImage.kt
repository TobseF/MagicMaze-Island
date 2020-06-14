package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Image
import com.soywiz.korge.view.anchor
import com.soywiz.korge.view.position
import com.soywiz.korge.view.size
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Tile

class WorldImage(tileSize: Number,
        val position: Point,
        val image: BmpSlice,
        val tile: Tile,
        val gridPos: PositionGrid.Position) : Image(image) {
    init {
        anchor(0.5, 0.5)
        size(tileSize, tileSize)
        position(position)
    }

    override fun toString(): String {
        return "\n\n $tile: ($x,$y) [$gridPos]"
    }
}