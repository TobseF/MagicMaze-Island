package tfr.korge.jam.roguymaze.model

/**
 * A single tile which can be placed on the [GridLayer].
 */
enum class Tile(private val shortName: String? = null) {
    A, B, C, D,

    //Assets
    A1("A1"), A2("A2"), A3("A3"), A4("A4"), A5("A5"), A6("A6"), A7("A7"), A8("A8"), A9("A9"), A10("A10"), A11("A11"),

    //Home
    Home1("E1"), Home2("E2"), Home3("E3"), Home4("E4"),

    //Home
    Door1("D1"), Door2("D2"), Door3("D3"), Door4("D4"),

    //Mask
    Mask1("M1"), Mask2("M2"), Mask3("M3"), Mask4("M4"),

    //Finish
    Finish1("F1"), Finish2("F2"), Finish3("F3"), Finish4("F4"),

    Land, Info("I"),

    /**
     * A wall which blocks falling stones. Not used.
     */
    Water,

    /**
     * An empty field.
     */
    Grass, Empty("_"), Border("H"),


    /**
     * The Tile is not present. Happens if Tile coordinates are out of the [GridLayer].
     */
    OutOfSpace("X"), Player1("1"), Player2("2"), Player3("3"), Player4("4");

    val index = ordinal

    fun shortName(): String = shortName ?: name.first().toString()

    fun getPlayerNumber(): Int {
        return name.last().toString().toIntOrNull() ?: 0
    }

    fun isMask() = name.startsWith("Mask")

    fun isHome() = name.startsWith("Home")

    fun isTile() = !isOutOfSpace()

    fun isExit() = name.contains("Door") || this == Info

    fun isNotTile() = !isTile()

    fun isOutOfSpace() = this == OutOfSpace

    fun isPlayer() = name.contains("Player")

    fun isDoorForHero(playerNumber: Int): Boolean {
        return isDoor() && name.last().toString() == playerNumber.toString()
    }

    private fun isDoor() = name.startsWith("Door")

    companion object {

        private val toTile = mutableMapOf<String, Tile>()

        init {
            values().forEach { toTile[it.shortName()] = it }
        }

        fun getTile(index: Int): Tile {
            if (index > values().size) {
                throw IllegalArgumentException("Tile index $index > ${values().size} is not available")
            }
            val tile = values()[index]
            if (tile.isNotTile()) {
                throw IllegalArgumentException("Tile $tile for index $index is not a tile")
            }
            return tile
        }

        fun getFinish(playerNumber: Int): Tile {
            return when (playerNumber) {
                1 -> Finish1
                2 -> Finish2
                3 -> Finish3
                4 -> Finish4
                else -> throw IllegalArgumentException("No finish for Player $playerNumber")
            }
        }

        fun getTile(shortName: String): Tile {
            return toTile[shortName] ?: throw IllegalArgumentException("Failed finding tile for'$shortName'")
        }

        fun randomTile() = values().filter { it.isTile() }.random()

    }


}