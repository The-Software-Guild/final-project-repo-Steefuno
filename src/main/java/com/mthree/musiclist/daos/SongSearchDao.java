/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos;

import com.mthree.musiclist.models.Song;

/**
 * 
 * @author Steven
 */
public interface SongSearchDao {
    /**
     * Gets a song by id
     * @param id the song's id
     * @return the Song information
     * @throws Exception
     */
    public Song getSong(int id) throws Exception;
    
    /**
     * Searches songs by a partial string query
     * @param query the string to search
     * @return an array of the resulting songs
     * @throws Exception
     */
    public Song[] searchSongs(String query) throws Exception;
}
