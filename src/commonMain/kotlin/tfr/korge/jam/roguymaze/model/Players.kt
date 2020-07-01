package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.InputEvent
import tfr.korge.jam.roguymaze.math.PositionGrid.Position

class Players(var players: MutableList<Player> = mutableListOf()) {

    operator fun get(playerNumber: Int) = players[playerNumber - 1]

    fun isTaken(pos: Position): Boolean {
        return players.filter { it.pos() == pos }.any()
    }

    class Player(val number: Int,
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