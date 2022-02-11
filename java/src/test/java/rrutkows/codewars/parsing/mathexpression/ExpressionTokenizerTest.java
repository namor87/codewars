package rrutkows.codewars.parsing.mathexpression;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;


public class ExpressionTokenizerTest {

    ExpressionTokenizer tokenizer = new ExpressionTokenizer();

    @Test
    public void tokenizeInteger() {
        final List<MathToken> actual = tokenizer.apply("7");

        assertThat(actual, contains(new TerminalValue(7.0)));
    }

    @Test
    public void tokenizeDecimal() {
        final List<MathToken> actual = tokenizer.apply("7.5432");

        assertThat(actual, contains(new TerminalValue(7.5432)));
    }

    @Test
    public void tokenizeSimpleOperation() {
        final List<MathToken> actual = tokenizer.apply("7+7");

        assertThat(actual, contains(
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(7.0)
        ));
    }

    @Test
    public void tokenizeParenthesisOpening() {
        final List<MathToken> actual = tokenizer.apply("(");
        assertThat(actual, contains(Parenthesis.OPENING));
    }

    @Test
    public void tokenizeParenthesisClosing() {
        final List<MathToken> actual = tokenizer.apply(")");
        assertThat(actual, contains(Parenthesis.CLOSING));
    }

    @Test
    public void tokenizeExpressionInParenthesis() {
        final List<MathToken> actual = tokenizer.apply("(7+7)");
        assertThat(actual, contains(
                Parenthesis.OPENING,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(7.0),
                Parenthesis.CLOSING
        ));
    }

    @Test
    public void tokenizeUnaryMinus() {
        final List<MathToken> actual = tokenizer.apply("-7");

        assertThat(actual, contains(
                UnaryOperation.MINUS,
                new TerminalValue(7.0)
        ));
    }

    @Test
    public void handleUnaryMinusNextToBinaryOperation() {
        final List<MathToken> actual = tokenizer.apply("8*-7");

        assertThat(actual, contains(
                new TerminalValue(8.0),
                BinaryOperation.MULTIPLY,
                UnaryOperation.MINUS,
                new TerminalValue(7.0)
        ));
    }

    @Test
    public void handleUnaryMinusNextToOpeningBracket() {
        final List<MathToken> actual = tokenizer.apply("8*(-7+9)");

        assertThat(actual, contains(
                new TerminalValue(8.0),
                BinaryOperation.MULTIPLY,
                Parenthesis.OPENING,
                UnaryOperation.MINUS,
                new TerminalValue(7.0),
                BinaryOperation.ADD,
                new TerminalValue(9.0),
                Parenthesis.CLOSING
        ));
    }

    @Test
    public void handleBinaryMinusBetweenParenthesisExpressions() {
        final List<MathToken> actual = tokenizer.apply("(8/2)-(-7+9)");

        assertThat(actual, contains(
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
        ));
    }

    @Test
    public void handleMultipleUnaryMinusesInARow() {
        final List<MathToken> actual = tokenizer.apply("5----2+---7");

        assertThat(actual, contains(
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
        ));
    }

    @Test
    public void canHandleSpaces() {
        final List<MathToken> actual = tokenizer.apply("   4   +   7-8  / 6  *  1   ");

        assertThat(actual, contains(
                new TerminalValue(4.0),
                BinaryOperation.ADD,
                new TerminalValue(7.0),
                BinaryOperation.SUBTRACT,
                new TerminalValue(8.0),
                BinaryOperation.DIVIDE,
                new TerminalValue(6.0),
                BinaryOperation.MULTIPLY,
                new TerminalValue(1.0)
        ));
    }

}