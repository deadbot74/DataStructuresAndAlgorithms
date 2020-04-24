package com.dsa.trees;

import java.util.*;

public class Graph {

    private class Node {
        String label;

        Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private Map<String, Node> nodes = new HashMap<>();
    private Map<Node, List<Node>> adjacencyList = new HashMap<>();

    public void addNode(String label) {
        Node node = new Node(label);
        nodes.putIfAbsent(label, node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        if (fromNode == null || toNode == null)
            throw new IllegalArgumentException();

        adjacencyList.get(fromNode).add(toNode);
//        adjacencyList.get(toNode).add(fromNode); In case of undirected graph
    }

    public void print() {
        for (Node node : adjacencyList.keySet()) {
            List<Node> list = adjacencyList.get(node);
            System.out.println(node + " is connected to " + list);
        }
    }

    public void removeNode(String label) {
        Node node = nodes.get(label);
        if (node == null) return;
        for (Node curr : adjacencyList.keySet())
            adjacencyList.get(curr).remove(node);
        adjacencyList.remove(node);
        nodes.remove(label);
    }

    public void removeEdge(String from, String to) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        if (fromNode == null || toNode == null)
            return;

        adjacencyList.get(fromNode).remove(toNode); // directed graph
    }

    public void depthFirstSearch(String root) {
        Node node = nodes.get(root);
        if (node == null) return;
        depthFirstSearch(node, new HashSet<>());
    }

    private void depthFirstSearch(Node root, Set<Node> visited) {
        System.out.println(root);
        visited.add(root);

        for (Node node : adjacencyList.get(root))
            if (!visited.contains(node))
                depthFirstSearch(node, visited);
    }

    public void iterativeDepthFirstSearch(String root) {
        Node node = nodes.get(root);
        if (node == null) return;

        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            if (visited.contains(curr)) continue;
            System.out.println(curr);
            visited.add(curr);

            for (Node neighbour : adjacencyList.get(curr))
                if (!visited.contains(neighbour))
                    stack.push(neighbour);
        }
    }

    public void iterativeBreadthFirstSearch(String root){
        Node node = nodes.get(root);
        if(node == null) return;

        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);

        while (!queue.isEmpty()){
            Node curr = queue.remove();
            if(visited.contains(curr)) continue;
            System.out.println(curr);
            visited.add(curr);

            for(Node neighbour: adjacencyList.get(curr))
                if(!visited.contains(neighbour))
                    queue.add(neighbour);
        }
    }

    public List<String> topologicalSort(){
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();

        for(Node node: nodes.values())
            topologicalSort(node, visited, stack);

        List<String> sortedNodes = new ArrayList<>();
        while (!stack.isEmpty())
            sortedNodes.add(stack.pop().label);

        return sortedNodes;
    }

    private void topologicalSort(Node node, Set<Node> visited, Stack<Node> stack){
        if(visited.contains(node)) return;

        visited.add(node);
        for(Node curr: adjacencyList.get(node))
            topologicalSort(curr, visited, stack);

        // push to the stack on the order of nodes which are completely visited,
        // the ones with no edges will be pushed first, and popped last, so they will be evaluated last
        stack.push(node);
    }

    public boolean hasCycle(){
        Set<Node> all = new HashSet<>(nodes.values()); // can use arraylist as well for iteration
        Set<Node> visiting = new HashSet<>();
        Set<Node> visited = new HashSet<>();

        while(!all.isEmpty()){
            Node current = all.iterator().next();
            if(hasCycle(current, all, visiting, visited)) return true;
        }
        return false;
    }

    private boolean hasCycle(Node node, Set<Node> all,
                             Set<Node> visiting, Set<Node> visited){
        all.remove(node);
        visiting.add(node);

        for(Node neighbour: adjacencyList.get(node)){
            if(visited.contains(neighbour)) continue;

            if(visiting.contains(neighbour)) return true;

            if(hasCycle(neighbour, all, visiting, visited)) return true;
        }

        visiting.remove(node);
        visited.add(node);

        return false;
    }
}
