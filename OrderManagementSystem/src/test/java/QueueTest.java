import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import structures.Queue;

public class QueueTest {
    private Queue<Integer> queue;

    @BeforeEach
    public void setUp() {
        queue = new Queue<>();
    }

    @Test
    public void testEnqueue() {
        queue.enqueue(51);
        assertEquals(1, queue.size());
        assertEquals(51,queue.front());
    }

    @Test
    public void testDequeue() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.size());

        assertEquals(2, queue.dequeue());
        assertEquals(1, queue.size());

        assertEquals(3, queue.dequeue());
        assertEquals(0, queue.size());
        assertNull(queue.dequeue());
    }

    @Test
    public void testFront(){
        assertNull(queue.front());
        queue.enqueue(51);
        assertEquals(51, queue.front());

        queue.enqueue(52);
        assertEquals(51, queue.front());
        queue.dequeue();
        assertEquals(52, queue.front());
    }

    @Test
    public void testSize(){
        assertEquals(0, queue.size());
        queue.enqueue(5);
        assertEquals(1, queue.size());
        queue.enqueue(10);
        assertEquals(2, queue.size());
        queue.dequeue();
        assertEquals(1, queue.size());
        queue.dequeue();
        assertEquals(0, queue.size());

    }


}
