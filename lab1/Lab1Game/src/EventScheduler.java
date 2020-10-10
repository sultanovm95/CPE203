import java.util.*;

final class EventScheduler
{
   public PriorityQueue<Event> eventQueue;
   public Map<Entity, List<Event>> pendingEvents;
   public double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }

   public EventScheduler() {

   }

   public void scheduleEvent(EventScheduler scheduler,
                                    Entity entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * scheduler.timeScale);
      Event event = new Event(action, time, entity);

      scheduler.eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = scheduler.pendingEvents.getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      scheduler.pendingEvents.put(entity, pending);
   }

   public void scheduleActions(Entity entity, EventScheduler scheduler,
                                      WorldModel world, ImageStore imageStore)
   {
      switch (entity.kind)
      {
         case MINER_FULL:
            scheduleEvent(scheduler, entity,
                    scheduler.createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
            scheduler.scheduleEvent(scheduler, entity, scheduler.createAnimationAction(entity, 0),
                    scheduler.getAnimationPeriod(entity));
            break;

         case MINER_NOT_FULL:
            scheduleEvent(scheduler, entity,
                    scheduler.createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
            scheduleEvent(scheduler, entity,
                    scheduler.createAnimationAction(entity, 0), scheduler.getAnimationPeriod(entity));
            break;

         case ORE:
            scheduleEvent(scheduler, entity,
                    scheduler.createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
            break;

         case ORE_BLOB:
            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
            scheduleEvent(scheduler, entity,
                    createAnimationAction(entity, 0), scheduler.getAnimationPeriod(entity));
            break;

         case QUAKE:
            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
            scheduleEvent(scheduler, entity,
                    createAnimationAction(entity, entity.QUAKE_ANIMATION_REPEAT_COUNT),
                    getAnimationPeriod(entity));
            break;

         case VEIN:
            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
            break;

         default:
      }
   }

   public Action createActivityAction(Entity entity, WorldModel world,
                                      ImageStore imageStore)
   {
      return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
   }

   public Action createAnimationAction(Entity entity, int repeatCount)
   {
      return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
   }

   public int getAnimationPeriod(Entity entity)
   {
      switch (entity.kind)
      {
         case MINER_FULL:
         case MINER_NOT_FULL:
         case ORE_BLOB:
         case QUAKE:
            return entity.animationPeriod;
         default:
            throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s",
                            entity.kind));
      }
   }

   public void unscheduleAllEvents(EventScheduler scheduler,
                                          Entity entity)
   {
      List<Event> pending = scheduler.pendingEvents.remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            scheduler.eventQueue.remove(event);
         }
      }
   }


}
