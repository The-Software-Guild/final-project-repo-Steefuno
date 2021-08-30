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
public class UserSavedSong {
    final private int id;
    final private int userId;
    final private int songId;
    
    /**
     * Constructs a new UserSavedSong with the given ids
     * @param id the relation's id
     * @param userId the user's id
     * @param songId the song's id
     */
    public UserSavedSong(int id, int userId, int songId) {
        this.id = id;
        this.userId = userId;
        this.songId = songId;
    }
    
    /**
     * Get the relation's id
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the user's id
     * @return the id
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Get the song's id
     * @return the id
     */
    public int getSongId() {
        return songId;
    }
}
