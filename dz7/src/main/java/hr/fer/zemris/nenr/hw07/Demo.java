package hr.fer.zemris.nenr.hw07;

import hr.fer.zemris.bscthesis.classes.ClassType;
import hr.fer.zemris.bscthesis.dataset.Dataset;
import hr.fer.zemris.bscthesis.dataset.Sample;
import hr.fer.zemris.nenr.hw07.model.NeuralNetwork;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dbrcina
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Program expects a path to dataset file!");
            System.out.println("Exiting...");
            return;
        }

        Path file = Paths.get(args[0]);
        Dataset dataset = createDataset(file);
        NeuralNetwork nn = new NeuralNetwork("2x8x3");
        nn.statistics(dataset, new double[nn.getWeightsCount()]);
    }

    private static Dataset createDataset(Path file) throws Exception {
        Dataset dataset = new Dataset() {
            private List<Sample> samples;

            @Override
            public void setSamples(List<Sample> samples) {
                this.samples = samples;
            }

            @Override
            public int numberOfSamples() {
                return samples.size();
            }

            @Override
            public List<Sample> shuffleSamples() {
                List<Sample> shuffled = new ArrayList<>(samples);
                Collections.shuffle(shuffled);
                return shuffled;
            }

            @Override
            public void loadDataset(Path file) throws Exception {
                List<String> lines = Files.readAllLines(file);
                samples = lines.stream()
                        .map(line -> {
                            double[] data = Arrays.stream(line.split("\\s+"))
                                    .mapToDouble(Double::parseDouble)
                                    .toArray();
                            double[] inputs = {data[0], data[1]};
                            double[] outputs = {data[2], data[3], data[4]};
                            return new Sample(inputs, outputs, ClassType.determineFor(outputs));
                        })
                        .collect(Collectors.toList());
            }

            @Override
            public Iterator<Sample> iterator() {
                return samples.iterator();
            }
        };
        dataset.loadDataset(file);
        return dataset;
    }

}
