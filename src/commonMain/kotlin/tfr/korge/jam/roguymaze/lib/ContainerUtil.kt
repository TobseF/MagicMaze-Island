package tfr.korge.jam.roguymaze.lib

import com.soywiz.korge.view.*
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Size

fun Container.textCentered(text: String, textSize: Double = 16.0, font: BitmapFont, center: Point) {
    this.text(text, font = font, textSize = textSize) {
        val textBounds = this.getLocalBounds()
        position(center.x - (textBounds.width / 2), center.y - (textBounds.height / 2))
    }
}

fun Container.ninePatch(
    ninePatch: com.soywiz.korim.bitmap.NinePatchBitmap32, size: Size, callback: (NinePatchEx.() -> Unit) = {}
) {
    this.ninePatch(ninePatch = ninePatch, width = size.width, height = size.height, callback = callback)
}