package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.math.PositionGrid.Position

/**
 * Field with [rows] of [Tile]s.
 */
open class GridLayer(val columnsSize: Int, val rowSize: Int) : Iterable<Row> {

    private val rows = Array(rowSize) { Row(columnsSize) }

    companion object {

        fun fromString(string: String): GridLayer {
            val values = string.split("[").filter { it.isNotBlank() }
            val rows = values.map { parseRow(it) }
            val field = GridLayer(rows[0].size(), rows.size)
            rows.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, tile ->
                    field[rowIndex][columnIndex] = tile
                }
            }

            return field
        }

        private fun parseRow(line: String): Row {
            val values = line.trim().removePrefix("[").removeSuffix("]").trim().split(",").filter { it.isNotBlank() }
                    .map { it.trim() }
            val tiles = values.map { Tile.getTile(it) }
            val row = Row(tiles.size)
            tiles.forEachIndexed { index, tile -> row[index] = tile }
            return row
        }
    }

    fun rotate() {
        val a = clone()

        val n = columnsSize
        var tmp: Tile?
        for (x in 0 until (n / 2)) {
            for (y in x until n - x - 1) {
                tmp = a[x][y]
                // Move values from right to top
                a[x][y] = a[y][n - x - 1]
                // Move values from bottom to right
                a[y][n - x - 1] = a[n - x - 1][n - y - 1]
                // Move values from left to bottom
                a[n - x - 1][n - y - 1] = a[n - y - 1][x]
                // Assign temp to left
                a[n - y - 1][x] = tmp
            }
        }
        a.copyTo(this)
    }

    open fun clone(): GridLayer = copyTo(GridLayer(columnsSize, rowSize))

    fun copyTo(gridLayer: GridLayer): GridLayer {
        rows.forEachIndexed { rowIndex, row ->
            gridLayer.rows[rowIndex] = row.clone()
        }
        return gridLayer
    }

    fun getTileCell(position: Position) = TileCell(get(position), position)

    fun getTileCell(column: Int = 0, row: Int = 0) = TileCell(get(column, row), Position(column, row))

    fun getTileCellOnGround(column: Int) = getTileCell(column, rowSize - 1)

    operator fun get(position: Position): Tile = get(position.x, position.y)

    fun getTile(position: Position) = getTile(position.x, position.y)

    fun getTile(column: Int, row: Int): Tile {
        return if (isOnField(column, row)) {
            rows[row][column]
        } else {
            Tile.OutOfSpace
        }
    }

    operator fun get(column: Int, row: Int) = getTile(column, row)

    fun getColumn(column: Int): List<Tile> {
        if (column >= columnsSize) {
            throw IllegalArgumentException("Column $column is out of range: $columnsSize")
        }
        return (0 until rowSize).map { row -> get(column, row) }
    }

    fun getColumnCell(column: Int): List<TileCell> {
        return getColumn(column).mapIndexed { row, tile -> TileCell(tile, Position(column, row)) }
    }

    fun isOnField(column: Int, row: Int): Boolean {
        return isColumnField(column) && isRowField(row)
    }

    fun isNotOnField(column: Int, row: Int) = !isOnField(column, row)

    fun isColumnField(column: Int): Boolean {
        return (column in 0 until columnsSize)
    }

    fun isRowField(row: Int): Boolean {
        return (row in 0 until rowSize)
    }

    operator fun set(column: Int, row: Int, value: Int) {
        rows[row][column] = value
    }

    operator fun set(column: Int, row: Int, value: Tile) {
        rows[row][column] = value
    }

    operator fun set(position: Position, value: Tile) {
        rows[position.y][position.x] = value
    }

    fun set(tileCell: TileCell) {
        set(tileCell.position, tileCell.tile)
    }

    operator fun get(row: Int): Row = getRow(row)

    fun getRow(row: Int): Row {
        return if (isRowField(row)) {
            rows[row]
        } else {
            Row.outOfSpace()
        }
    }

    override fun iterator(): Iterator<Row> = rows.iterator()

    fun listAllCells(): List<TileCell> = listAllPositions().map { getTileCell(it) }

    fun listAllPositions(): List<Position> {
        return (0 until rowSize).flatMap { row ->
            (0 until columnsSize).map { column ->
                Position(column, row)
            }
        }
    }

    fun shuffle() {
        listAllPositions().forEach { position -> set(position, Tile.randomTile()) }
    }

    fun reload(levelData: String) {
        fromString(levelData).listAllCells().forEach(this::set)
    }

    override fun toString(): String {
        return rows.joinToString("\n") { it.toString() }
    }
}
















