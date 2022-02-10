package rrutkows.codewars.parsing.mathexpression;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface MathToken {

    TokenType getType();

    Double getValue();

    Function<Double, Double> getUnaryOperation() throws UnsupportedOperationException;

    BiFunction<Double, Double, Double> getBinaryOperation() throws UnsupportedOperationException;

    enum TokenType {
        VALUE,
        BINARY_OPERATION,
        UNARY_OPERATION
    }

    public class MathTokenFactory {



        static MathToken of(String representation) throws IllegalArgumentException {
            try {
                switch (representation) {
                    case "+": return BinaryOperation.OPERATOR_ADD;
                    case "-": return BinaryOperation.OPERATOR_SUBTRACT;
                    case "*": return BinaryOperation.OPERATOR_MULTIPLY;
                    case "/": return BinaryOperation.OPERATOR_DIVIDE;
                    default:  return new TerminalValue(Double.valueOf(representation));
                }
            }
            catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(String.format("Unable to interpret token: [%s]", representation), nfe);
            }
        }
    }
}

class TerminalValue implements MathToken {

    @Override
    public String toString() {
        return String.format("ValueToken{value=%s}", value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminalValue that = (TerminalValue) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private final Double value;

    TerminalValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public TokenType getType() {
        return TokenType.VALUE;
    }

    @Override
    public BiFunction<Double, Double, Double> getBinaryOperation() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't get operation of a value Token");
    }

    @Override
    public Function<Double, Double> getUnaryOperation() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't get operation of a value Token");
    }
}

class UnaryOperation implements MathToken {
    public static final UnaryOperation UNARY_MINUS = new UnaryOperation("-", x -> -x);

    @Override
    public String toString() {
        return String.format("UnaryOperation{%s}", description);
    }

    private final String description;
    private final Function<Double, Double> operation;

    UnaryOperation(String description, Function<Double, Double> operation) {
        this.description = description;
        this.operation = operation;
    }

    @Override
    public TokenType getType() {
        return TokenType.UNARY_OPERATION;
    }

    @Override
    public Double getValue() {
        throw new UnsupportedOperationException("Can't get value of a binary operation Token");
    }

    @Override
    public Function<Double, Double> getUnaryOperation() throws UnsupportedOperationException {
        return operation;
    }

    @Override
    public BiFunction<Double, Double, Double> getBinaryOperation() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't get binary operation of a unary operation Token");
    }
}

class BinaryOperation implements MathToken {
    public static final BinaryOperation OPERATOR_ADD = new BinaryOperation("+", (a, b) -> a + b);
    public static final BinaryOperation OPERATOR_SUBTRACT = new BinaryOperation("-", (a, b) -> a - b);
    public static final BinaryOperation OPERATOR_MULTIPLY = new BinaryOperation("*", (a, b) -> a * b);
    public static final BinaryOperation OPERATOR_DIVIDE = new BinaryOperation("/", (a, b) -> a / b);

    @Override
    public String toString() {
        return String.format("BinaryOperation{%s}", description);
    }

    private final String description;
    private final BiFunction<Double, Double, Double> operation;

    BinaryOperation(String description, BiFunction<Double, Double, Double> operation) {
        this.description = description;
        this.operation = operation;
    }

    @Override
    public TokenType getType() {
        return TokenType.BINARY_OPERATION;
    }

    @Override
    public Double getValue() {
        throw new UnsupportedOperationException("Can't get value of a binary operation Token");
    }

    @Override
    public Function<Double, Double> getUnaryOperation() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't get unary operation of a binary operation Token");
    }

    @Override
    public BiFunction<Double, Double, Double> getBinaryOperation() throws UnsupportedOperationException {
        return operation;
    }
}

