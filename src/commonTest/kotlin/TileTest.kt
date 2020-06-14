import tfr.korge.jam.roguymaze.model.Tile
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun testIsPlayer() {
        assertTrue(Tile.Door1.isDoorForPlayer(1))
        assertFalse(Tile.Door2.isDoorForPlayer(1))
        assertFalse(Tile.Door1.isDoorForPlayer(0))
        assertFalse(Tile.Door1.isDoorForPlayer(5))
    }
}