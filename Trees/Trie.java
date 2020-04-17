package com.dsa.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    public final int ALPHABET_SIZE = 26;

    private class Node {
        private char value;
        private HashMap<Character, Node> children = new HashMap<>();
        private boolean isEndOfWord;

        Node(char value) {
            this.value = value;
        }

        private boolean hasChild(char c){
            return children.containsKey(c);
        }

        private void addChild(char c){
            children.put(c, new Node(c));
        }

        private Node getChild(char c){
            return children.get(c);
        }

        private Node[] getChildren(){
            return children.values().toArray(new Node[0]);
        }

        private boolean hasChildren(){
            return children.size() != 0;
        }

        private void removeChild(char c){
            children.remove(c);
        }
    }

    private Node root = new Node(' ');

    public void insert(String word) {
        Node curr = root;
        for(char c: word.toCharArray()){
            if(!curr.hasChild(c)){
                curr.addChild(c);
            }
            curr = curr.getChild(c);
        }
        curr.isEndOfWord = true;
    }

    public boolean isPresent(String word){
        if(word == null) return false;

        Node curr = root;
        for(char c: word.toCharArray()){
            if(!curr.hasChild(c)){
                return false;
            }
            curr = curr.getChild(c);
        }
        return curr.isEndOfWord;
    }

    public void traverse(){
        traverse(root);
    }

    private void traverse(Node root){

        //Pre-order
        System.out.println(root.value);

        for(Node node: root.getChildren()){
            traverse(node);
        }
    }

    public void remove(String word){
        if(word == null)
            return;
        remove(word, root, 0);
    }

    private void remove(String word, Node root, int index){
        if(word.length() == index){
            root.isEndOfWord = false;
            return;
        }

        char ch = word.charAt(index);
        Node node = root.getChild(ch);
        if(node == null) return;

        remove(word, node, index + 1);

        if(!node.hasChildren() && !node.isEndOfWord){
            root.removeChild(ch);
        }
    }

    String word = null;
    public List<String> autoComplete(String prefix){
        ArrayList<String> words = new ArrayList<>();
        Node lastNode = findLastNode(prefix);
        autoComplete(lastNode, prefix, words);
        return words;
    }

    private void autoComplete(Node root, String prefix, List<String> words){
        if(root == null) return;
        if(root.isEndOfWord){
            words.add(prefix);
        }
        for(Node child: root.getChildren()){
            autoComplete(child, prefix + child.value, words);
        }
    }

    private Node findLastNode(String prefix){
        if(prefix == null) return null;
        Node curr = root;
        for(char c: prefix.toCharArray()){
            Node child = curr.getChild(c);
            if(child == null) return null;
            curr = child;
        }
        return curr;
    }

}
