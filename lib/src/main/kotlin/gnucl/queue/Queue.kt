package gnucl.queue

import Standard
import java.util.Collections

interface QueueKernel<T> : Standard<Queue<T>>, Iterable<T> {
    fun dequeue(): T
    fun enqueue(x: T)
    fun length(): Int
}

interface Queue<T> : QueueKernel<T> {
    fun append(q: Queue<T>)
    fun flip()
    fun front(): T
    fun replaceFront(x: T): T
    fun rotate(distance: Int)
    fun sort(order: Comparable<T>)
}

sealed class QueueImpl<T : Any> : QueueSecondary<T>() {
    private var impl = java.util.ArrayDeque<T>()

    override fun dequeue(): T = impl.remove()
    override fun enqueue(x: T) {
        impl.add(x)
    }

    override fun length(): Int = impl.size

    override fun append(q: Queue<T>) {
        impl.addAll(q)
    }

    override fun flip() {
        impl.toMutableList().asReversed()
    }

    override fun front(): T {
        return impl.first()
    }

    override fun replaceFront(x: T): T {
        val v = impl.removeFirst()
        impl.addFirst(x)
        return v
    }

    override fun rotate(distance: Int) {
        val xs = impl.toMutableList()
        Collections.rotate(xs, distance)
    }

    override fun sort(order: Comparable<T>) {
        impl.toMutableList().sortBy { x -> order.compareTo(x) }
    }

    override fun iterator(): Iterator<T> = impl.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        // type safety?????
        // if (other?.javaClass != javaClass) return false
        other as QueueImpl<*>

        return impl == other.impl
    }

    override fun hashCode(): Int {
        return impl.hashCode()
    }

    override fun clear() {
        impl.clear()
    }

    override fun newInstance(): Queue<T> {
        return Queue1L()
    }

    override fun transferFrom(source: Queue<T>) {
        source as QueueImpl<T>
        impl = source.impl.clone()
        source.clear()
    }
}

abstract class QueueSecondary<T : Any> : Queue<T> {
    private fun toQueueImpl(queue: Queue<T>): QueueImpl<T> {
        val q = Queue1L<T>()
        queue.forEach { x -> q.enqueue(x) }

        return q
    }

    private fun fromQueueImpl(queue: Queue<T>) {
        this.transferFrom(queue)
    }

    override fun append(q: Queue<T>) {
        val v = toQueueImpl(this)
        v.append(q)
        fromQueueImpl(v)
    }

    override fun flip() {
        val q = toQueueImpl(this)
        q.flip()
        fromQueueImpl(q)
    }

    override fun front(): T = toQueueImpl(this).front()

    override fun replaceFront(x: T): T {
        val q = toQueueImpl(this)
        val v = q.replaceFront(x)
        fromQueueImpl(q)
        return v
    }

    override fun rotate(distance: Int) {
        val q = toQueueImpl(this)
        q.rotate(distance)
        fromQueueImpl(q)
    }

    override fun sort(order: Comparable<T>) {
        val q = toQueueImpl(this)
        q.sort(order)
        fromQueueImpl(q)
    }
}

@Suppress("unused")
class Queue1L<T : Any> : QueueImpl<T>()

@Suppress("unused")
class Queue2<T : Any> : QueueImpl<T>()

@Suppress("unused")
class Queue3<T : Any> : QueueImpl<T>()
