package rrutkows.codewars.parsing.mathexpression;

import java.util.List;
import java.util.function.Function;

public class MathEvaluator {

    private final Pipeline<String, Double> expressionEvaluator =
            new Pipeline<>(new ExpressionTokenizer())
            .addHandler(new RPNConverter())
            .addHandler(new RPNEvaluator());

    public double calculate(String expression) {
        return expressionEvaluator.execute(expression);
    }

}

class Pipeline<I, O>  {
    private final Function<I, O> currentHandler;

    Pipeline(Function<I, O> handler) {
        this.currentHandler = handler;
    }

    <K> Pipeline<I, K> addHandler(Function<O, K> newHandler) {
        return new Pipeline<>(input -> newHandler.apply(currentHandler.apply(input)));
    }

    public O execute(I input) {
        return currentHandler.apply(input);
    }
}