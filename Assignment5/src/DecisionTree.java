import java.io.*;
import java.util.*;

public class DecisionTree {

    // Data structure to hold records
    static class Record {
        String[] attributes;
        String label;

        Record(String[] attributes, String label) {
            this.attributes = attributes;
            this.label = label;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "attributes=" + Arrays.toString(attributes) +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    public static List<Record> readCSV(String filename) throws IOException {
        List<Record> dataset = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        br.readLine(); // Skip the header
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            dataset.add(new Record(Arrays.copyOf(values, values.length - 1), values[values.length - 1]));
        }
        br.close();
        return dataset;
    }


    // Entropy calculation
    public static double calculateEntropy(List<Record> dataset) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Record record : dataset) {
            labelCounts.put(record.label, labelCounts.getOrDefault(record.label, 0) + 1);
        }

        double entropy = 0.0;
        int total = dataset.size();
        System.out.println("Instances: " + total);
        System.out.println("Counts: " + labelCounts);
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            String label = entry.getKey();
            int count = entry.getValue();
            double probability = (double) count / total;

            System.out.println("P(" + label + "): " + probability + " (" + count + "/" + total + ")");
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        System.out.println("entropy: " + entropy + "\n");
        return entropy;
    }

    // Information Gain calculation
    public static double calculateInformationGain(List<Record> dataset, int attributeIndex) {
        double totalEntropy = calculateEntropy(dataset);
        Map<String, List<Record>> subsets = new HashMap<>();


        for (Record record : dataset) {
            String key = record.attributes[attributeIndex];
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(record);
        }

        double weightedEntropy = 0.0;
        for (List<Record> subset : subsets.values()) {
            weightedEntropy += ((double) subset.size() / dataset.size()) * calculateEntropy(subset);
        }

        System.out.println("Result across the current split:");

        System.out.println("  Weighted Entropy: " + weightedEntropy);
        return totalEntropy - weightedEntropy;
    }

    // Find the attribute with the highest information gain
    public static int bestAttributeToSplit(List<Record> dataset, Set<Integer> usedAttributes) {
        double maxGain = -1;
        int bestAttribute = -1;
        for (int i = 0; i < dataset.get(0).attributes.length; i++) {
            if (!usedAttributes.contains(i)) {
                double gain = calculateInformationGain(dataset, i);
                System.out.println("  Information Gain: " + gain);
                if (gain > maxGain) {
                    maxGain = gain;
                    bestAttribute = i;
                }
            }
        }

        System.out.println("\nHighest Information Gain is: " + maxGain);
        System.out.println("The best choice to split is " + bestAttribute);
        return bestAttribute;
    }



    static class DecisionNode {
        String label;
        String attribute;
        Map<String, DecisionNode> children = new HashMap<>();

        public DecisionNode(String label) {
            this.label = label;
        }

        public DecisionNode(String attribute, Map<String, DecisionNode> children) {
            this.attribute = attribute;
            this.children = children;
        }
    }

    // Recursive function to build the decision tree
    public static DecisionNode buildDecisionTree(List<Record> dataset, Set<Integer> usedAttributes, List<String> attributes) {
        if (allSameLabel(dataset)) {
            return new DecisionNode(dataset.get(0).label); // Leaf node
        }

        if (usedAttributes.size() == dataset.get(0).attributes.length) {
            return new DecisionNode(majorityLabel(dataset)); // No attributes left
        }

        int bestAttr = bestAttributeToSplit(dataset, usedAttributes);
        usedAttributes.add(bestAttr);

        Map<String, List<Record>> subsets = splitDatasetByAttribute(dataset, bestAttr);
        Map<String, DecisionNode> children = new HashMap<>();

        for (Map.Entry<String, List<Record>> entry : subsets.entrySet()) {
            children.put(entry.getKey(), buildDecisionTree(entry.getValue(), new HashSet<>(usedAttributes), attributes));
        }

        return new DecisionNode(attributes.get(bestAttr), children);
    }

    // Helper methods to check labels
    public static boolean allSameLabel(List<Record> dataset) {
        String firstLabel = dataset.get(0).label;
        for (Record record : dataset) {
            if (!record.label.equals(firstLabel)) {
                return false;
            }
        }
        return true;
    }

    public static String majorityLabel(List<Record> dataset) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Record record : dataset) {
            labelCounts.put(record.label, labelCounts.getOrDefault(record.label, 0) + 1);
        }
        return Collections.max(labelCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static Map<String, List<Record>> splitDatasetByAttribute(List<Record> dataset, int attributeIndex) {
        Map<String, List<Record>> subsets = new HashMap<>();
        for (Record record : dataset) {
            String key = record.attributes[attributeIndex];
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(record);
        }
        return subsets;
    }

    public static List<String> getAllAttributes(List<Record> dataset) {
        return Arrays.asList("Math", "Statistics", "Science", "English");
    }


    // Print the decision tree recursively
    public static void printTree(DecisionNode node, String indent) {
        if (node.children.isEmpty()) {
            System.out.println(indent + "-> " + node.label);
        } else {
            System.out.println(indent + "[Attribute: " + node.attribute + "]");
            for (Map.Entry<String, DecisionNode> child : node.children.entrySet()) {
                System.out.println(indent + "--> " + child.getKey());
                printTree(child.getValue(), indent + "  ");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            String file = args[0];
            List<Record> dataset = readCSV(file);  // Adjust your path to the CSV file
            System.out.println(dataset);
            DecisionNode tree = buildDecisionTree(dataset, new HashSet<>(), getAllAttributes(dataset));
            printTree(tree, "  ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
