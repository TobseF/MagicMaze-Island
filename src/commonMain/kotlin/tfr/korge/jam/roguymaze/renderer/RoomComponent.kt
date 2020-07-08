package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korim.bitmap.BmpSlice
import tfr.korge.jam.roguymaze.TileClickedEvent
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.Tile

class RoomComponent(val room: Room,
        world: WorldComponent,
        val bus: EventBus,
        val resources: Resources,
        val worldSprites: WorldSprites,
        val view: View) : Container() {

    val borderLeft = BordersSprites(resources) { it.borderLeft }
    val borderRight = BordersSprites(resources) { it.borderRight }
    val borderTop = BordersSprites(resources) { it.borderTop }
    val borderBottom = BordersSprites(resources) { it.borderBottom }

    val items = GridLayerComponent(room.items, world, worldSprites, this)

    init {
        GridLayerComponent(room.ground, world, worldSprites, this) {
            bus.send(TileClickedEvent(it.tile, it.gridPos, it.pos))
        }
        GridLayerComponent(room.bordersLeft, world, borderLeft, this)
        GridLayerComponent(room.bordersRight, world, borderRight, this)
        GridLayerComponent(room.bordersTop, world, borderTop, this)
        GridLayerComponent(room.bordersBottom, world, borderBottom, this)
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