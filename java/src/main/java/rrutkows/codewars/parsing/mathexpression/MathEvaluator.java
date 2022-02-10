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
//
//
//    private class SymbolParser implements Handler<String, List<Symbol>> {
//
//        @Override
//        public List<Symbol> process(String input) {
//            return List.of(new Symbol("1"), new Symbol("+"), new Symbol("1"));
//        }
//    }
//
//    private class Symbol {
//        private String value;
//        public Symbol(String s) {
//            value = s;
//        }
//        Token toTerm() {
//            try {
//                return new Value(Double.valueOf(value));
//            }
//            catch (NumberFormatException nfe) {
//                if (value.length() > 1) {
//                    throw new ComputationModuleCoreMeltdown("This is not a Symbol: " + value);
//                }
//                else {
//                    switch (value) {
//                        case "(": return new Opening();
//                        case ")" : return new Closing();
//                        default:
//                            if ("-+*/".contains(value)) {
//                                return new Operator(value);
//                            }
//                            else {
//                                throw new ComputationModuleCoreMeltdown("This is not a known Operation: " + value);
//                            }
//                    }
//                }
//            }
//        }
//    }
//
//    private class ExpressionBuilder implements Handler<List<Symbol>, Expression> {
//        @Override
//        public Expression process(List<Symbol> input) {
//            Expression exp = new Expression();
//            for (Symbol symbol : input) {
//                    exp.push(symbol.toTerm());
//            }
//            return exp;
//        }
//    }
//
//
//    private interface Term {
//        double evaluate();
//    }
//
//    private class Expression implements Term {
//        public Expression() {
//
//        }
//
//        public void push(Token term) {
//
//        }
//
//        @Override
//        public double evaluate() {
//            return 0;
//        }
//    }
//
//    private class ExpressionEvaluator implements Handler<Expression, Double> {
//        @Override
//        public Double process(Expression input) {
//            return input.evaluate();
//        }
//    }
//
////    UnaryOperator<Double> unaryMinus = d -> (-d);
////
////    private class Terminal implements Expr {
////
////        private double value;
////
////        public Terminal(double value) {
////            this.value = value;
////        }
////
////        @Override
////        public double evaluate() {
////            return value;
////        }
////    }
////
////    private class Unary implements Expr {
////
////        UnaryOperator<double>
////        private double value;
////
////        public Terminal(double value) {
////            this.value = value;
////        }
////
////        @Override
////        public double evaluate() {
////            return value;
////        }
////    }
//
//



//
//
//    private class ComputationModuleCoreMeltdown extends RuntimeException {
//        public ComputationModuleCoreMeltdown(String s) {
//            super(s);
//        }
//    }
//
//    private class Opening implements Token {
//    }
//
//    private class Closing implements Token {
//    }
//
//    private class Operator implements Token {
//        public Operator(String value) {
//        }
//    }
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