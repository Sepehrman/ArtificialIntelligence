import java.util.Random;

class Forward_Propagation {
    // Inputs and outputs for training

    Random rand = new Random();
    double[] weights = new double[3];
    double bias;


    double[][] inputs = new double[][]{
            {0, 0, 1},
            {1, 1, 1},
            {1, 0, 1},
            {0, 1, 1}
    };

    double[][] outputs = new double[][]{
            {0},
            {1},
            {1},
            {0}
    };

    public double[][] getInputs() {
        return inputs;
    }

    public double[][] getOutputs() {
        return outputs;
    }

    public double getBias() {
        return bias;
    }

    public Forward_Propagation() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble();
        }
        bias = rand.nextDouble(); // Random bias
    }

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
