import java.util.Random;

class Forward_Propagation {
    public double[][] inputs = new double[][]{
            {0, 0, 1},
            {1, 1, 1},
            {1, 0, 1},
            {0, 1, 1}
    };

    public double[][] outputs = new double[][]{
            {0},
            {1},
            {1},
            {0}
    };

    private double[] weights;
    private double bias;

    public Forward_Propagation() {
        Random random = new Random();
        weights = new double[inputs[0].length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble();
        }
        bias = random.nextDouble();
    }

    public double[] calculateOutput(double[] input) {
        double linearOutput = 0;
        for (int i = 0; i < weights.length; i++) {
            linearOutput += input[i] * weights[i];
        }
        linearOutput += bias;
        return new double[]{sigmoid(linearOutput)};
    }

    public void updateWeights(double[] input, double expectedOutput, double actualOutput, double learningRate) {
        double error = expectedOutput - actualOutput;
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * input[i];
        }
        bias += learningRate * error; // Update bias
    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

}
