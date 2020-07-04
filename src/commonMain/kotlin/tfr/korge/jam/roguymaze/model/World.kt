package tfr.korge.jam.roguymaze.model

import tfr.korge.jam.roguymaze.level.WorldFactory
import tfr.korge.jam.roguymaze.math.PositionGrid.Position


class World(val rooms: MutableList<Room>, val team: Team, val totalRooms: Int, val factory: WorldFactory) {

    var roomName = "A"

    /**
     *  Selected hero (1-4).
     */
    var selectedHero = 1

    /**
     * Player out of [playersCount] (Not the hero). Starting with 1.
     */
    var selectedPlayer = 1

    /**
     * Total numbers of players
     */
    var playersCount = 1

    fun getPlayer(playerNumber: Int) = team[playerNumber]

    fun getGroundTileCell(pos: Position): TileCell {
        return TileCell(getGroundTileAbsolute(pos), pos)
    }

    fun isTakenByPlayer(pos: Position): Boolean {
        return team.isTaken(pos)
    }

    fun getGroundTileAbsolute(pos: Position): Tile {
        return getRoom(pos)?.getGroundTileAbsolute(pos) ?: Tile.OutOfSpace
    }

    fun getGroundTileCellAbsolute(pos: Position): TileCell {
        return getRoom(pos)?.getGroundTileCellAbsolute(pos) ?: TileCell(Tile.OutOfSpace, pos)
    }

    fun getRoom(pos: Position): Room? {
        return rooms.find { it.contains(pos) }
    }

    fun getItemTileRelative(pos: Position): Tile {
        return getRoom(pos)?.getItemTileRelative(pos) ?: Tile.OutOfSpace
    }

    fun getItemTileAbsolute(pos: Position): Tile {
        return getRoom(pos)?.getItemTileAbsolute(pos) ?: Tile.OutOfSpace
    }

    fun getItemTileCellAbsolute(pos: Position): TileCell {
        return getRoom(pos)?.getItemTileCellAbsolute(pos) ?: TileCell(Tile.OutOfSpace, pos)
    }

    fun getActionSet() = factory.getActionSet(this.playersCount)
    fun getAllowedActions() = getActionSet().getAllowedActions(this.selectedPlayer)

}