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
public class SongDetailed extends Song {
    final private String imageUrl;
    final private String lyricsUrl;
    
    /**
     * Constructs a new Song with the given id, name, and artist name
     * @param id the song's id
     * @param name the song's name
     * @param artist the song's artist
     * @param imageUrl the song's album/single cover
     * @param lyricsUrl the song's lyrics
     */
    public SongDetailed(int id, String name, String artist, String imageUrl, String lyricsUrl) {
        super(id, name, artist);
        this.imageUrl = imageUrl;
        this.lyricsUrl = lyricsUrl;
    }
    
    /**
     * Get the song's imageUrl
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Get the song's lyricsUrl
     * @return the lyricsUrl
     */
    public String getLyricsUrl() {
        return lyricsUrl;
    }
}
