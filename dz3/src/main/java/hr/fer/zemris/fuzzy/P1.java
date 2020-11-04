package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.defuzzify.COADefuzzifier;
import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.system.AkcelFuzzySystem;
import hr.fer.zemris.fuzzy.system.FuzzySystem;
import hr.fer.zemris.fuzzy.util.Debug;
import hr.fer.zemris.fuzzy.util.Operations;

import java.util.*;

public class P1 {

    public static void main(String[] args) {
        // Biramo način dekodiranja neizrazitosti:
        Defuzzifier def = new COADefuzzifier();

        // Stvaranje oba sustava:
        // Grade se baze pravila i sve se inicijalizira
        Implication mamdani = new Mamdani(true);
        FuzzySystem fsAkcel = new AkcelFuzzySystem(mamdani, Operations.zadehAnd(), Operations.zadehOr(), def);
        List<Map.Entry<IFuzzySet[], IFuzzySet>> rules = new ArrayList<>(fsAkcel.getRules().entrySet());

        Scanner sc = new Scanner(System.in);
        System.out.print("Pick a rule: ");
        int ruleNumber = Integer.parseInt(sc.nextLine());
        Map.Entry<IFuzzySet[], IFuzzySet> rule = rules.get(ruleNumber -1);
        System.out.print("Enter values: ");
        String line = sc.nextLine();
        double[] values = Arrays.stream(line.split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
        IFuzzySet result = fsAkcel.applyRule(rule, values);
        int a = (int) def.defuzzify(result);
        Debug.print(result, "Result");
        System.out.println(a);
    }

}
