package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.defuzzify.COADefuzzifier;
import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.system.AkcelFuzzySystem;
import hr.fer.zemris.fuzzy.system.FuzzySystem;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystem;
import hr.fer.zemris.fuzzy.system.Rule;
import hr.fer.zemris.fuzzy.util.Debug;
import hr.fer.zemris.fuzzy.util.Operations;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class P2 {

    public static void main(String[] args) {
        // Biramo način dekodiranja neizrazitosti:
        Defuzzifier def = new COADefuzzifier();

        // Stvaranje oba sustava:
        // Grade se baze pravila i sve se inicijalizira
        Implication implication = new Mamdani(true);
        IBinaryFunction tNorm = Operations.zadehAnd();
        IBinaryFunction sNorm = Operations.zadehOr();
        FuzzySystem fs = new AkcelFuzzySystem(implication, tNorm, sNorm, def);
        List<Rule> rules = fs.getRules();
        System.out.println("Baza pravila za akceleraciju:");
        for (int i = 0; i < rules.size(); i++) {
            System.out.println((i + 1) + ". " + rules.get(i).getDescription());
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Unesi('kraj' za kraj) L D LK DK V S: ");
            String line = sc.nextLine();
            if (line.toLowerCase().equals("kraj")) {
                System.out.println("Izlazim...");
                break;
            }
            double[] values = Arrays.stream(line.split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            IFuzzySet result = fs.fuzzifiedConclusion(values);
            int r = (int) def.defuzzify(result);
            Debug.print(result, "Rezulat");
            System.out.println(r);
        }
        sc.close();
    }

}
