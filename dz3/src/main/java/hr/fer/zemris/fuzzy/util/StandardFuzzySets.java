package hr.fer.zemris.fuzzy.util;

import hr.fer.zemris.fuzzy.function.IIntUnaryFunction;

public class StandardFuzzySets {

    public static IIntUnaryFunction lFunction(int alpha, int beta) {
        return x -> {
            if (x < alpha) return 1.0;
            else if (x >= beta) return 0.0;
            else return (beta - x * 1.0) / (beta - alpha);
        };
    }

    public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
        return x -> {
            if (x < alpha) return 0.0;
            else if (x >= beta) return 1.0;
            else return (x * 1.0 - alpha) / (beta - alpha);
        };
    }

    public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
        return x -> {
            if (x < alpha || x >= gamma) return 0.0;
            else if (x >= alpha && x < beta) return (x * 1.0 - alpha) / (beta - alpha);
            else return (gamma - x * 1.0) / (gamma - beta);
        };
    }

}
