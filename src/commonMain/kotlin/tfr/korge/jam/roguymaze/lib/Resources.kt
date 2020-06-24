package tfr.korge.jam.roguymaze.lib

import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.NinePatchBitmap32
import com.soywiz.korim.bitmap.readNinePatch
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs

suspend fun loadImage(fileName: String): Bitmap = resourcesVfs["images/$fileName"].readBitmap()

suspend fun loadWorldImage(fileName: String): Bitmap = resourcesVfs["images/world/$fileName"].readBitmap()

suspend fun loadFont(fileName: String): BitmapFont = resourcesVfs["fonts/$fileName"].readBitmapFont()

suspend fun loadNinePatch(fileName: String): NinePatchBitmap32 = resourcesVfs["images/$fileName"].readNinePatch()

class Resources : AsyncDependency {

    companion object {
        operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { Resources() }
        }
    }

    lateinit var fontBubble: BitmapFont
    lateinit var fontSmall: BitmapFont

    lateinit var imageButton: Bitmap
    lateinit var imageBackground: Bitmap
    lateinit var messageBox: NinePatchBitmap32

    lateinit var worldGrass: Bitmap
    lateinit var worldLand: Bitmap
    lateinit var worldWater: Bitmap

    lateinit var table: Bitmap

    lateinit var player1: Bitmap
    lateinit var player2: Bitmap
    lateinit var player3: Bitmap
    lateinit var player4: Bitmap

    lateinit var mask1: Bitmap
    lateinit var mask2: Bitmap
    lateinit var mask3: Bitmap
    lateinit var mask4: Bitmap

    lateinit var home1: Bitmap
    lateinit var home2: Bitmap
    lateinit var home3: Bitmap
    lateinit var home4: Bitmap

    lateinit var exit1: Bitmap
    lateinit var exit2: Bitmap
    lateinit var exit3: Bitmap
    lateinit var exit4: Bitmap


    lateinit var finish1: Bitmap
    lateinit var finish2: Bitmap
    lateinit var finish3: Bitmap
    lateinit var finish4: Bitmap


    lateinit var info: Bitmap

    lateinit var borderLeft: Bitmap
    lateinit var borderRight: Bitmap
    lateinit var borderTop: Bitmap
    lateinit var borderBottom: Bitmap


    lateinit var asset1: Bitmap
    lateinit var asset2: Bitmap
    lateinit var asset3: Bitmap
    lateinit var asset4: Bitmap
    lateinit var asset5: Bitmap
    lateinit var asset6: Bitmap
    lateinit var asset7: Bitmap

    lateinit var uiActionSearch: Bitmap
    lateinit var uiMapZoomIn: Bitmap
    lateinit var uiActionMoveDown: Bitmap
    lateinit var uiMapZoomOut: Bitmap
    lateinit var uiMap: Bitmap
    lateinit var uiMapMoveDown: Bitmap
    lateinit var uiTimer: Bitmap

    lateinit var uiPlayer1: Bitmap
    lateinit var uiPlayer2: Bitmap
    lateinit var uiPlayer3: Bitmap
    lateinit var uiPlayer4: Bitmap

    lateinit var uiCheckDisabled: Bitmap
    lateinit var uiCheckEnabled: Bitmap
    lateinit var uiPanelBottomLeft: Bitmap
    lateinit var uiPanelTopLeft: Bitmap
    lateinit var uiPanelTopRight: Bitmap
    lateinit var uiPanelTopCenter: Bitmap

    lateinit var buttonSettings: Bitmap
    lateinit var buttonInfo: Bitmap

    lateinit var helpPage1: Bitmap
    lateinit var helpPage2: Bitmap
    lateinit var helpPage3: Bitmap


    lateinit var uiMaskEmpty: Bitmap
    lateinit var uiMask1: Bitmap
    lateinit var uiMask2: Bitmap
    lateinit var uiMask3: Bitmap
    lateinit var uiMask4: Bitmap

    lateinit var uiHomeEmpty: Bitmap
    lateinit var uiHome1: Bitmap
    lateinit var uiHome2: Bitmap
    lateinit var uiHome3: Bitmap
    lateinit var uiHome4: Bitmap

    fun getPlayer(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> player1
            2 -> player2
            3 -> player3
            4 -> player4
            else -> throw IllegalArgumentException("Out of range: " + playerNumber)
        }
    }

    fun getUiPlayer(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> uiPlayer1
            2 -> uiPlayer2
            3 -> uiPlayer3
            4 -> uiPlayer4
            else -> throw IllegalArgumentException("Out of range: " + playerNumber)
        }
    }

    fun getUiMask(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> uiMask1
            2 -> uiMask2
            3 -> uiMask3
            4 -> uiMask4
            else -> throw IllegalArgumentException("Out of range: " + playerNumber)
        }
    }

    fun getUiHome(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> uiHome1
            2 -> uiHome2
            3 -> uiHome3
            4 -> uiHome4
            else -> throw IllegalArgumentException("Out of range: " + playerNumber)
        }
    }

    fun getFinish(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> finish1
            2 -> finish2
            3 -> finish3
            4 -> finish4
            else -> throw IllegalArgumentException("Out of range: " + playerNumber)
        }
    }

    override suspend fun init() {
        imageButton = loadImage("button.png")
        table = loadImage("table.png")

        buttonSettings = loadImage("settings.png")
        buttonInfo = loadImage("faq.png")

        helpPage1 = loadImage("help_1.png")
        helpPage2 = loadImage("help_2.png")
        helpPage3 = loadImage("help_3.png")

        player1 = loadImage("player_1.png")
        player2 = loadImage("player_2.png")
        player3 = loadImage("player_3.png")
        player4 = loadImage("player_4.png")

        mask1 = loadImage("mask_1.png")
        mask2 = loadImage("mask_2.png")
        mask3 = loadImage("mask_3.png")
        mask4 = loadImage("mask_4.png")

        home1 = loadImage("home_1.png")
        home2 = loadImage("home_2.png")
        home3 = loadImage("home_3.png")
        home4 = loadImage("home_4.png")

        uiMask1 = loadImage("ui_mask_1.png")
        uiMask2 = loadImage("ui_mask_2.png")
        uiMask3 = loadImage("ui_mask_3.png")
        uiMask4 = loadImage("ui_mask_4.png")

        uiHome1 = loadImage("ui_home_1.png")
        uiHome2 = loadImage("ui_home_2.png")
        uiHome3 = loadImage("ui_home_3.png")
        uiHome4 = loadImage("ui_home_4.png")

        exit1 = loadImage("exit_1.png")
        exit2 = loadImage("exit_2.png")
        exit3 = loadImage("exit_3.png")
        exit4 = loadImage("exit_4.png")


        uiActionSearch = loadImage("ui_action_search.png")
        uiMapZoomIn = loadImage("ui_map_zoom_in.png")
        uiActionMoveDown = loadImage("ui_action_move_down.png")
        uiMap = loadImage("ui_map.png")
        uiMapZoomOut = loadImage("ui_map_zoom_out.png")
        uiMapMoveDown = loadImage("ui_map_move_down.png")
        uiTimer = loadImage("ui_timer.png")
        uiHomeEmpty = loadImage("ui_home.png")
        uiMaskEmpty = loadImage("ui_mask.png")
        uiPlayer1 = loadImage("ui_player_1.png")
        uiPlayer2 = loadImage("ui_player_2.png")
        uiPlayer3 = loadImage("ui_player_3.png")
        uiPlayer4 = loadImage("ui_player_4.png")
        uiCheckDisabled = loadImage("ui_check.png")
        uiCheckEnabled = loadImage("ui_check_enabled.png")
        uiPanelBottomLeft = loadImage("ui_panel_bottom_left.png")
        uiPanelTopLeft = loadImage("ui_panel_top_left.png")
        uiPanelTopRight = loadImage("ui_panel_top_right.png")
        uiPanelTopCenter = loadImage("ui_panel_top_center.png")

        finish1 = loadImage("finish_1.png")
        finish2 = loadImage("finish_2.png")
        finish3 = loadImage("finish_3.png")
        finish4 = loadImage("finish_4.png")

        info = loadWorldImage("info.png")

        borderLeft = loadWorldImage("border_left.png")
        borderRight = loadWorldImage("border_right.png")
        borderTop = loadWorldImage("border_top.png")
        borderBottom = loadWorldImage("border_bottom.png")

        asset1 = loadWorldImage("asset_1.png")
        asset2 = loadWorldImage("asset_2.png")
        asset3 = loadWorldImage("asset_3.png")
        asset4 = loadWorldImage("asset_4.png")
        asset5 = loadWorldImage("asset_5.png")
        asset6 = loadWorldImage("asset_6.png")
        asset7 = loadWorldImage("asset_7.png")


        worldGrass = loadWorldImage("grass.png")
        worldLand = loadWorldImage("land.png")
        worldWater = loadWorldImage("water.png")

        fontBubble = loadFont("bubble.fnt")
        fontSmall = fontBubble

        messageBox = loadNinePatch("message_box.9.png")
    }

}