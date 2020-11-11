package equality;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

public class TestCases
{
   @Test
   public void testExercise1()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise5()
   {
      List<CourseSection> myCourse = new ArrayList<>();
      myCourse.add(new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student MO = new Student("Sultanov", "Mukhammadorif", 24, myCourse);
      final Student ZB = new Student("Sultanov", "Mukhammadorif", 24, myCourse);


      assertTrue(MO.equals(ZB));
      assertTrue(ZB.equals(MO));
   }

   @Test
   public void testExercise6()
   {
      List<CourseSection> myCourse = new ArrayList<>();
      myCourse.add(new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student MO = new Student("Shokirov", "Alisher", 27, myCourse);
      final Student ZB = new Student("Sultanov", "Mukhammadorif", 24, myCourse);

      assertFalse(MO.equals(ZB));
      assertFalse(ZB.equals(MO));
   }

   @Test
   public void testExercise7()
   {
      List<CourseSection> myCourse = new ArrayList<>();
      myCourse.add(new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student MO = new Student("Sultanov", "Mukhammadorif", 24, myCourse);
      final Student ZB = new Student("Sultanov", "Mukhammadorif", 24, myCourse);

      assertEquals(MO.hashCode(), ZB.hashCode());
   }
}
