import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

final class WorldView
{
   public PApplet screen;
   public WorldModel world;
   public int tileWidth;
   public int tileHeight;
   public Viewport viewport;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }

   public WorldView() {

   }

   public Point worldToViewport(Viewport viewport, int col, int row)
   {
      return new Point(col - viewport.col, row - viewport.row);
   }

   public Optional<PImage> getBackgroundImage(WorldModel world,
                                              Point pos)
   {
      if (world.withinBounds(world, pos))
      {
         return Optional.of(getCurrentImage(world.getBackgroundCell(world, pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).images
                 .get(((Background)entity).imageIndex);
      }
      else if (entity instanceof Entity)
      {
         return ((Entity)entity).images.get(((Entity)entity).imageIndex);
      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         entity));
      }
   }

   public void shift(Viewport viewport, int col, int row)
   {
      viewport.col = col;
      viewport.row = row;
   }

   public int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }

   public void shiftView(WorldView view, int colDelta, int rowDelta)
   {
      int newCol = clamp(view.viewport.col + colDelta, 0,
              view.world.numCols - view.viewport.numCols);
      int newRow = clamp(view.viewport.row + rowDelta, 0,
              view.world.numRows - view.viewport.numRows);

      shift(view.viewport, newCol, newRow);
   }
}
