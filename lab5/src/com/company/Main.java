package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Song song1 = new Song("Billie Elish", "Ocean eyes", 2019);
        Song song2 = new Song("Aillie Alish", "Bcean Byes", 2020);
        Song song3 = new Song("Cillie Alish", "Bcean Byes", 2020);

        Comparator<Song> ac = new ArtistComparator();

        // LIST which sorts them by [getArtist] String in descending ORDER

        List<Song> listArtistName = new ArrayList<Song>();

        listArtistName.add(song1);
        listArtistName.add(song2);
        listArtistName.add(song3);


        listArtistName.sort(ac);

        for(int i = 0; i < listArtistName.size(); i++) {
            System.out.println(listArtistName.get(i).getArtist());

        }

    }
}
