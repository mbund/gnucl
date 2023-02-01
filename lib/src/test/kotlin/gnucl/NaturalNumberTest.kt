package gnucl

//import components.naturalnumber.*
import gnucl.naturalnumber.*
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class NaturalNumberCustom : NaturalNumberSecondary {
    private var x: BigInteger

    constructor() {
        this.x = 0.toBigInteger()
    }

    constructor(x: Int) {
        this.x = x.toBigInteger()
    }

    override fun multiplyBy10(k: Int) {
        this.x *= 10.toBigInteger()
        this.x += k.toBigInteger()
    }

    override fun clear() {
        this.x = 0.toBigInteger()
    }

    override fun newInstance(): NaturalNumber {
        return NaturalNumberCustom()
    }

    override fun transferFrom(source: NaturalNumber) {
        source as NaturalNumberCustom
        this.x = source.x
        source.clear()
    }

    override fun isZero(): Boolean = this.x == 0.toBigInteger()

    override fun divideBy10(): Int {
        val d = this.x.remainder(10.toBigInteger())
        this.x /= 10.toBigInteger()
        return d.toInt()
    }

}

class NaturalNumberTest {
    @Test
    fun stringify() {
        val nn: NaturalNumber = NaturalNumber1L(8124)
        assertEquals("8124", nn.toString())
    }

    @Test
    fun equals() {
        assertEquals(NaturalNumber1L(123), NaturalNumber1L(123))
        assertEquals(true, NaturalNumber1L(123).equals(NaturalNumber2(123)))
        assertEquals(true, NaturalNumber1L(123).equals(NaturalNumberCustom(123)))
        assertEquals(true, NaturalNumberCustom(123).equals(NaturalNumber1L(123)))
    }

    @Test
    fun newInstance() {
        val nn1: NaturalNumber = NaturalNumber3(8)
        val nn2: NaturalNumber = nn1.newInstance()
        nn1.add(nn2)
        assertEquals(NaturalNumber1L(8), nn1)
        assertEquals(NaturalNumber1L(0), nn2)
    }

    @Test
    fun subtract() {
        val nn1: NaturalNumberSecondary = NaturalNumber1L(8)
        val nn2: NaturalNumber = NaturalNumber1L(5)
        nn1.subtract(nn2)
        assertEquals(NaturalNumber1L(3), nn1)
        assertEquals(NaturalNumber1L(5), nn2)
    }

    @Test
    fun subtractCustomNN() {
        val nn1: NaturalNumber = NaturalNumberCustom(8)
        val nn2: NaturalNumber = NaturalNumberCustom(5)
        nn1.subtract(nn2)
        assertEquals(NaturalNumberCustom(3), nn1)
        assertEquals(NaturalNumberCustom(5), nn2)
    }

    @Test
    fun multiplyBy10() {
        val nn: NaturalNumber = NaturalNumber1L(34)
        nn.multiplyBy10(7)
        assertEquals(NaturalNumber1L(347), nn)
    }

    @Test
    fun divideBy10() {
        val nn: NaturalNumber = NaturalNumber1L(347)
        val x = nn.divideBy10()
        assertEquals(7, x)
        assertEquals(NaturalNumber1L(34), nn)
    }

    @Test
    fun divideBy10CustomNN() {
        val nn: NaturalNumber = NaturalNumberCustom(347)
        val x = nn.divideBy10()
        assertEquals(7, x)
        assertEquals(NaturalNumberCustom(34), nn)
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

//    @Test
//    fun `root 3 64 Custom NN`() {
//        val nn: NaturalNumber = NaturalNumberCustom(64)
//        nn.root(3)
//        assertEquals(NaturalNumberCustom(4), nn)
//    }

    @Test
    fun radix() {
        assertEquals(10, NaturalNumber.RADIX)
    }

    @Test
    fun copyFrom() {
        val nn1: NaturalNumber = NaturalNumber1L(7)
        val nn2: NaturalNumber = NaturalNumber1L(3)
        nn2.copyFrom(nn1)
        assertEquals(NaturalNumber1L(7), nn1)
        assertEquals(NaturalNumber1L(7), nn2)
    }

    @Test
    fun transferFrom() {
        val nn1: NaturalNumber = NaturalNumber1L(7)
        val nn2: NaturalNumber = NaturalNumber1L(3)
        nn2.transferFrom(nn1)
        assertEquals(NaturalNumber1L(), nn1)
        assertEquals(NaturalNumber1L(7), nn2)
    }

//    @Test
//    fun transferFromNNCustom() {
//        val nn1: NaturalNumber = NaturalNumberCustom(7)
//        val nn2: NaturalNumber = NaturalNumberCustom(3)
//        nn2.transferFrom(nn1)
//        assertEquals(NaturalNumberCustom(), nn1)
//        assertEquals(NaturalNumberCustom(7), nn2)
//    }
}
