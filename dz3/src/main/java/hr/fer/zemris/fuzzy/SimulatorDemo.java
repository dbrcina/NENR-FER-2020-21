package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.defuzzify.COADefuzzifier;
import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.system.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.system.FuzzySystem;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystemMin;

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
        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

        // Objekt za citanje sa stdin:
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Glavna petla:
        while (true) {
            String line = br.readLine();
            if (line.equals("KRAJ")) {
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
