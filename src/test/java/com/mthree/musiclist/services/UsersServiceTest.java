/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */
package com.mthree.musiclist.services;

import com.mthree.musiclist.daos.SongDataDao;
import com.mthree.musiclist.daos.UserDataDao;
import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.models.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Steven
 */
@SpringBootTest
public class UsersServiceTest {
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private SongDataDao songDataDao;
    
    @Autowired
    private UserDataDao userDataDao;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public UsersServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws DataAccessException {
        jdbcTemplate.update(
            "DELETE FROM userSavedSong " +
            "WHERE true "
        );
        jdbcTemplate.update(
            "DELETE FROM song " +
            "WHERE true "
        );
        jdbcTemplate.update(
            "DELETE FROM user " +
            "WHERE true "
        );
        
        jdbcTemplate.update(
            "INSERT INTO user(id, name) VALUES " +
            "   (100, 'Steve') "
        );
        jdbcTemplate.update(
            "INSERT INTO song(id, name, artist) VALUES " +
            "   (362381, 'Mack the Knife', 'Bobby Darin') "
        );
        jdbcTemplate.update(
            "INSERT INTO userSavedSong(userId, songId) VALUES " +
            "   (100, 362381) "
        );
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getUser method, of class UsersService.
     */
    @Test
    public void getUser_by_ID_Should_Get_A_User() {
        User user;
        
        try {
            user = usersService.getUser(100);
        } catch (Exception e) {
            fail("Should have gotten the user without error: " + e);
            return;
        }
        
        assertEquals(
            user.getName(),
            "Steve",
            "User should be Steve"
        );
    }
    
    @Test
    public void getUser_by_ID_Should_Fail_Without_User() {
        try {
            usersService.getUser(8675309);
            fail("Should have errored");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of getUser method, of class UsersService.
     */
    @Test
    public void testGetUser_String() {
        User user;
        
        try {
            user = usersService.getUser("Steve");
        } catch (Exception e) {
            fail("Should have gotten the user without error: " + e);
            return;
        }
        
        assertEquals(
            user.getId(),
            100,
            "User should be 100"
        );
    }
    
    @Test
    public void getUser_by_String_Should_Fail_Without_User() {
        try {
            usersService.getUser("Eduardo");
            fail("Should have errored");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of addUser method, of class UsersService.
     */
    @Test
    public void addUser_Should_Add_Properly() {
        try {
            usersService.addUser("Eduardo");
        } catch (Exception e) {
            fail("Should have added the user without error: " + e);
            return;
        }
        
        try {
            usersService.getUser("Eduardo");
        } catch (Exception e) {
            fail("Should have gotten the user without error: " + e);
            return;
        }
    }
    
    @Test
    public void addUser_Should_Fail_On_Duplicate() {
        try {
            usersService.addUser("Steve");
            fail("Should have failed to add user");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of getUserSavedSongs method, of class UsersService.
     */
    @Test
    public void getUserSavedSongs_Should_Get_All_Saved() {
        List<Song> songs;
        // Get the initial list
        try {
            songs = usersService.getUserSavedSongs(100);
        } catch (Exception e) {
            fail("Should have gotten the list without error: " + e);
            return;
        }
        
        // Assert there is 1 song
        assertEquals(
            songs.size(),
            1,
            "Should have 1 song saved"
        );
        
        // Delete the 1 song
        try {
            usersService.deleteSongFromUser(100, 362381);
        } catch (Exception e) {
            fail("Should have deleted the save without error: " + e);
            return;
        }
        
        // Get the new list
        try {
            songs = usersService.getUserSavedSongs(100);
        } catch (Exception e) {
            fail("Should have gotten the list without error: " + e);
            return;
        }
        
        // Assert there is 0 songs
        assertEquals(
            songs.size(),
            0,
            "Should have 0 songs saved"
        );
    }

    /**
     * Test of userHasSongSaved method, of class UsersService.
     */
    @Test
    public void userHasSongSaved_Should_Check_If_Saved_Properly() {
        boolean isSaved;
        
        // Get if the song is saved
        try {
            isSaved = usersService.userHasSongSaved(100, 362381);
        } catch (Exception e) {
            fail("Should have gotten if saved without error: " + e);
            return;
        }
        
        // Assert
        assertTrue(
            isSaved,
            "User should have Mack The Knife saved"
        );
        
        // Get if the song is saved
        try {
            isSaved = usersService.userHasSongSaved(100, 1);
        } catch (Exception e) {
            fail("Should have gotten if saved without error: " + e);
            return;
        }
        
        // Assert
        assertTrue(
            !isSaved,
            "User should not have song 1 saved"
        );
    }

    /**
     * Test of saveSongToUser method, of class UsersService.
     */
    @Test
    public void saveSongToUser_Should_Save() {
        boolean isSaved;
        
        try {
            usersService.saveSongToUser(100, 1);
        } catch (Exception e) {
            System.out.println(e);
            fail("Should have gotten if saved without error: " + e);
            return;
        }
        
        try {
            isSaved = usersService.userHasSongSaved(100, 1);
        } catch (Exception e) {
            fail("Should have gotten if saved without error: " + e);
            return;
        }
        
        // Assert
        assertTrue(
            isSaved,
            "User should have song 1 saved"
        );
    }
}
