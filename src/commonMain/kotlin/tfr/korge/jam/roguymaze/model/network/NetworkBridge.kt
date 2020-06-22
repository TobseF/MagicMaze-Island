package tfr.korge.jam.roguymaze.model.network

import com.soywiz.klogger.Logger
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.async.launch
import com.soywiz.korio.net.ws.WebSocketClient
import com.soywiz.korio.net.ws.readString
import com.soywiz.korio.util.OS
import kotlinx.coroutines.CoroutineScope
import tfr.korge.jam.roguymaze.*
import tfr.korge.jam.roguymaze.InputEvent.Action
import tfr.korge.jam.roguymaze.lib.EventBus
import tfr.korge.jam.roguymaze.model.World
import tfr.korge.jam.roguymaze.renderer.animation.TileAnimator

class NetworkBridge(val bus: EventBus,
        val scope: CoroutineScope,
        val world: World,
        var userName: String = "unknown") : AsyncDependency {


    val dummyHost = "ws://echo.websocket.org"
    val localHost = "localhost"
    val publicHost = "clean-code.rocks"
    var host =  publicHost



    val allowedEvents = setOf(
            Action.PlayerLeft, Action.PlayerRight, Action.PlayerUp, Action.PlayerDown, Action.FoundNextRoom)

    var roomName = "A"

    /**
     * 1-5
     */
    var playerId = 1

    /**
     * 1-5
     */
    var playersCount = 1

    var socket: WebSocketClient? = null

    val log = Logger<NetworkBridge>()

    init {
        bus.register<Update> { handleUpdate(it) }
        bus.register<ChangeRoomEvent> {
            roomName = it.roomName
            log.info { "Changed room to $roomName" }
        }
        bus.register<ChangePlayerEvent> {
            playerId = it.playerId
            log.info { "Changed player Id to $playerId" }
        }
        bus.register<ChangePlayersCountEvent> {
            playersCount = it.playersCount
            log.info { "Changed player count to $playersCount" }
        }
        bus.register<InputEvent> {
            if (!it.isNetworkEvent) {
                handleInputEvent(it)
            }
        }
    }

    fun userId(): String {
        return "$playersCount-$playerId"
    }

    private suspend fun handleInputEvent(event: InputEvent) {
        if (allowedEvents.contains(event.action)) {
            log.debug { "handleInputEvent$event" }

            broadcastCommand("InputEvent", roomName, userId(), event.toDataString())
        }
    }

    fun InputEvent.toDataString(): String {
        var data = "${this.action}>${this.playerNumber}"
        if (this.roomId != null) {
            data += ";" + this.roomId
        }
        return data
    }


    suspend fun broadcastCommand(command: String, room: String, sender: String, message: String) {
        broadcast("@$room#$sender->/$command:$message")
    }

    suspend fun broadcast(messageData: String) {
        log.debug { messageData }
        socket?.send(messageData)
    }

    suspend fun handleUpdate(update: Update) {
        log.debug { "Handle update$update" }
        broadcastCommand(update.action, roomName, userName, update.playerUpdate())
    }

    companion object {
        suspend operator fun invoke(injector: AsyncInjector): NetworkBridge {
            injector.mapSingleton {
                NetworkBridge(get(), get(), get())
            }
            return injector.get()
        }
    }

    suspend fun openSocket() {
        val port = "8080"
        val url = "ws://$host:$port/ws"
        log.info { "Creating WebSocketClient: $url" }
        socket = WebSocketClient(url, debug = false)
        socket?.let {
            configureSocket(it)
        }
    }

    suspend fun configureSocket(socket: WebSocketClient) {
        socket.onStringMessage.add {
            scope.launch {
                handleData(it)
            }
        }
        socket.onError.add { println("failed" + it) }

        socket.send("New Client!")
        println("socket:" + socket.readString())

        socket.onOpen.add {
            log.info { "connected" }
            scope.launch {
                socket.send("hallo 42")
            }

        }
    }

    fun nameId() = "$playersCount-$playerId"

    suspend fun handleData(networkData: String) {
        log.debug { "handleData: $networkData" }

        if (networkData.startsWith("@")) {

            //"@$room#$sender->/$command:$message"
            val room = networkData.substringAfter("@").substringBefore("#").trim()
            val sender = networkData.substringAfter("#").substringBefore("->").trim()
            val action = networkData.substringAfter("->/").substringBefore(":").trim()
            val message = networkData.substringAfter(":")

            log.debug { "parsed:room=$room,sender=$sender,action=$action,message=$message" }

            if (sender == nameId()) {
                log.debug { "Ignoring own command $networkData" }
            } else if (action == Update.action) {
                executePlayerUpdate(message)
            } else if (action == "InputEvent") {
                executeInputEvent(message)
            } else {
                log.info { "Unknown action: $action" }
            }
        }


    }

    fun executeInputEvent(message: String) {
        val eventAction = message.substringBefore(">")
        val eventData = message.substringAfter(">")
        val eventDataValues = eventData.split(";")
        var playerNumber: Int? = null
        var roomNumber: Int? = null
        if (eventDataValues.isNotEmpty()) {
            playerNumber = eventDataValues[0].toIntOrNull()
        }
        if (eventDataValues.size >= 2) {
            roomNumber = eventDataValues[1].toIntOrNull()
        }

        val action = Action.parseValue(eventAction)

        if (allowedEvents.contains(action) && playerNumber != null) {
            bus.send(InputEvent(action, playerNumber, roomId = roomNumber, isNetworkEvent = true))
        } else {
            log.info { "Ignoring event: $action ($playerNumber)" }
        }

    }

    fun executePlayerUpdate(updateCommand: String) {
        val update = Update.readCommandMessage(updateCommand)
        log.debug { "Parsed Update: $update" }
        update.players.forEach { player ->
            /*world.getPlayer(player.number)?.let { playerPos ->
                val start = playerPos.position
                val target = Position(player.pos.x, player.pos.y)
                log.debug { "Execute Update: $start-$target" }
                mechanics.swapTiles(start, target)
                tileAnimator.animateSwap(start, target)
            }*/


        }
    }

    override suspend fun init() {
        if (OS.isJsBrowserOrWorker) {
            scope.launch {
                try {
                    openSocket()
                } catch (e: Exception) {
                    log.warn { "Failed creating web Socket" }
                }
            }
        } else {
            log.info { "No network support on Windows " }
        }

    }


}