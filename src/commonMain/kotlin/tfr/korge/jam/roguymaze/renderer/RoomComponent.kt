package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korge.input.onClick
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
        worldSprites: WorldSprites,
        val view: View) : Container() {

    val borderLeft = BordersSprites(resources) { it.borderLeft }
    val borderRight = BordersSprites(resources) { it.borderRight }
    val borderTop = BordersSprites(resources) { it.borderTop }
    val borderBottom = BordersSprites(resources) { it.borderBottom }

    val items: GridLayerComponent

    init {
        val ground = GridLayerComponent(room.ground, world, worldSprites)
        addChild(ground)
        addChild(GridLayerComponent(room.bordersLeft, world, borderLeft))
        addChild(GridLayerComponent(room.bordersRight, world, borderRight))
        addChild(GridLayerComponent(room.bordersTop, world, borderTop))
        addChild(GridLayerComponent(room.bordersBottom, world, borderBottom))
        items = GridLayerComponent(room.items, world, worldSprites)
        addChild(items)
        onClick { e ->
            val clickPos = localToGlobal(e.currentPosLocal)
            ground.listAllImages().firstOrNull { image ->
                image?.image?.hitTestAny(clickPos.x, clickPos.y) ?: false
            }?.let { image ->
                bus.send(TileClickedEvent(image.tile, room.getAbsoluteWorldPosition(image.gridPos), image.image.pos))
            }
        }
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