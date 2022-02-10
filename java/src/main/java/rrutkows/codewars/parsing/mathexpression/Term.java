package rrutkows.codewars.parsing.mathexpression;


public interface Term {
    double evaluate();
}

class Monomial implements Term {
    private Double value;

    public Monomial(Double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}

class Expression implements Term {
    @Override
    public double evaluate() {
        return 0;
    }
}