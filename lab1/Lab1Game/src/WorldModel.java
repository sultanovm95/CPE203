import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

final class WorldModel
{
   public int numRows;
   public int numCols;
   public Background background[][];
   public Entity occupancy[][];
   public Set<Entity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
              (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
   }

   public Optional<Entity> getOccupant(WorldModel world, Point pos)
   {
      if (world.isOccupied(world, pos))
      {
         return Optional.of(world.getOccupancyCell(world, pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public boolean isOccupied(WorldModel world, Point pos)
   {
      return withinBounds(world, pos) &&
              getOccupancyCell(world, pos) != null;
   }

   public Entity getOccupancyCell(WorldModel world, Point pos)
   {
      return world.occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(WorldModel world, Point pos,
                                       Entity entity)
   {
      world.occupancy[pos.y][pos.x] = entity;
   }

   public boolean withinBounds(WorldModel world, Point pos)
   {
      return pos.y >= 0 && pos.y < world.numRows &&
              pos.x >= 0 && pos.x < world.numCols;
   }

   public void transformFull(Entity entity, WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
   {
      Entity miner = imageStore.createMinerNotFull(entity.id, entity.resourceLimit,
              entity.position, entity.actionPeriod, entity.animationPeriod,
              entity.images);

      entity.removeEntity(world, entity);
      scheduler.unscheduleAllEvents(scheduler, entity);

      entity.addEntity(world, miner);
      scheduler.scheduleActions(miner, scheduler, world, imageStore);
   }

   public Background getBackgroundCell(WorldModel world, Point pos)
   {
      return world.background[pos.y][pos.x];
   }



}
