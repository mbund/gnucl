package gnucl

//import components.queue.*
import gnucl.queue.*
import kotlin.test.Test
import kotlin.test.assertEquals

class QueueCustom<T: Any> : QueueSecondary<T>() {
    private var impl = java.util.ArrayDeque<T>()

    override fun dequeue(): T = impl.remove()

    override fun enqueue(x: T) {
        impl.add(x)
    }
    override fun length(): Int = impl.size

    override fun clear() {
        impl.clear()
    }

    override fun iterator(): MutableIterator<T> = impl.iterator()

    override fun newInstance(): Queue<T> = QueueCustom()

    override fun transferFrom(source: Queue<T>) {
        source as QueueCustom<T>
        impl = source.impl.clone()
        source.clear()
    }

}


class QueueTest {
    @Test
    fun misc() {
        val queue = Queue1L<Int>()
        assertEquals("<>", queue.toString())
        queue.enqueue(3)
        queue.enqueue(82)
        queue.enqueue(-12)
        queue.enqueue(7)

        assertEquals(4, queue.length())
        assertEquals("<3,82,-12,7>", queue.toString())

        assertEquals(3, queue.dequeue())
        assertEquals(82, queue.dequeue())
        assertEquals(-12, queue.dequeue())
        assertEquals(7, queue.dequeue())

        assertEquals(0, queue.length())
    }

    @Test
    fun append() {
        val queue1 = Queue1L<Int>()
        queue1.enqueue(1)
        queue1.enqueue(2)
        queue1.enqueue(3)
        queue1.enqueue(4)
        val queue2 = Queue1L<Int>()
        queue2.enqueue(5)
        queue2.enqueue(6)
        queue2.enqueue(7)

        queue1.append(queue2)

        assertEquals("<1,2,3,4,5,6,7>", queue1.toString())
        assertEquals("<>", queue2.toString())
    }

    @Test
    fun appendCustom() {
        val queue1 = QueueCustom<Int>()
        queue1.enqueue(1)
        queue1.enqueue(2)
        queue1.enqueue(3)
        queue1.enqueue(4)
        val queue2 = QueueCustom<Int>()
        queue2.enqueue(5)
        queue2.enqueue(6)
        queue2.enqueue(7)

        queue1.append(queue2)

        assertEquals("<1,2,3,4,5,6,7>", queue1.toString())
        assertEquals("<>", queue2.toString())
    }

    @Test
    fun newInstance() {
        val queue1 = Queue1L<String>()
        queue1.enqueue("abc")
        queue1.enqueue("def")
        val queue2 = queue1.newInstance()

        assertEquals(2, queue1.length())
        assertEquals(0, queue2.length())
    }

    @Test
    fun transferFrom() {
        val queue1 = Queue1L<Int>()
        queue1.enqueue(4)
        queue1.enqueue(5)
        queue1.enqueue(6)
        val queue2 = Queue1L<Int>()

        assertEquals(3, queue1.length())
        assertEquals(0, queue2.length())
        queue2.transferFrom(queue1)
        assertEquals(0, queue1.length())
        assertEquals(3, queue2.length())

        assertEquals(4, queue2.dequeue())
        assertEquals(5, queue2.dequeue())
        assertEquals(6, queue2.dequeue())

        assertEquals(0, queue2.length())
    }

    @Test
    fun transferFromCustom() {
        val queue1 = QueueCustom<Int>()
        queue1.enqueue(4)
        queue1.enqueue(5)
        queue1.enqueue(6)
        val queue2 = QueueCustom<Int>()

        assertEquals(3, queue1.length())
        assertEquals(0, queue2.length())
        queue2.transferFrom(queue1)
        assertEquals(0, queue1.length())
        assertEquals(3, queue2.length())

        assertEquals(4, queue2.dequeue())
        assertEquals(5, queue2.dequeue())
        assertEquals(6, queue2.dequeue())

        assertEquals(0, queue2.length())
    }

    @Test
    fun iterate() {
        val queue1 = Queue1L<Int>()
        queue1.enqueue(4)
        queue1.enqueue(5)
        queue1.enqueue(6)

        assertEquals(4, queue1.min())
        assertEquals(6, queue1.max())
        assertEquals(15, queue1.sum())
    }
}
