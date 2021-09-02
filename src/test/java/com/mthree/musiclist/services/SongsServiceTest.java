/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */
package com.mthree.musiclist.services;

import com.mthree.musiclist.daos.SongDataDao;
import com.mthree.musiclist.daos.UserDataDao;
import com.mthree.musiclist.models.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Steven
 */
@SpringBootTest
public class SongsServiceTest {
    @Autowired
    private SongsService songsService;
    
    @Autowired
    private SongDataDao songDataDao;
    
    @Autowired
    private UserDataDao userDataDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSong method, of class SongsService.
     */
    @Test
    public void getSong_Should_Get_A_Song() {
        Song song;
        
        try {
            song = songsService.getSong(54282);
        } catch (Exception e) {
            fail("Should have gotten the song without error: " + e);
            return;
        }
        
        assertEquals(
            song.getName(),
            "New Kid in Town",
            "Song should have been New Kid in Town"
        );
        assertEquals(
            song.getArtist(),
            "Eagles",
            "Artist should have been the Eagles"
        );
    }
    
    @Test
    public void getSong_Should_Fail_On_Invalid_Id() {
        Song song;
        
        try {
            song = songsService.getSong(1000000000);
            fail("Should have errored");
            return;
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of searchSongs method, of class SongsService.
     */
    @Test
    public void searchSongs_Should_Find_Songs() {
        Song[] songs;
        
        try {
            songs = songsService.searchSongs("just a jack knife");
        } catch (Exception e) {
            fail("Should have gotten the song without error: " + e);
            return;
        }
        
        assertTrue(
            songs != null,
            "Should have an array of songs"
        );
        assertTrue(
            songs.length > 0,
            "Should have at least one song"
        );
    }
}
