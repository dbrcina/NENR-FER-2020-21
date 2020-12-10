package hr.fer.zemris.nenr.hw05;

import hr.fer.zemris.bscthesis.ann.NeuralNetwork;
import hr.fer.zemris.bscthesis.ann.afunction.Sigmoid;
import hr.fer.zemris.bscthesis.classes.ClassType;
import hr.fer.zemris.bscthesis.dataset.Dataset;

import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TrainNetworkDemo {

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.out.println("Program expects 4 arguments...");
            System.out.println("Exiting...");
            return;
        }
        String file = args[0];
        int M = Integer.parseInt(args[1]);
        int[] layers = Arrays.stream(args[2].toLowerCase().split("x"))
                .mapToInt(Integer::parseInt)
                .toArray();
        if (layers[0] != 2 * M) {
            System.out.println("Input layer needs to have " + (2 * M) + " neurons, but it has " + layers[0] + "!");
            return;
        }
        if (layers[layers.length - 1] != 5) {
            System.out.println("Output layer needs to have 5 neurons, but it has " + layers[layers.length - 1] + "!");
            return;
        }
        String alg = args[3];

        ClassType.init();
        Dataset dataset = Util.prepareSimpleDataset(file);
        NeuralNetwork.LearningType learningType = switch (alg) {
            case "1" -> NeuralNetwork.LearningType.BATCH;
            case "2" -> NeuralNetwork.LearningType.ONLINE;
            case "3" -> NeuralNetwork.LearningType.MINI_BATCH;
            default -> throw new IllegalStateException("Unexpected value: " + alg);
        };

        NeuralNetwork nn = new NeuralNetwork(layers, new Sigmoid(), dataset);
        nn.setLearningType(learningType);
        nn.setBatchSize(20);

        nn.train(100_000, 0.02, 0.01);

        System.out.println("Saving neural network object to 'nn.object' file...");
        try (ObjectOutputStream os = new ObjectOutputStream(
                Files.newOutputStream(Paths.get("nn.object")))
        ) {
            os.writeObject(nn);
            System.out.println("Object was successfully saved!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Neural network object was not saved!");
        }
    }

}
