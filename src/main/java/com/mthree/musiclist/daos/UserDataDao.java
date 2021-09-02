/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos;

import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.models.User;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @author Steven
 */
public interface UserDataDao {
    /**
     * Gets a user
     * @param id the user's id
     * @return the user
     * @throws DataAccessException 
     */
    public User getUser(int id) throws DataAccessException;
    
    /**
     * Gets a user
     * @param name the user's name
     * @return the user
     * @throws DataAccessException 
     */
    public User getUser(String name) throws DataAccessException;
    
    /**
     * Adds a new user
     * @param name the user's name
     * @return the user's ID
     * @throws DataAccessException 
     */
    public int addUser(String name) throws DataAccessException;
    
    /**
     * Gets the songs saved by the user
     * @param id the user's id
     * @return the list of songs
     * @throws DataAccessException 
     */
    public List<Song> getUserSavedSongs(int id) throws DataAccessException;
    
    /**
     * Gets the list of songs saved by a user
     * @param userId the user's id
     * @param songId the song's id
     * @return if the user has the song saved
     * @throws DataAccessException 
     */
    public boolean userHasSongSaved(int userId, int songId) throws DataAccessException;
    
    /**
     * Saves a song under a userId
     * @param userId the user's id
     * @param songId the song's id
     * @throws DataAccessException 
     */
    public void saveSongToUser(int userId, int songId) throws DataAccessException;
    
    /**
     * Deletes a saved song under a user Id
     * @param userId the user's id
     * @param songId the song's id
     * @throws DataAccessException 
     */
    public void deleteSongFromUser(int userId, int songId) throws DataAccessException;
}
