/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos;

import com.mthree.musiclist.daos.dbmappers.SongMapper;
import com.mthree.musiclist.models.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author Steven
 */
@Repository
public class SongDataDaoMySqlImplementation implements SongDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    final private static String GET_SONG_BY_ID =
        "SELECT id, name, artist " +
        "FROM song " +
        "WHERE id = ? "
    ;
    
    final private static String INSERT_SONG =
        "INSERT INTO song(id, name, artist) VALUES " +
        "   (?, ?, ?) "
    ;
    
    /**
     * Gets a song from storage
     * @param id the song's id
     * @return the song's information
     * @throws DataAccessException 
     */
    @Override
    public Song getSong(int id) throws DataAccessException {
        Song song;
        
        song = (Song) jdbcTemplate.queryForObject(GET_SONG_BY_ID, new SongMapper(), id);
        return song;
    }
    
    /**
     * Adds a song to storage
     * @param song the song to add
     * @throws DataAccessException 
     */
    @Override
    public void addSong(Song song) throws DataAccessException {
        jdbcTemplate.update(
            (Connection connection) -> {
                PreparedStatement preparedStatement;
                
                preparedStatement = connection.prepareStatement(INSERT_SONG);
                preparedStatement.setInt(1, song.getId());
                preparedStatement.setString(2, song.getName());
                preparedStatement.setString(3, song.getArtist());
                
                return preparedStatement;
            }
        );
    }
}
