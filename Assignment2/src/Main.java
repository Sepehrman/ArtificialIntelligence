import java.util.*;

class Node {
    String name;
    int heuristic;
    List<Edge> edges;

    Node(String name, int heuristic) {
        this.name = name;
        this.heuristic = heuristic;
        this.edges = new ArrayList<>();
    }

    void addEdge(Node target, int cost) {
        edges.add(new Edge(this, target, cost));
    }
}

class Edge {
    Node source;
    Node target;
    int cost;

    Edge(Node source, Node target, int cost) {
        this.source = source;
        this.target = target;
        this.cost = cost;
    }
}

class Graph {
    Map<String, Node> nodes;

    Graph() {
        nodes = new HashMap<>();
    }

    void addNode(String name, int heuristic) {
        nodes.put(name, new Node(name, heuristic));
    }

    void addEdge(String sourceName, String targetName, int pathCost) {
        Node source = nodes.get(sourceName);
        Node target = nodes.get(targetName);
        if (source != null && target != null) {
            source.addEdge(target, pathCost);
        } else {
            System.out.println("Source or Target node not found.");
        }
    }

    Node getNode(String name) {
        return nodes.get(name);
    }

    void printGraph() {
        for (Node node : nodes.values()) {
            System.out.print("Node " + node.name + " (Heuristic: " + node.heuristic + ") -> ");
            for (Edge edge : node.edges) {
                System.out.print(edge.target.name + " (Cost: " + edge.cost + "), ");
            }
            System.out.println();
        }
    }
}

class AStar {
    static class NodeRecord implements Comparable<NodeRecord> {
        Node node;
        NodeRecord parent;
        int costSoFar;
        int estimatedTotalCost;

        NodeRecord(Node node, NodeRecord parent, int costSoFar, int estimatedTotalCost) {
            this.node = node;
            this.parent = parent;
            this.costSoFar = costSoFar;
            this.estimatedTotalCost = estimatedTotalCost;
        }

        @Override
        public int compareTo(NodeRecord other) {
            return Integer.compare(this.estimatedTotalCost, other.estimatedTotalCost);
        }
    }

    public static List<String> aStarSearch(Graph graph, String startName, String goalName) {
        Node start = graph.getNode(startName);
        Node goal = graph.getNode(goalName);

        PriorityQueue<NodeRecord> openList = new PriorityQueue<>();
        Map<String, NodeRecord> openMap = new HashMap<>();
        Map<String, NodeRecord> closedMap = new HashMap<>();

        NodeRecord startRecord = new NodeRecord(start, null, 0, start.heuristic);
        openList.add(startRecord);
        openMap.put(start.name, startRecord);

        while (!openList.isEmpty()) {
            NodeRecord current = openList.poll();
            openMap.remove(current.node.name);

            if (current.node.name.equals(goal.name)) {
                return reconstructPath(current);
            }

            for (Edge edge : current.node.edges) {
                Node target = edge.target;
                int costSoFar = current.costSoFar + edge.cost;
                int estimatedTotalCost = costSoFar + target.heuristic;

                if (closedMap.containsKey(target.name)) {
                    continue;
                }

                NodeRecord targetRecord = openMap.get(target.name);
                if (targetRecord == null) {
                    targetRecord = new NodeRecord(target, current, costSoFar, estimatedTotalCost);
                    openList.add(targetRecord);
                    openMap.put(target.name, targetRecord);
                } else if (costSoFar < targetRecord.costSoFar) {
                    targetRecord.parent = current;
                    targetRecord.costSoFar = costSoFar;
                    targetRecord.estimatedTotalCost = estimatedTotalCost;
                    openList.remove(targetRecord);
                    openList.add(targetRecord);
                }
            }

            closedMap.put(current.node.name, current);
        }

        return null; // No path found
    }

    private static List<String> reconstructPath(NodeRecord goalRecord) {
        List<String> path = new ArrayList<>();
        NodeRecord current = goalRecord;

        while (current != null) {
            path.add(current.node.name);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }
}

public class Main {
    public final static String[] STATES = {"A", "B", "C", "D", "E", "H", "J", "G1", "G2", "G3"};

    public static void main(String[] args) {
        int[][] cost_matrix = {
                {0, 0, 0, 6, 1, 0, 0, 0, 0, 0},
                {5, 0, 2, 0, 0, 0, 0, 0, 0, 0},
                {9, 3, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 2, 0, 0, 0, 0, 0},
                {6, 0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 7, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 8, 7, 0, 0, 0}
        };

        int[] heuristic_vector = {5, 7, 3, 4, 6, 8, 5, 0, 0, 0};

        Graph graph = new Graph();

        // Add heuristics to the corresponding state
        for (int i = 0; i < STATES.length; ++i) {
            graph.addNode(STATES[i], heuristic_vector[i]);
        }

        // Read the cost & heuristic values into directed-weighted graphs
        int toIndex = 0;
        while (toIndex != cost_matrix.length) {
            for (int fromIndex = 0; fromIndex < cost_matrix.length; ++fromIndex) {
                if (cost_matrix[toIndex][fromIndex] != 0) {
                    graph.addEdge(STATES[fromIndex], STATES[toIndex], cost_matrix[toIndex][fromIndex]);
                }
            }
            toIndex++;
        }

        graph.printGraph();

        // Perform A* search from A to G1
        List<String> path = AStar.aStarSearch(graph, "A", "G1");
        List<String> path1 = AStar.aStarSearch(graph, "A", "G2");
        List<String> path2 = AStar.aStarSearch(graph, "A", "G3");

        // TODO: Add a print for cost of each path (can read from the list and match the numbers with corresponding letters)

        if (path != null) {
            System.out.println("Path from A to G1: " + path);
        } else {
            System.out.println("No path found from A to G1.");
        }

        if (path != null) {
            System.out.println("Path from A to G2: " + path1);
        } else {
            System.out.println("No path found from A to G2.");
        }

        if (path != null) {
            System.out.println("Path from A to G3: " + path2);
        } else {
            System.out.println("No path found from A to G3.");
        }
    }
}
