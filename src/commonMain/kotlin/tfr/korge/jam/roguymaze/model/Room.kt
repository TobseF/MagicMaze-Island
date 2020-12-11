package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.GameFlow.Direction
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import kotlin.math.abs
import kotlin.reflect.KMutableProperty0

/**
 * A level containing the initial [GridLayer] and an optinal tile reserve.
 */
data class Room(val id: Int = 0,
        val rotation: Int = 0,
        var offsetX: Int = 0,
        var offsetY: Int = 0,
        var identified: Boolean = true) {


    val ground: GridLayer = GridLayer(size, size)
    val walls: GridLayer = GridLayer(size, size)
    val items: GridLayer = GridLayer(size, size)

    val bordersLeft: GridLayer = GridLayer(size, size)
    val bordersRight: GridLayer = GridLayer(size, size)
    val bordersTop: GridLayer = GridLayer(size, size)
    val bordersBottom: GridLayer = GridLayer(size, size)

    val cache = Cache()


    companion object {
        val size = 4
    }

    fun removeExit(direction: Direction) {
        when (direction) {
            Direction.Left -> items[0, 1] = Tile.Empty
            Direction.Right -> items[size - 1, 1] = Tile.Empty
            Direction.Up -> items[2, 0] = Tile.Empty
            Direction.Down -> items[2, size - 1] = Tile.Empty
        }
    }

    fun getExit(pos: Position) = Exit(pos.toRelative())

    fun pos() = Position(offsetX, offsetY)

    fun hasExit(direction: Direction): Boolean {
        return getExit(direction) != null
    }

    fun getExit(direction: Direction): Exit? {
        return items.listAllCells().filter { it.tile.isExit() }.map { Exit(it.position) }
            .firstOrNull { it.direction() == direction }
    }

    data class Exit(val pos: Position) {

        fun direction(): Direction? {
            if (pos.y == 0 && pos.x > 0) {
                return Direction.Up
            } else if (pos.y == size - 1 && pos.x > 0) {
                return Direction.Down
            } else if (pos.y > 0 && pos.x == 0) {
                return Direction.Left
            } else if (pos.y > 0 && pos.x == size - 1) {
                return Direction.Right
            }
            return null
        }
    }

    fun getGroundTileAbsolute(wold: Position): Tile {
        return ground.getTile(wold.toRelative())
    }

    fun getGroundTileCellAbsolute(wold: Position): TileCell {
        return ground.getTileCell(wold.toRelative())
    }

    fun Position.toRelative(): Position {
        return Position(abs(this.x - offsetX), abs(this.y - offsetY))
    }

    fun getAbsoluteWorldPosition(pos: Position): Position = Position(
            offsetX + pos.x, offsetY + pos.y)

    fun getItemTileRelative(position: Position): Tile {
        return items.getTile(position)
    }

    fun getItemTileCellRelative(position: Position): TileCell {
        return TileCell(getItemTileRelative(position), position)
    }

    fun getItemTileCellAbsolute(absolute: Position): TileCell {
        return TileCell(getItemTileRelative(absolute.toRelative()), absolute.toRelative())
    }

    fun getItemTileAbsolute(absolute: Position) = getItemTileRelative(absolute.toRelative())

    fun getBorderLeftRelative(relative: Position) = bordersLeft.getTile(relative)

    fun getBorderLeftAbsolute(absolute: Position) = getBorderLeftRelative(absolute.toRelative())

    fun getBorderRightRelative(relative: Position) = bordersRight.getTile(relative)

    fun getBorderRightAbsolute(absolute: Position) = getBorderRightRelative(absolute.toRelative())

    fun getBorderTopRelative(relative: Position) = bordersTop.getTile(relative)

    fun getBorderTopAbsolute(absolute: Position) = getBorderTopRelative(absolute.toRelative())

    fun getBorderBottomRelative(relative: Position) = bordersBottom.getTile(relative)

    fun getBorderBottomAbsolute(absolute: Position): Tile {
        return getBorderBottomRelative(absolute.toRelative())
    }

    fun contains(pos: Position): Boolean {
        val inX = pos.x in offsetX until offsetX + size
        val inY = pos.y in offsetY until offsetY + size
        return (inX && inY)
    }


    class Cache {
        var groundData: String? = null
        var wallData: String? = null
        var itemsData: String? = null
        var bordersLeftData: String? = null
        var bordersRightData: String? = null
        var bordersTopData: String? = null
        var bordersBottomData: String? = null
    }

    private fun loadLayer(layerData: String, field: GridLayer, cache: KMutableProperty0<String?>): Room {
        cache.set(layerData)
        GridLayer.fromString(layerData).copyTo(field)
        return this
    }

    fun loadGround(groundData: String) = loadLayer(groundData, ground, cache::groundData)

    fun loadWalls(wallData: String) = loadLayer(wallData, walls, cache::wallData)

    fun loadItems(itemsData: String) = loadLayer(itemsData, items, cache::itemsData)

    fun loadBordersTop(borderData: String) = loadLayer(borderData, bordersTop, cache::bordersTopData)

    fun loadBordersLeft(borderData: String) = loadLayer(borderData, bordersLeft, cache::bordersLeftData)

    fun loadBordersRight(borderData: String) = loadLayer(borderData, bordersRight, cache::bordersRightData)

    fun loadBordersBottom(borderData: String) = loadLayer(borderData, bordersBottom, cache::bordersBottomData)

    override fun toString() = "room$id (x:$offsetX,y:$offsetY, rot:$rotation)"

    fun reset() {
        cache.groundData?.let { loadGround(it) }
        cache.wallData?.let { loadWalls(it) }
        cache.itemsData?.let { loadItems(it) }
    }

}