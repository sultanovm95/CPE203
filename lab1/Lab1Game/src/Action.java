import java.util.Optional;
import java.util.Random;

final class Action
{
   public ActionKind kind;
   public Entity entity;
   public WorldModel world;
   public ImageStore imageStore;
   public int repeatCount;
   Random rand = new Random();


   public Action(ActionKind kind, Entity entity, WorldModel world,
      ImageStore imageStore, int repeatCount)
   {
      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   public Action() {

   }




   public void executeAction(Action action, EventScheduler scheduler)
   {
      switch (action.kind)
      {
         case ACTIVITY:
            executeActivityAction(action, scheduler);
            break;

         case ANIMATION:
            executeAnimationAction(action, scheduler);
            break;
      }
   }

   public void executeAnimationAction(Action action,
                                             EventScheduler scheduler)
   {
      imageStore.nextImage(action.entity);

      EventScheduler eventScheduler = new EventScheduler();

      if (action.repeatCount != 1)
      {
         eventScheduler.scheduleEvent(scheduler, action.entity,
                 scheduler.createAnimationAction(action.entity,
                         Math.max(action.repeatCount - 1, 0)),
                 scheduler.getAnimationPeriod(action.entity));
      }
   }

   public void executeActivityAction(Action action,
                                            EventScheduler scheduler)
   {
      switch (action.entity.kind)
      {
         case MINER_FULL:
            executeMinerFullActivity(action.entity, action.world,
                    action.imageStore, scheduler);
            break;

         case MINER_NOT_FULL:
            executeMinerNotFullActivity(action.entity, action.world,
                    action.imageStore, scheduler);
            break;

         case ORE:
            executeOreActivity(action.entity, action.world, action.imageStore,
                    scheduler);
            break;

         case ORE_BLOB:
            executeOreBlobActivity(action.entity, action.world,
                    action.imageStore, scheduler);
            break;

         case QUAKE:
            executeQuakeActivity(action.entity, action.world, action.imageStore,
                    scheduler);
            break;

         case VEIN:
            executeVeinActivity(action.entity, action.world, action.imageStore,
                    scheduler);
            break;

         default:
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            action.entity.kind));
      }
   }

   public void executeOreActivity(Entity entity, WorldModel world,
                                         ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = entity.position;  // store current position before removing

      entity.removeEntity(world, entity);
      scheduler.unscheduleAllEvents(scheduler, entity);

      Entity blob = entity.createOreBlob(entity.id + entity.BLOB_ID_SUFFIX,
              pos, entity.actionPeriod / entity.BLOB_PERIOD_SCALE,
              entity.BLOB_ANIMATION_MIN +
                      rand.nextInt(entity.BLOB_ANIMATION_MAX - entity.BLOB_ANIMATION_MIN),
              imageStore.getImageList(imageStore, entity.BLOB_KEY));

      entity.addEntity(world, blob);
      scheduler.scheduleActions(blob, scheduler, world, imageStore);
   }

   public void executeMinerFullActivity(Entity entity, WorldModel world,
                                               ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = entity.findNearest(world, entity.position,
              EntityKind.BLACKSMITH);

      if (fullTarget.isPresent() &&
              entity.moveToFull(entity, world, fullTarget.get(), scheduler))
      {
         world.transformFull(entity, world, scheduler, imageStore);
      }
      else
      {
         scheduler.scheduleEvent(scheduler, entity,
                 scheduler.createActivityAction(entity, world, imageStore),
                 entity.actionPeriod);
      }
   }

   public void executeMinerNotFullActivity(Entity entity,
                                                  WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = entity.findNearest(world, entity.position,
              EntityKind.ORE);

      if (!notFullTarget.isPresent() ||
              !entity.moveToNotFull(entity, world, notFullTarget.get(), scheduler) ||
              !entity.transformNotFull(entity, world, scheduler, imageStore))
      {
         scheduler.scheduleEvent(scheduler, entity,
                 scheduler.createActivityAction(entity, world, imageStore),
                 entity.actionPeriod);
      }
   }



   public void executeOreBlobActivity(Entity entity, WorldModel world,
                                             ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> blobTarget = entity.findNearest(world,
              entity.position, EntityKind.VEIN);
      long nextPeriod = entity.actionPeriod;

      if (blobTarget.isPresent())
      {
         Point tgtPos = blobTarget.get().position;

         if (entity.moveToOreBlob(entity, world, blobTarget.get(), scheduler))
         {
            Entity quake = entity.createQuake(tgtPos,
                    imageStore.getImageList(imageStore, entity.QUAKE_KEY));

            entity.addEntity(world, quake);
            nextPeriod += entity.actionPeriod;
            scheduler.scheduleActions(quake, scheduler, world, imageStore);
         }
      }

      scheduler.scheduleEvent(scheduler, entity,
              scheduler.createActivityAction(entity, world, imageStore),
              nextPeriod);
   }

   public void executeQuakeActivity(Entity entity, WorldModel world,
                                           ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(scheduler, entity);
      entity.removeEntity(world, entity);
   }

   public void executeVeinActivity(Entity entity, WorldModel world,
                                          ImageStore imageStore, EventScheduler scheduler)
   {

      Optional<Point> openPt = entity.findOpenAround(world, entity.position);

      if (openPt.isPresent())
      {
         Entity ore = imageStore.createOre(entity.ORE_ID_PREFIX + entity.id,
                 openPt.get(), entity.ORE_CORRUPT_MIN +
                         rand.nextInt(entity.ORE_CORRUPT_MAX - entity.ORE_CORRUPT_MIN),
                 imageStore.getImageList(imageStore, entity.ORE_KEY));
         entity.addEntity(world, ore);
         scheduler.scheduleActions(ore, scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(scheduler, entity,
              scheduler.createActivityAction(entity, world, imageStore),
              entity.actionPeriod);
   }




}
