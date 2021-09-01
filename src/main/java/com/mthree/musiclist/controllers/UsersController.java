/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.controllers;

import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.models.User;
import com.mthree.musiclist.services.UsersService;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// https://dzone.com/articles/how-to-use-cookies-in-spring-boot
/**
 * 
 * @author Steven
 */
@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/getProfile")
    public String displayProfile(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie, 
        Model model, 
        HttpServletResponse response, 
        RedirectAttributes redirectAttributes
    ) {
        int id;
        User user;
        List<Song> songs;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
        } catch(NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    "You must be logged in to see your profile"
                }
            );
            return "redirect:/login";
        }
        
        // Get the user data
        try {
            user = usersService.getUser(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        
        // Get the saved songs
        try {
            songs = usersService.getUserSavedSongs(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/";
        }
        model.addAttribute("songs", songs);
        
        return "/getProfile";
    }
    
    @PostMapping("/saveSong")
    public String saveSong(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie,
        Integer songId, 
        HttpServletRequest request, 
        HttpServletResponse response, 
        RedirectAttributes redirectAttributes
    ) {
        int id;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
        } catch(NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    "You must be logged in to save a song"
                }
            );
            return "redirect:/login";
        }
        
        // Make sure the user exists
        try {
            usersService.getUser(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/login";
        }
        
        // Save the song
        try {
            usersService.saveSongToUser(id, songId);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/getProfile";
        }
        
        return "redirect:/getSong?songId=" + songId;
    }
    
    @PostMapping("/deleteSong")
    public String deleteSong(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie,
        Integer songId, 
        HttpServletRequest request, 
        HttpServletResponse response, 
        RedirectAttributes redirectAttributes
    ) {
        int id;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
        } catch(NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    "You must be logged in to save a song"
                }
            );
            return "redirect:/login";
        }
        
        // Make sure the user exists
        try {
            usersService.getUser(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/login";
        }
        
        // Delete the song
        try {
            usersService.deleteSongFromUser(id, songId);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/getProfile";
        }
        
        return "redirect:/getSong?songId=" + songId;
    }
    
    @PostMapping("/login")
    public String login(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) {
        int id;
        Cookie cookie;
        
        // Check if id is inputted
        try {
            id = Integer.parseInt(
                request.getParameter("id")
            );
        } catch(NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    "Invalid ID"
                }
            );
            return "redirect:/login";
        }
        
        // Check if id exists
        try {
            usersService.getUser(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                e.getMessage()
            );
            return "redirect:/login";
        }
        
        // Store UID as cookie
        cookie = new Cookie("uid", Integer.toString(id));
        cookie.setMaxAge(24 * 60 * 60); // expires in a day
        cookie.setPath("/");
        
        response.addCookie(cookie);
        return "redirect:/getProfile";
    }
}
