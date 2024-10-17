import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.Stack;
import exceptions.StackException;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    private Stack<Integer> stack;

    @BeforeEach
    void setup() {
        stack = new Stack<>();
    }

    @Test
    void itShouldPushItemToTheTopOfTheStack() {
        stack.push(10);
        assertEquals(1, stack.size());
    }

    @Test
    void itShouldPopTheTopItemFromTheStack() throws StackException {
        stack.push(10);
        stack.push(20);

        int poppedItem = stack.pop();
        assertEquals(20, poppedItem);
        assertEquals(1, stack.size());

        poppedItem = stack.pop();
        assertEquals(10, poppedItem);
        assertTrue(stack.isEmpty());
    }


    @Test
    void itShouldThrowExceptionWhenPoppingFromEmptyStack() {
        Exception exception = assertThrows(StackException.class, () -> {
            stack.pop();
        });

        assertEquals("Stack is empty", exception.getMessage());
    }
}
