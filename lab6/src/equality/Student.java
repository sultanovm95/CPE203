package equality;

import java.util.List;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Student) {
         Student student = (Student) obj;
         if(student.surname.equals(this.surname)) {
            if(student.givenName.equals(this.givenName)) {
               if(student.age == this.age) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   @Override
   public int hashCode() {
      final int startCode = 23 ;
      int result = startCode * this.age + currentCourses.size()*32 ;
      return result;
   }
}
