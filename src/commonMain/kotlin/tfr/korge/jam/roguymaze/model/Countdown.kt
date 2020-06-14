package tfr.korge.jam.roguymaze.model

import com.soywiz.klock.PerformanceCounter


class Countdown(var start: Double = PerformanceCounter.milliseconds, val minutes: Int = 0, val seconds: Int = 0) {

    val duration = (minutes * 60 + seconds) * 1000
    val endTime = start + duration

    fun getTime(now: Double = PerformanceCounter.milliseconds): Double = (endTime - now).coerceAtLeast(0.0)
}
