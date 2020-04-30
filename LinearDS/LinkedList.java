package com.dsa.lineards;

import java.util.NoSuchElementException;

public class LinkedList {
    private class Node{
        private int value;
        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node first = null;
    private Node last = null;
    private int size;

    public void addFirst(int value){
        Node node = new Node(value, null);

        if(isEmpty())
            first = last = node;
        else{
            node.next = first;
            first = node;
        }

        size++;
    }

    public void addLast(int value){
        Node node = new Node(value, null);

        if(isEmpty())
            first = last = node;
        else
            last = last.next = node;

        size++;
    }

    public void removeFirst(){
        if(isEmpty())
            throw new NoSuchElementException();

        else if(first.next == null)
            first = last = null;

        else{
            Node second = first.next;
            first.next = null;
            first = second;
        }

        size--;
    }

    public void removeLast(){
        Node prev = getPreviousNode(last);

        if(isEmpty())
            throw new NoSuchElementException();

        else if(prev == null)
            first = last = null;

        else{
            last = prev;
            last.next = null;
        }

        size--;
    }

    public int indexOf(int value){
        int index = 0;
        Node current = first;
        while (current != null){
            if(current.value == value)
                return index;
            current = current.next;
            ++index;
        }
        return -1;
    }

    public int size(){
        return size;
    }

    public boolean contains(int value){
        return indexOf(value) != -1;
    }

    public int[] toArray(){
        int[] array = new int[size];
        Node curr = first;
        int index = 0;
        while (curr != null){
            array[index++] = curr.value;
            curr = curr.next;
        }
        return array;
    }

    public void reverse(){
        if (isEmpty())
            return;

        Node previous, current, next;
        previous = first;
        current = first.next;

        while (current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        last = first;
        last.next = null;
        first = previous;
    }

    public int getKthNodeFromTheEnd(int k){
        if(isEmpty())
            throw new IllegalStateException();

        Node one = first;
        Node two = first;
        for(int i = 0; i < k - 1; ++i) {
            two = two.next;
            if(two == null)
                throw new IllegalArgumentException();
        }
        while(two != last){
            one = one.next;
            two = two.next;
        }
        return one.value;

    }

    private boolean isEmpty(){
        return first == null;
    }

    private Node getPreviousNode(Node node){
        Node current = first;
        while(current != null){
            if(current.next == node)
                return current;
            current = current.next;
        }
        return null;
    }

}
