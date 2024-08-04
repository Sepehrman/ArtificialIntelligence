import java.util.*;
/**
 * @author Sepehr Mansouri
 * Artificial Intelligence - Assignment 2
 * The purpose of this assignment was to develop an A * Search Algorithm from a start state to a given goal state.
 * This was done through keeping track of a search space using it's cost matrix and heuristic vector
 */

/**
 * Represents a node object for our graph data structure.
 */
class Node {
    String name;
    int heuristic;
    List<Edge> edges;

    /**
     * Constructs a new Node object with the specified name and heuristic value.
     * @param name The name of the node.
     * @param heuristic The heuristic value of the node.
     */
    Node(String name, int heuristic) {
        this.name = name;
        this.heuristic = heuristic;
        this.edges = new ArrayList<>();
    }

    /**
     * Adds an edge to this node towards a specified target node with a given cost.
     * (In other words, creates a path with a cost).
     * @param target The target node of the edge.
     * @param cost The cost of the edge.
     */
    void addEdge(Node target, int cost) {
        edges.add(new Edge(this, target, cost));
    }
}

/**
 * Represents an edge in the graph connecting two nodes with a specified cost.
 */
class Edge {
    Node source;
    Node target;
    int cost;

    /**
     * Constructs a new Edge object with the specified source node, target node, and cost.
     * @param source The source node of the edge.
     * @param target The target node of the edge.
     * @param cost The cost of the edge.
     */
    Edge(Node source, Node target, int cost) {
        this.source = source;
        this.target = target;
        this.cost = cost;
    }
}

/**
 * The following class represents a Graph implementation that keeps track of the states along with the nodes
 */
class Graph {
    Map<String, Node> nodes;
    List<MyClass.PathResult> pathResults = new ArrayList<>();

    Graph() {
        nodes = new HashMap<>();
    }

    void addPath(MyClass.PathResult path) {
        pathResults.add(path);
    }

    /**
     * Adds a node to the graph with the specified name and heuristic value.
     * @param name The name of the node.
     * @param heuristic The heuristic value of the node.
     */
    void addNode(String name, int heuristic) {
        nodes.put(name, new Node(name, heuristic));
    }

    /**
     * Adds an edge between two nodes in the graph with a specified path cost.
     * @param sourceName The name of the source node.
     * @param targetName The name of the target node.
     * @param pathCost The cost of the path from source to target.
     */
    void addEdge(String sourceName, String targetName, int pathCost) {
        Node source = nodes.get(sourceName);
        Node target = nodes.get(targetName);
        if (source != null && target != null) {
            source.addEdge(target, pathCost);
        } else {
            System.out.println("Source or Target node not found.");
        }
    }

    /**
     * Retrieves a node given its name.
     * @param name The name of the node to retrieve.
     * @return The node with the specified name, or null if not found.
     */
    Node getNode(String name) {
        return nodes.get(name);
    }

    /**
     * Prints the graph, showing each node, its heuristic value, and its edges with costs.
     */
    void printGraph() {
        for (Node node : nodes.values()) {
            System.out.print("Node " + node.name + " (Heuristic: " + node.heuristic + ") -> ");
            for (Edge edge : node.edges) {
                System.out.print(edge.target.name + " (Cost: " + edge.cost + "), ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/**
 * Implements the A* search algorithm for finding the shortest path in reaching a goal state within the graph.
 */
public class MyClass {

    // Represents a node record in the priority queue
    static class NodeRecord implements Comparable<NodeRecord> {
        Node node;
        NodeRecord parent;
        int costSoFar;
        int estimatedTotalCost;

        /**
         * Constructs a new NodeRecord with the specified node, parent, cost so far, and estimated total cost.
         * @param node The node represented by this record.
         * @param parent The parent node record.
         * @param costSoFar The cost incurred to reach this node.
         * @param estimatedTotalCost The estimated total cost to reach the goal.
         */
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

    // Represents the result of the A* search
    public static class PathResult {
        List<String> path;
        List<Integer> costs;
        int numberOfCycles;

        /**
         * Constructs a new PathResult with the specified path, costs, and number of cycles.
         * @param path The path found by the search.
         * @param costs The costs associated with the path.
         * @param numberOfCycles The number of cycles performed in the search.
         */
        PathResult(List<String> path, List<Integer> costs, int numberOfCycles) {
            this.path = path;
            this.costs = costs;
            this.numberOfCycles = numberOfCycles;
        }

        public void printForCheapestPath() {
            StringBuilder pathBuilder = new StringBuilder();
            StringBuilder costsBuilder = new StringBuilder();
            final String SOURCE = path.get(0);
            final String DESTINATION = path.get(path.size() - 1);
            int sum = 0;

            int size = Math.min(path.size(), costs.size());
            pathBuilder.append("Cheapest path is from ").append(SOURCE).append(" to ").append(DESTINATION).append(". With the Path: ");

            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    pathBuilder.append(" -> ");
                }

                sum += costs.get(i);

                pathBuilder.append(path.get(i));
            }
            pathBuilder.append(" -> ").append(DESTINATION);
            pathBuilder.append("\nOverall Cost of: ").append(sum).append(" and number of cycles ").append(numberOfCycles);

            System.out.println(pathBuilder);

        }

        /**
         * Prints the results of the search, including the path, costs, and the number of cycles.
         */
        public void printResults() {
            StringBuilder pathBuilder = new StringBuilder();
            StringBuilder costsBuilder = new StringBuilder();
            final String SOURCE = path.get(0);
            final String DESTINATION = path.get(path.size() - 1);
            int sum = 0;

            int size = Math.min(path.size(), costs.size());
            System.out.println("From " + SOURCE + " to " + DESTINATION + ":");
            pathBuilder.append("Path:  ");
            costsBuilder.append("Cost:    ");

            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    pathBuilder.append(" -> ");
                    costsBuilder.append("    ");
                }

                sum += costs.get(i);

                pathBuilder.append(path.get(i));
                costsBuilder.append(costs.get(i));
            }
            pathBuilder.append(" -> ").append(DESTINATION);
            costsBuilder.append("   =  ").append(sum);
            System.out.println(pathBuilder);
            System.out.println(costsBuilder);
            System.out.println("Number of cycles: " + numberOfCycles + "\n");
        }
    }

    /**
     * Runs the A* Search from a starting state to a goal state, keeping track of the cost and heuristic vector.
     * @param graph The graph to search.
     * @param startName The name of the start node.
     * @param goalName The name of the goal node.
     * @return The result of the search containing the path, costs, and number of cycles, or null if no path is found.
     */
    public static PathResult performAStarSearch(Graph graph, String startName, String goalName) {
        Node start = graph.getNode(startName);
        Node goal = graph.getNode(goalName);

        PriorityQueue<NodeRecord> openList = new PriorityQueue<>();
        Map<String, NodeRecord> openMap = new HashMap<>();
        Map<String, NodeRecord> closedMap = new HashMap<>();

        NodeRecord startRecord = new NodeRecord(start, null, 0, start.heuristic);
        openList.add(startRecord);
        openMap.put(start.name, startRecord);

        int numberOfCycles = 0;

        while (!openList.isEmpty()) {
            numberOfCycles++;
            NodeRecord current = openList.poll();
            openMap.remove(current.node.name);

            if (current.node.name.equals(goal.name)) {
                return reconstructPath(current, numberOfCycles);
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

    /**
     * Reconstructs the path from the goal node record back to the start node.
     * @param goalRecord The NodeRecord for the goal node.
     * @param numberOfCycles The number of cycles performed in the search.
     * @return The PathResult containing the reconstructed path, costs, and number of cycles.
     */
    private static PathResult reconstructPath(NodeRecord goalRecord, int numberOfCycles) {
        List<String> path = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        NodeRecord current = goalRecord;

        while (current != null) {
            path.add(current.node.name);
            if (current.parent != null) {
                costs.add(current.costSoFar - current.parent.costSoFar);
            }
            current = current.parent;
        }

        Collections.reverse(path);
        Collections.reverse(costs);
        return new PathResult(path, costs, numberOfCycles);
    }

    // Main method for running the A* search
    public static void main(String[] args) {
        final String[] STATES = {"A", "B", "C", "D", "E", "H", "J", "G1", "G2", "G3"};

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

        for (int i = 0; i < STATES.length; ++i) {
            graph.addNode(STATES[i], heuristic_vector[i]);
        }

        for (int toIndex = 0; toIndex < cost_matrix.length; toIndex++) {
            for (int fromIndex = 0; fromIndex < cost_matrix.length; fromIndex++) {
                if (cost_matrix[toIndex][fromIndex] != 0) {
                    graph.addEdge(STATES[fromIndex], STATES[toIndex], cost_matrix[toIndex][fromIndex]);
                }
            }
        }



        PathResult resultG1 = performAStarSearch(graph, "A", "G1");
        PathResult resultG2 = performAStarSearch(graph, "A", "G2");
        PathResult resultG3 = performAStarSearch(graph, "A", "G3");

        graph.addPath(resultG1);
        graph.addPath(resultG2);
        graph.addPath(resultG3);


        PathResult cheapestPath = null;
        int minCost = Integer.MAX_VALUE;


        for (PathResult result : graph.pathResults) {
            result.printResults();
            int cost = result.costs.stream().mapToInt(Integer::intValue).sum();
            if (cost < minCost) {
                minCost = cost;
                cheapestPath = result;
            }
        }

        // Determine and print the cheapest path


        if (cheapestPath != null) {
            cheapestPath.printForCheapestPath();
        } else {
            System.out.println("No valid paths found.");
        }
    }
}
