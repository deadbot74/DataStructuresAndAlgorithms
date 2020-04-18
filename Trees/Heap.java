package com.dsa.trees;

// main functions to implement:
// heapify(), buildHeap(), heapSort(), increase/decrease key, extract min/max

public class Heap {

    private int[] nodes = new int[10];
    private int size;

    public void insert(int value) {
        if (size == nodes.length) {
            throw new IllegalStateException();
        }

        nodes[size++] = value;
        bubbleUp();
    }

    private int delete() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        int node = nodes[size];
        nodes[0] = nodes[--size];
        bubbleDown(0);
        return node;
    }

    private void bubbleUp() {
        int childIndex = size - 1;
        int parentIndex = (int) (childIndex - 1) / 2;

        while (childIndex > 0 && nodes[childIndex] > nodes[parentIndex]) {
            swap(childIndex, parentIndex);
            childIndex = parentIndex;
            parentIndex = (int) (childIndex - 1) / 2;
        }
    }

    private void bubbleDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        int max = index;
        if (leftChild < size && nodes[leftChild] > nodes[max]) {
            max = leftChild;
        }
        if (nodes[rightChild] > nodes[max]) {
            max = rightChild;
        }

        if (max != index) {
            swap(max, index);
            bubbleDown(max);
        }
    }

    private void swap(int a, int b) {
        int temp = nodes[a];
        nodes[a] = nodes[b];
        nodes[b] = temp;
    }

}
