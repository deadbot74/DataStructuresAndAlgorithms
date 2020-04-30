package com.dsa.lineards;

import java.util.Arrays;

public class Stack {
    private int top;
    private int[] items = new int[5];

    public void push(int item){
        if(top == items.length)
            throw new StackOverflowError();

        items[top++] = item;
    }

    public int pop(){
        if(top == 0)
            throw new IllegalStateException();

        return items[--top];
    }

    public int peek(){
        if(top == 0)
            throw new IllegalStateException();

        return items[top - 1];
    }

    public boolean isEmpty(){
        return top == 0;
    }

    @Override
    public String toString(){
        int[] content = Arrays.copyOfRange(items, 0, top);
        return Arrays.toString(content);
    }

}
