package com.dsa.trees;

import java.util.*;

public class WeightedGraph {

    private class Node {
        private String label;
        private List<Edge> edges = new ArrayList<>();

        Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public void addEdge(Node to, int weight) {
            edges.add(new Edge(this, to, weight));
        }

        public List<Edge> getEdges() {
            return edges;
        }
    }

    private class Edge {
        private Node from;
        private Node to;
        private int weight;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

    // for priority queue used in dijkstra
    private class NodeEntry {
        private Node node;
        private int priority;

        public NodeEntry(Node node, int priority) {
            this.node = node;
            this.priority = priority;
        }
    }

    private Map<String, Node> nodes = new HashMap<>();
//    Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    public void addNode(String label) {
        Node node = new Node(label);
        nodes.putIfAbsent(label, node);
//        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to, int weight) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        if (fromNode == null || toNode == null)
            throw new IllegalArgumentException();

//        adjacencyList.get(fromNode).add(new Edge(fromNode, toNode, weight));
//        adjacencyList.get(toNode).add(new Edge(toNode, fromNode, weight));

        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }

    public void print() {
        for (Node node : nodes.values()) {
            List<Edge> edges = node.getEdges();
            System.out.println(edges);
        }
    }

    public int getShortestDistance(String from, String to) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        if (fromNode == null || toNode == null)
            throw new IllegalArgumentException();

        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : nodes.values())
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(fromNode, 0);

        PriorityQueue<NodeEntry> queue = new PriorityQueue<>(
                Comparator.comparingInt(ne -> ne.priority)
        );
        queue.add(new NodeEntry(fromNode, 0));

        while (!queue.isEmpty()) {
            Node current = queue.remove().node;
//            if(current == toNode) break;
            visited.add(current);
            for (Edge edge : current.getEdges()) {
                if (visited.contains(edge.to)) continue;
                int newDistance = distances.get(current) + edge.weight;
                if (newDistance < distances.get(edge.to)) {
                    distances.replace(edge.to, newDistance);
                    queue.add(new NodeEntry(edge.to, newDistance));
                }
            }
        }
        return distances.get(toNode);
    }

    public List<String> getShortestPath(String from, String to){
        List<String> path = new ArrayList<>();

        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        if(fromNode == null || toNode == null)
            throw new IllegalArgumentException();

        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : nodes.values())
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(fromNode, 0);
        previous.put(fromNode, null);

        PriorityQueue<NodeEntry> queue = new PriorityQueue<>(
                Comparator.comparingInt(ne -> ne.priority)
        );
        queue.add(new NodeEntry(fromNode, 0));

        while (!queue.isEmpty()){
            Node current = queue.remove().node;
            visited.add(current);
            for(Edge edge: current.getEdges()){
                Node neighbour = edge.to;
                if(visited.contains(neighbour)) continue;
                int newDistance = distances.get(current) + edge.weight;
                if(newDistance < distances.get(neighbour)) {
                    distances.replace(neighbour, newDistance);
                    previous.put(neighbour, current);
                    queue.add(new NodeEntry(neighbour, newDistance));
                }
            }
        }
        return buildPath(previous, path, toNode);
    }

    private List<String> buildPath(Map<Node, Node> previous, List<String> path, Node toNode){
        Stack<Node> stack = new Stack<>();
        stack.push(toNode);
        Node prev = previous.get(toNode);
        while(prev != null){
            stack.push(prev);
            prev = previous.get(prev);
        }

        while (!stack.isEmpty())
            path.add(stack.pop().label);

        return path;
    }

    public boolean hasCycle(){
        Set<Node> visited = new HashSet<>();
        for(Node node: nodes.values())
            if(!visited.contains(node) && hasCycle(node, null, visited))
                    return true;
        return false;
    }

    private boolean hasCycle(Node node, Node parent, Set<Node> visited){
        visited.add(node);
        for(Edge edge: node.getEdges()){
            Node current = edge.to;
            if(current == parent) continue;
            if(visited.contains(current)) return true;
            if(hasCycle(current, node, visited)) return true;
        }
        return false;
    }

    public WeightedGraph getMinimumSpanningTree(){
        WeightedGraph tree = new WeightedGraph();

        //handling graph with no nodes
        if(nodes.isEmpty())
            return tree;

        PriorityQueue<Edge> queue = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.weight)
        );

        Node startNode = nodes.values().iterator().next();
        queue.addAll(startNode.getEdges());
        tree.addNode(startNode.label);

        //handling graph inputs w/o any edges
        if(queue.isEmpty())
            return tree;

        while(tree.nodes.size() < nodes.size()){
            Edge minEdge = queue.remove();
            Node adjacentNode = minEdge.to;
            if(tree.containsNode(adjacentNode.label)) continue;
            tree.addNode(adjacentNode.label);
            tree.addEdge(minEdge.from.label, adjacentNode.label, minEdge.weight);

            for(Edge edge: adjacentNode.getEdges())
                if(!tree.containsNode(edge.to.label))
                    queue.add(edge);
        }
        return tree;
    }

    private boolean containsNode(String label){
        return nodes.containsKey(label);
    }
}
