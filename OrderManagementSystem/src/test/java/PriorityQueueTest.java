import exceptions.NoElementFoundException;
import exceptions.PriorityFullException;
import exceptions.SmallerKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import structures.PriorityQueue;

public class PriorityQueueTest {

    private PriorityQueue<Integer> priorityQueue;

    @BeforeEach
    public void setup() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void testInsertAndMaximum() throws PriorityFullException, NoElementFoundException {
        priorityQueue.insert(10);
        priorityQueue.insert(20);
        priorityQueue.insert(5);

        int max = priorityQueue.maximum();
        assertEquals(20, max);
    }

    @Test
    public void testMaximumThrowsExceptionOnEmptyQueue() {
        assertThrows(NoElementFoundException.class, () -> {
            priorityQueue.maximum();
        });
    }

    @Test
    public void testExtractMaximum() throws PriorityFullException, NoElementFoundException {
        priorityQueue.insert(30);
        priorityQueue.insert(15);
        priorityQueue.insert(40);

        int max = priorityQueue.extractMaximum();
        assertEquals(40, max);

        max = priorityQueue.maximum();
        assertEquals(30, max);
    }

    @Test
    public void testExtractMaximumThrowsExceptionOnEmptyQueue() {
        assertThrows(NoElementFoundException.class, () -> {
            priorityQueue.extractMaximum();
        });
    }

    @Test
    public void testInsertThrowsExceptionWhenFull() throws PriorityFullException {
        for (int i = 0; i < 20; i++) {
            priorityQueue.insert(i);
        }

        assertThrows(PriorityFullException.class, () -> {
            priorityQueue.insert(21);
        });
    }

    @Test
    public void testIncreaseKey() throws PriorityFullException, SmallerKeyException, NoElementFoundException {
        priorityQueue.insert(10);
        priorityQueue.insert(15);
        priorityQueue.insert(5);

        priorityQueue.increaseKey(2, 20);

        int max = priorityQueue.maximum();
        assertEquals(20, max);
    }

    @Test
    public void testIncreaseKeyThrowsSmallerKeyException() throws PriorityFullException {
        priorityQueue.insert(25);
        priorityQueue.insert(15);
        priorityQueue.insert(35);

        assertThrows(SmallerKeyException.class, () -> {
            priorityQueue.increaseKey(1, 10);
        });
    }

    @Test
    public void testSize() throws PriorityFullException {
        assertEquals(0, priorityQueue.size());

        priorityQueue.insert(10);
        priorityQueue.insert(20);

        assertEquals(2, priorityQueue.size());
    }

    @Test
    public void testInsertMaintainsCorrectOrder() throws PriorityFullException, NoElementFoundException {
        priorityQueue.insert(50);
        priorityQueue.insert(40);
        priorityQueue.insert(60);

        assertEquals(60, priorityQueue.maximum());

        priorityQueue.extractMaximum();
        assertEquals(50, priorityQueue.maximum());
    }
}