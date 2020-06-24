package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korim.bitmap.BmpSlice
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.Tile

class RoomComponent(val room: Room,
        world: WorldComponent,
        val resources: Resources,
        val worldSprites: WorldSprites,
        val view: View) : Container() {

    val borderLeft = BordersSprites(resources) { it.borderLeft }
    val borderRight = BordersSprites(resources) { it.borderRight }
    val borderTop = BordersSprites(resources) { it.borderTop }
    val borderBottom = BordersSprites(resources) { it.borderBottom }

    val items = GridLayerComponent(room.items, world, worldSprites, view)

    init {
        addChild(GridLayerComponent(room.ground, world, worldSprites, view))
        addChild(items)
        addChild(GridLayerComponent(room.bordersLeft, world, borderLeft, view))
        addChild(GridLayerComponent(room.bordersRight, world, borderRight, view))
        addChild(GridLayerComponent(room.bordersTop, world, borderTop, view))
        addChild(GridLayerComponent(room.bordersBottom, world, borderBottom, view))
    }

    open class BordersSprites(resources: Resources, val border: (Resources) -> BmpSlice) : WorldSprites(resources) {

        override fun getTile(tile: Tile): BmpSlice? {
            if (resources != null && tile == Tile.Border) {

                return border.invoke(resources)
            }
            return super.getTile(tile)
        }
    }


}