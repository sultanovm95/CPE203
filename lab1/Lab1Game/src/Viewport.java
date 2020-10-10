import processing.core.PImage;

import java.util.Optional;

final class Viewport
{
   public int row;
   public int col;
   public int numRows;
   public int numCols;

   public Viewport(int numRows, int numCols)
   {
      this.numRows = numRows;
      this.numCols = numCols;
   }

   public Viewport() {

   }

   public Point viewportToWorld(Viewport viewport, int col, int row)
   {
      return new Point(col + viewport.col, row + viewport.row);
   }

   public void drawBackground(WorldView view)
   {
      for (int row = 0; row < view.viewport.numRows; row++)
      {
         for (int col = 0; col < view.viewport.numCols; col++)
         {
            Point worldPoint = viewportToWorld(view.viewport, col, row);
            Optional<PImage> image = view.getBackgroundImage(view.world,
                    worldPoint);
            if (image.isPresent())
            {
               view.screen.image(image.get(), col * view.tileWidth,
                       row * view.tileHeight);
            }
         }
      }
   }

   public void drawEntities(WorldView view)
   {
      for (Entity entity : view.world.entities)
      {
         Point pos = entity.position;

         if (contains(view.viewport, pos))
         {
            Point viewPoint = view.worldToViewport(view.viewport, pos.x, pos.y);
            view.screen.image(view.getCurrentImage(entity),
                    viewPoint.x * view.tileWidth, viewPoint.y * view.tileHeight);
         }
      }
   }

   public boolean contains(Viewport viewport, Point p)
   {
      return p.y >= viewport.row && p.y < viewport.row + viewport.numRows &&
              p.x >= viewport.col && p.x < viewport.col + viewport.numCols;
   }


   public void drawViewport(WorldView view)
   {
      drawBackground(view);
      drawEntities(view);
   }




}
