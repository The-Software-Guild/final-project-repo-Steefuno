/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.models;

/**
 * 
 * @author Steven
 */
public class Song {
    final private int id;
    final private String name;
    final private String artist;
    
    /**
     * Constructs a new Song with the given id, name, and artist name
     * @param id the song's id
     * @param name the song's name
     * @param artist the song's artist
     */
    public Song(int id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }
    
    /**
     * Get the song's id
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the song's name
     * @return the song's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the song's artist
     * @return the artist's name
     */
    public String getArtist() {
        return artist;
    }
}
