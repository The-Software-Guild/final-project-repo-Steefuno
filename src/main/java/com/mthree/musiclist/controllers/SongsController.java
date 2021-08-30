/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.controllers;

import com.mthree.musiclist.services.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author Steven
 */
@Controller
public class SongsController {
    @Autowired
    private SongsService songsService;
    
    
}
