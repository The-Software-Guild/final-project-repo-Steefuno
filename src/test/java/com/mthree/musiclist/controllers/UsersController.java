/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.controllers;

import com.mthree.musiclist.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// https://dzone.com/articles/how-to-use-cookies-in-spring-boot
/**
 * 
 * @author Steven
 */
@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    
    
}
