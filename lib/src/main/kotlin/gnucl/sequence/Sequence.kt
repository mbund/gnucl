package gnucl.sequence

import Standard
import java.util.ArrayList

interface SequenceKernel<T> : Standard<Sequence<T>>, Iterable<T> {
    fun add(pos: Int, x: T)
    fun length(): Int
    fun remove(pos: Int): T
}

interface Sequence<T> : SequenceKernel<T> {
    fun append(q: Sequence<T>)
    fun entry(pos: Int): T
    fun extract(pos1: Int, pos2: Int, s: Sequence<T>)
    fun flip()
    fun insert(pos: Int, s: Sequence<T>)
    fun replaceEntry(pos: Int, x: T): T
}

sealed class SequenceImpl<T : Any> : SequenceSecondary<T>() {
    internal var impl = ArrayList<T>()

    // Kernel implementation

    override fun add(pos: Int, x: T) = impl.add(pos, x)
    override fun length(): Int = impl.size
    override fun remove(pos: Int): T = impl.removeAt(pos)

    // Implementation

    override fun append(q: Sequence<T>) {
        impl.addAll(q)
        
    }

    override fun entry(pos: Int): T = impl[pos]

    override fun extract(pos1: Int, pos2: Int, s: Sequence<T>) {
        s as SequenceImpl<T>
        impl.filterIndexedTo(s.impl) { index, _ -> index in pos1 until pos2 }
        impl.removeAll(s.impl.toSet())
    }

    override fun flip() {
        impl.asReversed()
    }

    override fun insert(pos: Int, s: Sequence<T>) {
        s as SequenceImpl<T>
        impl.addAll(pos, s.impl)
    }

    override fun replaceEntry(pos: Int, x: T): T = impl.set(pos, x)

    // GNUCL Standard methods

    override fun clear() {
        impl.clear()
    }

    override fun newInstance(): Sequence<T> {
        return Sequence1L()
    }

    override fun transferFrom(source: Sequence<T>) {
        source as SequenceImpl<T>
        impl = source.impl.clone() as ArrayList<T>
        source.clear()
    }

    // Java methods
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SequenceImpl<*>) return false
        if (this.length() != other.length()) return false

        return impl.zip(other).count { pair -> pair.first == pair.second } == this.length()
    }
    
    override fun hashCode(): Int {
        return impl.hashCode()
    }

    override fun iterator(): MutableIterator<T> = impl.iterator()
    
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("<")
        var sep = ""
        impl.forEach { x -> builder.append(sep, x); sep = ","; }
        builder.append(">")
        return builder.toString()
    }
}

abstract class SequenceSecondary<T : Any> : Sequence<T> {
    private fun toSequenceImpl(sequence: Sequence<T>): SequenceImpl<T> {
        val s = Sequence1L<T>()
        while (sequence.length() > 0) s.add(0, sequence.remove(sequence.length() - 1))
        s.forEach { x -> sequence.add(sequence.length(), x) }

        return s
    }

    private fun fromSequenceImpl(sequence: SequenceImpl<T>) {
        this.clear()
        sequence.impl.forEach { x -> this.add(this.length(), x) }
    }

    // Implementation

    override fun append(q: Sequence<T>) {
        val v = toSequenceImpl(this)
        v.append(q)
        fromSequenceImpl(v)
    }

    override fun entry(pos: Int): T {
        val s = toSequenceImpl(this)
        fromSequenceImpl(s)
        return entry(pos)
    }

    override fun extract(pos1: Int, pos2: Int, s: Sequence<T>) {
        val seq = toSequenceImpl(this)
        val outSeq = toSequenceImpl(s)
        seq.extract(pos1, pos2, outSeq)
        fromSequenceImpl(seq)

        s.clear()
        outSeq.impl.forEach { x -> s.add(s.length(), x) }
    }

    override fun flip() {
        val s = toSequenceImpl(this)
        s.flip()
        fromSequenceImpl(s)
    }

    override fun insert(pos: Int, s: Sequence<T>) {
        val seq = toSequenceImpl(this)
        seq.insert(pos, s)
        fromSequenceImpl(seq)
    }

    override fun replaceEntry(pos: Int, x: T): T {
        val seq = toSequenceImpl(this)
        val v = seq.replaceEntry(pos, x)
        fromSequenceImpl(seq)
        return v
    }

    // Java methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sequence<*>) return false
        if (this.length() != other.length()) return false

        return this.zip(other).count { pair -> pair.first == pair.second } == this.length()
    }

    override fun hashCode(): Int = toSequenceImpl(this).hashCode()

    override fun toString(): String = toSequenceImpl(this).toString()
}

@Suppress("unused")
class Sequence1L<T : Any> : SequenceImpl<T>()

@Suppress("unused")
class Sequence2L<T : Any> : SequenceImpl<T>()

@Suppress("unused")
class Sequence3<T : Any> : SequenceImpl<T>()
