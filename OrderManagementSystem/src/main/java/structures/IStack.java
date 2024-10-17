package structures;

import exceptions.StackException;

public interface IStack<T> {

    boolean isEmpty();

    void push(T item);

    T pop() throws StackException;

    T top() throws StackException;

    int size();
}
