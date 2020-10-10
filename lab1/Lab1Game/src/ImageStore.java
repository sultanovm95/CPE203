import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

final class ImageStore
{
   public Map<String, List<PImage>> images;
   public List<PImage> defaultImages;

   public static final int COLOR_MASK = 0xffffff;
   public static final int KEYED_IMAGE_MIN = 5;
   private static final int KEYED_RED_IDX = 2;
   private static final int KEYED_GREEN_IDX = 3;
   private static final int KEYED_BLUE_IDX = 4;

   public static final int BGND_NUM_PROPERTIES = 4;
   public static final int BGND_ID = 1;
   public static final int BGND_COL = 2;
   public static final int BGND_ROW = 3;



   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }



   public void nextImage(Entity entity)
   {
      entity.imageIndex = (entity.imageIndex + 1) % entity.images.size();
   }

   public Entity createOre(String id, Point position, int actionPeriod,
                           List<PImage> images)
   {
      return new Entity(EntityKind.ORE, id, position, images, 0, 0,
              actionPeriod, 0);
   }

   public Entity createVein(String id, Point position, int actionPeriod,
                            List<PImage> images)
   {
      return new Entity(EntityKind.VEIN, id, position, images, 0, 0,
              actionPeriod, 0);
   }

   public Entity createMinerNotFull(String id, int resourceLimit,
                                    Point position, int actionPeriod, int animationPeriod,
                                    List<PImage> images)
   {
      return new Entity(EntityKind.MINER_NOT_FULL, id, position, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }

   public Entity createMinerFull(String id, int resourceLimit,
                                        Point position, int actionPeriod, int animationPeriod,
                                        List<PImage> images)
   {
      return new Entity(EntityKind.MINER_FULL, id, position, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }

   public List<PImage> getImageList(ImageStore imageStore, String key)
   {
      return imageStore.images.getOrDefault(key, imageStore.defaultImages);
   }

   public void tryAddEntity(WorldModel world, Entity entity)
   {
      if (world.isOccupied(world, entity.position))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      entity.addEntity(world, entity);
   }


   public Entity createBlacksmith(String id, Point position,
                                         List<PImage> images)
   {
      return new Entity(EntityKind.BLACKSMITH, id, position, images,
              0, 0, 0, 0);
   }

   public Entity createObstacle(String id, Point position,
                                       List<PImage> images)
   {
      return new Entity(EntityKind.OBSTACLE, id, position, images,
              0, 0, 0, 0);
   }


   public void processImageLine(Map<String, List<PImage>> images,
                                       String line, PApplet screen)
   {
      String[] attrs = line.split("\\s");
      if (attrs.length >= 2)
      {
         String key = attrs[0];
         PImage img = screen.loadImage(attrs[1]);
         if (img != null && img.width != -1)
         {
            List<PImage> imgs = getImages(images, key);
            imgs.add(img);

            if (attrs.length >= KEYED_IMAGE_MIN)
            {
               int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
               int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
               int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
               setAlpha(img, screen.color(r, g, b), 0);
            }
         }
      }
   }




   public void setAlpha(PImage img, int maskColor, int alpha)
   {
      int alphaValue = alpha << 24;
      int nonAlpha = maskColor & COLOR_MASK;
      img.format = PApplet.ARGB;
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
         {
            img.pixels[i] = alphaValue | nonAlpha;
         }
      }
      img.updatePixels();
   }

   public List<PImage> getImages(Map<String, List<PImage>> images,
                                        String key)
   {
      List<PImage> imgs = images.get(key);
      if (imgs == null)
      {
         imgs = new LinkedList<>();
         images.put(key, imgs);
      }
      return imgs;
   }

   public  boolean parseBackground(String [] properties,
                                   WorldModel world, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         setBackground(world, pt,
                 new Background(id, imageStore.getImageList(imageStore, id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public void setBackground(WorldModel world, Point pos,
                             Background background)
   {
      if (world.withinBounds(world, pos))
      {
         background.setBackgroundCell(world, pos, background);
      }
   }




}
