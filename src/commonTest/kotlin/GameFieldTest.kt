import tfr.korge.jam.roguymaze.model.GridLayer
import tfr.korge.jam.roguymaze.model.Row
import tfr.korge.jam.roguymaze.model.Tile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GameFieldTest {

    @Test
    fun testGetField() {
        val field = GridLayer(4, 3)
        assertEquals(
                field.toString(), """
                        |[_,_,_,_]
                        |[_,_,_,_]
                        |[_,_,_,_]
                        """.trimMargin())

        field[0][1] = "A"
        field[1][1] = "B"
        field[2][1] = "C"

        assertEquals(
                field.toString(), """
                        |[_,A,_,_]
                        |[_,B,_,_]
                        |[_,C,_,_]
                        """.trimMargin())


        assertEquals(Tile.A, field.get(column = 1, row = 0))
        assertEquals(Tile.B, field.get(column = 1, row = 1))
        assertEquals(Tile.C, field.get(column = 1, row = 2))

        assertEquals(Tile.A, field[0][1])
        assertEquals(Tile.B, field[1][1])
        assertEquals(Tile.C, field[2][1])

        assertEquals(Tile.OutOfSpace, field[-1][0])
        assertEquals(Tile.OutOfSpace, field[0][-1])
    }

    @Test
    fun testGetFieldOutOfSpace() {
        val field = GridLayer(2, 2)
        assertEquals(Tile.OutOfSpace, field[-1][0])
        assertEquals(Tile.OutOfSpace, field[0][-1])
        assertEquals(Tile.OutOfSpace, field[0][2])
        assertEquals(Tile.OutOfSpace, field[2][0])
    }

    @Test
    fun testGetRowOutOfSpace() {
        val field = GridLayer(2, 2)
        assertEquals(Row.outOfSpace(), field[-1])
        assertEquals(Row.outOfSpace(), field[2])
    }


    @Test
    fun testConvertGameFieldToString() {
        val field = GridLayer(4, 3)
        assertEquals(
                field.toString(), """
                        |[_,_,_,_]
                        |[_,_,_,_]
                        |[_,_,_,_]
                        """.trimMargin())

        field[0][0] = "A"
        field[1][1] = "B"
        field[2][2] = "C"
        field[2][3] = Tile.D

        assertEquals(
                field.toString(), """
                        |[A,_,_,_]
                        |[_,B,_,_]
                        |[_,_,C,D]
                        """.trimMargin())
    }

    @Test
    fun testRotateCounterClockwise() {
        val field = GridLayer(3, 3)
        field[0][0] = "A"
        field[2][2] = Tile.B
        assertEquals(
                field.toString(), """
                        |[A,_,_]
                        |[_,_,_]
                        |[_,_,B]
                        """.trimMargin())
        field.rotate()

        assertEquals(
                """
                        |[_,_,B]
                        |[_,_,_]
                        |[A,_,_]
                        """.trimMargin(), field.toString())
    }

    @Test
    fun testRotateCounterClockwise2() {
        val field = GridLayer.fromString(
                """
                        |[1,2,3]
                        |[4,5,6]
                        |[7,8,9]
                        """.trimMargin())
        println(field)
        field.rotate()
        println(field)

        assertEquals(
                """
                        |[3,6,9]
                        |[2,5,8]
                        |[1,4,7]
                        """.trimMargin(), field.toString())
    }

    @Test
    fun testCopyField() {
        val fieldA = GridLayer.fromString(
                """
                    |[A,_]
                    |[_,_]
                    """.trimMargin())
        val fieldB = fieldA.clone()
        assertEquals(
                """
                    |[A,_]
                    |[_,_]
                    """.trimMargin(), fieldB.toString())
        fieldB[0][1] = "A"
        assertEquals(
                """
                    |[A,A]
                    |[_,_]
                    """.trimMargin(), fieldB.toString())
        assertEquals(
                """
                    |[A,_]
                    |[_,_]
                    """.trimMargin(), fieldA.toString())
    }

    @Test
    fun testReadGameFieldFromString() {
        val field = GridLayer.fromString(
                """
                        |[A,_,_,_]
                        |[_,B,_,_]
                        |[_,_,C,W]
                        """.trimMargin())

        assertEquals(
                field.toString(), """
                        |[A,_,_,_]
                        |[_,B,_,_]
                        |[_,_,C,W]
                        """.trimMargin())
    }


    @Test
    fun testGetColumn() {
        val field = GridLayer.fromString(
                """
                        |[_,A,_,_]
                        |[_,B,_,_]
                        |[_,C,_,_]
                        """.trimMargin())
        assertEquals(listOf(Tile.A, Tile.B, Tile.C), field.getColumn(1))
        assertEquals(listOf(Tile.Empty, Tile.Empty, Tile.Empty), field.getColumn(0))
    }

    @Test
    fun testGetColumnOutOfRange() {
        val field = GridLayer(1, 1)
        assertFailsWith(IllegalArgumentException::class) { field.getColumn(1) }
    }


}