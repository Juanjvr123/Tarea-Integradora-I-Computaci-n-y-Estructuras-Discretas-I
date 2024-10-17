package structures;

public interface IQueue<T> {
    boolean isEmpty();

    void enqueue(T item);

    T dequeue();

    T front();

    int size();
}
