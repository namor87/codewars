package rrutkows.codewars.parsing.mathexpression;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;


public class RPNEvaluator implements Function<List<MathToken>, Double> {

    Stack<Double> argumentStack = new Stack<>();

    @Override
    public Double apply(List<MathToken> rpnExpression) {
        for (MathToken token : rpnExpression) {
            switch (token.getType()) {
                case NUMBER:
                    argumentStack.add(((TerminalValue) token).getValue());
                    break;
                case UNARY_OPERATION:
                    Double arg = argumentStack.pop();
                    final Double result = ((UnaryOperation) token).getUnaryOperation().apply(arg);
                    argumentStack.add(result);
                    break;
                case BINARY_OPERATION:
                    Double right = argumentStack.pop();
                    Double left = argumentStack.pop();
                    argumentStack.add(((BinaryOperation) token).getBinaryOperation().apply(left, right));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected operation: " + token);
            }
        }
        return argumentStack.pop();
    }
}
