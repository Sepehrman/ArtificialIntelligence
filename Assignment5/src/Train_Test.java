class Train_Test {
    public static void main(String[] args) {
        Forward_Propagation perceptron = new Forward_Propagation();
        double learningRate = 0.1;

        // Training the perceptron for 1000 iterations
        for (int epoch = 0; epoch < 1000; epoch++) {
            for (int i = 0; i < perceptron.inputs.length; i++) {
                double[] output = perceptron.calculateOutput(perceptron.inputs[i]);
                perceptron.updateWeights(perceptron.inputs[i], perceptron.outputs[i][0], output[0], learningRate);
            }
        }

        // Testing the perceptron with a new instance
        double[] testInstance = new double[]{0, 0, 0}; // Instance 5: Off, Off, Off
        double[] testOutput = perceptron.calculateOutput(testInstance);

        System.out.println("Test instance: Off, Off, Off");
        System.out.println("Predicted Alarm Class (0 for Fake, 1 for Real): " + (testOutput[0] >= 0.5 ? 1 : 0));
    }
}