package structures;

public class Queue<T> implements IQueue<T> {

    private QNode<T> front;
    private QNode<T> rear;
    private int size;

    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }


    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public void enqueue(T item) {
        QNode<T> newNode = new QNode<>(item);
        if (rear != null) {
            rear.setNext(newNode);
        }
        rear = newNode;
        if (front == null) {
            front = rear;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T item = front.getValue();
        front = front.getNext();
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    @Override
    public T front() {
        if (isEmpty()) {
            return null;
        }
        return front.getValue();
    }

    @Override
    public int size() {
        return size;
    }
}