package structures;

import exceptions.StackException;

public class Stack<T> implements IStack<T> {

    private SNode<T> top;
    private int size = 0;

    public Stack() {
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void push(T item) {
        top = new SNode<>(item, top);
        size++;
    }

    @Override
    public T pop() throws StackException {
        if (isEmpty()) {
            throw new StackException("Stack is empty");
        }
        T item = top.getValue();
        top = top.getNext();
        size--;
        return item;
    }

    @Override
    public T top() throws StackException {
        if (isEmpty()) {
            throw new StackException("Stack is empty");
        }
        return top.getValue();
    }

    @Override
    public int size() {
        return size;
    }
}