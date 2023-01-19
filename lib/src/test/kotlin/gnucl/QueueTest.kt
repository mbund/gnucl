package gnucl

//import components.queue.*
import gnucl.queue.*

import kotlin.test.Test
import kotlin.test.assertEquals

class QueueTest {
    @Test
    fun misc() {
        val queue = Queue1L<Int>()
        queue.enqueue(3)
        queue.enqueue(82)
        queue.enqueue(-12)
        queue.enqueue(7)

        assertEquals(4, queue.length())

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

        assertEquals(7, queue1.length())
        assertEquals(1, queue1.dequeue())
        assertEquals(2, queue1.dequeue())
        assertEquals(3, queue1.dequeue())
        assertEquals(4, queue1.dequeue())
        assertEquals(5, queue1.dequeue())
        assertEquals(6, queue1.dequeue())
        assertEquals(7, queue1.dequeue())
        assertEquals(0, queue1.length())

        assertEquals(3, queue2.length())
        assertEquals(5, queue2.dequeue())
        assertEquals(6, queue2.dequeue())
        assertEquals(7, queue2.dequeue())
        assertEquals(0, queue2.length())
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
}
