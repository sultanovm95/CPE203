package com.company;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;


public class TestCases
{
   private static final double DELTA = 0.00001;

   @Test
   public void testSimpleIf1()
   {
      assertEquals(1.7, SimpleIf.max(1.2, 1.7), DELTA);
   }

   @Test
   public void testSimpleIf2()
   {
      assertEquals(9.0, SimpleIf.max(9.0, 3.2), DELTA);
   }

   @Test
   public void testSimpleIf3()
   {
      //fail("Missing SimpleIf3");
      /* TO DO: Write one more valid test case. */
      assertEquals(1.0, SimpleIf.max(1.0, -3.2), DELTA);
   }

   @Test
   public void testSimpleLoop1()
   {
      assertEquals(7, SimpleLoop.sum(3, 4));
   }

   @Test
   public void testSimpleLoop2()
   {
      assertEquals(7, SimpleLoop.sum(-2, 4));
   }

   @Test
   public void testSimpleLoop3()
   {
      //fail("Missing SimpleLoop3");
      /* TO DO: Write one more valid test case to make sure that
         this function is not just returning 7. */
      assertEquals(18, SimpleLoop.sum(-2, 6));
   }

   @Test
   public void testSimpleArray1()
   {
      /* What are those parameters?  They are newly allocated arrays
         with initial values. */
      assertArrayEquals(
         new int[] {1, 4, 9},
         SimpleArray.squareAll(new int[] {1, 2, 3}));
   }

   @Test
   public void testSimpleArray2()
   {
      assertArrayEquals(
         new int[] {49, 25},
         SimpleArray.squareAll(new int[] {7, 5}));
   }

   @Test
   public void testSimpleArray3()
   {
      //fail("Missing SimpleArray3");
      /* TO DO: Add a new test case. */
      assertArrayEquals(
              new int[] {0, 9, 81},
              SimpleArray.squareAll(new int[] {0, 3, 9}));
   }

   @Test
   public void testSimpleList1()
   {
      List<Integer> input =
         new LinkedList<Integer>(Arrays.asList(new Integer[] {1, 2, 3}));
      List<Integer> expected =
         new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 4, 9}));

      assertEquals(expected, SimpleList.squareAll(input));
   }

   @Test
   public void testSimpleList2()
   {
      //fail("Missing SimpleList2");
      /* TO DO: Add a new test case. */
      List<Integer> input =
              new LinkedList<Integer>(Arrays.asList(new Integer[] {4, 5, 6}));
      List<Integer> expected =
              new ArrayList<Integer>(Arrays.asList(new Integer[] {16, 25, 36}));

      assertEquals(expected, SimpleList.squareAll(input));
   }

   @Test
   public void testBetterLoop1()
   {
      assertTrue(BetterLoop.contains(new int[] {7, 5}, 5));
   }

   @Test
   public void testBetterLoop2()
   {
      assertTrue(BetterLoop.contains(new int[] {7, 5, 2, 4}, 4));
   }

   @Test
   public void testBetterLoop3()
   {
      //fail("Missing BetterLoop3");
      /* TO DO: Write a valid test case where the expected result is false. */
      assertTrue(BetterLoop.contains(new int[] {21, -5, 62, 24}, -5));
   }

   @Test
   public void testExampleMap1()
   {
      List<String> expected = Arrays.asList("Julie", "Zoe");
      Map<String, List<Course>> courseListsByStudent = new HashMap<>();

      courseListsByStudent.put("Julie",
         Arrays.asList(
            new Course("CPE 123", 4),
            new Course("CPE 101", 4),
            new Course("CPE 202", 4),
            new Course("CPE 203", 4),
            new Course("CPE 225", 4)));
      courseListsByStudent.put("Paul",
         Arrays.asList(
            new Course("CPE 101", 4),
            new Course("CPE 202", 4),
            new Course("CPE 203", 4),
            new Course("CPE 225", 4)));
      courseListsByStudent.put("Zoe",
         Arrays.asList(
            new Course("CPE 123", 4),
            new Course("CPE 203", 4),
            new Course("CPE 471", 4),
            new Course("CPE 473", 4),
            new Course("CPE 476", 4),
            new Course("CPE 572", 4)));

      /*
       * Why compare HashSets here?  Just so that the order of the
       * elements in the list is not important for this test.
       */
      assertEquals(new HashSet<>(expected),
         new HashSet<>(ExampleMap.highEnrollmentStudents(
            courseListsByStudent, 16)));
   }

   @Test
   public void testExampleMap2()
   {
      //fail("Missing ExampleMap2");
      /* TO DO: Write another valid test case. */

      List<String> expected = Arrays.asList("Julie", "Zoe");
      Map<String, List<Course>> courseListsByStudent = new HashMap<>();

      courseListsByStudent.put("Sultanov",
              Arrays.asList(
                      new Course("CPE 123", 4),
                      new Course("CPE 101", 4),
                      new Course("CPE 202", 4),
                      new Course("CPE 203", 4),
                      new Course("CPE 225", 4)));
      courseListsByStudent.put("Orif",
              Arrays.asList(
                      new Course("CPE 101", 4),
                      new Course("CPE 202", 4),
                      new Course("CPE 203", 4),
                      new Course("CPE 225", 4)));

   }

   @Test
   public void testPointX()
   {
      Point checkInstance = new Point(1.0, 2.0);
      assertEquals(1.0, checkInstance.getX(), DELTA);
   }

   @Test
   public void testPointY()
   {
      Point checkInstance = new Point(-1.0, 15.0);
      assertEquals(15.0, checkInstance.getY(), DELTA);
   }

   @Test
   public void testGetRadius()
   {
      Point checkInstance = new Point(4.0, 3.0);
      assertEquals(5.0, checkInstance.getRadius(), DELTA);
   }

   @Test
   public void testGetAngle()
   {
      // data degree: 135 position(-2,2)
      // data degree: 45 position (1,1)
      double degree = 135;
      double expected = degree * (Math.PI/180);
      Point checkInstance = new Point( -2.0, 2.0);
      assertEquals(expected, checkInstance.getAngle(), DELTA);
   }

   @Test
   public void testRotate90()
   {
      Point checkInstance = new Point( 1.0, 3.0);
      Point rotatedInstance = checkInstance.rotate90();
      // expected [3,-1]
      assertArrayEquals(new double[] {3.0, -1.0}, new double[] {rotatedInstance.getX(), rotatedInstance.getY()}, DELTA);
   }


}


