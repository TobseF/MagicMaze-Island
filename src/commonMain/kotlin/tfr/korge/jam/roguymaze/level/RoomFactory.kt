package tfr.korge.jam.roguymaze.level

import com.soywiz.klogger.Logger
import tfr.korge.jam.roguymaze.model.Room

class RoomFactory {
    val log = Logger<RoomFactory>()

    fun createRoom(tile: Int): Room {
        log.info { "Generating room $tile" }

        return when (tile) {
            0 -> createLevel0()
            1 -> createLevel1()
            2 -> createLevel2()
            3 -> createLevel3()
            4 -> createLevel4()
            5 -> createLevel5()
            6 -> createLevel6()
            7 -> createLevel7()
            8 -> createLevel8()
            9 -> createLevel9()
            10 -> createLevel10()
            11 -> createLevel11()
            12 -> createLevel12()
            else -> createDemoRoom(99)
        }
    }

    fun createDemoRoom(tile: Int): Room {

        val groundData = """
            |[W,G,G,W]
            |[G,G,G,G]
            |[L,G,G,G]
            |[W,G,W,W]
            """.trimMargin()

        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        val bordersRight = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,_,_,_]
            """.trimMargin()


        val bordersTop = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()


        val itemData = """
            |[_,D2,_,_]
            |[D1,_,_,D3]
            |[_,_,_,_]
            |[_,D4,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel0(): Room {
        val tile = 0
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,W,G,W]
            |[G,G,G,G]
            |[L,G,G,G]
            |[L,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D2,_]
            |[D1,_,_,D3]
            |[_,_,_,_]
            |[_,_,D4,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,H,_]
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,H,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,_]
            |[_,H,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel1(): Room {
        val tile = 1
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,W,G,W]
            |[G,G,G,G]
            |[L,G,G,L]
            |[L,G,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D4,_]
            |[D3,_,_,D1]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,H,H,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,H,_,_]
            |[_,H,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,H,_]
            |[_,_,H,_]
            |[_,_,H,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel2(): Room {
        val tile = 2
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,L,G,G]
            |[G,G,G,G]
            |[W,G,G,W]
            |[L,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,_,_]
            |[D2,_,_,D3]
            |[_,_,_,_]
            |[_,_,_,M2]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,H,H,H]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,H]
            |[_,H,_,_]
            |[_,H,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,H,H]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel3(): Room {
        val tile = 3
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,L,L]
            |[G,W,W,G]
            |[G,W,W,G]
            |[G,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,M1,_,_]
            |[D1,_,_,D2]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,H,H,H]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,H]
            """.trimMargin()
        val bordersLeft = """
            |[H,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[H,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[H,H,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel4(): Room {
        val tile = 4
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,G,L]
            |[G,L,G,G]
            |[L,L,G,G]
            |[L,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,_,_]
            |[D4,_,_,D1]
            |[_,_,_,_]
            |[_,E3,_,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,H,H,H]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,_,H,_]
            |[_,_,H,H]
            |[_,_,_,H]
            """.trimMargin()
        val bordersLeft = """
            |[H,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,H,_,_]
            """.trimMargin()
        val bordersTop = """
            |[H,H,H,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel5(): Room {
        val tile = 5
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,W,G,L]
            |[G,G,G,G]
            |[G,G,L,G]
            |[W,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D3,_]
            |[D4,_,_,_]
            |[E4,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,H,H,H]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,H,_,H]
            |[_,_,_,H]
            |[_,_,_,H]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,H,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel6(): Room {
        val tile = 6
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,G,L]
            |[G,W,G,G]
            |[G,G,G,G]
            |[L,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D3,_]
            |[_,_,M3,D1]
            |[_,_,_,_]
            |[_,_,D2,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,H,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[H,_,_,_]
            |[H,_,H,_]
            |[H,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[H,H,_,_]
            |[_,_,H,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel7(): Room {
        val tile = 7
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,G,G,L]
            |[G,G,G,W]
            |[L,L,G,L]
            |[W,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,E2,D4,_]
            |[D1,_,_,_]
            |[_,_,_,_]
            |[_,_,D2,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersRight = """
            |[_,H,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,H,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel8(): Room {
        val tile = 8
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,G]
            |[G,L,G,G]
            |[G,G,G,L]
            |[L,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D3,_]
            |[D2,_,_,_]
            |[_,_,_,_]
            |[_,_,D4,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,H,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel9(): Room {
        val tile = 9
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,G]
            |[L,G,G,G]
            |[G,G,G,W]
            |[W,W,G,W]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D3,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,D2,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,H,_]
            |[_,_,H,_]
            |[H,H,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,H]
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,H]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel10(): Room {
        val tile = 10
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,W]
            |[G,G,G,W]
            |[G,G,W,W]
            |[L,G,G,W]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D4,_]
            |[M4,_,_,_]
            |[_,_,_,_]
            |[_,_,D1,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[H,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[H,_,_,_]
            |[H,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel11(): Room {
        val tile = 11
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,W]
            |[G,G,G,W]
            |[W,G,G,W]
            |[W,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D4,_]
            |[D2,_,_,_]
            |[_,_,_,_]
            |[_,_,_,E1]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,H,H,_]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,H,_,_]
            |[_,H,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersLeft = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createLevel12(): Room {
        val tile = 12
        // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,G,L]
            |[G,G,W,G]
            |[G,G,W,G]
            |[G,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A8]
        val itemData = """
            |[_,_,D3,_]
            |[_,_,_,D2]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        // Borders [H]
        val bordersBottom = """
            |[_,H,_,_]
            |[H,_,_,_]
            |[_,H,_,_]
            |[H,H,H,H]
            """.trimMargin()
        val bordersRight = """
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,H]
            |[_,_,_,H]
            """.trimMargin()
        val bordersLeft = """
            |[H,_,_,_]
            |[H,_,_,_]
            |[H,_,_,_]
            |[H,_,_,_]
            """.trimMargin()
        val bordersTop = """
            |[H,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            |[_,_,_,_]
            """.trimMargin()

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersButtom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

}