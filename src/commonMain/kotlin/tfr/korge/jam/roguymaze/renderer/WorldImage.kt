package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Image
import com.soywiz.korge.view.anchor
import com.soywiz.korge.view.position
import com.soywiz.korge.view.size
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.math.PositionGrid
import tfr.korge.jam.roguymaze.model.Tile

class WorldImage(
    tileSize: Double,
    val position: Point,
    val bitmap: BmpSlice,
    val tile: Tile,
    val gridPos: PositionGrid.Position,
    val image: Image = Image(bitmap)
) {
    init {
        image.apply {
            anchor(0.5, 0.5)
            size(tileSize, tileSize)
            position(position)
        }
    }

    override fun toString(): String {
        return "\n\n $tile: ($image.x,$image.y) [$gridPos]"
    }
}