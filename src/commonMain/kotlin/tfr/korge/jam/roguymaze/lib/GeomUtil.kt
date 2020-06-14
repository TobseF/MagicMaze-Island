package tfr.korge.jam.roguymaze.lib

import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korma.geom.*

fun Bitmap.centered(point: Point): Point = point.copy().sub(this.center())
fun Point.moveUp(marginTop: Number): Point = Point(this.x, this.y - marginTop.toDouble())
fun Point.moveLeft(marginTop: Number): Point = Point(this.x - marginTop.toDouble(), this.y)
fun Point.moveRight(marginTop: Number): Point = Point(this.x + marginTop.toDouble(), this.y)
fun Point.moveDown(marginTop: Number): Point = Point(this.x, this.y + marginTop.toDouble())
fun Bitmap.center(): IPoint = this.bounds().center()
fun Bitmap.bounds() = Rectangle(0, 0, this.width, this.height)
fun Rectangle.center() = Point(this.width / 2, this.height / 2)
fun Size.half() = Size(Point(this.p.div(2)))