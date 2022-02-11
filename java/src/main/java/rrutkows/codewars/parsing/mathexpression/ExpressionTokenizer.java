package rrutkows.codewars.parsing.mathexpression;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static rrutkows.codewars.parsing.mathexpression.MathToken.TokenType.*;


public class ExpressionTokenizer implements Function<String, List<MathToken>> {
    public static final Character DOT = '.';
    public static final List<Character> OPERATOR_CHARACTERS = List.of('-', '+', '*', '/', '(', ')');

    private final StringBuilder buff = new StringBuilder();
    private final List<MathToken> tokens = new LinkedList<>();

    @Override
    public List<MathToken> apply(String expression) {
        for (char ch : expression.toCharArray()) {
            if (isBlank(ch)) {
                flush();
            } else if (isValidOperator(ch)) {
                flush();
                buff.append(ch);
                flush();
            } else if (isPartOfNumber(ch)) {
                buff.append(ch);
            } else {
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

    private static boolean isBlank(char c) {
        return Character.isWhitespace(c);
    }

    private void flush() {
        if (buff.length() > 0) {
            tokens.add(tokenFactory(buff.toString()));
        }
        buff.setLength(0);
    }

    private MathToken tokenFactory(String representation) throws IllegalArgumentException {
        try {
            switch (representation) {
                case "+": return BinaryOperation.ADD;
                case "-":
                    if (!binaryOperatorRequired()) {return UnaryOperation.MINUS;}
                    else {return BinaryOperation.SUBTRACT;}
                case "*": return BinaryOperation.MULTIPLY;
                case "/": return BinaryOperation.DIVIDE;
                case "(": return Parenthesis.OPENING;
                case ")": return Parenthesis.CLOSING;
                default: return new TerminalValue(Double.valueOf(representation));
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format("Unable to interpret token: [%s]", representation), nfe);
        }
    }

    private Optional<MathToken> getLastToken() {
        return tokens.size() > 0 ? Optional.of(tokens.get(tokens.size() - 1)) : Optional.empty();
    }

    private boolean binaryOperatorRequired() {
        return getLastToken()
                .filter(token -> token.getType() == NUMBER || token.getType() == PARENTHESIS_CLOSE)
                .isPresent();
    }

}
