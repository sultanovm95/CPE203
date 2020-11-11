package equality;

import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   // additional likely methods not defined since they are not needed for testing


   @Override
   public boolean equals(Object obj) {
      if(obj instanceof CourseSection ) {
         CourseSection course = (CourseSection) obj;
         if(this.number == course.number) {
            if(this.prefix.compareTo(course.prefix) == 0) {
               if(this.enrollment == course.enrollment) {
                  if(this.startTime.equals(course.startTime)) {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   @Override
   public int hashCode() {
      final int startCode = 23 ;
      int result = startCode * this.enrollment + startTime.getHour() + startTime.getMinute()
              + endTime.getMinute() + endTime.getHour() + this.number.length() + this.prefix.length();
      return result;
   }
}
