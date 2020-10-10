import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

final class Background
{
   public String id;
   public List<PImage> images;
   public int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   public Background() {

   }

   public void loadImages(Scanner in, ImageStore imageStore,
                                 PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            imageStore.processImageLine(imageStore.images, in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
                    lineNumber));
         }
         lineNumber++;
      }
   }

   public void setBackgroundCell(WorldModel world, Point pos,
                                        Background background)
   {
      world.background[pos.y][pos.x] = background;
   }




}
