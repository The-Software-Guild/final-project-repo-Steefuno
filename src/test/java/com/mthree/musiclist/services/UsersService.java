/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.services;

import com.mthree.musiclist.daos.SongDataDao;
import com.mthree.musiclist.daos.UserDataDao;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    
}
