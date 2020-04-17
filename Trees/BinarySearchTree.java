package com.euler.defaultpackage;

public class BinarySearchTree {

    private class Node{
        int value;
        Node rightChild;
        Node leftChild;

        Node(int value){
            this.value = value;
            this.leftChild = this.rightChild = null;
        }

        @Override
        public String toString() {
            return "Node: " + value;
        }
    }

    Node root;

    BinarySearchTree(){
        this.root = null;
    }

    void insertNode(int value){
        Node curr = root;
        if(root == null) {
            root = new Node(value);
            return;
        }
        while(true){

            if(curr.value > value){
                if(curr.leftChild == null){
                    curr.leftChild = new Node(value);
                    break;
                }
                curr = curr.leftChild;
            }
            else{
                if(curr.rightChild == null){
                    curr.rightChild = new Node(value);
                    break;
                }
                curr = curr.rightChild;
            }
        }

    }

    void printInOrder(Node root){
       if(root == null){
           return;
       }
       else{
           printInOrder(root.leftChild);
           System.out.println(root.value);
           printInOrder(root.rightChild);
       }
    }

    boolean search(int val){
        Node curr = root;
        while(curr != null){
            if(curr.value == val){
                return true;
            }
            else if(val > curr.value){
                curr = curr.rightChild;
            }
            else{
                curr = curr.leftChild;
            }
        }
        return false;
    }

    int calculateHeight(Node root){

        if(root == null){
            return 0;
        }
        if(root.leftChild == null && root.rightChild == null){
            return 0;
        }
        else{
            return 1 + Integer.max(calculateHeight(root.leftChild), calculateHeight(root.rightChild));
        }
    }

    boolean isEqual(Node root1, Node root2){
        if(root1 == null && root2 == null){
            return true;
        }
        if(root1.value == root2.value)
            return isEqual(root1.leftChild, root2.leftChild) && isEqual(root1.rightChild, root2.rightChild);
        return false;
    }


}
