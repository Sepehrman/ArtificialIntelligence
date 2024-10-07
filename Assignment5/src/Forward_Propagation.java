import java.util.Random;

class Forward_Propagation {
    // Inputs and outputs for training
    double[][] inputs = new double[][]{
            {0, 0, 1}, // Instance 1
            {1, 1, 1}, // Instance 2
            {1, 0, 1}, // Instance 3
            {0, 1, 1}  // Instance 4
    };

    double[][] outputs = new double[][]{
            {0}, // Fake
            {1}, // Real
            {1}, // Real
            {0}  // Fake
    };

    double[] weights = new double[3]; // Weights for 3 inputs
    double bias;
    Random rand = new Random();

    public Forward_Propagation() {
        // Initialize weights and bias
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble(); // Random weight between 0 and 1
        }
        bias = rand.nextDouble(); // Random bias
    }

    // Sigmoid activation function
    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    // Calculate output for a given input
    public double[] calculateOutput(double[] input) {
        double sum = bias; // Start with bias
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[i];
        }
        double activatedOutput = sigmoid(sum);
        return new double[]{activatedOutput}; // Return activated output
    }
}
