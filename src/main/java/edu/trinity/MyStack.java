package edu.trinity;

import java.util.EmptyStackException;

public class MyStack<T> {
    // Top of the stack = end of the array //
    private T[] elements;
    private int size;

    // Constructor that uses 'Object', which is then casted to T[] //
    public MyStack(){
        elements = (T[]) new Object[10];
        size = 0;
    }
    public void push(T hello) {
        elements[size++] = hello;
    }

    public T peek() {
        if (isEmpty()){
            throw new EmptyStackException();
        }
        return elements[size - 1];
    }

    public T pop() {
        if (isEmpty()){
            throw new EmptyStackException();
        }
        // Decrecment before using the size value //
        T popVal = elements[--size];
        elements[size] = null;
        return popVal;
    }

    public boolean isEmpty() {
        return (size == 0);
    }
}