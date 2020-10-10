import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import processing.core.PImage;

final class Entity
{
   public EntityKind kind;
   public String id;
   public Point position;
   public List<PImage> images;
   public int imageIndex;
   public int resourceLimit;
   public int resourceCount;
   public int actionPeriod;
   public int animationPeriod;

   public final String BLOB_KEY = "blob";
   public final String BLOB_ID_SUFFIX = " -- blob";
   public final int BLOB_PERIOD_SCALE = 4;
   public final int BLOB_ANIMATION_MIN = 50;
   public final int BLOB_ANIMATION_MAX = 150;

   public final String ORE_ID_PREFIX = "ore -- ";
   public final int ORE_CORRUPT_MIN = 20000;
   public final int ORE_CORRUPT_MAX = 30000;
   public final int ORE_REACH = 1;

   public final String QUAKE_KEY = "quake";
   public final String QUAKE_ID = "quake";
   public final int QUAKE_ACTION_PERIOD = 1100;
   public final int QUAKE_ANIMATION_PERIOD = 100;
   public final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   public static final String MINER_KEY = "miner";
   public static final int MINER_NUM_PROPERTIES = 7;
   public static final int MINER_ID = 1;
   public static final int MINER_COL = 2;
   public static final int MINER_ROW = 3;
   public static final int MINER_LIMIT = 4;
   public static final int MINER_ACTION_PERIOD = 5;
   public static final int MINER_ANIMATION_PERIOD = 6;

   public static final String OBSTACLE_KEY = "obstacle";
   public static final int OBSTACLE_NUM_PROPERTIES = 4;
   public static final int OBSTACLE_ID = 1;
   public static final int OBSTACLE_COL = 2;
   public static final int OBSTACLE_ROW = 3;

   public static final String ORE_KEY = "ore";
   public static final int ORE_NUM_PROPERTIES = 5;
   public static final int ORE_ID = 1;
   public static final int ORE_COL = 2;
   public static final int ORE_ROW = 3;
   public static final int ORE_ACTION_PERIOD = 4;

   public static final String SMITH_KEY = "blacksmith";
   public static final int SMITH_NUM_PROPERTIES = 4;
   public static final int SMITH_ID = 1;
   public static final int SMITH_COL = 2;
   public static final int SMITH_ROW = 3;

   public static final String VEIN_KEY = "vein";
   public static final int VEIN_NUM_PROPERTIES = 5;
   public static final int VEIN_ID = 1;
   public static final int VEIN_COL = 2;
   public static final int VEIN_ROW = 3;
   public static final int VEIN_ACTION_PERIOD = 4;

   public static final int PROPERTY_KEY = 0;

   public static final String BGND_KEY = "background";

   public Entity(EntityKind kind, String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount,
      int actionPeriod, int animationPeriod)
   {
      this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }

   public Entity() {

   }

   /*
         Creating Heroes

    */
   public Entity createOreBlob(String id, Point position,
                                      int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Entity(EntityKind.ORE_BLOB, id, position, images,
              0, 0, actionPeriod, animationPeriod);
   }

   public Entity createQuake(Point position, List<PImage> images)
   {
      return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images,
              0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   /////////////////////////////////////

   public Optional<Entity> findNearest(WorldModel world, Point pos,
                                              EntityKind kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : world.entities)
      {
         if (entity.kind == kind)
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = pos.distanceSquared(nearest.position, pos);

         for (Entity other : entities)
         {
            int otherDistance = pos.distanceSquared(other.position, pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public boolean moveToNotFull(Entity miner, WorldModel world,
                                       Entity target, EventScheduler scheduler)
   {
      if (world.adjacent(miner.position, target.position))
      {
         miner.resourceCount += 1;
         target.removeEntity(world, target);
         scheduler.unscheduleAllEvents(scheduler, target);

         return true;
      }
      else
      {
         Point nextPos = nextPositionMiner(miner, world, target.position);

         if (!miner.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(world, nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(scheduler, occupant.get());
            }

            moveEntity(world, miner, nextPos);
         }
         return false;
      }
   }

   public void moveEntity(WorldModel world, Entity entity, Point pos)
   {
      Point oldPos = entity.position;
      if (world.withinBounds(world, pos) && !pos.equals(oldPos))
      {
         world.setOccupancyCell(world, oldPos, null);
         entity.removeEntityAt(world, pos);
         world.setOccupancyCell(world, pos, entity);
         entity.position = pos;
      }
   }

   //////////////


   public Point nextPositionMiner(Entity entity, WorldModel world,
                                         Point destPos)
   {
      int horiz = Integer.signum(destPos.x - entity.position.x);
      Point newPos = new Point(entity.position.x + horiz,
              entity.position.y);

      if (horiz == 0 || world.isOccupied(world, newPos))
      {
         int vert = Integer.signum(destPos.y - entity.position.y);
         newPos = new Point(entity.position.x,
                 entity.position.y + vert);

         if (vert == 0 || world.isOccupied(world, newPos))
         {
            newPos = entity.position;
         }
      }

      return newPos;
   }


   public boolean transformNotFull(Entity entity, WorldModel world,
                                          EventScheduler scheduler, ImageStore imageStore)
   {
      if (entity.resourceCount >= entity.resourceLimit)
      {
         Entity miner = imageStore.createMinerFull(entity.id, entity.resourceLimit,
                 entity.position, entity.actionPeriod, entity.animationPeriod,
                 entity.images);

         removeEntity(world, entity);
         scheduler.unscheduleAllEvents(scheduler, entity);

         entity.addEntity(world, miner);
         scheduler.scheduleActions(miner, scheduler, world, imageStore);

         return true;
      }

      return false;
   }

   public void removeEntity(WorldModel world, Entity entity)
   {
      removeEntityAt(world, entity.position);
   }

   public void removeEntityAt(WorldModel world, Point pos)
   {
      if (world.withinBounds(world, pos)
              && world.getOccupancyCell(world, pos) != null)
      {
         Entity entity = world.getOccupancyCell(world, pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.position = new Point(-1, -1);
         world.entities.remove(entity);
         world.setOccupancyCell(world, pos, null);
      }
   }

   public void addEntity(WorldModel world, Entity entity)
   {
      if (world.withinBounds(world, entity.position))
      {
         world.setOccupancyCell(world, entity.position, entity);
         world.entities.add(entity);
      }
   }

   public boolean moveToFull(Entity miner, WorldModel world,
                                    Entity target, EventScheduler scheduler)
   {
      if (world.adjacent(miner.position, target.position))
      {
         return true;
      }
      else
      {
         Point nextPos = nextPositionMiner(miner, world, target.position);

         if (!miner.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(world, nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(scheduler, occupant.get());
            }

            moveEntity(world, miner, nextPos);
         }
         return false;
      }
   }


   /// POSITION BLOB

   public boolean moveToOreBlob(Entity blob, WorldModel world,
                                       Entity target, EventScheduler scheduler)
   {
      if (world.adjacent(blob.position, target.position))
      {
         removeEntity(world, target);
         scheduler.unscheduleAllEvents(scheduler, target);
         return true;
      }
      else
      {
         Point nextPos = nextPositionOreBlob(blob, world, target.position);

         if (!blob.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(world, nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(scheduler, occupant.get());
            }

            moveEntity(world, blob, nextPos);
         }
         return false;
      }
   }

   public Point nextPositionOreBlob(Entity entity, WorldModel world,
                                           Point destPos)
   {
      int horiz = Integer.signum(destPos.x - entity.position.x);
      Point newPos = new Point(entity.position.x + horiz,
              entity.position.y);

      Optional<Entity> occupant = world.getOccupant(world, newPos);

      if (horiz == 0 ||
              (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
      {
         int vert = Integer.signum(destPos.y - entity.position.y);
         newPos = new Point(entity.position.x, entity.position.y + vert);
         occupant = world.getOccupant(world, newPos);

         if (vert == 0 ||
                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
         {
            newPos = entity.position;
         }
      }

      return newPos;
   }

   public Optional<Point> findOpenAround(WorldModel world, Point pos)
   {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
      {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (world.withinBounds(world, newPt) &&
                    !world.isOccupied(world, newPt))
            {
               return Optional.of(newPt);
            }
         }
      }
      return Optional.empty();
   }


   public boolean parseMiner(String [] properties, WorldModel world,
                                    ImageStore imageStore)
   {
      if (properties.length == MINER_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                 Integer.parseInt(properties[MINER_ROW]));
         Entity entity = imageStore.createMinerNotFull(properties[MINER_ID],
                 Integer.parseInt(properties[MINER_LIMIT]),
                 pt,
                 Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                 Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                 imageStore.getImageList(imageStore, MINER_KEY));
         imageStore.tryAddEntity(world, entity);
      }

      return properties.length == MINER_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String [] properties, WorldModel world,
                                       ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = imageStore.createObstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList(imageStore, OBSTACLE_KEY));
         imageStore.tryAddEntity(world, entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseOre(String [] properties, WorldModel world,
                                  ImageStore imageStore)
   {
      if (properties.length == ORE_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                 Integer.parseInt(properties[ORE_ROW]));
         Entity entity = imageStore.createOre(properties[ORE_ID],
                 pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                 imageStore.getImageList(imageStore, ORE_KEY));
         imageStore.tryAddEntity(world, entity);
      }

      return properties.length == ORE_NUM_PROPERTIES;
   }

   public boolean parseSmith(String [] properties, WorldModel world,
                                    ImageStore imageStore)
   {
      if (properties.length == SMITH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                 Integer.parseInt(properties[SMITH_ROW]));
         Entity entity = imageStore.createBlacksmith(properties[SMITH_ID],
                 pt, imageStore.getImageList(imageStore, SMITH_KEY));
         imageStore.tryAddEntity(world, entity);
      }

      return properties.length == SMITH_NUM_PROPERTIES;
   }

   public boolean parseVein(String [] properties, WorldModel world,
                                   ImageStore imageStore)
   {
      if (properties.length == VEIN_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                 Integer.parseInt(properties[VEIN_ROW]));
         Entity entity = imageStore.createVein(properties[VEIN_ID],
                 pt,
                 Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                 imageStore.getImageList(imageStore, VEIN_KEY));
         imageStore.tryAddEntity(world, entity);
      }

      return properties.length == VEIN_NUM_PROPERTIES;
   }

   public boolean processLine(String line, WorldModel world,
                                     ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return imageStore.parseBackground(properties, world, imageStore);
            case MINER_KEY:
               return parseMiner(properties, world, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, world, imageStore);
            case ORE_KEY:
               return parseOre(properties, world, imageStore);
            case SMITH_KEY:
               return parseSmith(properties, world, imageStore);
            case VEIN_KEY:
               return parseVein(properties, world, imageStore);
         }
      }

      return false;
   }

   public void load(Scanner in, WorldModel world, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }



}
