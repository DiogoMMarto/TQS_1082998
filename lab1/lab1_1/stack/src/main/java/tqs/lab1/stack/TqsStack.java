package tqs.lab1.stack;

import java.util.LinkedList;

public class TqsStack<T> {

    LinkedList<T> stack = new LinkedList<>();

    public TqsStack(){}

    public T pop(){
        return stack.pop();
    }

    public int size(){
        return stack.size();
    }

    public T peek(){
        return stack.getLast();
    }

    public void push(T elem){
        stack.add(elem);
    }

    public boolean isEmpty(){
        return stack.size() == 0;
    }

}
