package hr.fer.zemris.nenr.hw07.model;

import hr.fer.zemris.bscthesis.dataset.Dataset;
import hr.fer.zemris.bscthesis.dataset.Sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dbrcina
 */
public class NeuralNetwork {

    private final int[] layers;
    private final int weightsCount;

    public NeuralNetwork(String network) {
        try {
            layers = Arrays.stream(network.toLowerCase().strip().split("\\s*x\\s*"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (Exception e) {
            throw new RuntimeException("Invalid architecture!");
        }
        int counter = 0;
        counter += layers[1] * layers[0] * 2;
        for (int i = 2; i < layers.length; i++) {
            counter += layers[i] * (layers[i - 1] + 1);
        }
        weightsCount = counter;
    }

    public int getWeightsCount() {
        return weightsCount;
    }

    public double[] calcOutputs(double[] inputs, double[] weights) {
        int inputLayer = layers[0];
        if (inputLayer != inputs.length) {
            throw new RuntimeException("Size of the inputs array is invalid. It should be " + inputLayer + "!");
        }
        if (weightsCount != weights.length) {
            throw new RuntimeException("Size of the weights array is invalid. It should be " + weightsCount + "!");
        }

        int weightsPointer = 0;
        double[] outputs = Arrays.copyOf(inputs, inputLayer);
        for (int i = 1; i < layers.length; i++) {
            double[] currentLayerOutputs = new double[layers[i]];
            for (int j = 0; j < currentLayerOutputs.length; j++) {
                if (i == 1) {
                    currentLayerOutputs[j] = kernel(inputs, weights, weightsPointer);
                    weightsPointer += inputs.length * 2;
                } else {
                    double net = 0.0;
                    for (double previous : outputs) {
                        net += weights[weightsPointer++] * previous;
                    }
                    net += weights[weightsPointer++];
                    currentLayerOutputs[j] = sigmoid(net);
                }
            }
            outputs = currentLayerOutputs;
        }
        return outputs;
    }

    private double kernel(double[] inputs, double[] weights, int weightsPointer) {
        double sum = 0.0;
        for (double x : inputs) {
            sum += Math.abs(x - weights[weightsPointer]) / Math.abs(weights[weightsPointer + 1]);
            weightsPointer += 2;
        }
        return 1 / (1 + sum);
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double calcError(Dataset dataset, double[] weights) {
        double error = 0.0;
        for (Sample sample : dataset) {
            double[] inputs = sample.getInputs();
            double[] desiredOutputs = sample.getClassType().getDesiredOutputs();
            double[] actualOutputs = calcOutputs(inputs, weights);
            for (int j = 0; j < actualOutputs.length; j++) {
                error += Math.pow(desiredOutputs[j] - actualOutputs[j], 2);
            }
        }
        return error / dataset.numberOfSamples();
    }

    public void statistics(Dataset dataset, double[] weights) {
        int positives = 0;
        int negatives = 0;
        List<String> negativeInputs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Sample sample : dataset) {
            double[] inputs = sample.getInputs();
            double[] desiredOutputs = sample.getClassType().getDesiredOutputs();
            double[] actualOutputs = calcOutputs(inputs, weights);
            for (int j = 0; j < actualOutputs.length; j++) {
                actualOutputs[j] = actualOutputs[j] < 0.5 ? 0.0 : 1.0;
            }
            if (Arrays.equals(desiredOutputs, actualOutputs)) {
                positives++;
            } else {
                negatives++;
                sb.append("Ulazi           : ").append(Arrays.toString(inputs));
                sb.append("\n");
                sb.append("Željeni izlazi  : ").append(Arrays.toString(desiredOutputs));
                sb.append("\n");
                sb.append("Dobiveni izlazi : ").append(Arrays.toString(actualOutputs));
                sb.append("\n");
                negativeInputs.add(sb.toString());
                sb.setLength(0);
            }

        }
        sb.append("Dobri izlazi : ").append(positives);
        sb.append("\n");
        sb.append("Loši izlazi  : ").append(negatives);
        sb.append("\n");
        System.out.println(sb.toString());

        if (negatives != 0) {
            System.out.println("Krivi rezultati za ulaze:");
        }
        negativeInputs.forEach(System.out::println);
    }

}
