import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;



class Split {

    private double averageEntropies;
    private double total;
    private String subject;
    private ArrayList<Subset> subsets;
    private double parentEntropy;
    private double informationGain;

    public Split() {
        this.total = 0;
        subsets = new ArrayList<Subset>();
        parentEntropy = 0;
    }

    public void setInformationGain(double informationGain) {
        this.informationGain = informationGain;
    }

    public void setParentEntropy(double parentEntropy) {
        this.parentEntropy = parentEntropy;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<Subset> getSubsets() {
        return subsets;
    }

    public void addSubset(Subset subset) {
        subsets.add(subset);
        total += subset.getInstanceCount();
    }



    public void flush() {
        subsets.clear();
    }

    public double getAverageEntropies() {
        return averageEntropies;
    }

    public void calculateAverageEntropies() {
        averageEntropies = 0;
        for (Subset subset : subsets) {
            averageEntropies += ((subset.getInstanceCount()/total) * subset.getEntropy());
        }
    }

    public double getInformationGain() {
        return parentEntropy - averageEntropies;
    }


    @Override
    public String toString() {
        return "Weighted Avg. Entropy: " + averageEntropies + "\nInformation Gain: " + getInformationGain();
    }

}



class Subset {
    private final String attributeName;
    private final String attributeValue;
    private final double entropy;
    private final double parentEntropy;
    private final double instanceCount;


    public Subset(double instanceCount, String attributeName, String attributeValue, double entropy, double parentEntropy) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.entropy = entropy;
        this.parentEntropy = parentEntropy;
        this.instanceCount = instanceCount;
    }


    public double getInstanceCount() {
        return instanceCount;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String toString() {
        return "Subset for " + attributeName + ", Value: " + attributeValue
                + ", Entropy: " + entropy + ", Parent Entropy: " + parentEntropy + ", " +
                "instanceCount: " + instanceCount;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public double getEntropy() {
        return entropy;
    }

    public double getParentEntropy() {
        return parentEntropy;
    }
}

public class DecisionTree {
    private final HashMap<Integer, String> attributesIndices = new HashMap<>();
    private final ArrayList<String[]> data = new ArrayList<>();
    private final String file;

    public DecisionTree(String filename) {
        this.file = filename;
        readData();
    }


    public void readData() {
        try {
            // Create objects to read the file
            File file = new File(this.file);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            // Store current file line and its data
            String currentLine;
            String[] currentFileData;

            // Read the first line with the attributes (column names) in the file
            currentLine = bufferReader.readLine();
            currentFileData = currentLine.split(",");
            // Store the attributes and their column index in the file
            for (int columnIndex = 0; columnIndex < currentFileData.length; columnIndex++) {
                String attributeName = currentFileData[columnIndex];
                attributesIndices.put(columnIndex, attributeName);
            }

            // Read the file line by line
            while ((currentLine = bufferReader.readLine()) != null) {
                // Get line of data
                currentFileData = currentLine.split(",");
                // Store data in the ArrayList
                data.add(currentFileData);
            }
            bufferReader.close();

        } catch (Exception exception) {
            // If an error occurs, print the error message
            System.out.println("Error: " + exception.getMessage());
        }
    }

    public double calculateEntropy(double recordFraction) {
        if (recordFraction == 0) {
            return 0;
        }
        return -recordFraction * (Math.log(recordFraction) / Math.log(2));

    }

    public double calculateTotalChildEntropy(ArrayList<Double> recordFractions) {
        // Calculate child entropy: H(X) = −p(x1)log2 p(x1) −p(x2)log2 p(x2) ... −p(xn)log2 p(xn)
        double childEntropy = 0;
        // Sum all entropy values
        for (double currentFraction : recordFractions) {

            childEntropy += calculateEntropy(currentFraction);
        }

        System.out.println(" Child Entropy = " + childEntropy);
        return childEntropy;
    }

    public double calculateAverageChildrenEntropy(ArrayList<double[]> fractionEntropyPairs) {
        double averageEntropy = 0;

        // Loop through each set of pairs in the ArrayList
        for (double[] currentPair : fractionEntropyPairs) {
            double currentFraction = currentPair[0];
            double currentEntropy = currentPair[1];

            // Calculate weighted average entropy by multiplying each fraction by its entropy, and sum the results
            averageEntropy += currentFraction * currentEntropy;
        }

        System.out.println("Average Children Entropy = " + averageEntropy);
        return averageEntropy;
    }

    public Subset getHighestInformationGain(ArrayList<Subset> subsets) {


        Subset highestSubset = subsets.get(0);
        System.out.println("ARR  " + subsets);
//        double highestInformationGain = highestSubset.getInformationGain();
//        for (Subset currentSubset : subsets) {
//            if (currentSubset.getInformationGain() > highestInformationGain) {
//                highestInformationGain = currentSubset.getInformationGain();
//            }
//        }

//        System.out.println("Highest Information Gain = " + highestInformationGain);
        // Return the entropy of the subset with the highest information gain, so it can become the next parent entropy
        return highestSubset;
    }

    public HashSet<String> getUniqueAttributeValues(int attributeIndex) {
        HashSet<String> attributeValues = new HashSet<>();

        // Loop through all rows in the dataset
        for (String[] currentData : data) {
            attributeValues.add(currentData[attributeIndex]);
        }

        return attributeValues;
    }

    public double calculateSubsetEntropy(ArrayList<String[]> subsetData) {
        // Store the index of the attribute to split on
        int booleanIndex = attributesIndices.size() - 1;
        int yesValueCount = 0;
        int noValueCount = 0;
        double totalValues = subsetData.size();

        // Loop through all data to count yes and no occurrences
        for (String[] currentData : subsetData) {
            String booleanValue = currentData[booleanIndex];
            if (booleanValue.equalsIgnoreCase("yes")) {
                yesValueCount++;
            } else {
                noValueCount++;
            }
        }

        double yesFraction = yesValueCount / totalValues;
        double noFraction = noValueCount / totalValues;

        System.out.println(" Instances: " + totalValues + "\n # Positive datasets ((A+) grade in programming) = " +
                yesValueCount + "\n # Negative datasets (No (A+) grade in programming) = " + noValueCount);
        System.out.println(" Yes fraction = " + yesValueCount + "/" + totalValues);
        System.out.println(" No fraction = " + noValueCount + "/" + totalValues);

        ArrayList<Double> recordFractions = new ArrayList<>();
        recordFractions.add(yesFraction);
        recordFractions.add(noFraction);
        return calculateTotalChildEntropy(recordFractions);
    }

    public HashMap<String, ArrayList<String[]>> splitAttributeData(ArrayList<String[]> data, int attributeIndex) {
        // Stores pairs of: attribute name, all data values for that attribute
        HashMap<String, ArrayList<String[]>> splitData = new HashMap<>();

        // Loop through all rows in the dataset
        for (String[] currentData : data) {
            // Get the value of the attribute to split on
            String attributeValue = currentData[attributeIndex];

            // If the value doesn't exist in the map, create a new list for it
            if (!splitData.containsKey(attributeValue)) {
                splitData.put(attributeValue, new ArrayList<>());
            }

            // Add the current row to the appropriate list
            splitData.get(attributeValue).add(currentData);
        }

        return splitData;
    }

    public void calculateAllSubsetsEntropy(HashMap<String, ArrayList<String[]>> subsets, double parentEntropy) {
        for (String attributeValue : subsets.keySet()) {
            ArrayList<String[]> subset = subsets.get(attributeValue);

            // Calculate entropy for this subset
            double subsetEntropy = calculateSubsetEntropy(subset);

            // Calculate information gain: Information Gain = entropy(parent) – [average entropy(children)]
            double informationGain = parentEntropy - subsetEntropy;

            // Print the subset values along with its entropy and information gain
            System.out.println("SUBSET for attribute value: " + attributeValue);
            for (String[] arrayValue: subset) {
                for (String value: arrayValue) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            System.out.println("Entropy: " + subsetEntropy);
            System.out.println("Information Gain: " + informationGain);
            System.out.println();
        }
    }

    public double calculateParentEntropy() {
        System.out.println("Entropy of the whole dataset:");

        double parentEntropy = calculateSubsetEntropy(data);
        System.out.println("Dataset Entropy: " + parentEntropy);

        ArrayList<Subset> parentEntropyOptions = new ArrayList<>(); // Don't modify yet
        ArrayList<Split> entropiesOptions = new ArrayList<Split>();

        for (int attributeIndex = 0; attributeIndex < attributesIndices.size() - 1; attributeIndex++) {
            System.out.println("**************************************************");
            String attribute = attributesIndices.get(attributeIndex);
            System.out.println("\nSplit based on Subject of " + attribute);
            HashMap<String, ArrayList<String[]>> subsets = splitAttributeData(data, attributeIndex);
            Split splitCollection = new Split();

            for (String attributeValue : subsets.keySet()) {
                System.out.println("- Subset of grade " + attributeValue + ":");
                ArrayList<String[]> subset = subsets.get(attributeValue);
                double subsetEntropy = calculateSubsetEntropy(subset);
                Subset currentSubset = new Subset(subset.size(), attribute, attributeValue, subsetEntropy, parentEntropy);
                System.out.println();
                splitCollection.addSubset(currentSubset);
            }
            splitCollection.setSubject(attribute);
            splitCollection.calculateAverageEntropies();
            splitCollection.setParentEntropy(parentEntropy);
            entropiesOptions.add(splitCollection);
            System.out.println(splitCollection);
        }
        Split splitWithHighestInfoGain = findSubsetsOfHighestInfoGain(entropiesOptions);
        Subset nextParentSubset = getHighestInformationGain(parentEntropyOptions);
        return nextParentSubset.getEntropy();
    }


    public Split findSubsetsOfHighestInfoGain(List<Split> listSubsets) {
        Split pickedSplit = null;
        double highestInfoGain = 0.0;
        for (Split split : listSubsets) {
            if (split.getInformationGain() > highestInfoGain) {
                highestInfoGain = split.getInformationGain();
                pickedSplit = split;
            }
        }
        System.out.println("\n\n**************************************************");
        System.out.println("Highest Information Gain: " + highestInfoGain);
        System.out.println("Next Subset to split is on subject " + pickedSplit.getSubject());
        return pickedSplit;
    }


    /**
     * Runs the ID3 algorithm, starting with receiving its current parent entropy
     */
    public void runID3() {
        double parentEntropy = calculateParentEntropy();
        int numAttributes = data.get(0).length;
        for (int index = 0; index < numAttributes; index++) {
            HashMap<String, ArrayList<String[]>> currentAttributeData = splitAttributeData(data, index);
            calculateAllSubsetsEntropy(currentAttributeData, parentEntropy);
        }
    }


    public static void main(String[] args) {

        try {
            String filename = args[0];
            DecisionTree decisionTree = new DecisionTree(filename);
            decisionTree.runID3();
        } catch (Exception e) {
            System.out.println("Please enter a valid .csv file for the Decision Tree\n" + e.getMessage());
        }

    }
}