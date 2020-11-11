package com.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));

      List<Song> expectedList = Arrays.asList(
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
      );

      songList.sort(new ArtistComparator());

      assertEquals(songList, expectedList);
   }

   @Test
   public void testLambdaTitleComparator()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));

      List<Song> expectedList = Arrays.asList(
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005)
      );

      songList.sort((Song o1, Song o2) -> o1.getTitle().compareTo(o2.getTitle()));

      assertEquals(songList, expectedList);
   }

   @Test
   public void testYearExtractorComparator()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));

      List<Song> expectedList = Arrays.asList(

              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("City and Colour", "Sleeping Sickness", 2007)

      );
      Comparator<Song> byYear = Comparator.comparing(Song::getYear);
      songList.sort(byYear);
      
      assertEquals(songList, expectedList);
   }

   @Test
   public void testComposedComparator()
   {

      Comparator<Song> c1 = Comparator.comparing(Song::getTitle);
      Comparator<Song> c2 = Comparator.comparing(Song::getYear);

      ComposedComparator compare = new ComposedComparator(c1,c2);

      int [] compareTest = { compare.compare(songs[1],songs[2]), compare.compare(songs[3],songs[4])  };

      boolean [] testCompare = { compareTest[0] < 0 , compareTest[1] < 0};

      assertEquals(true, testCompare[0]);
      assertEquals(true, testCompare[1]);

   }


   @Test
   public void testThenComparing()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));

      List<Song> expectedList = Arrays.asList(
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005)
      );

      Comparator<Song> bySongNameAndYear = Comparator.comparing(Song::getTitle)
              .thenComparing(Song::getYear);

      songList.sort(bySongNameAndYear);

      assertEquals(songList, expectedList);

   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));

      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song> sortByArtistTitleYear = Comparator
              .comparing(Song::getArtist)
              .thenComparing(Song::getTitle)
              .thenComparing(Song::getYear);

      songList.sort(sortByArtistTitleYear);

      assertEquals(songList, expectedList);
   }
}
