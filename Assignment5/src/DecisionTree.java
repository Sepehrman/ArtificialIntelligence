import java.io.*;
import java.util.*;
/**
 * @author Sepehr Mansouri
 * Artificial Intelligence - Assignment 5
 * The purpose of this assignment was to develop an ID3 Decision Tree by reading through a .csv file and
 * reading through subsets of data and making relevant decisions based
 */

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

    /**
     * Reads the CSV file, extracts attributes and records.
     * @param filename the path of the CSV file to read.
     * @param attributes a list to hold the attribute names.
     * @return a list of records from the CSV file.
     * @throws IOException if there is an issue reading the file.
     */
    public static List<Record> readCSV(String filename, List<String> attributes) throws IOException {
        List<Record> dataset = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Reading the header to get attribute names
        String line = br.readLine();
        if (line != null) {
            String[] headers = line.split(",");
            attributes.addAll(Arrays.asList(headers).subList(0, headers.length - 1)); // Skip the label column
        }

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            dataset.add(new Record(Arrays.copyOf(values, values.length - 1), values[values.length - 1]));
        }
        br.close();
        return dataset;
    }

    /**
     * Calculates the entropy of the given dataset.
     * @param dataset the dataset for which entropy is calculated.
     * @return the calculated entropy value.
     */
    public static double calculateEntropy(List<Record> dataset) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Record record : dataset) {
            labelCounts.put(record.label, labelCounts.getOrDefault(record.label, 0) + 1);
        }

        double entropy = 0.0;
        int total = dataset.size();
        System.out.println("  Instances: " + total);
        System.out.println("  Counts: " + labelCounts);
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            String label = entry.getKey();
            int count = entry.getValue();
            double probability = (double) count / total;

            System.out.println("  P(" + label + "): " + probability + " (" + count + "/" + total + ")");
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        System.out.println("  Entropy: " + entropy + "\n");
        return entropy;
    }

    /**
     * Calculates the information gain for a specific attribute in the dataset.
     * @param dataset the dataset used for information gain calculation.
     * @param attributeIndex the index of the attribute being evaluated.
     * @param attributeName the name of the attribute.
     * @return the calculated information gain for the given attribute.
     */
    public static double calculateInformationGain(List<Record> dataset, int attributeIndex, String attributeName) {
        System.out.println("\nCalculating entropies for attribute: " + attributeName);

        double totalEntropy = calculateEntropy(dataset);
        Map<String, List<Record>> subsets = new HashMap<>();

        for (Record record : dataset) {
            String key = record.attributes[attributeIndex];
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(record);
        }

        double weightedEntropy = 0.0;
        for (Map.Entry<String, List<Record>> entry : subsets.entrySet()) {
            String subsetValue = entry.getKey();
            List<Record> subset = entry.getValue();

            System.out.println("Subset '" + subsetValue + "'");
            weightedEntropy += ((double) subset.size() / dataset.size()) * calculateEntropy(subset);
        }

        System.out.println("=======================================");
        System.out.println("  Weighted Entropy: " + weightedEntropy);
        return totalEntropy - weightedEntropy;
    }

    /**
     * Determines the best attribute to split the dataset.
     * @param dataset the dataset to be split.
     * @param usedAttributes the set of attributes that have already been used.
     * @param attributes the list of all attributes.
     * @return the index of the best attribute to split on.
     */
    public static int bestAttributeToSplit(List<Record> dataset, Set<Integer> usedAttributes, List<String> attributes) {
        double maxGain = -1;
        int bestAttribute = -1;
        for (int i = 0; i < dataset.get(0).attributes.length; i++) {
            if (!usedAttributes.contains(i)) {
                double gain = calculateInformationGain(dataset, i, attributes.get(i));
                System.out.println("  Information Gain: " + gain + "\n" +
                        "=======================================");
                if (gain > maxGain) {
                    maxGain = gain;
                    bestAttribute = i;
                }
            }
        }

        System.out.println("\nHighest Information Gain is: " + maxGain);
        System.out.println(".·. The best choice to split is " + attributes.get(bestAttribute) + "\n");
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

    /**
     * Builds the decision tree recursively.
     *
     * @param dataset the dataset used to build the tree.
     * @param usedAttributes the set of attributes that have already been used.
     * @param attributes the list of all attributes.
     * @return the root node of the decision tree.
     */
    public static DecisionNode buildDecisionTree(List<Record> dataset, Set<Integer> usedAttributes, List<String> attributes) {
        if (allSameLabel(dataset)) {
            return new DecisionNode(dataset.get(0).label); // Leaf node
        }

        if (usedAttributes.size() == dataset.get(0).attributes.length) {
            return new DecisionNode(majorityLabel(dataset)); // No attributes left
        }

        int bestAttr = bestAttributeToSplit(dataset, usedAttributes, attributes);
        usedAttributes.add(bestAttr);

        Map<String, List<Record>> subsets = splitDatasetByAttribute(dataset, bestAttr);
        Map<String, DecisionNode> children = new HashMap<>();

        for (Map.Entry<String, List<Record>> entry : subsets.entrySet()) {
            children.put(entry.getKey(), buildDecisionTree(entry.getValue(), new HashSet<>(usedAttributes), attributes));
        }

        return new DecisionNode(attributes.get(bestAttr), children);
    }

    /**
     * Checks if all records in the dataset have the same label.
     * @param dataset the dataset to be checked
     * @return true if all records have the same label, false otherwise
     */
    public static boolean allSameLabel(List<Record> dataset) {
        String firstLabel = dataset.get(0).label;
        for (Record record : dataset) {
            if (!record.label.equals(firstLabel)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the majority label in the dataset.
     * @param dataset the dataset to find the majority label from
     * @return the label that occurs most frequently in the dataset
     */
    public static String majorityLabel(List<Record> dataset) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Record record : dataset) {
            labelCounts.put(record.label, labelCounts.getOrDefault(record.label, 0) + 1);
        }
        return Collections.max(labelCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Splits the dataset by the given attribute index.
     * @param dataset the dataset to be split
     * @param attributeIndex the index of the attribute to split by
     * @return a map where the keys are attribute values and the values are subsets of the dataset
     */
    public static Map<String, List<Record>> splitDatasetByAttribute(List<Record> dataset, int attributeIndex) {
        Map<String, List<Record>> subsets = new HashMap<>();
        for (Record record : dataset) {
            String key = record.attributes[attributeIndex];
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(record);
        }
        return subsets;
    }


    /**
     * Prints the decision tree recursively.
     * @param node the root node of the tree.
     * @param indent the string used to indent each level of the tree.
     */
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

    /**
     * Main method to execute the decision tree process.
     * @param args command line arguments (expects the first argument to be the filename of the dataset).
     * @throws IOException if there is an issue with file reading.
     */
    public static void main(String[] args) throws IOException {
        try {
            if (args.length != 1) {
                System.out.println("You must include a .csv file to read from.");
                return;
            }
            String file = args[0];
            List<String> attributes = new ArrayList<>();
            List<Record> dataset = readCSV(file, attributes);  // Read file and header labels
            DecisionNode tree = buildDecisionTree(dataset, new HashSet<>(), attributes);
            System.out.println("Process Finished.\n\nDecision Tree Structure: ");
            printTree(tree, "  ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}