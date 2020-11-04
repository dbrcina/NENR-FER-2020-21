package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.defuzzify.COADefuzzifier;
import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.system.AkcelFuzzySystem;
import hr.fer.zemris.fuzzy.system.FuzzySystem;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystem;
import hr.fer.zemris.fuzzy.util.Operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SimulatorDemo {

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        Implication implication = new Mamdani(true);
        IBinaryFunction tNormFunction = Operations.zadehAnd();
        IBinaryFunction sNormFunction = Operations.zadehOr();
        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fsAkcel = new AkcelFuzzySystem(implication, tNormFunction, sNormFunction, def);
        FuzzySystem fsKormilo = new KormiloFuzzySystem(implication, tNormFunction, sNormFunction, def);

        int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0;
        String line;

        while (true) {
            if ((line = input.readLine()) != null) {
                if (line.charAt(0) == 'K')
                    break;
                Scanner s = new Scanner(line);
                L = s.nextInt();
                D = s.nextInt();
                LK = s.nextInt();
                DK = s.nextInt();
                V = s.nextInt();
                S = s.nextInt();
            }
            double[] values = {L, D, LK, DK, V, S};
            int a = (int) fsAkcel.conclude(values);
            int k = (int) fsKormilo.conclude(values);
            System.out.println(a + " " + k);
            System.out.flush();
        }
    }

}
