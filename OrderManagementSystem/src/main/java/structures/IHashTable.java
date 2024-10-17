package structures;

import java.util.List;

public interface IHashTable <K,V>{
    public void insert(K key, V value);
    public V search(K key);
    public void delete(K key);
    public void set(K key, V value);
    public List<V> getAll();
}
