import tfr.korge.jam.roguymaze.model.Countdown
import tfr.korge.jam.roguymaze.renderer.util.TimerFormatter
import kotlin.test.Test
import kotlin.test.assertEquals

class CountdownTest {

    @Test
    fun testRoomUp() {
        val countdown = Countdown(start = 0.0, minutes = 1, seconds = 30)
        val time = countdown.getTime(1000.0) / 10
        assertEquals(8900.0, time)
        val formatter = TimerFormatter()
        val formattedTimeAsString = formatter.getFormattedTimeAsString(time)
        assertEquals("00:01", formatter.getFormattedTimeAsString(1000.0))
        assertEquals("00:08", formattedTimeAsString)
    }
}
