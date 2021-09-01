/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos;

import com.mthree.musiclist.daos.dbmappers.SongMapper;
import com.mthree.musiclist.daos.dbmappers.UserMapper;
import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Steven
 */
@Repository
public class UserDataDaoMySqlImplementation implements UserDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    final private static String SELECT_USER_BY_ID =
        "SELECT id, name " +
        "FROM user " +
        "WHERE id = ? "
    ;
    
    final private static String INSERT_USER =
        "INSERT INTO user(name) VALUES " +
        "   (?) "
    ;
    
    final private static String SELECT_USER_SAVED_SONGS =
        "SELECT song.id, song.name, song.artist " +
        "FROM song " +
        "INNER JOIN userSavedSong ON userSavedSong.songId = song.id " +
        "WHERE userSavedSong.userId = ? "
    ;
    
    final private static String INSERT_USER_SAVED_SONG = 
        "INSERT INTO userSavedSong(userId, songId) VALUES " +
        "   (?, ?) "
    ;
    
    final private static String DELETE_USER_SAVED_SONG =
        "DELETE FROM userSavedSong " +
        "WHERE " +
        "   userId = ? AND " +
        "   songId = ? "
    ;
    
    /**
     * Gets a user
     * @param id the user's id
     * @return the user
     * @throws DataAccessException 
     */
    @Override
    public User getUser(int id) throws DataAccessException {
        User user;
        
        user = (User) jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new UserMapper(), id);
        
        return user;
    }
    
    /**
     * Adds a new user
     * @param name the user's name
     * @return the user's ID
     * @throws DataAccessException 
     */
    @Override
    public int addUser(String name) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(
            (Connection connection) -> {
                PreparedStatement preparedStatement;
                
                preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                
                return preparedStatement;
            },
            keyHolder
        );
        
        return keyHolder.getKey().intValue();
    }
    
    /**
     * Gets the songs saved by the user
     * @param id the user's id
     * @return the list of songs
     * @throws DataAccessException 
     */
    @Override
    public List<Song> getUserSavedSongs(int id) throws DataAccessException {
        List<Song> songs;
        
        songs = jdbcTemplate.query(SELECT_USER_SAVED_SONGS, new SongMapper(), id);
        
        return songs;
    }
    
    /**
     * Saves a song under a userId
     * @param userId the user's id
     * @param songId the song's id
     * @throws DataAccessException 
     */
    @Override
    public void saveSongToUser(int userId, int songId) throws DataAccessException {
        jdbcTemplate.update(
            (Connection connection) -> {
                PreparedStatement preparedStatement;
                
                preparedStatement = connection.prepareStatement(INSERT_USER_SAVED_SONG);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, songId);
                
                return preparedStatement;
            }
        );
    }
    
    /**
     * Deletes a saved song under a user Id
     * @param userId the user's id
     * @param songId the song's id
     * @throws DataAccessException 
     */
    @Override
    public void deleteSongFromUser(int userId, int songId) throws DataAccessException {
        jdbcTemplate.update(
            (Connection connection) -> {
                PreparedStatement preparedStatement;
                
                preparedStatement = connection.prepareStatement(DELETE_USER_SAVED_SONG);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, songId);
                
                return preparedStatement;
            }
        );
    }
}
