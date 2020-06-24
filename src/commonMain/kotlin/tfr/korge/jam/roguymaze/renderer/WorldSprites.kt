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

        if (resources != null) {
            if (tile == Tile.Land) {
                return resources.worldLand
            }
            if (tile == Tile.Grass) {
                return resources.worldGrass
            }
            if (tile == Tile.Water) {
                return resources.worldWater
            }
            if (tile == Tile.Player1) {
                return resources.player1
            }
            if (tile == Tile.Player2) {
                return resources.player2
            }
            if (tile == Tile.Player3) {
                return resources.player3
            }
            if (tile == Tile.Player4) {
                return resources.player4
            }

            if (tile == Tile.Mask1) {
                return resources.mask1
            }
            if (tile == Tile.Mask2) {
                return resources.mask2
            }
            if (tile == Tile.Mask3) {
                return resources.mask3
            }
            if (tile == Tile.Mask4) {
                return resources.mask4
            }

            if (tile == Tile.Home1) {
                return resources.home1
            }
            if (tile == Tile.Home2) {
                return resources.home2
            }
            if (tile == Tile.Home3) {
                return resources.home3
            }
            if (tile == Tile.Home4) {
                return resources.home4
            }

            if (tile == Tile.Door1) {
                return resources.exit1
            }
            if (tile == Tile.Door2) {
                return resources.exit2
            }
            if (tile == Tile.Door3) {
                return resources.exit3
            }
            if (tile == Tile.Door4) {
                return resources.exit4
            }

            if (tile == Tile.A1) {
                return resources.asset1
            }
            if (tile == Tile.A2) {
                return resources.asset2
            }
            if (tile == Tile.A2) {
                return resources.asset2
            }
            if (tile == Tile.A3) {
                return resources.asset3
            }
            if (tile == Tile.A4) {
                return resources.asset4
            }
            if (tile == Tile.A5) {
                return resources.asset5
            }
            if (tile == Tile.A6) {
                return resources.asset6
            }
            if (tile == Tile.A7) {
                return resources.asset7
            }
            if (tile == Tile.Finish1) {
                return resources.finish1
            }
            if (tile == Tile.Finish2) {
                return resources.finish2
            }
            if (tile == Tile.Finish3) {
                return resources.finish3
            }
            if (tile == Tile.Finish4) {
                return resources.finish4
            }


            if (tile == Tile.Info) {
                return resources.info
            }

        }


        return null
    }



}