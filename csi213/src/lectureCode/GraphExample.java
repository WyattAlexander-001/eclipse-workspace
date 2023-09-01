package lectureCode;

import java.util.ArrayList;
import java.util.Comparator; //A comparison function used for sorting or ordering collections.
import java.util.HashMap; //A hash table-based implementation of the Map interface.
import java.util.Map; //Interface for key-value mappings.
import java.util.PriorityQueue;

public class GraphExample {

    class Vertex { //city
        String name;
        ArrayList<Edge> edges;

        public Vertex(String name) {
            this.name = name;
            this.edges = new ArrayList<>();
        }
    }

    class Edge {//road
        Vertex start, end;
        int cost;

        public Edge(Vertex start, Vertex end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    Map<String, Vertex> vertices; //A map storing vertices (cities) in the graph, with the city name as the key, vertex is the city object.

    public GraphExample() {
        vertices = new HashMap<>();
    }

    public void addVertex(String name) { //Method to add a vertex (city) to the graph.
        vertices.put(name, new Vertex(name));
    }

    public void addEdge(String start, String end, int cost) {
        Vertex startVertex = vertices.get(start);
        Vertex endVertex = vertices.get(end);
        if (startVertex != null && endVertex != null) {
            Edge edge = new Edge(startVertex, endVertex, cost);
            startVertex.edges.add(edge);
            // If the graph is undirected, add an edge in the opposite direction as well.
            endVertex.edges.add(new Edge(endVertex, startVertex, cost));
        }
    }

    public void displayGraph() {
        for (Vertex vertex : vertices.values()) {
            System.out.print(vertex.name + " -> ");
            for (Edge edge : vertex.edges) {
                System.out.print(edge.end.name + " (" + edge.cost + ") ");
            }
            System.out.println();
        }
    }

    public int dijkstraShortestPath(String start, String end) {
        Map<Vertex, Integer> distances = new HashMap<>();
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Vertex startVertex = vertices.get(start);
        Vertex endVertex = vertices.get(end);

        if (startVertex == null || endVertex == null) {
            return -1;
        }

        for (Vertex vertex : vertices.values()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(startVertex, 0);
        pq.offer(startVertex);

        while (!pq.isEmpty()) {
            Vertex current = pq.poll();

            if (current.equals(endVertex)) {
                return distances.get(current);
            }

            for (Edge edge : current.edges) {
                int newDistance = distances.get(current) + edge.cost;

                if (newDistance < distances.get(edge.end)) {
                    distances.put(edge.end, newDistance);
                    pq.remove(edge.end);
                    pq.offer(edge.end);
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        GraphExample graph = new GraphExample();

        graph.addVertex("CityA");
        graph.addVertex("CityB");
        graph.addVertex("CityC");

        graph.addEdge("CityA", "CityB", 100);
        graph.addEdge("CityA", "CityC", 150);
        graph.addEdge("CityB", "CityC", 120);

        graph.displayGraph();

        int shortestPath = graph.dijkstraShortestPath("CityA", "CityC");
        //
        System.out.println("Shortest path between CityA and CityC: " + shortestPath);
    }
}
