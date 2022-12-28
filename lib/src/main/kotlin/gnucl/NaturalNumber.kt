package gnucl.naturalnumber

import java.math.BigInteger

interface NaturalNumberKernel {
    fun multiplyBy10(k: Int)
    fun divideBy10(): Int
    fun isZero(): Boolean
    companion object {
        const val RADIX: Int = 10
    }
}

interface NaturalNumber : NaturalNumberKernel {
    fun add(n: NaturalNumber)
    fun canConvertToInt(): Boolean
    fun canSetFromString(s: String): Boolean
    fun copyFrom(n: NaturalNumber)
    fun decrement()
    fun divide(n: NaturalNumber)
    fun increment()
    fun multiply(n: NaturalNumber)
    fun power(p: Int)
    fun root(r: Int)
    fun setFromInt(i: Int)
    fun setFromString(s: String)
    fun subtract(n: NaturalNumber)
    fun toInt(): Int

    companion object {
        const val RADIX = NaturalNumberKernel.RADIX
    }
}

open class NaturalNumberSecondary : NaturalNumber {
    var v: BigInteger

    constructor() {
        v = 0.toBigInteger()
    }

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
        if (n is NaturalNumberSecondary) {
            v += n.v
            verify()
        }
    }

    override fun subtract(n: NaturalNumber) {
        if (n is NaturalNumberSecondary) {
            v -= n.v
            verify()
        }
    }

    override fun multiply(n: NaturalNumber) {
        if (n is NaturalNumberSecondary) {
            v *= n.v
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

        // type safety?????
        // if (other?.javaClass != javaClass) return false
        other as NaturalNumberSecondary

        return v == other.v
    }

    override fun hashCode(): Int {
        return v.toInt()
    }

    override fun canConvertToInt(): Boolean {
        return v <= Int.MAX_VALUE.toBigInteger()
    }

    override fun canSetFromString(s: String): Boolean {
        return s.matches(Regex("0|[1-9]\\d*"))
    }

    override fun copyFrom(n: NaturalNumber) {
        if (n is NaturalNumberSecondary) v = n.v
    }

    override fun decrement() {
        v--
        verify()
    }

    override fun divide(n: NaturalNumber) {
        if (n is NaturalNumberSecondary) {
            v /= n.v
            verify()
        }
    }

    override fun increment() {
        v++
        verify()
    }

    override fun power(p: Int) {
        v.pow(p)
    }

    // https://stackoverflow.com/a/32553759
    override fun root(r: Int) {
        if (r < 2) throw Exception("Violation of: r >= 2")

        var b = 0.toBigInteger().flipBit(1 + v.bitLength() / r)

        var a: BigInteger
        do {
            a = b
            b = ((a * ((r - 1).toBigInteger())) + (v / a.pow(r - 1))) / r.toBigInteger()
        } while (b < a)
        v = a
    }

    override fun setFromInt(i: Int) {
        v = i.toBigInteger()
    }

    override fun setFromString(s: String) {
        v = s.toBigInteger()
    }

    override fun toInt(): Int {
        return v.toInt()
    }
}

class NaturalNumber1L : NaturalNumberSecondary {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

class NaturalNumber2 : NaturalNumberSecondary {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

class NaturalNumber2a : NaturalNumberSecondary {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

class NaturalNumber3 : NaturalNumberSecondary {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

class NaturalNumber4 : NaturalNumberSecondary {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}
