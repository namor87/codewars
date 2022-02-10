package rrutkows.codewars.parsing.mathexpression;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionTokenizer implements Function<String, List<MathToken>> {
    @Override
    public List<MathToken> apply(String s) {
        return new InternalTokenizer(s).tokenize();
    }
    
    private static class InternalTokenizer {
        public static final Character DOT = '.';
        public static final List<Character> OPERATOR_CHARACTERS = List.of(Character.valueOf('-'), Character.valueOf('+'), Character.valueOf('*'), Character.valueOf('/'), Character.valueOf('('), Character.valueOf(')'));

        private final String expression;
        private final StringBuilder buff = new StringBuilder();
        private final List<MathToken> tokens = new LinkedList<>();

        public InternalTokenizer(String expression) {
            this.expression = expression;
        }

        public List<MathToken> tokenize() {
            for(char ch : expression.toCharArray()) {
                if (isBlank(ch)) {
                    flush();
                }
                else if (isValidOperator(ch)) {
                    flush();
                    buff.append(ch);
                    flush();
                }
                else if(isPartOfNumber(ch)) {
                    buff.append(ch);
                }
                else {
                    throw new IllegalArgumentException("Failed to interpret character \"" + ch + "\" + ofthe expression: " + expression);
                }
            }
            flush();
            return List.copyOf(tokens);
        }

        private boolean isPartOfNumber(char ch) {
            return Character.isDigit(ch) || DOT.equals(ch);
        }

        private boolean isValidOperator(char ch) {
            return OPERATOR_CHARACTERS.contains(ch);
        }

        private void flush() {
            if(buff.length() > 0) {
                tokens.add(MathToken.MathTokenFactory.of(buff.toString()));
            }
            buff.setLength(0);
        }

        private static boolean isBlank(char c) {
            return Character.isWhitespace(c);
        }

    }
}
