package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.GameFlow.Direction
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import kotlin.math.abs

/**
 * A level containing the initial [GridLayer] and an optinal tile reserve.
 */
data class Room(val id: Int = 0,
        val rotation: Int = 0,
        var offsetX: Int = 0,
        var offsetY: Int = 0,
        var identified: Boolean = true) {

    fun getExit(): Exit {
        return items.listAllCells().filter { it.tile == Tile.Info }.map { Exit(it.position) }.first()
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
        return items.listAllCells().filter { it.tile.isExit() }.map { Exit(it.position) }
                .any { it.direction() == direction }
    }

    class Exit(val pos: Position) {

        fun direction(): Direction? {
            if (pos.y == 0 && pos.x > 0) {
                return Direction.Up
            } else if (pos.y == 4 - 1 && pos.x > 0) {
                return Direction.Down
            } else if (pos.y > 0 && pos.x == 0) {
                return Direction.Left
            } else if (pos.y > 0 && pos.x == size - 1) {
                return Direction.Right
            }
            return null
        }
    }

    companion object {
        val size = 4
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

    fun getItemTileAbsolute(absolute: Position): Tile {
        return getItemTileRelative(absolute.toRelative())
    }

    fun getBorderLeftRelative(relative: Position): Tile {
        return bordersLeft.getTile(relative)
    }

    fun getBorderLeftAbsolute(absolute: Position): Tile {
        return getBorderLeftRelative(absolute.toRelative())
    }

    fun getBorderRightRelative(relative: Position): Tile {
        return bordersRight.getTile(relative)
    }

    fun getBorderRightAbsolute(absolute: Position): Tile {
        return getBorderRightRelative(absolute.toRelative())
    }

    fun getBorderTopRelative(relative: Position): Tile {
        return bordersTop.getTile(relative)
    }

    fun getBorderTopAbsolute(absolute: Position): Tile {
        return getBorderTopRelative(absolute.toRelative())
    }

    fun getBorderBottomRelative(relative: Position): Tile {
        return bordersBottom.getTile(relative)
    }

    fun getBorderBottomAbsolute(absolute: Position): Tile {
        return getBorderBottomRelative(absolute.toRelative())
    }


    fun contains2(pos: Position): Boolean {
        var posX = (pos.x / size) - (offsetX / size)
        var posY = (pos.y / size) - (offsetY / size)

        if (posX > 0) {
            if (posY > 0) {
                return posX in 0 until 1 && posY in 0 until 1
            } else {
                return posX in 0 until 1 && posY in -1 until 0
            }
        } else {
            if (posY > 0) {
                return posX in -1 until 0 && posY in 0 until 1
            } else {
                return posX in -1 until 0 && posY in -1 until 0
            }
        }

    }

    fun contains(pos: Position): Boolean {
        return ground.listAllPositions().any { pos == Position(it.x + offsetX, it.y + offsetY) }
    }

    fun containsFaster(pos: Position): Boolean {
        var posX = pos.x
        var posY = pos.y
        var offX = offsetX
        var offY = offsetY
        if (offX < 0 && posX < 0) {
            offX = abs(offX)
            posX = abs(posX)
        }
        if (offY < 0 && posY < 0) {
            offY = abs(offY)
            posY = abs(posY)
        }
        val inX = posX in offX until offX + size
        val inY = posY in offY until offY + size
        return (inX && inY)

    }

    val size = Room.size


    val ground: GridLayer = GridLayer(size, size)
    val walls: GridLayer = GridLayer(size, size)
    val items: GridLayer = GridLayer(size, size)

    val bordersLeft: GridLayer = GridLayer(size, size)
    val bordersRight: GridLayer = GridLayer(size, size)
    val bordersTop: GridLayer = GridLayer(size, size)
    val bordersBottom: GridLayer = GridLayer(size, size)

    val cache = Cache()

    class Cache {
        var groundData: String? = null
        var wallData: String? = null
        var itemsData: String? = null
        var bordersLeftData: String? = null
        var bordersRightData: String? = null
        var bordersTopData: String? = null
        var bordersBottomData: String? = null
    }

    fun loadGround(groundData: String): Room {
        cache.groundData = groundData
        GridLayer.fromString(groundData).copyTo(ground)
        return this
    }

    fun loadWalls(wallData: String): Room {
        cache.wallData = wallData
        GridLayer.fromString(wallData).copyTo(walls)
        return this
    }

    fun loadItems(itemsData: String): Room {
        cache.itemsData = itemsData
        GridLayer.fromString(itemsData).copyTo(items)
        return this
    }

    fun loadBordersTop(itemsData: String): Room {
        cache.bordersTopData = itemsData
        GridLayer.fromString(itemsData).copyTo(bordersTop)
        return this
    }

    fun loadBordersLeft(itemsData: String): Room {
        cache.bordersLeftData = itemsData
        GridLayer.fromString(itemsData).copyTo(bordersLeft)
        return this
    }

    fun loadBordersRight(itemsData: String): Room {
        cache.bordersRightData = itemsData
        GridLayer.fromString(itemsData).copyTo(bordersRight)
        return this
    }

    fun loadBordersBottom(itemsData: String): Room {
        cache.bordersBottomData = itemsData
        GridLayer.fromString(itemsData).copyTo(bordersBottom)
        return this
    }


    override fun toString() = "room$id (x:$offsetX,y:$offsetY, rot:$rotation)"

    fun reset() {
        cache.groundData?.let { loadGround(it) }
        cache.wallData?.let { loadWalls(it) }
        cache.itemsData?.let { loadItems(it) }
    }


}