package structures;

public class HNode<K,V> {
    private K key;
    private V value;

    private HNode<K,V> next;

    public HNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public void setValue(V value) {
        this.value = value;
    }
    public void setNext(HNode<K,V> next){
        this.next = next;
    }

    public V getValue(){
        return this.value;
    }

    public HNode<K,V> getNext (){
        return this.next;
    }

    public K getKey(){
        return this.key;
    }
}