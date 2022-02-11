package rrutkows.codewars.parsing.mathexpression;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;

public class RPNEvaluatorTest {

    RPNEvaluator evaluator = new RPNEvaluator();

    @Test
    public void tokenizeSimpleOperation() {

        //  7+7

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(7.0),
                new TerminalValue(7.0),
                BinaryOperation.ADD
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(14.0, actual);
    }

    @Test
    public void tokenizeExpressionInParenthesis() {

        //  (7+7)

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(7.0),
                new TerminalValue(7.0),
                BinaryOperation.ADD
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(14.0, actual);
    }

    @Test
    public void tokenizeUnaryMinus() {

        //  -7

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(7.0),
                UnaryOperation.MINUS
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(-7.0, actual);
    }

    @Test
    public void handleUnaryMinusNextToBinaryOperation() {

        //  8*-7

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(8.0),
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                BinaryOperation.MULTIPLY
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(-56.0, actual);
    }

    @Test
    public void handleUnaryMinusNextToOpeningBracket() {

        //  8*(-7+9)

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(8.0),
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                new TerminalValue(9.0),
                BinaryOperation.ADD,
                BinaryOperation.MULTIPLY
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(16.0, actual);
    }

    @Test
    public void handleBinaryMinusBetweenParenthesisExpressions() {

        //  (8/2)-(-7+9)

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(8.0),
                new TerminalValue(2.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(7.0),
                UnaryOperation.MINUS,
                new TerminalValue(9.0),
                BinaryOperation.ADD,
                BinaryOperation.SUBTRACT
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(2.0, actual);
    }

    @Test
    public void handleMultipleUnaryMinusesInARow() {

        //  5----2+---7

        final List<MathToken> rpnExpression = List.of(
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
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(0.0, actual);
    }

    @Test
    public void preserverOperationPrecedence() {

        //  4 * 3 + 7 - 8 / 4 * 5

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(4.0),
                new TerminalValue(3.0),
                BinaryOperation.MULTIPLY,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(8.0),
                new TerminalValue(4.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(5.0),
                BinaryOperation.MULTIPLY,
                BinaryOperation.SUBTRACT
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(9.0, actual);
    }

    @Test
    public void preservesOrderInParenthesis() {

        //  4 * (5 + (9 - 5)) / (2 * 3)

        final List<MathToken> rpnExpression = List.of(
                new TerminalValue(4.0),
                new TerminalValue(5.0),
                new TerminalValue(9.0),
                new TerminalValue(5.0),
                BinaryOperation.SUBTRACT,
                BinaryOperation.ADD,
                BinaryOperation.MULTIPLY,
                new TerminalValue(2.0),
                new TerminalValue(3.0),
                BinaryOperation.MULTIPLY,
                BinaryOperation.DIVIDE
        );

        Double actual = evaluator.apply(rpnExpression);

        assertEquals(6.0, actual);
    }
}