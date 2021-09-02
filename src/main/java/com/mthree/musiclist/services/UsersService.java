/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.services;

import com.mthree.musiclist.daos.SongDataDao;
import com.mthree.musiclist.daos.SongSearchDao;
import com.mthree.musiclist.daos.UserDataDao;
import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Steven
 */
@Component
public class UsersService {
    @Autowired
    private UserDataDao userDataDao;
        
    @Autowired
    private SongDataDao songDataDao;
    
    @Autowired
    private SongSearchDao songSearchDao;
    
    /**
     * Gets a user
     * @param id the user's id
     * @return the user data
     * @throws Exception 
     */
    public User getUser(int id) throws Exception {
        User user;
        try {
            user = userDataDao.getUser(id);
        } catch (DataAccessException e) {
            throw new Exception("Invalid ID", e);
        }
        
        return user;
    }
    
    /**
     * Gets a user
     * @param name the user's name
     * @return the user data
     * @throws Exception 
     */
    public User getUser(String name) throws Exception {
        User user;
        try {
            user = userDataDao.getUser(name);
        } catch (DataAccessException e) {
            throw new Exception("Invalid Name", e);
        }
        
        return user;
    }
    
    /**
     * Adds a new user
     * @param name the user's name
     * @return the user data
     * @throws Exception 
     */
    public User addUser(String name) throws Exception {
        User user;
        try {
            user = new User(
                userDataDao.addUser(name),
                name
            );
        } catch (DataAccessException e) {
            throw new Exception(
                String.format("Cannot use name %s", name),
                e
            );
        }
        
        return user;
    }
    
    /**
     * Gets the list of songs saved by a user
     * @param id the user's id
     * @return the list of songs
     * @throws Exception 
     */
    public List<Song> getUserSavedSongs(int id) throws Exception {
        List<Song> songs;
        try {
            songs = userDataDao.getUserSavedSongs(id);
        } catch (DataAccessException e) {
            throw new Exception("Internal Error", e);
        }
        
        return songs;
    }
    
    /**
     * Gets the list of songs saved by a user
     * @param userId the user's id
     * @param songId the song's id
     * @return if the user has the song saved
     * @throws Exception 
     */
    public boolean userHasSongSaved(int userId, int songId) throws Exception {
        boolean isSaved;
        try {
            isSaved = userDataDao.userHasSongSaved(userId, songId);
        } catch (DataAccessException e) {
            throw new Exception("Internal Error", e);
        }
            
        return isSaved;
    }
    
    public void saveSongToUser(int userId, int songId) throws Exception {
        Song song;
        
        // Get the song
        try {
            song = songSearchDao.getSong(songId);
        } catch(Exception e) {
            throw new Exception(
                String.format("Could not find song %d", songId),
                e
            );
        }
        
        // Cache the song
        try {
            songDataDao.addSong(
                song
            );
        } catch(DataAccessException e) {
            throw new Exception(
                String.format("Could not insert song %d", song.getId()),
                e
            );
        }
        
        // Save the song to user
        try {
            userDataDao.saveSongToUser(userId, song.getId());
        } catch(DataAccessException e) {
            throw new Exception(
                String.format("Could not save song %d to user %d", song.getId(), userId),
                e
            );
        }
    }
    
    public void deleteSongFromUser(int userId, int songId) throws Exception {
        try {
            userDataDao.deleteSongFromUser(userId, songId);
        } catch(DataAccessException e) {
            throw new Exception(
                e
            );
        }
    }
}
