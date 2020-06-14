package tfr.korge.jam.roguymaze.model.network

data class UpdateEvent(val update: Update)

data class Update(val players: List<Player>) {

    companion object {
        val action = "update"

        fun read(string: String): Update {
            return readCommandMessage(string.substringAfter("$action/"))
        }

        fun readCommandMessage(string: String): Update {
            val players: List<Player> = string.split(";").map { it ->
                val values = it.split(",").map { it.toInt() }
                Player(values[0], Pos(values[1], values[2]))
            }
            return Update(players)
        }
    }

    data class Player(val number: Int, val pos: Pos)

    data class Pos(val x: Int, val y: Int)

    fun write(): String {
        return action + "/" + playerUpdate()
    }

    fun playerUpdate() = players.map { "${it.number},${it.pos.x},${it.pos.y}" }.joinToString(";")

    val action = Update.action
}