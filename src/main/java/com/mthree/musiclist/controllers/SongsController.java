/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.controllers;

import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.models.User;
import com.mthree.musiclist.services.SongsService;
import com.mthree.musiclist.services.UsersService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 
 * @author Steven
 */
@Controller
public class SongsController {
    @Autowired
    private SongsService songsService;
    
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/getSong")
    public String displaySongInformation(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie, 
        Integer id, 
        Model model, 
        RedirectAttributes redirectAttributes
    ) {
        int uid;
        User user;
        Song song;
        
        model.addAttribute("saved", false);
        
        // Get the user id
        try {
            uid = Integer.parseInt(idCookie);
            user = usersService.getUser(uid);
        
            model.addAttribute("name", user.getName());
            redirectAttributes.addFlashAttribute("name", user.getName());
            model.addAttribute("saved", usersService.userHasSongSaved(user.getId(), id));
        } catch(Exception e) {
            // do nothing
        }
        
        try {
            song = songsService.getSong(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/";
        }
        
        model.addAttribute("song", song);
        return "/getSong";
    }
    
    @GetMapping("/search")
    public String searchForSongs(
        @CookieValue(value = "uid", defaultValue = "-1") String idCookie, 
        Model model,
        HttpServletRequest request, 
        RedirectAttributes redirectAttributes
    ) {
        int uid;
        User user;
        String query;
        Song[] songs;
        
        // Get the user
        try {
            uid = Integer.parseInt(idCookie);
            user = usersService.getUser(uid);
        
            model.addAttribute("name", user.getName());
            redirectAttributes.addFlashAttribute("name", user.getName());
        } catch(Exception e) {
            // do nothing
        }
        
        query = request.getParameter("q");
        
        try {
            songs = songsService.searchSongs(query);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "notifications",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/";
        }
        
        model.addAttribute("q", query);
        model.addAttribute("songs", songs);
        return "/search";
    }
}
