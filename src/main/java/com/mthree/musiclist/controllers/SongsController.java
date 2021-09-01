/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.controllers;

import com.mthree.musiclist.models.Song;
import com.mthree.musiclist.services.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    /**
     * Displays a song's detailed information
     * @param id the song's id
     * @param model the page's model
     * @param redirectAttributes the model to insert redirect information
     * @return the redirect
     */
    @GetMapping("/getSong")
    public String displaySongInformation(Integer id, Model model, RedirectAttributes redirectAttributes) {
        Song song;
        
        try {
            song = songsService.getSong(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
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
    public String searchForSongs(String query, Model model, RedirectAttributes redirectAttributes) {
        Song[] songs;
        
        try {
            songs = songsService.searchSongs(query);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errors",
                new String[]{
                    e.getMessage()
                }
            );
            return "redirect:/search";
        }
        
        model.addAttribute("songs", songs);
        return "/search";
    }
}
