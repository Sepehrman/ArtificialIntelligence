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
    static class NodeCost implements Comparable<NodeCost> {
        Node node;
        int cost;
        int estimatedTotalCost;

        NodeCost(Node node, int cost, int estimatedTotalCost) {
            this.node = node;
            this.cost = cost;
            this.estimatedTotalCost = estimatedTotalCost;
        }

        @Override
        public int compareTo(NodeCost other) {
            return Integer.compare(this.estimatedTotalCost, other.estimatedTotalCost);
        }
    }

    public static List<String> aStarSearch(Graph graph, String startName, String goalName) {
        Node start = graph.getNode(startName);
        Node goal = graph.getNode(goalName);

        if (start == null || goal == null) {
            System.out.println("Start or Goal node not found.");
            return Collections.emptyList();
        }

        PriorityQueue<NodeCost> openSet = new PriorityQueue<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Integer> gScore = new HashMap<>();
        Set<Node> closedSet = new HashSet<>();

        openSet.add(new NodeCost(start, 0, start.heuristic));
        gScore.put(start, 0);

        while (!openSet.isEmpty()) {
            NodeCost current = openSet.poll();
            Node currentNode = current.node;

            System.out.println("Exploring node " + currentNode.name + " with current cost " + current.cost + " and heuristic " + currentNode.heuristic);

            if (currentNode.equals(goal)) {
                return reconstructPath(cameFrom, currentNode);
            }

            closedSet.add(currentNode);

            for (Edge edge : currentNode.edges) {
                Node neighbor = edge.target;
                int tentativeGScore = gScore.getOrDefault(currentNode, Integer.MAX_VALUE) + edge.cost;

                if (closedSet.contains(neighbor)) {
                    continue;
                }

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, currentNode);
                    gScore.put(neighbor, tentativeGScore);
                    int fScore = tentativeGScore + neighbor.heuristic;
                    openSet.add(new NodeCost(neighbor, tentativeGScore, fScore));

                    System.out.println("Adding node " + neighbor.name + " to open set with cost " + tentativeGScore + " and fScore " + fScore);
                }
            }
        }

        return Collections.emptyList(); // Path not found
    }

    private static List<String> reconstructPath(Map<Node, Node> cameFrom, Node currentNode) {
        List<String> path = new LinkedList<>();
        while (currentNode != null) {
            path.add(0, currentNode.name);
            currentNode = cameFrom.get(currentNode);
        }
        return path;
    }
}

public class Main {


    public final static String[] STATES = {"A", "B", "C", "D", "E", "H", "J", "G1", "G2", "G3"};


    public static void main(String[] args) {


        int[][] cost_matrix = {
                {0,0,0,6,1,0,0,0,0,0},
                {5,0,2,0,0,0,0,0,0,0},
                {9,3,0,0,0,0,0,0,0,0},
                {0,0,1,0,2,0,0,0,0,0},
                {6,0,0,0,0,2,0,0,0,0},
                {0,0,0,7,0,0,0,0,0,0},
                {0,0,0,0,2,0,0,0,0,0},
                {0,9,0,0,0,0,0,0,0,0},
                {0,0,0,5,0,0,0,0,0,0},
                {0,0,0,0,0,8,7,0,0,0}
        };

        int[] heuristic_vector= {5,7,3,4,6,8,5,0,0,0};
        List<Integer> goalStates = new ArrayList<Integer>();
        for (int i = 0; i < heuristic_vector.length; i++) {
            if (heuristic_vector[i] == 0) {
                goalStates.add(i);
            }
        }


        Graph graph = new Graph();

        // Add heuristics to the corresponding state
        for (int i = 0; i < STATES.length; ++i) {
            graph.addNode(STATES[i], heuristic_vector[i]);
        }




        // Read the cost & heuristic values into directed-weighted graphs
        int toIndex = 0;
        while (toIndex != cost_matrix.length) {
            for (int fromIndex = 0; fromIndex < cost_matrix.length; ++ fromIndex) {

                if (cost_matrix[toIndex][fromIndex] != 0) {
                    graph.addEdge(STATES[fromIndex], STATES[toIndex], cost_matrix[toIndex][fromIndex]);

                }
            }
            toIndex++;
        }

        graph.printGraph();



//        // Add edges with costs
//        graph.addEdge("A", "B", 1);
//        graph.addEdge("A", "C", 4);
//        graph.addEdge("B", "C", 2);
//        graph.addEdge("B", "D", 5);
//        graph.addEdge("C", "E", 3);
//        graph.addEdge("D", "E", 1);

        // Print the graph to see the structure




        // Identify the Goal States and save them in a new vector
        // Write a program to implement the A * searrch
        // Print the cheapest path, the goal state and the number of cycles

        // Perform A* search from A to E
//        List<String> path = AStar.aStarSearch(graph, "A", "E");
//        System.out.println("Path from A to E: " + path);
    }
}
