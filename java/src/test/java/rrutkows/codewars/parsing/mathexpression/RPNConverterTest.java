package rrutkows.codewars.parsing.mathexpression;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;


public class RPNConverterTest {

    RPNConverter converter = new RPNConverter();

    @Test
    public void tokenizeSimpleOperation() {

        //  7+7

        final List<MathToken> infix = List.of(
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(7.0)
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(7.0),
                new TerminalValue(7.0),
                BinaryOperation.ADD
        ));
    }

    @Test
    public void tokenizeExpressionInParenthesis() {

        //  (7+7)

        final List<MathToken> infix = List.of(
                Parenthesis.OPENING,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(7.0),
                Parenthesis.CLOSING
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(7.0),
                new TerminalValue(7.0),
                BinaryOperation.ADD
        ));
    }

    @Test
    public void tokenizeUnaryMinus() {

        //  -7

        final List<MathToken> infix = List.of(
                UnaryOperation.MINUS,
                new TerminalValue(7.0)
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(7.0),
                UnaryOperation.MINUS
        ));
    }

    @Test
    public void handleUnaryMinusNextToBinaryOperation() {

        //  8*-7

        final List<MathToken> infix = List.of(
                new TerminalValue(8.0),
                BinaryOperation.MULTIPLY,
                UnaryOperation.MINUS,
                new TerminalValue(7.0)
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(8.0),
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                BinaryOperation.MULTIPLY
        ));
    }

    @Test
    public void handleUnaryMinusNextToOpeningBracket() {

        //  8*(-7+9)

        final List<MathToken> infix = List.of(
                new TerminalValue(8.0),
                BinaryOperation.MULTIPLY,
                Parenthesis.OPENING,
                UnaryOperation.MINUS,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(9.0),
                Parenthesis.CLOSING
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(8.0),
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                new TerminalValue(9.0),
                BinaryOperation.ADD,
                BinaryOperation.MULTIPLY
        ));
    }

    @Test
    public void handleBinaryMinusBetweenParenthesisExpressions() {

        //  (8/2)-(-7+9)

        final List<MathToken> infix = List.of(
                Parenthesis.OPENING,
                new TerminalValue(8.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(2.0),
                Parenthesis.CLOSING,
                BinaryOperation.SUBTRACT,
                Parenthesis.OPENING,
                UnaryOperation.MINUS,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(9.0),
                Parenthesis.CLOSING
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(8.0),
                new TerminalValue(2.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                new TerminalValue(9.0),
                BinaryOperation.ADD,
                BinaryOperation.SUBTRACT
        ));
    }

    @Test
    public void handleMultipleUnaryMinusesInARow() {

        //  5----2+---7

        final List<MathToken> infix = List.of(
                new TerminalValue(5.0),
                BinaryOperation.SUBTRACT,
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                new TerminalValue(2.0),
                BinaryOperation.ADD,
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                new TerminalValue(7.0)
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(5.0),
                new TerminalValue(2.0),
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                BinaryOperation.SUBTRACT,
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                UnaryOperation.MINUS,
                BinaryOperation.ADD
            ));
    }

    @Test
    public void preserverOperationPrecedence() {

        //  4 * 3 + 7 - 8 / 6 * 5

        final List<MathToken> infix = List.of(
                new TerminalValue(4.0),
                BinaryOperation.MULTIPLY,
                new TerminalValue(3.0),
                BinaryOperation.ADD,
                new TerminalValue(7.0),
                BinaryOperation.SUBTRACT,
                new TerminalValue(8.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(6.0),
                BinaryOperation.MULTIPLY,
                new TerminalValue(1.0)
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(4.0),
                new TerminalValue(3.0),
                BinaryOperation.MULTIPLY,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(8.0),
                new TerminalValue(6.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(5.0),
                BinaryOperation.MULTIPLY,
                BinaryOperation.SUBTRACT
        ));
    }

    @Test
    public void preservesOrderInParenthesis() {

        //  4 * (3 + (7 - 8)) / (6 * 1)

        final List<MathToken> infix = List.of(
                new TerminalValue(4.0),
                BinaryOperation.MULTIPLY,
                Parenthesis.OPENING,
                new TerminalValue(3.0),
                BinaryOperation.ADD,
                Parenthesis.OPENING,
                new TerminalValue(7.0),
                BinaryOperation.SUBTRACT,
                new TerminalValue(8.0),
                Parenthesis.CLOSING,
                Parenthesis.CLOSING,
                BinaryOperation.DIVIDE,
                Parenthesis.OPENING,
                new TerminalValue(6.0),
                BinaryOperation.MULTIPLY,
                new TerminalValue(1.0),
                Parenthesis.CLOSING
        );

        final List<MathToken> actual = converter.apply(infix);

        assertThat(actual, contains(
                new TerminalValue(4.0),
                new TerminalValue(3.0),
                new TerminalValue(7.0),
                new TerminalValue(8.0),
                BinaryOperation.SUBTRACT,
                BinaryOperation.ADD,
                BinaryOperation.MULTIPLY,
                new TerminalValue(6.0),
                new TerminalValue(5.0),
                BinaryOperation.MULTIPLY,
                BinaryOperation.DIVIDE
        ));
    }
}