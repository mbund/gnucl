package gnucl

// import components.naturalnumber.*
import gnucl.naturalnumber.*
import kotlin.test.Test
import kotlin.test.assertEquals

class NaturalNumberTest {
    @Test
    fun `add`() {
        val nn1: NaturalNumber = NaturalNumber1L(8)
        val nn2: NaturalNumber = NaturalNumber2(5)
        nn1.add(nn2)
        assertEquals(NaturalNumber1L(13), nn1)
        assertEquals(NaturalNumber1L(5), nn2)
    }

    @Test
    fun `subtract`() {
        val nn1: NaturalNumber = NaturalNumber1L(8)
        val nn2: NaturalNumber = NaturalNumber1L(5)
        nn1.subtract(nn2)
        assertEquals(NaturalNumber1L(3), nn1)
        assertEquals(NaturalNumber1L(5), nn2)
    }

    @Test
    fun `multiplyBy10`() {
        val nn: NaturalNumber = NaturalNumber1L(34)
        nn.multiplyBy10(7)
        assertEquals(NaturalNumber1L(347), nn)
    }

    @Test
    fun `divideBy10`() {
        val nn: NaturalNumber = NaturalNumber1L(347)
        var x = nn.divideBy10()
        assertEquals(7, x)
        assertEquals(NaturalNumber1L(34), nn)
    }

    @Test
    fun `root 2 64`() {
        val nn: NaturalNumber = NaturalNumber1L(64)
        nn.root(2)
        assertEquals(NaturalNumber1L(8), nn)
    }

    @Test
    fun `root 3 64`() {
        val nn: NaturalNumber = NaturalNumber1L(64)
        nn.root(3)
        assertEquals(NaturalNumber1L(4), nn)
    }

    @Test
    fun `radix`() {
        assertEquals(10, NaturalNumber.RADIX)
    }

    @Test
    fun `copyFrom`() {
        val nn1: NaturalNumber = NaturalNumber1L(7)
        val nn2: NaturalNumber = NaturalNumber1L()
        nn2.copyFrom(nn1)
        assertEquals(NaturalNumber1L(7), nn1)
        assertEquals(NaturalNumber1L(7), nn2)
    }
}
