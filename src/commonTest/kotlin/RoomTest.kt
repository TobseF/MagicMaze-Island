import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.Room
import kotlin.test.Test
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
}