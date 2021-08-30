/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos.dbmappers;

import com.mthree.musiclist.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author Steven
 */
public class UserMapper implements RowMapper {
    @Override
    public User mapRow(ResultSet resultSet, int index) throws SQLException {
        return new User(
            resultSet.getInt(1),
            resultSet.getString(2)
        );
    }
}
