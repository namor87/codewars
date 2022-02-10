package rrutkows.codewars.parsing.mathexpression;

import java.util.List;

public class MathEvaluator {

    public double calculate(String expression) {
        Pipeline<String, Double> calculator = new Pipeline<>(new SymbolParser())
                .addHandler(new ExpressionBuilder())
                .addHandler(new ExpressionEvaluator());
        return calculator.execute(expression);
    }


    private class SymbolParser implements Handler<String, List<Symbol>> {

        @Override
        public List<Symbol> process(String input) {
            return List.of(new Symbol("1"), new Symbol("+"), new Symbol("1"));
        }
    }

    private class Symbol {
        private String value;
        public Symbol(String s) {
            value = s;
        }
        Token toTerm() {
            try {
                return new Value(Double.valueOf(value));
            }
            catch (NumberFormatException nfe) {
                if (value.length() > 1) {
                    throw new ComputationModuleCoreMeltdown("This is not a Symbol: " + value);
                }
                else {
                    switch (value) {
                        case "(": return new Opening();
                        case ")" : return new Closing();
                        default:
                            if ("-+*/".contains(value)) {
                                return new Operator(value);
                            }
                            else {
                                throw new ComputationModuleCoreMeltdown("This is not a known Operation: " + value);
                            }
                    }
                }
            }
        }
    }

    private class ExpressionBuilder implements Handler<List<Symbol>, Expression> {
        @Override
        public Expression process(List<Symbol> input) {
            Expression exp = new Expression();
            for (Symbol symbol : input) {
                    exp.push(symbol.toTerm());
            }
            return exp;
        }
    }


    private interface Term {
        double evaluate();
    }

    private class Expression implements Term {
        public Expression() {

        }

        public void push(Token term) {

        }

        @Override
        public double evaluate() {
            return 0;
        }
    }

    private class ExpressionEvaluator implements Handler<Expression, Double> {
        @Override
        public Double process(Expression input) {
            return input.evaluate();
        }
    }

//    UnaryOperator<Double> unaryMinus = d -> (-d);
//
//    private class Terminal implements Expr {
//
//        private double value;
//
//        public Terminal(double value) {
//            this.value = value;
//        }
//
//        @Override
//        public double evaluate() {
//            return value;
//        }
//    }
//
//    private class Unary implements Expr {
//
//        UnaryOperator<double>
//        private double value;
//
//        public Terminal(double value) {
//            this.value = value;
//        }
//
//        @Override
//        public double evaluate() {
//            return value;
//        }
//    }


    interface Handler<I, O> {
        O process(I input);
    }

    class Pipeline<I, O> {
        private final Handler<I, O> currentHandler;

        Pipeline(Handler<I, O> currentHandler) {
            this.currentHandler = currentHandler;
        }

        <K> Pipeline<I, K> addHandler(Handler<O, K> newHandler) {
            return new Pipeline<>(input -> newHandler.process(currentHandler.process(input)));
        }

        O execute(I input) {
            return currentHandler.process(input);
        }
    }


    private class ComputationModuleCoreMeltdown extends RuntimeException {
        public ComputationModuleCoreMeltdown(String s) {
            super(s);
        }
    }

    private class Opening implements Token {
    }

    private class Closing implements Token {
    }

    private class Operator implements Token {
        public Operator(String value) {
        }
    }
}