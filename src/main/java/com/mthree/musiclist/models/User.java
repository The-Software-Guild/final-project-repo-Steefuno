/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.models;

/**
 * 
 * @author Steven
 */
public class User {
    final private int id;
    final private String name;
    
    /**
     * Constructs a new User with the given id and name
     * @param id the user's id
     * @param name the user's name
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * Get the user's id
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the user's name
     * @return the user's name
     */
    public String getName() {
        return name;
    }
}
