package tfr.korge.jam.roguymaze.renderer.util


class TimerFormatter {

    fun getFormattedTimeAsString(time: Double): String {
        val totalSeconds = time.toLong() / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds - (minutes * 60)
        fun twoDigits(number: Long) = if (number < 10) "0$number" else number.toString()
        return twoDigits(minutes) + ":" + twoDigits(seconds)
    }


}