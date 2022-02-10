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
    public void tokenizeSimpleOperation() {
        final List<MathToken> actual = tokenizer.apply("7+7");
        System.out.println(actual);
        assertThat(actual, contains(
                new TerminalValue(7.0),
                BinaryOperation.OPERATOR_ADD,
                new TerminalValue(7.0)
        ));
    }

    @Test
    public void tokenizeUnaryMinus() {
        final List<MathToken> actual = tokenizer.apply("-7");

        assertThat(actual, contains(
                UnaryOperation.UNARY_MINUS,
                new TerminalValue(7.0)
        ));
    }

}