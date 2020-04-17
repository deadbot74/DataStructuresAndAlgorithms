package com.dsa.trees;

public class AVLTree {

    private class AVLNode {
        private int height;
        private int value;
        private AVLNode leftChild, rightChild;

        AVLNode(int value) {
            this.value = value;
            this.leftChild = this.rightChild = null;
        }
    }

    private AVLNode root = null;

    public void insert(int value) {
        root = insert(root, value);
    }

    private AVLNode insert(AVLNode root, int value) {
        if (root == null) {
            return new AVLNode(value);
        }
        if (root.value > value) {
            root.leftChild = insert(root.leftChild, value);
        } else {
            root.rightChild = insert(root.rightChild, value);
        }

        root.height = calculateHeight(root);

//        balance(root);

        return balance(root);
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode right = node.rightChild;
        if (right.leftChild == null) {
            right.leftChild = node;
        } else {
            node.rightChild = right.leftChild;
            right.leftChild = node;
        }
        right.height = calculateHeight(right);
        node.height = calculateHeight(node);
        return right;
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode left = node.leftChild;
        if (left.rightChild == null) {
            left.rightChild = node;
        } else {
            node.leftChild = left.rightChild;
            left.rightChild = node;
        }
        left.height = calculateHeight(left);
        node.height = calculateHeight(node);
        return left;
    }

    private int calculateHeight(AVLNode node) {
        return Math.max(height(node.leftChild), height(node.rightChild)) + 1;
    }

    private AVLNode balance(AVLNode root) {
        if (isLeftHeavy(root)) {
            if (balanceFactor(root.leftChild) < 0) {
                root.leftChild = rotateLeft(root.leftChild);
            }
            return rotateRight(root);
        }
        if (isRightHeavy(root)) {
            if (balanceFactor(root.rightChild) > 0) {
                root.rightChild = rotateRight(root.rightChild);
            }
            return rotateLeft(root);
        }
        return root;
    }


    private boolean isLeftHeavy(AVLNode node) {
        return balanceFactor(node) > 1;
    }

    private boolean isRightHeavy(AVLNode node) {
        return balanceFactor(node) < -1;
    }

    private int balanceFactor(AVLNode node) {
        return (node == null) ? 0 : height(node.leftChild) - height(node.rightChild);
    }

    private int height(AVLNode node) {
        return (node == null) ? -1 : node.height;
    }


}
