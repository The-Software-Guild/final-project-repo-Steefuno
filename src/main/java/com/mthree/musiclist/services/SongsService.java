/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.services;

import com.mthree.musiclist.daos.SongDataDao;
import com.mthree.musiclist.daos.SongSearchDao;
import com.mthree.musiclist.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Steven
 */
@Component
public class SongsService {
    @Autowired
    private SongSearchDao songSearchDao;
    
    @Autowired
    private SongDataDao songDataDao;
    
    /**
     * Gets a song
     * @param id the song's id
     * @return the song information
     * @throws Exception
     * @throws IndexOutOfBoundsException 
     */
    public Song getSong(int id) throws Exception, IndexOutOfBoundsException {
        Song song;
        if (id <= 0) {
            throw new IndexOutOfBoundsException(
                String.format("ID %d must be a positive integer", id)
            );
        }
        
        // Try getting the song from the song data dao
        try {
            song = songDataDao.getSong(id);
        } catch(DataAccessException e) {
            // Get song from API endpoint if not found and then cache
            song = songSearchDao.getSong(id);
            songDataDao.addSong(song);
        }
        
        return song;
    }
    
    /**
     * Searches for songs given a query
     * @param query the query
     * @return the array of songs
     * @throws Exception
     * @throws NullPointerException 
     */
    public Song[] searchSongs(String query) throws Exception, NullPointerException {
        if (query == null) {
            throw new NullPointerException("Query must not be null");
        }
        
        return songSearchDao.searchSongs(query);
    }
}
