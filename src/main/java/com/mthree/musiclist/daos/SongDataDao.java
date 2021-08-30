/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos;

import com.mthree.musiclist.models.Song;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @author Steven
 */
public interface SongDataDao {
    /**
     * Gets a song from storage
     * @param id the song's id
     * @return the song's information
     * @throws DataAccessException 
     */
    public Song getSong(int id) throws DataAccessException;
    
    /**
     * Adds a song to storage
     * @param song the song to add
     * @throws DataAccessException 
     */
    public void addSong(Song song) throws DataAccessException;
}
