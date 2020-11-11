package calculator;

class AddExpression
   extends BinaryExpression
{
  public AddExpression(final Expression lft, final Expression rht)
   {
      super(lft,rht, "+");
   }

   @Override
   protected double _applyOperator(Double left, Double right) {
      return left + right;
   }
}
