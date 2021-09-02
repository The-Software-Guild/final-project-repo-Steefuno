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
    
    @GetMapping("/")
    public String displayIndex(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie,
        Model model
    ) {
        int id;
        User user;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
            user = usersService.getUser(id);
        
            model.addAttribute("name", user.getName());
        } catch(Exception e) {
            // do nothing
        }
        
        return "/index";
    }
    
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
                "notifications",
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
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/login";
        }
        model.addAttribute("name", user.getName());
        redirectAttributes.addFlashAttribute("name", user.getName());
        
        // Get the saved songs
        try {
            songs = usersService.getUserSavedSongs(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
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
        User user;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
        } catch(NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    "You must be logged in to save a song"
                }
            );
            return "redirect:/login";
        }
        
        // Make sure the user exists
        try {
            user = usersService.getUser(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("name", user.getName());
        
        // Save the song
        try {
            usersService.saveSongToUser(id, songId);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/getProfile";
        }
        
        return "redirect:/getSong?id=" + songId;
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
        User user;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
        } catch(NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    "You must be logged in to save a song"
                }
            );
            return "redirect:/login";
        }
        
        // Make sure the user exists
        try {
            user = usersService.getUser(id);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("name", user.getName());
        
        // Delete the song
        try {
            usersService.deleteSongFromUser(id, songId);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/getProfile";
        }
        return "redirect:/getSong?id=" + songId;
    }
    
    @GetMapping("/login")
    public String login(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie,
        HttpServletRequest request,
        HttpServletResponse response,
        Model model
    ) {
        int id;
        User user;
        
        // Get the user id
        try {
            id = Integer.parseInt(idCookie);
            user = usersService.getUser(id);
        
            model.addAttribute("name", user.getName());
        } catch(Exception e) {
            // do nothing
        }
        
        return "/login";
    }
    
    @PostMapping("/login")
    public String login(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) {
        String name;
        Cookie cookie;
        User user;
        
        // Get the name
        name = request.getParameter("name");
        
        // Check if id exists
        try {
            user = usersService.getUser(name);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                e.getMessage()
            );
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("name", user.getName());
        
        // Store UID as cookie
        cookie = new Cookie("uid", Integer.toString(user.getId()));
        cookie.setMaxAge(24 * 60 * 60); // expires in a day
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return "redirect:/getProfile";
    }
    
    @PostMapping("/register")
    public String register(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) {
        String name;
        Cookie cookie;
        User user;
        
        // validate name
        name = request.getParameter("name");
        
        if (name.length() < 3) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    "Name must be at least 3 characters"
                }
            );
            return "redirect:/login";
        }
        if (name.length() > 20) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    "Name cannot be longer than 20 characters"
                }
            );
            return "redirect:/login";
        }
        
        // Try adding the user
        try {
            user = usersService.addUser(name);
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                e.getMessage()
            );
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("name", user.getName());
        
        // Store UID as cookie
        cookie = new Cookie("uid", Integer.toString(user.getId()));
        cookie.setMaxAge(24 * 60 * 60); // expires in a day
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return "redirect:/getProfile";
    }
    
    
    @GetMapping("/logout")
    public String logout(
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) {
        Cookie cookie;
        
        // Store UID as cookie
        cookie = new Cookie("uid", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return "redirect:/login";
    }
}
