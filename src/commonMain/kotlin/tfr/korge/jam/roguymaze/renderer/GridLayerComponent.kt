package tfr.korge.jam.roguymaze.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Container
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.GameMechanics
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.GridLayer
import tfr.korge.jam.roguymaze.model.Row
import tfr.korge.jam.roguymaze.model.Tile

/**
 * Displays the tiles of a [GridLayer] with images out of [WorldSprites].
 */
open class GridLayerComponent(private val gridLayer: GridLayer,
        val world: WorldComponent,
        private val worldSprites: WorldSprites) : Container() {

    /**
     * Percentage up or downsizing of the tiles.
     * `1` means no scaling. `0.8` means `80%` image sizes, which creates `20%` padding.
     * It's 1% scaled to compensate a 1px highlighted border.
     */
    private val tileScale = 1.01

    private val tiles = Array(gridLayer.rowSize) {
        Array<WorldImage?>(gridLayer.columnsSize) { null }
    }

    fun listAllImages(): List<WorldImage?> {
        return (0 until gridLayer.rowSize).flatMap { row ->
            (0 until gridLayer.columnsSize).map { column ->
                tiles[row][column]
            }
        }
    }

    companion object {
        val log = Logger("GameFieldRenderer")

        suspend operator fun invoke(injector: AsyncInjector): GridLayerComponent {
            injector.run {
                val renderer = GridLayerComponent(get(), get(), get())
                mapInstance(renderer)
                return renderer
            }
        }
    }


    init {
        updateImagesFromField()
    }

    private fun updateImagesFromField() {
        removeChildren()
        resetAllFields()
        gridLayer.forEachIndexed(this::addRow)
    }

    private fun resetAllFields() {
        gridLayer.listAllPositions().forEach { tiles[it.y][it.x] = null }
    }

    private fun addRow(rowIndex: Int, row: Row) {
        row.forEachIndexed { columnIndex, tile ->
            if (tile.isTile()) {
                addTile(columnIndex, rowIndex, tile)
            }
        }
    }

    fun removeImage(position: Position) {
        val image = getTile(position)
        removeChild(image?.image)
        removeTileFromGrid(position)
    }

    fun addTile(position: Position, tile: Tile) {
        addTile(position.x, position.y, tile)
    }

    fun addTile(columnIndex: Int, rowIndex: Int, tile: Tile) {
        val gridPos = Position(columnIndex, rowIndex)
        val pos = Point(world.tileSize * columnIndex, world.tileSize * rowIndex)

        val bitmap = worldSprites.getTile(tile)
        if (bitmap != null) {
            val tileSize = world.tileSize * tileScale
            val image = WorldImage(tileSize, pos, bitmap, tile, gridPos)
            if (gridLayer.isNotOnField(columnIndex, rowIndex)) {
                throw IllegalArgumentException(
                        "Image position is out of space: column:$columnIndex,row:$rowIndex (${gridLayer.columnsSize}-${gridLayer.rowSize})")
            }
            val nextTimeImage = tiles[rowIndex][columnIndex]
            if (nextTimeImage != null && nextTimeImage.tile.isTile()) {
                log.info { "Adding tile on non empty cell:  ${nextTimeImage.tile} with $tile:  $columnIndex,row:$rowIndex (${gridLayer.columnsSize}-${gridLayer.rowSize}) " }
                removeChild(nextTimeImage.image)
            }
            tiles[rowIndex][columnIndex] = image
            addChild(image.image)
        }
    }

    private fun getTile(column: Int, row: Int): WorldImage? {
        return tiles[row][column]
    }

    private fun hasTile(column: Int, row: Int): Boolean = tiles[row][column] != null

    private fun getLogDebugData(): String = "images:\n${toString()}\nfields:\n$gridLayer"

    fun getTile(position: Position): WorldImage? {
        return getTile(position.x, position.y)
    }

    fun hasTile(position: Position): Boolean {
        return hasTile(position.x, position.y)
    }

    private fun setTile(tile: WorldImage?, position: Position) {
        tiles[position.y][position.x] = tile
    }

    fun removeTileFromGrid(position: Position) {
        setTile(null, position)
    }

    fun swapTiles(a: Position, b: Position) {
        val tileA = getTile(a)
        val tileB = getTile(b)
        setTile(tileA, b)
        setTile(tileB, a)
    }

    override fun toString(): String {
        return (0 until gridLayer.rowSize).joinToString("\n") { row ->
            "[" + (0 until gridLayer.columnsSize).map { column ->
                tiles[row][column]?.tile ?: Tile.Grass
            }.joinToString(", ") { tile -> tile.shortName() } + "]"
        }
    }

    fun move(move: GameMechanics.Move) {
        val tile = getTile(move.tile)
        setTile(tile, move.target)
        setTile(null, move.tile)
    }

}