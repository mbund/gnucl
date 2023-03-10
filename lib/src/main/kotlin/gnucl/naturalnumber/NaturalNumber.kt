package gnucl.naturalnumber

import Standard
import java.math.BigInteger

interface NaturalNumberKernel : Standard<NaturalNumber> {
    fun divideBy10(): Int
    fun isZero(): Boolean
    fun multiplyBy10(k: Int)

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
    internal var v: BigInteger
    
    constructor() {
        this.v = 0.toBigInteger()
    }

    constructor(x: Int) {
        this.v = x.toBigInteger()
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

    // Kernel implementation

    override fun multiplyBy10(k: Int) {
        v *= 10.toBigInteger()
        v += k.toBigInteger()
        verify()
    }

    override fun isZero(): Boolean {
        return v == 0.toBigInteger()
    }

    override fun divideBy10(): Int {
        val d = v.remainder(10.toBigInteger())
        v /= 10.toBigInteger()
        verify()
        return d.toInt()
    }

    // Implementation
    
    override fun add(n: NaturalNumber) {
        n as NaturalNumberImpl
        v += n.v
        verify()
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

    override fun multiply(n: NaturalNumber) {
        n as NaturalNumberImpl
        v *= n.v
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

    override fun subtract(n: NaturalNumber) {
        n as NaturalNumberImpl
        v -= n.v
        verify()
    }
    
    override fun toInt(): Int {
        return v.toInt()
    }

    // GNUCL Standard methods

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

    // Java methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other is NaturalNumberImpl) {
            return v == other.v
        }

        return super.equals(other)
    }

    override fun hashCode(): Int {
        return v.toInt()
    }

    override fun toString(): String {
        return v.toString()
    }
}

abstract class NaturalNumberSecondary : NaturalNumber {
    private fun toNaturalNumberImpl(nn: NaturalNumber): NaturalNumberImpl {
        val builder = StringBuilder()

        while (!nn.isZero()) builder.append(nn.divideBy10())
        builder.forEach { x -> nn.multiplyBy10(x.digitToInt()) }

        return NaturalNumber1L(builder.reversed().toString())
    }

    private fun fromNaturalNumberImpl(nn: NaturalNumberImpl) {
        this.clear()
        nn.v.toString().forEach { c -> this.multiplyBy10(c.digitToInt()) }
    }

    // Implementation

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
        nn.subtract(toNaturalNumberImpl(n))
        fromNaturalNumberImpl(nn)
    }

    override fun toInt(): Int {
        return toNaturalNumberImpl(this).toInt()
    }

    // Java methods

    override fun equals(other: Any?): Boolean {
        return if (other is NaturalNumber) {
            toNaturalNumberImpl(this) == toNaturalNumberImpl(other)
        } else false
    }

    override fun hashCode(): Int = toNaturalNumberImpl(this).hashCode()

    override fun toString(): String = toNaturalNumberImpl(this).toString()
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