package rrutkows.codewars.parsing.mathexpression;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

import static rrutkows.codewars.parsing.mathexpression.MathToken.TokenType.*;

public class RPNConverter implements Function<List<MathToken>, List<MathToken>> {

    List<MathToken> rpnExpression = new LinkedList<>();
    Stack<MathToken> operatorStack = new Stack<>() ;

    /**
     * shunting-yard algorithm (modified to support unary ops.)
     */
    @Override
    public List<MathToken> apply(List<MathToken> infixTokens) {

        for(MathToken token : infixTokens) {
            switch (token.getType()) {
                case NUMBER:
                    rpnExpression.add(token);
                    break;
                case UNARY_OPERATION:
                case BINARY_OPERATION:
                    while(! operatorStack.empty()
                        && (
                            ( operatorStack.peek().getType() == BINARY_OPERATION
                                    && ((MathOperator) operatorStack.peek()).getPrecedence() >= ((MathOperator) token).getPrecedence()
                            ) || (
                                operatorStack.peek().getType() == UNARY_OPERATION
                                    && ((MathOperator) operatorStack.peek()).getPrecedence() > ((MathOperator) token).getPrecedence()
                            )
                        )
                    ) {
                        rpnExpression.add(operatorStack.pop());
                    }
                    operatorStack.add(token);
                    break;
                case PARENTHESIS_OPEN:
                    operatorStack.add(token);
                    break;
                case PARENTHESIS_CLOSE:
                    while(! operatorStack.empty() && operatorStack.peek().getType() != PARENTHESIS_OPEN) {
                        rpnExpression.add(operatorStack.pop());
                    }
                    if(operatorStack.empty() || operatorStack.pop().getType() != PARENTHESIS_OPEN) {
                        throw new IllegalArgumentException("Parenthesis mismatch!!");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("unknown operation");
            }
        }
        while(! operatorStack.empty()) {
            final MathToken topToken = operatorStack.pop();
            if(topToken.getType() == PARENTHESIS_OPEN) {
                throw new IllegalArgumentException("Parenthesis mismatch!!");
            }
            rpnExpression.add(topToken);
        }
        return rpnExpression;
    }

}
