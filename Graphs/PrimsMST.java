package com.dsa.graphs;


import java.util.Arrays;

public class PrimsMST {

    private int vertices;

    public PrimsMST(int v){
        vertices = v;
    }

    public int findMinVertex(boolean[] mst, int[] key){
        int minimum = Integer.MAX_VALUE;
        int vertex = -1;
        for(int i = 0; i < key.length; ++i){
            if(!mst[i] && key[i] < minimum){
                minimum = key[i];
                vertex = i;
            }
        }
        return vertex;
    }

    public int[] primMST(int[][] graph){
        boolean[] mst = new boolean[vertices];
        int[] key = new int[vertices];
        int[] parent = new int[vertices];

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        for(int i = 0; i < vertices - 1; ++i){

            int minVertex = findMinVertex(mst, key);
            mst[minVertex] = true;

            for(int j = 0; j < vertices; ++j){
                if(graph[minVertex][j] > 0){
                    if(!mst[minVertex] && graph[minVertex][j] < key[j]){
                        key[j] = graph[minVertex][j];
                        parent[j] = minVertex;
                    }
                }
            }
        }
        return key;
    }

    public static void main(String[] args) {
        AdjacencyMatrix adjMatrix = new AdjacencyMatrix(5);
        adjMatrix.addEdge(0, 1, 5);
        adjMatrix.addEdge(0, 4, 3);
        adjMatrix.addEdge(1, 2, 2);
        adjMatrix.addEdge(1, 3, 9);
        adjMatrix.addEdge(2, 3, 7);
        adjMatrix.addEdge(3, 0, 7);
        adjMatrix.addEdge(4, 2, 1);

        adjMatrix.printMatrix();
        PrimsMST prmst = new PrimsMST(5);

        try{
            int[] key = prmst.primMST(adjMatrix.adjMatrix);
            System.out.println(Arrays.toString(key));
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }


    }
}
