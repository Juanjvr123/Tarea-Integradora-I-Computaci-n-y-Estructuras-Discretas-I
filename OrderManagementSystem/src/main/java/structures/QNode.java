package structures;

public class QNode<T>{
    private T value;
    private QNode<T> next;

    public QNode(T value) {
        this.value = value;
        this.next = null;
    }
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    public QNode<T> getNext() {
        return next;
    }
    public void setNext(QNode<T> next) {
        this.next = next;
    }
}