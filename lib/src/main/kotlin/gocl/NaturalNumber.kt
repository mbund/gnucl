import java.math.BigInteger

interface NaturalNumberKernel {
    fun multiplyBy10(k: Int)
    fun divideBy10(): Int
    fun isZero(): Boolean
}

interface NaturalNumber : NaturalNumberKernel {
    fun add(n: NaturalNumber)
    fun subtract(n: NaturalNumber)
}

class NaturalNumber1L : NaturalNumber {
    var v: BigInteger

    constructor(x: Int) {
        v = x.toBigInteger()
        verify()
    }

    constructor(x: String) {
        v = x.toBigInteger()
        verify()
    }

    private fun verify() {
        if (v < 0.toBigInteger()) {
            throw Exception("NaturalNumber must be greater than zero")
        }
    }

    override fun add(n: NaturalNumber) {
        if (n is NaturalNumber1L) {
            v += n.v
            verify()
        }
    }

    override fun subtract(n: NaturalNumber) {
        if (n is NaturalNumber1L) {
            v -= n.v
            verify()
        }
    }

    override fun multiplyBy10(k: Int) {
        v *= 10.toBigInteger()
        v += k.toBigInteger()
        verify()
    }

    override fun divideBy10(): Int {
        var d = v.remainder(10.toBigInteger())
        v /= 10.toBigInteger()
        verify()
        return d.toInt()
    }

    override fun isZero(): Boolean {
        return v == 0.toBigInteger()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as NaturalNumber1L

        return v == other.v
    }

    override fun hashCode(): Int {
        return v.toInt()
    }
}

typealias NaturalNumber2 = NaturalNumber1L

typealias NaturalNumber2a = NaturalNumber1L

typealias NaturalNumber3 = NaturalNumber1L

typealias NaturalNumber4 = NaturalNumber1L
