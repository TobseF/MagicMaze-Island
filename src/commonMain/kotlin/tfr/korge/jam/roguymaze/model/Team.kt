package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.InputEvent
import tfr.korge.jam.roguymaze.math.PositionGrid.Position
import tfr.korge.jam.roguymaze.model.Team.Hero

/**
 * A team out of 4 [Hero]s
 */
class Team(var heroes: MutableList<Hero> = mutableListOf()) {

    operator fun get(playerNumber: Int) = heroes[playerNumber - 1]

    fun isTaken(pos: Position): Boolean {
        return heroes.filter { it.pos() == pos }.any()
    }

    /**
     * One of four brave heroes.
     */
    class Hero(val number: Int,
            var x: Int = 0,
            var y: Int = 0,
            var collectedMask: Boolean = false,
            var inHome: Boolean = false) {

        val possibleActions = mutableSetOf<InputEvent.Action>()

        fun pos(x: Int, y: Int) {
            this.x = x
            this.y = y
        }

        fun pos() = Position(x, y)

        fun setPos(pos: Position) {
            x = pos.x
            y = pos.y
        }

    }
}