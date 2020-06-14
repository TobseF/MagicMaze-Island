package tfr.korge.jam.roguymaze.level

import tfr.korge.jam.roguymaze.GameFlow
import tfr.korge.jam.roguymaze.InputEvent.Action
import tfr.korge.jam.roguymaze.InputEvent.Action.*
import tfr.korge.jam.roguymaze.model.Players
import tfr.korge.jam.roguymaze.model.Room
import tfr.korge.jam.roguymaze.model.World


class WorldFactory {

    val undiscoveredRooms = mutableListOf<Room>()
    val maxRooms = 12

    /**
     * Number of players -> ActionSet
     */
    val actionSets = mutableMapOf<Int, ActionSet>()

    init {
        val set1 = ActionSet(1)
        val allEvents = setOf(
                PlayerLeft, PlayerRight, PlayerDown, PlayerUp, SelectPlayer)
        for (player in 0..5) {
            set1.getInputEvents(player).addAll(allEvents)
        }
        actionSets[0] = set1
        actionSets[1] = set1

        actionSets[2] = ActionSet(2).apply {
            addEvents(1, PlayerLeft, PlayerRight)
            addEvents(2, PlayerUp, PlayerDown, SelectPlayer)
        }

        actionSets[3] = ActionSet(3).apply {
            addEvents(1, PlayerLeft, SelectPlayer)
            addEvents(2, PlayerRight)
            addEvents(3, PlayerUp, PlayerDown)
        }

        actionSets[4] = ActionSet(5).apply {
            addEvents(1, PlayerLeft, SelectPlayer)
            addEvents(2, PlayerRight)
            addEvents(3, PlayerUp)
            addEvents(4, PlayerDown)
        }

        actionSets[5] = ActionSet(5).apply {
            addEvents(1, PlayerLeft)
            addEvents(2, PlayerRight)
            addEvents(3, PlayerUp)
            addEvents(4, PlayerDown)
            addEvents(5, SelectPlayer)
        }

    }

    fun getUndiscoveredById(nextRoomId: Int): Room? {
        val next = undiscoveredRooms.firstOrNull { it.id == nextRoomId }
        if (next != null) {
            undiscoveredRooms.remove(next)
        }
        return next
    }

    fun getUndiscoveredRoom(enterDirection: GameFlow.Direction): Room? {
        val next = undiscoveredRooms.firstOrNull { it.hasExit(enterDirection) }
        if (next != null) {
            undiscoveredRooms.remove(next)
        }
        return next
    }

    fun Players.load(players: Int, numberOfTeamMates: Int): Players {
        (1..players).forEach {
            val nextPlayer = Players.Player(it)
            val allowedSets: MutableMap<Int, MutableSet<Action>>? = actionSets[numberOfTeamMates]?.allowed
            allowedSets?.get(it)?.let { allowedSet: MutableSet<Action> ->
                nextPlayer.possibleActions.addAll(allowedSet)
            }
            this.players.add(nextPlayer)
        }
        return this
    }

    /**
     * numberOfTeamMates : 1-5 players
     */
    fun createWorld(numberOfTeamMates: Int): World {
        val players = Players().load(4, numberOfTeamMates)
        players[1].pos(1, 1)
        players[2].pos(1, 2)
        players[3].pos(2, 1)
        players[4].pos(2, 2)

        val roomFactory = RoomFactory()
        for (roomNumber in 1 until 12) {
            undiscoveredRooms += roomFactory.createRoom(roomNumber)
        }
        undiscoveredRooms.shuffle()
        val start = roomFactory.createRoom(0)
        return World(mutableListOf(start), players, maxRooms, factory = this)
    }


    class ActionSet(players: Int) {

        /**
         *  player number -> List<InputEvent>
         */
        val allowed = mutableMapOf<Int, MutableSet<Action>>()

        fun getInputEvents(playerNumber: Int): MutableSet<Action> {
            return allowed[playerNumber] ?: mutableSetOf()
        }

        fun addEvent(playerNumber: Int, event: Action) {
            val events = allowed.getOrPut(playerNumber, { mutableSetOf() })
            events.add(event)
        }

        fun addEvents(playerNumber: Int, vararg event: Action) {
            val events = allowed.getOrPut(playerNumber, { mutableSetOf() })
            events.addAll(event)
        }
    }
}