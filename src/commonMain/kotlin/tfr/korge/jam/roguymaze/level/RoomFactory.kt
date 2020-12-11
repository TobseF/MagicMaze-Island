package tfr.korge.jam.roguymaze.level

import com.soywiz.klogger.Logger
import tfr.korge.jam.roguymaze.model.Room

class RoomFactory {
    val log = Logger<RoomFactory>()

    fun createRoom(tile: Int): Room {
        log.info { "Generating room $tile" }

        return when (tile) {
            0 -> createRoom0()
            1 -> createRoom1()
            2 -> createRoom2()
            3 -> createRoom3()
            4 -> createRoom4()
            5 -> createRoom5()
            6 -> createRoom6()
            7 -> createRoom7()
            8 -> createRoom8()
            9 -> createRoom9()
            10 -> createRoom10()
            11 -> createRoom11()
            12 -> createRoom12()
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom0(): Room {
        val tile = 0 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,W,G,W]
            |[G,G,G,G]
            |[L,G,G,G]
            |[L,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,_,D2,_]
            |[D1,_,_,D3]
            |[_,_,_,_]
            |[A3,_,D4,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom1(): Room {
        val tile = 1 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,W,G,W]
            |[G,G,G,G]
            |[L,G,G,L]
            |[L,G,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,_,D4,_]
            |[D3,_,_,D1]
            |[A1,_,_,_]
            |[_,_,_,A2]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom2(): Room {
        val tile = 2 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,L,G,G]
            |[G,G,G,G]
            |[W,G,G,W]
            |[L,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,A1,_,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom3(): Room {
        val tile = 3 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,L,L]
            |[G,W,W,G]
            |[G,W,W,G]
            |[G,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,M1,A2,A4]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom4(): Room {
        val tile = 4 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,G,L]
            |[G,L,G,G]
            |[L,L,G,G]
            |[L,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,_,_,_]
            |[D4,A7,_,D1]
            |[A8,A3,_,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom5(): Room {
        val tile = 5 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[W,W,G,L]
            |[G,G,G,G]
            |[G,G,L,G]
            |[W,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,_,D3,_]
            |[D4,_,_,_]
            |[E4,_,A9,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom6(): Room {
        val tile = 6 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,G,L]
            |[G,W,G,G]
            |[G,G,G,G]
            |[L,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,_,D3,_]
            |[_,_,M3,D1]
            |[_,_,_,_]
            |[_,A10,D2,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom7(): Room {
        val tile = 7 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,G,G,L]
            |[G,G,G,W]
            |[L,L,G,L]
            |[W,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,E2,D4,_]
            |[D1,_,_,_]
            |[_,A6,_,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom8(): Room {
        val tile = 8 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,G]
            |[G,L,G,G]
            |[G,G,G,L]
            |[L,L,G,L]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[A7,_,D3,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom9(): Room {
        val tile = 9 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,G]
            |[L,G,G,G]
            |[G,G,G,W]
            |[W,W,G,W]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[A5,_,D3,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom10(): Room {
        val tile = 10 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,W]
            |[G,G,G,W]
            |[G,G,W,W]
            |[L,G,G,W]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,A5,D4,_]
            |[M4,_,_,_]
            |[_,_,_,_]
            |[A6,_,D1,_]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom11(): Room {
        val tile = 11 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[L,L,G,W]
            |[G,G,G,W]
            |[W,G,G,W]
            |[W,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
            .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

    fun createRoom12(): Room {
        val tile = 12 // [W]ater, [G]rass, [L]and
        val groundData = """
            |[G,G,G,L]
            |[G,G,W,G]
            |[G,G,W,G]
            |[G,G,G,G]
            """.trimMargin()

        // Exit-Info [I], Exit-Doors [E1-E4], Mask1 [M1-4], Asset [A1-A11]
        val itemData = """
            |[_,_,D3,A11]
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

        return Room(tile).loadGround(groundData).loadItems(itemData).loadBordersBottom(bordersBottom)
                .loadBordersRight(bordersRight).loadBordersLeft(bordersLeft).loadBordersTop(bordersTop)
    }

}