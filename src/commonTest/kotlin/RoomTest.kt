import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.Room
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoomTest {

    @Test
    fun testRoomUp() {
        val room = Room(offsetX = 4, offsetY = 0)
        assertTrue(room.contains(Position(4, 0)))
        assertTrue(room.contains(Position(5, 0)))

        val room1 = Room(offsetX = 0, offsetY = 0)
        assertTrue(room1.contains(Position(3, 0)))
        assertFalse(room1.contains(Position(4, 0)))
    }

    @Test
    fun parseTest() {
        val parsed = "1;2".split(";")
        assertEquals("1", parsed[0])
        assertEquals("2", parsed[1])

        val parsed2 = "1".split(";")
        assertEquals("1", parsed[0])
    }
}