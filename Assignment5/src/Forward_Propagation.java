import java.util.Random;

/**
 * @author Sepehr Mansouri
 * Artificial Intelligence - Assignment 5
 * The Forward_Propagation class implements a simple feedforward neural network.
 * It uses a single layer of weights and a bias to perform binary classification
 * on the provided input data using the sigmoid activation function.
 */

class Forward_Propagation {
    /**
     * Input dataset for the neural network.
     * Each row represents a sample with features.
     */
    public double[][] inputs = new double[][]{
            {0, 0, 1},
            {1, 1, 1},
            {1, 0, 1},
            {0, 1, 1}
    };

    /**
     * Corresponding output dataset for the neural network.
     * Each row represents the expected output for each sample.
     */
    public double[][] outputs = new double[][]{
            {0},
            {1},
            {1},
            {0}
    };

    /**
     * Array of weights for the neural network.
     */
    private double[] weights;

    /**
     * The bias value for the neural network.
     */
    private double bias;

    /**
     * Constructor for the Forward_Propagation class.
     * Initializes the weights and bias with random values.
     */
    public Forward_Propagation() {
        Random random = new Random();
        weights = new double[inputs[0].length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble();
        }
        bias = random.nextDouble();
    }

    /**
     * Calculates the output of the neural network for a given input.
     * @param input The input array for which the output is to be calculated.
     * @return The calculated output after applying the sigmoid activation function.
     */
    public double[] calculateOutput(double[] input) {
        double linearOutput = 0;
        for (int i = 0; i < weights.length; i++) {
            linearOutput += input[i] * weights[i];
        }
        linearOutput += bias;
        return new double[]{sigmoid(linearOutput)};
    }

    /**
     * Updates the weights and bias of the neural network using the calculated output
     * and the expected output based on the error.
     * @param input The input data used for weight adjustment.
     * @param expectedOutput The expected output for the given input.
     * @param actualOutput The actual output calculated by the network.
     * @param learningRate The rate at which the weights are updated.
     */
    public void updateWeights(double[] input, double expectedOutput, double actualOutput, double learningRate) {
        double error = expectedOutput - actualOutput;
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * input[i];
        }
        bias += learningRate * error; // Update bias
    }

    /**
     * Computes the sigmoid activation function.
     * @param x The input value.
     * @return The output of the sigmoid function.
     */
    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * Computes the derivative of the sigmoid function
     * @param x The output of the sigmoid function.
     * @return The derivative of the sigmoid function.
     */
    public double sigmoidDerivative(double x) {
        return x * (1 - x);
    }
}
