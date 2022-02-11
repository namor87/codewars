package rrutkows.codewars.parsing.mathexpression;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface MathToken {

    TokenType getType();

    enum TokenType {
        NUMBER,
        BINARY_OPERATION,
        UNARY_OPERATION,
        PARENTHESIS_OPEN,
        PARENTHESIS_CLOSE
    }
}

interface MathOperator {
    Integer getPrecedence();
    BiFunction<Double, Double, Double> getBinaryOperation();
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
        return value.equals(((TerminalValue) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private final Double value;

    TerminalValue(Double value) {
        this.value = value;
    }


    public Double getValue() {
        return value;
    }

    @Override
    public TokenType getType() {
        return TokenType.NUMBER;
    }

}

class Parenthesis implements MathToken {
    public static final Parenthesis OPENING = new Parenthesis(true, TokenType.PARENTHESIS_OPEN);
    public static final Parenthesis CLOSING = new Parenthesis(false, TokenType.PARENTHESIS_CLOSE);

    private final boolean isOpening;
    private final TokenType type;

    private Parenthesis(boolean isOpening, TokenType type) {
        this.isOpening = isOpening;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Parenthesis{%s}", isOpening ? "(" : ")");
    }

    @Override
    public TokenType getType() {
        return type;
    }

    public boolean isOpening() {
        return isOpening;
    }
}

class UnaryOperation implements MathToken {
    public static final UnaryOperation MINUS = new UnaryOperation("-", x -> -x);

    private final String description;
    private final Function<Double, Double> operation;

    private UnaryOperation(String description, Function<Double, Double> operation) {
        this.description = description;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.format("UnaryOperation{%s}", description);
    }

    @Override
    public TokenType getType() {
        return TokenType.UNARY_OPERATION;
    }

    public Function<Double, Double> getUnaryOperation() {
        return operation;
    }
}

class BinaryOperation implements MathToken, MathOperator {
    public static final BinaryOperation ADD = new BinaryOperation("+", (a, b) -> a + b, 20);
    public static final BinaryOperation SUBTRACT = new BinaryOperation("-", (a, b) -> a - b, 20);
    public static final BinaryOperation MULTIPLY = new BinaryOperation("*", (a, b) -> a * b, 30);
    public static final BinaryOperation DIVIDE = new BinaryOperation("/", (a, b) -> a / b, 30);

    private final String description;
    private final BiFunction<Double, Double, Double> operation;
    private final Integer precedence;

    private BinaryOperation(String description, BiFunction<Double, Double, Double> operation, Integer precedence) {
        this.description = description;
        this.operation = operation;
        this.precedence = precedence;
    }

    @Override
    public String toString() {
        return String.format("BinaryOperation{%s}", description);
    }

    @Override
    public TokenType getType() {
        return TokenType.BINARY_OPERATION;
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public BiFunction<Double, Double, Double> getBinaryOperation() {
        return operation;
    }
}
