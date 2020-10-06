package com.company;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap
{
   public static List<String> highEnrollmentStudents(
      Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List<String> overEnrolledStudents = new LinkedList<>();

      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */

      for(String name : courseListsByStudentName.keySet()) {
         int sumOfUnits = 0;
         List<Course> selCourse = courseListsByStudentName.get(name);
         for(int i = 0; i < selCourse.size(); i++) {
            sumOfUnits += selCourse.get(i).getNumUnits();
         }
         if(sumOfUnits > 16) {
            overEnrolledStudents.add(name);
         }
      }

      return overEnrolledStudents;      
   }
}
