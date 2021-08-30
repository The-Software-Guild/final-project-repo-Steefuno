/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos.dbmappers;

import com.mthree.musiclist.models.Song;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author Steven
 */
public class SongMapper implements RowMapper {
    @Override
    public Song mapRow(ResultSet resultSet, int index) throws SQLException {
        return new Song(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3)
        );
    }
}
