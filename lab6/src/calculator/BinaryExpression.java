package calculator;

abstract class BinaryExpression implements Expression {
    private final Expression lft;
    private final Expression rht;
    private String operation;

    BinaryExpression(Expression left, Expression right, String operation) {
        this.lft = left;
        this.rht = right;
        this.operation = operation;
    }

    public String toString() {
        return " (" + lft + " " + operation + " " + rht + " )";
    }

    @Override
    public double evaluate(final Bindings bindings)
    {
        return _applyOperator(lft.evaluate(bindings), rht.evaluate(bindings));
    }

    protected abstract double _applyOperator(Double left, Double right);
}
