package structures;

import exceptions.NoElementFoundException;
import exceptions.PriorityFullException;
import exceptions.SmallerKeyException;
import model.Order;

public interface IPriorityQueue<T> {

    void insert(T element) throws PriorityFullException;


    T maximum() throws NoElementFoundException;


    T extractMaximum() throws NoElementFoundException;


    void increaseKey(int index, T newKey) throws SmallerKeyException;

}