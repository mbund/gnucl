package gnucl

//import components.sequence.*
import gnucl.sequence.*
import java.util.ArrayList
import kotlin.test.Test
import kotlin.test.assertEquals

class SequenceCustom<T: Any> : SequenceSecondary<T>() {
    internal var impl = ArrayList<T>()

    override fun newInstance(): Sequence<T> = SequenceCustom()

    override fun clear() = impl.clear()

    override fun iterator(): MutableIterator<T> = impl.iterator()

    override fun remove(pos: Int): T = impl.removeAt(pos)

    override fun length(): Int = impl.size

    override fun add(pos: Int, x: T) = impl.add(pos, x)

    override fun transferFrom(source: Sequence<T>) {
        source as SequenceCustom<T>
        impl = source.impl.clone() as ArrayList<T>
        source.clear()
    }

}

class SequenceTest {
    @Test
    fun misc() {
        val sequence = Sequence1L<Int>()
        assertEquals("<>", sequence.toString())

        sequence.add(0, 10)
        sequence.add(0, 7)
        sequence.add(sequence.length(), 3)
        sequence.add(sequence.length(), 5)

        assertEquals("<7,10,3,5>", sequence.toString())

        val sequence2 = Sequence1L<Int>()
        sequence.extract(1, 3, sequence2)

        assertEquals("<7,5>", sequence.toString())
        assertEquals("<10,3>", sequence2.toString())
    }

    @Test
    fun miscCustom() {
        val sequence = SequenceCustom<Int>()
        assertEquals("<>", sequence.toString())

        sequence.add(0, 10)
        sequence.add(0, 7)
        sequence.add(sequence.length(), 3)
        sequence.add(sequence.length(), 5)

        assertEquals("<7,10,3,5>", sequence.toString())

        val sequence2 = SequenceCustom<Int>()
        sequence.extract(1, 3, sequence2)

        assertEquals("<7,5>", sequence.toString())
        assertEquals("<10,3>", sequence2.toString())

        val sequence2Transferred = sequence2.newInstance()
        sequence2Transferred.transferFrom(sequence2)
        assertEquals("<10,3>", sequence2Transferred.toString())
        assertEquals("<>", sequence2.toString())
    }

}
