
class Train_Test {
    Forward_Propagation perceptron = new Forward_Propagation();
    double learningRate = 0.1;

    // Train the perceptron
    public void train() {
        for (int epoch = 0; epoch < 1000; epoch++) {
            for (int i = 0; i < perceptron.inputs.length; i++) {
                double[] input = perceptron.inputs[i];
                double[] output = perceptron.outputs[i];

                // Calculate the predicted output
                double[] predicted = perceptron.calculateOutput(input);
                double error = output[0] - predicted[0];

                // Update weights and bias
                for (int j = 0; j < perceptron.weights.length; j++) {
                    perceptron.weights[j] += learningRate * error * input[j];
                }
                perceptron.bias += learningRate * error;
            }
        }
    }

    // Test the perceptron on a new instance
    public void test(double[] instance) {
        double[] result = perceptron.calculateOutput(instance);
        String classification = result[0] >= 0.5 ? "Real" : "Fake";
        System.out.println("The alarm is: " + classification);
    }

    public static void main(String[] args) {
        Train_Test trainer = new Train_Test();
        trainer.train(); // Train the perceptron

        // Test with Instance 5: {0, 0, 0}
        double[] instance5 = {0, 0, 0};
        trainer.test(instance5); // Test with new instance
    }
}
