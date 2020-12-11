package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korim.bitmap.BmpSlice
import tfr.korge.jam.roguymaze.lib.Resources
import tfr.korge.jam.roguymaze.lib.SpriteBatch
import tfr.korge.jam.roguymaze.model.Tile

/**
 * A [SpriteBatch] with images for all [Tile]s.
 */
open class WorldSprites(val resources: Resources? = null) {

    open fun getTile(tile: Tile): BmpSlice? {
        resources?.apply {
            return when (tile) {
                Tile.Land -> worldLand
                Tile.Grass -> worldGrass
                Tile.Water -> worldWater
                Tile.Player1 -> player1
                Tile.Player2 -> player2
                Tile.Player3 -> player3
                Tile.Player4 -> player4
                Tile.Mask1 -> mask1
                Tile.Mask2 -> mask2
                Tile.Mask3 -> mask3
                Tile.Mask4 -> mask4
                Tile.Home1 -> home1
                Tile.Home2 -> home2
                Tile.Home3 -> home3
                Tile.Home4 -> home4
                Tile.Door1 -> exit1
                Tile.Door2 -> exit2
                Tile.Door3 -> exit3
                Tile.Door4 -> exit4
                Tile.A1 -> asset1
                Tile.A2 -> asset2
                Tile.A3 -> asset3
                Tile.A4 -> asset4
                Tile.A5 -> asset5
                Tile.A6 -> asset6
                Tile.A7 -> asset7
                Tile.A8 -> asset8
                Tile.A9 -> asset9
                Tile.A10 -> asset10
                Tile.A11 -> asset11
                Tile.Finish1 -> finish1
                Tile.Finish2 -> finish2
                Tile.Finish3 -> finish3
                Tile.Finish4 -> finish4
                Tile.Info -> info
                else -> null
            }
        }
        return null
    }

}