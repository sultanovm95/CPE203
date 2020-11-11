package com.company;

import java.util.Comparator;

public class ArtistComparator implements Comparator<Song> {

    @Override
    public int compare(Song song1, Song song2) {
        int compare = song2.getArtist().compareTo(song1.getArtist());
        if(compare > 0) {
            return -1;
        } else if(compare < 0) {
            return 1;
        } else {
            if(song2.getYear() > song1.getYear()) {
                return -1;
            } else if (song2.getYear() < song1.getYear()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
