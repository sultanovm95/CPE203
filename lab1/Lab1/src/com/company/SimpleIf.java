package com.company;

class SimpleIf
{
   public static double max(double x, double y)
   {
      if(x > y)
         return x;
      else if(x < y)
         return y;
      else
         return x; // clearly not correct -- but testable
   }
}
