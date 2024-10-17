package structures;

import exceptions.NoElementFoundException;
import exceptions.PriorityFullException;
import exceptions.SmallerKeyException;

import java.util.ArrayList;

public class PriorityQueue<T> implements IPriorityQueue<T> {

    private ArrayList<T> heap;
    private static final int MAX = 20;

    public PriorityQueue() {
        heap = new ArrayList<>(MAX);
    }

    @Override
    public void insert(T element) throws PriorityFullException {
        if (heap.size() >= MAX) {
            throw new PriorityFullException("Priority queue is full.");
        }
        heap.add(element);
        heapifyUp(heap.size() - 1);
    }

    @Override
    public T maximum() throws NoElementFoundException {
        if (heap.isEmpty()) {
            throw new NoElementFoundException("Priority queue is empty.");
        }
        return heap.get(0);
    }

    @Override
    public T extractMaximum() throws NoElementFoundException {
        if (heap.isEmpty()) {
            throw new NoElementFoundException("Priority queue is empty.");
        }
        T maxElement = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown(0);
        }
        return maxElement;
    }

    @Override
    public void increaseKey(int index, T newKey) throws SmallerKeyException {
        if (compareTo(heap.get(index), newKey) > 0) {
            throw new SmallerKeyException("New key is smaller than current key.");
        }
        heap.set(index, newKey);
        heapifyUp(index);
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (compareTo(heap.get(index), heap.get(parentIndex)) > 0) {
                T temp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, temp);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heap.size() && compareTo(heap.get(left), heap.get(largest)) > 0) {
            largest = left;
        }

        if (right < heap.size() && compareTo(heap.get(right), heap.get(largest)) > 0) {
            largest = right;
        }

        if (largest != index) {
            T temp = heap.get(index);
            heap.set(index, heap.get(largest));
            heap.set(largest, temp);
            heapifyDown(largest);
        }
    }

    private int compareTo(T a, T b) {
        return ((Comparable<T>) a).compareTo(b);
    }


    public int size() {
        return heap.size();
    }
}