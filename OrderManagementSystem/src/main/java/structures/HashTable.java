package structures;

import java.util.ArrayList;
import java.util.List;

public class HashTable<K,V> implements IHashTable <K,V>{

    public static final int hashTableExtension = 100;
    private HNode<K,V>[] nodes;

    public HashTable() {
        nodes = new HNode[hashTableExtension];
    }

    @Override
    public void insert(K key, V value){
        int index = hashFunction(key);
        HNode<K, V> node = new HNode<>(key, value);
        if(nodes[index] == null){
            nodes[index] = node;
        }else if(nodes[index] != null){
            node.setNext(nodes[index]);
            nodes[index] = node;
        }
    }

    @Override
    public V search(K key) {
        int index = hashFunction(key);
        if(nodes[index] == null){
            return null;
        }
        HNode<K,V> temporal = nodes[index];
        do{
            if(temporal.getKey().equals(key)){
                return temporal.getValue();
            }else{
                temporal = temporal.getNext();
            }
        }while(temporal.getNext() != null);

        return null;
    }

    @Override
    public void delete(K key) {
        int index = hashFunction(key);

        if (nodes[index] == null) {
            return;
        }

        if (nodes[index].getKey().equals(key)) {
            if (nodes[index].getNext() == null) {
                nodes[index] = null;
            } else {
                nodes[index] = nodes[index].getNext();
            }
            return;
        }
        delete(nodes[index], key);
    }

    private void delete(HNode<K, V> current, K key) {
        if (current.getNext() == null) {
            return;
        }
        if (current.getNext().getKey().equals(key)) {
            current.setNext(current.getNext().getNext());
        } else {
            delete(current.getNext(), key);
        }
    }


    public int hashFunction(K key){
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % hashTableExtension;
    }

    @Override
    public void set(K key, V value){
        int index = hashFunction(key);

        if(nodes[index].getKey().equals(key)){
            nodes[index].setValue(value);
        }else{
            set(nodes[index].getNext(), value, key);
        }
    }

    private void set(HNode<K, V> actual, V value, K key){
        if(actual.getKey().equals(key)){
            actual.setValue(value);
        }else{
            set(actual.getNext(), value, key);
        }
    }

    @Override
    public List<V> getAll() {
        ArrayList<V> objects = new ArrayList<>();

        for (int i = 0; i < hashTableExtension; i++) {
            HNode<K,V> currentNode = nodes[i];
            while (currentNode != null) {
                objects.add(currentNode.getValue());
                currentNode = currentNode.getNext();
            }
        }

        return objects;
    }

    public HNode<K, V> searchNode(K key) {
        int index = hashFunction(key);
        HNode<K, V> current = nodes[index];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }
}


