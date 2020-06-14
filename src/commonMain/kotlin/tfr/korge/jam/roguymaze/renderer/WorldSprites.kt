package tfr.korge.jam.roguymaze.renderer

import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.bitmap.sliceWithBounds
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
                return resources.worldLand.toBmpSlice()
            }
            if (tile == Tile.Grass) {
                return resources.worldGrass.toBmpSlice()
            }
            if (tile == Tile.Water) {
                return resources.worldWater.toBmpSlice()
            }
            if (tile == Tile.Player1) {
                return resources.player1.toBmpSlice()
            }
            if (tile == Tile.Player2) {
                return resources.player2.toBmpSlice()
            }
            if (tile == Tile.Player3) {
                return resources.player3.toBmpSlice()
            }
            if (tile == Tile.Player4) {
                return resources.player4.toBmpSlice()
            }

            if (tile == Tile.Mask1) {
                return resources.mask1.toBmpSlice()
            }
            if (tile == Tile.Mask2) {
                return resources.mask2.toBmpSlice()
            }
            if (tile == Tile.Mask3) {
                return resources.mask3.toBmpSlice()
            }
            if (tile == Tile.Mask4) {
                return resources.mask4.toBmpSlice()
            }

            if (tile == Tile.Home1) {
                return resources.home1.toBmpSlice()
            }
            if (tile == Tile.Home2) {
                return resources.home2.toBmpSlice()
            }
            if (tile == Tile.Home3) {
                return resources.home3.toBmpSlice()
            }
            if (tile == Tile.Home4) {
                return resources.home4.toBmpSlice()
            }

            if (tile == Tile.Door1) {
                return resources.exit1.toBmpSlice()
            }
            if (tile == Tile.Door2) {
                return resources.exit2.toBmpSlice()
            }
            if (tile == Tile.Door3) {
                return resources.exit3.toBmpSlice()
            }
            if (tile == Tile.Door4) {
                return resources.exit4.toBmpSlice()
            }

            if (tile == Tile.A1) {
                return resources.asset1.toBmpSlice()
            }
            if (tile == Tile.A2) {
                return resources.asset2.toBmpSlice()
            }
            if (tile == Tile.A2) {
                return resources.asset2.toBmpSlice()
            }
            if (tile == Tile.A3) {
                return resources.asset3.toBmpSlice()
            }
            if (tile == Tile.A4) {
                return resources.asset4.toBmpSlice()
            }
            if (tile == Tile.A5) {
                return resources.asset5.toBmpSlice()
            }
            if (tile == Tile.A6) {
                return resources.asset6.toBmpSlice()
            }
            if (tile == Tile.A7) {
                return resources.asset7.toBmpSlice()
            }
            if (tile == Tile.Finish1) {
                return resources.finish1.toBmpSlice()
            }
            if (tile == Tile.Finish2) {
                return resources.finish2.toBmpSlice()
            }
            if (tile == Tile.Finish3) {
                return resources.finish3.toBmpSlice()
            }
            if (tile == Tile.Finish4) {
                return resources.finish4.toBmpSlice()
            }


            if (tile == Tile.Info) {
                return resources.info.toBmpSlice()
            }

        }


        return null
    }

    fun Bitmap.toBmpSlice() = this.sliceWithBounds(0, 0, this.width, this.height)


}