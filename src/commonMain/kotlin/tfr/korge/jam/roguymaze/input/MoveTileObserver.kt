package tfr.korge.jam.roguymaze.input

import com.soywiz.korev.MouseButton
import com.soywiz.korev.MouseEvent
import com.soywiz.korev.TouchEvent
import com.soywiz.korge.component.MouseComponent
import com.soywiz.korge.component.TouchComponent
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.Views
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import tfr.korge.jam.roguymaze.DragTileEvent
import tfr.korge.jam.roguymaze.input.DragListener.DragEvent
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.renderer.WorldComponent

class MoveTileObserver(override val view: Stage,
        val bus: EventBus,
        private val world: WorldComponent) : DragListener.DragEventListener, TouchComponent, MouseComponent {

    companion object {
        suspend operator fun invoke(injector: AsyncInjector): MoveTileObserver {
            injector.run {
                return MoveTileObserver(get(), get(), get())
            }
        }
    }

    private val dragListener = DragListener(view, Room.size, this)

    override fun onTouchEvent(views: Views, e: TouchEvent) {
        dragListener.onTouchEvent(views, e)
    }

    override fun onMouseEvent(views: Views, event: MouseEvent) {
        if (event.type == MouseEvent.Type.SCROLL) {
            //Why do I get no scroll event?
        }
        dragListener.onMouseEvent(views, event)
        if (event.button == MouseButton.RIGHT && event.type == MouseEvent.Type.DOWN) {
            val point = event.point()
            //            println("field: ${world.getField(point)}(${point.x},${point.y})")
        }
    }

    fun MouseEvent.point() = project(Point(this.x, y))

    fun project(point: Point): Point {
        return view.globalToLocalXY(point.x, point.y, point)
    }

    override fun onDragEvent(dragEvent: DragEvent) {
        val start: Position = world.getField(dragEvent.start)
        val end: Position = world.getField(dragEvent.end)
        val distance = start.distance(end)

        if (distance == 1.0 && world.isOnGrid(start) && world.isOnGrid(end)) {
            bus.send(DragTileEvent(start, end))
        }
    }

}