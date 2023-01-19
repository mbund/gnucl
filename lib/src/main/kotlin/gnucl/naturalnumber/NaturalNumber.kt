package gnucl.naturalnumber

import Standard
import java.math.BigInteger

interface NaturalNumberKernel : Standard<NaturalNumber> {
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

sealed class NaturalNumberImpl : NaturalNumberSecondary {
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
        n as NaturalNumberImpl
        v += n.v
        verify()
    }

    override fun subtract(n: NaturalNumber) {
        n as NaturalNumberImpl
        v -= n.v
        verify()
    }

    override fun multiply(n: NaturalNumber) {
        n as NaturalNumberImpl
        v *= n.v
        verify()
    }

    override fun multiplyBy10(k: Int) {
        v *= 10.toBigInteger()
        v += k.toBigInteger()
        verify()
    }

    override fun divideBy10(): Int {
        val d = v.remainder(10.toBigInteger())
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
        other as NaturalNumberImpl

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
        n as NaturalNumberImpl
        v = n.v
    }

    override fun decrement() {
        v--
        verify()
    }

    override fun divide(n: NaturalNumber) {
        n as NaturalNumberImpl
        v /= n.v
        verify()
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

    override fun clear() {
        v = 0.toBigInteger()
    }

    override fun newInstance(): NaturalNumber {
        return NaturalNumber1L()
    }

    override fun transferFrom(source: NaturalNumber) {
        source as NaturalNumberImpl
        v = source.v
        source.clear()
    }
}

abstract class NaturalNumberSecondary : NaturalNumber {
    private fun toNaturalNumberImpl(nn: NaturalNumber): NaturalNumberImpl {
        var xs = ""
        while (!nn.isZero()) xs += nn.divideBy10()
        xs.forEach { x -> nn.multiplyBy10(x.digitToInt()) }

        return NaturalNumber1L(xs.toList().reversed().toString())
    }

    private fun fromNaturalNumberImpl(nn: NaturalNumberImpl) {
        this.transferFrom(nn)
    }

    override fun add(n: NaturalNumber) {
        val nn = toNaturalNumberImpl(this)
        nn.add(n)
        fromNaturalNumberImpl(nn)
    }

    override fun canConvertToInt(): Boolean {
        return toNaturalNumberImpl(this).canConvertToInt()
    }

    override fun canSetFromString(s: String): Boolean {
        return toNaturalNumberImpl(this).canSetFromString(s)
    }

    override fun copyFrom(n: NaturalNumber) {
        fromNaturalNumberImpl(toNaturalNumberImpl(n))
    }

    override fun decrement() {
        val nn = toNaturalNumberImpl(this)
        nn.decrement()
        fromNaturalNumberImpl(nn)
    }

    override fun divide(n: NaturalNumber) {
        val nn = toNaturalNumberImpl(this)
        nn.divide(n)
        fromNaturalNumberImpl(nn)
    }

    override fun increment() {
        val nn = toNaturalNumberImpl(this)
        nn.increment()
        fromNaturalNumberImpl(nn)
    }

    override fun multiply(n: NaturalNumber) {
        val nn = toNaturalNumberImpl(this)
        nn.multiply(n)
        fromNaturalNumberImpl(nn)
    }

    override fun power(p: Int) {
        val nn = toNaturalNumberImpl(this)
        nn.power(p)
        fromNaturalNumberImpl(nn)
    }

    override fun root(r: Int) {
        val nn = toNaturalNumberImpl(this)
        nn.root(r)
        fromNaturalNumberImpl(nn)
    }

    override fun setFromInt(i: Int) {
        val nn = toNaturalNumberImpl(this)
        nn.setFromInt(i)
        fromNaturalNumberImpl(nn)
    }

    override fun setFromString(s: String) {
        val nn = toNaturalNumberImpl(this)
        nn.setFromString(s)
        fromNaturalNumberImpl(nn)
    }

    override fun subtract(n: NaturalNumber) {
        val nn = toNaturalNumberImpl(this)
        nn.subtract(n)
        fromNaturalNumberImpl(nn)
    }

    override fun toInt(): Int {
        return toNaturalNumberImpl(this).toInt()
    }
}

@Suppress("unused")
class NaturalNumber1L : NaturalNumberImpl {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

@Suppress("unused")
class NaturalNumber2 : NaturalNumberImpl {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

@Suppress("unused")
class NaturalNumber2a : NaturalNumberImpl {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

@Suppress("unused")
class NaturalNumber3 : NaturalNumberImpl {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}

@Suppress("unused")
class NaturalNumber4 : NaturalNumberImpl {
    constructor() : super()
    constructor(x: Int) : super(x)
    constructor(x: String) : super(x)
}