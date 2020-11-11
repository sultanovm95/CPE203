package calculator;

class SubtractExpression
   extends BinaryExpression
{
   private final Expression lft;
   private final Expression rht;

   public SubtractExpression(final Expression lft, final Expression rht)
   {
      super(lft, rht, "-");
      this.lft = lft;
      this.rht = rht;
   }

   @Override
   protected double _applyOperator(Double left, Double right) {
      return left - right;
   }
}
