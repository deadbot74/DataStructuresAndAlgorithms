package com.dsa.lineards;

public class Array {
    private int[] items;
    private int count;
    public Array(int length){
        items = new int[length];
    }

    public void insert(int item){
        if(items.length == count){
            int[] newArray = new int[2*count];
            for(int i = 0; i < count; ++i)
                newArray[i] = items[i];
            items = newArray;
        }
        items[count++] = item;
    }

    public void removeAt(int index){
        if(index < 0 || index >= count)
            throw new IllegalArgumentException();

        for(int i = index + 1; i < count; ++i)
            items[i - 1] = items[i];

        count--;
    }

    public int indexOf(int item){
        for(int i = 0; i < count; ++i)
            if(items[i] == item)
                return i;
        return -1;
    }

    public void print(){
        for(int i = 0; i < count; ++i)
            System.out.println(items[i]);
    }
}
