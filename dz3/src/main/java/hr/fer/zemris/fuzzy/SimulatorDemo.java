package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.defuzzify.COADefuzzifier;
import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.system.AkcelFuzzySystem;
import hr.fer.zemris.fuzzy.system.FuzzySystem;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystem;
import hr.fer.zemris.fuzzy.util.Operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SimulatorDemo {

    public static void main(String[] args) throws IOException {
        // Biramo način dekodiranja neizrazitosti:
        Defuzzifier def = new COADefuzzifier();

        // Stvaranje oba sustava:
        // Grade se baze pravila i sve se inicijalizira
        Implication mamdani = new Mamdani(Operations.zadehAnd(), true);
        FuzzySystem fsAkcel = new AkcelFuzzySystem(mamdani, Operations.zadehAnd(), Operations.zadehOr(), def);
        FuzzySystem fsKormilo = new KormiloFuzzySystem(mamdani, Operations.zadehAnd(), Operations.zadehOr(), def);

        // Objekt za citanje sa stdin:
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Glavna petla:
        while (true) {
            String line = br.readLine();
            if (line.toUpperCase().equals("KRAJ")) {
                br.close();
                break;
            }
            double[] values = Arrays.stream(line.split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            double a = fsAkcel.conclude(values);
            double k = fsKormilo.conclude(values);
            System.out.println(a + " " + k);
            System.out.flush();
        }

    }

}
