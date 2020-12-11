package tfr.korge.jam.roguymaze.lib

import com.soywiz.korge.atlas.Atlas
import com.soywiz.korge.atlas.readAtlas
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.bitmap.NinePatchBitmap32
import com.soywiz.korim.bitmap.readNinePatch
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs


//suspend fun loadImage(fileName: String): BmpSlice = resourcesVfs["images/$fileName"].readBitmap()

//suspend fun loadWorldImage(fileName: String): BmpSlice = resourcesVfs["images/world/$fileName"].readBitmap()

suspend fun loadFont(fileName: String): BitmapFont = resourcesVfs["fonts/$fileName"].readBitmapFont()

suspend fun loadNinePatch(fileName: String): NinePatchBitmap32 = resourcesVfs["images/$fileName"].readNinePatch()

class Resources : AsyncDependency {

    fun loadImage(fileName: String): BmpSlice = atlas[fileName]
    fun loadImage(fileName: String, number: Int): BmpSlice = atlas[fileName + "_" + (number + 1)]

    fun loadDialogImage(fileName: String): BmpSlice = dialogAtlas[fileName]

    companion object {
        operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { Resources() }
        }
    }

    lateinit var atlas: Atlas
    lateinit var dialogAtlas: Atlas

    lateinit var fontBubble: BitmapFont
    lateinit var fontSmall: BitmapFont

    lateinit var imageBackground: BmpSlice
    lateinit var messageBox: NinePatchBitmap32

    lateinit var worldGrass: BmpSlice
    lateinit var worldLand: BmpSlice
    lateinit var worldWater: BmpSlice

    lateinit var table: BmpSlice

    lateinit var player1: BmpSlice
    lateinit var player2: BmpSlice
    lateinit var player3: BmpSlice
    lateinit var player4: BmpSlice
    lateinit var player1_Selected: BmpSlice
    lateinit var player2_Selected: BmpSlice
    lateinit var player3_Selected: BmpSlice
    lateinit var player4_Selected: BmpSlice

    lateinit var mask1: BmpSlice
    lateinit var mask2: BmpSlice
    lateinit var mask3: BmpSlice
    lateinit var mask4: BmpSlice

    lateinit var home1: BmpSlice
    lateinit var home2: BmpSlice
    lateinit var home3: BmpSlice
    lateinit var home4: BmpSlice

    lateinit var exit1: BmpSlice
    lateinit var exit2: BmpSlice
    lateinit var exit3: BmpSlice
    lateinit var exit4: BmpSlice


    lateinit var finish1: BmpSlice
    lateinit var finish2: BmpSlice
    lateinit var finish3: BmpSlice
    lateinit var finish4: BmpSlice


    lateinit var info: BmpSlice

    lateinit var borderLeft: BmpSlice
    lateinit var borderRight: BmpSlice
    lateinit var borderTop: BmpSlice
    lateinit var borderBottom: BmpSlice

    lateinit var asset1: BmpSlice
    lateinit var asset2: BmpSlice
    lateinit var asset3: BmpSlice
    lateinit var asset4: BmpSlice
    lateinit var asset5: BmpSlice
    lateinit var asset6: BmpSlice
    lateinit var asset7: BmpSlice
    lateinit var asset8: BmpSlice
    lateinit var asset9: BmpSlice
    lateinit var asset10: BmpSlice
    lateinit var asset11: BmpSlice

    lateinit var uiActionSearch: BmpSlice
    lateinit var uiMapZoomIn: BmpSlice
    lateinit var uiActionMoveDown: BmpSlice
    lateinit var uiMapZoomOut: BmpSlice
    lateinit var uiMap: BmpSlice
    lateinit var uiMapMoveDown: BmpSlice
    lateinit var uiTimer: BmpSlice

    lateinit var uiPlayer1: BmpSlice
    lateinit var uiPlayer2: BmpSlice
    lateinit var uiPlayer3: BmpSlice
    lateinit var uiPlayer4: BmpSlice

    lateinit var uiCheckDisabled: BmpSlice
    lateinit var uiCheckEnabled: BmpSlice
    lateinit var uiPanelBottomLeft: BmpSlice
    lateinit var uiPanelTopLeft: BmpSlice
    lateinit var uiPanelTopRight: BmpSlice
    lateinit var uiPanelTopCenter: BmpSlice

    lateinit var buttonSettings: BmpSlice
    lateinit var buttonInfo: BmpSlice

    lateinit var helpPage1: BmpSlice
    lateinit var helpPage2: BmpSlice
    lateinit var helpPage3: BmpSlice


    lateinit var uiMaskEmpty: BmpSlice
    lateinit var uiMask1: BmpSlice
    lateinit var uiMask2: BmpSlice
    lateinit var uiMask3: BmpSlice
    lateinit var uiMask4: BmpSlice

    lateinit var uiHomeEmpty: BmpSlice
    lateinit var uiHome1: BmpSlice
    lateinit var uiHome2: BmpSlice
    lateinit var uiHome3: BmpSlice
    lateinit var uiHome4: BmpSlice

    fun getPlayer(playerNumber: Int): BmpSlice {
        return when (playerNumber) {
            1 -> player1
            2 -> player2
            3 -> player3
            4 -> player4
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    fun getPlayerSelected(playerNumber: Int): BmpSlice {
        return when (playerNumber) {
            1 -> player1_Selected
            2 -> player2_Selected
            3 -> player3_Selected
            4 -> player4_Selected
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    fun getUiPlayer(playerNumber: Int): BmpSlice {
        return when (playerNumber) {
            1 -> uiPlayer1
            2 -> uiPlayer2
            3 -> uiPlayer3
            4 -> uiPlayer4
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    fun getUiMask(playerNumber: Int): BmpSlice {
        return when (playerNumber) {
            1 -> uiMask1
            2 -> uiMask2
            3 -> uiMask3
            4 -> uiMask4
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    fun getUiHome(playerNumber: Int): BmpSlice {
        return when (playerNumber) {
            1 -> uiHome1
            2 -> uiHome2
            3 -> uiHome3
            4 -> uiHome4
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    fun getFinish(playerNumber: Int): BmpSlice {
        return when (playerNumber) {
            1 -> finish1
            2 -> finish2
            3 -> finish3
            4 -> finish4
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    override suspend fun init() {
        atlas = resourcesVfs["images.atlas.json"].readAtlas()
        dialogAtlas = resourcesVfs["window.atlas.json"].readAtlas()

        buttonSettings = loadImage("settings.png")
        buttonInfo = loadImage("faq.png")

        table = loadDialogImage("table.png")
        helpPage1 = loadDialogImage("help_1.png")
        helpPage2 = loadDialogImage("help_2.png")
        helpPage3 = loadDialogImage("help_3.png")

        player1 = loadImage("player_1.png")
        player2 = loadImage("player_2.png")
        player3 = loadImage("player_3.png")
        player4 = loadImage("player_4.png")

        player1_Selected = loadImage("player_1_s.png")
        player2_Selected = loadImage("player_2_s.png")
        player3_Selected = loadImage("player_3_s.png")
        player4_Selected = loadImage("player_4_s.png")

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

        info = loadImage("world_info.png")

        borderLeft = loadImage("world_border_left.png")
        borderRight = loadImage("world_border_right.png")
        borderTop = loadImage("world_border_top.png")
        borderBottom = loadImage("world_border_bottom.png")

        asset1 = loadImage("world_asset_1.png")
        asset2 = loadImage("world_asset_2.png")
        asset3 = loadImage("world_asset_3.png")
        asset4 = loadImage("world_asset_4.png")
        asset5 = loadImage("world_asset_5.png")
        asset6 = loadImage("world_asset_6.png")
        asset7 = loadImage("world_asset_7.png")
        asset8 = loadImage("world_asset_8.png")
        asset9 = loadImage("world_asset_9.png")
        asset10 = loadImage("world_asset_10.png")
        asset11 = loadImage("world_asset_11.png")

        worldGrass = loadImage("world_grass.png")
        worldLand = loadImage("world_land.png")
        worldWater = loadImage("world_water.png")

        fontBubble = loadFont("bubble.fnt")
        fontSmall = fontBubble

        messageBox = loadNinePatch("message_box.9.png")
    }

}