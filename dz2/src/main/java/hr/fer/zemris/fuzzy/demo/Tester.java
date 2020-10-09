package hr.fer.zemris.fuzzy.demo;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Tester {

    private static final PrintStream STDOUT = System.out;

    public static void main(String[] args) throws Exception {
        System.out.println("Testing Primjer1:");
        test(Primjer1.class, Paths.get("results/Primjer1.txt"), Paths.get("results/Primjer1.out"));

        System.out.println("Testing Primjer2:");
        test(Primjer2.class, Paths.get("results/Primjer2.txt"), Paths.get("results/Primjer2.out"));

        System.out.println("Testing Primjer3:");
        test(Primjer3.class, Paths.get("results/Primjer3.txt"), Paths.get("results/Primjer3.out"));
    }

    private static void test(Class<?> clazz, Path txtPath, Path outPath) throws Exception {
        System.setOut(new PrintStream(Files.newOutputStream(outPath), true));
        clazz.getMethod("main", String[].class).invoke(clazz, new String[1]);
        List<String> txtLines = Files.readAllLines(txtPath);
        List<String> outLines = Files.readAllLines(outPath);
        System.setOut(STDOUT);
        System.out.println("Are files equal? " + txtLines.equals(outLines));
    }

}
