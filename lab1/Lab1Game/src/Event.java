import java.util.List;

final class Event
{
   public Action action;
   public long time;
   public Entity entity;

   public Event(Action action, long time, Entity entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }

   public Event() {

   }

   public void removePendingEvent(EventScheduler scheduler,
                                         Event event)
   {
      List<Event> pending = scheduler.pendingEvents.get(event.entity);

      if (pending != null)
      {
         pending.remove(event);
      }
   }

   public void updateOnTime(EventScheduler scheduler, long time)
   {

      while (!scheduler.eventQueue.isEmpty() &&
              scheduler.eventQueue.peek().time < time)
      {
         Event next = scheduler.eventQueue.poll();

         removePendingEvent(scheduler, next);

         executeAction(next.action, scheduler);
      }
   }




}
