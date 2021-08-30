/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.musiclist.daos;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mthree.musiclist.models.Song;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Steven
 */
// https://www.baeldung.com/unirest
// https://www.javadoc.io/doc/com.konghq/unirest-java/3.1.00/kong/unirest/json/JSONObject.html
// https://rapidapi.com/brianiswu/api/genius/
@Component
public class SongSearchDaoGeniusApiUnirestImplementation {
    final private static String HEADER_KEY_HOST =
        "x-rapidapi-host"
    ;
    final private static String HEADER_VALUE_HOST =
        "genius.p.rapidapi.com"
    ;
    
    final private static String HEADER_KEY_API_KEY =
        "x-rapidapi-key"
    ;
    final private static String HEADER_VALUE_API_KEY =
        "0bdd461970mshcf75ee1cdef7e94p15c5e2jsnc811ce3e6287"
    ;
    
    final private static String GET_SONG =
        "https://genius.p.rapidapi.com/songs/%d"
    ;
    
    final private static String SEARCH_SONGS = 
        "https://genius.p.rapidapi.com/search"
    ;
    
    /**
     * Gets a song by id
     * @param id the song's id
     * @return the Song information
     * @throws Exception
     */
    public Song getSong(int id) throws Exception {
        HttpResponse<JsonNode> response;
        JSONObject songJson;
        Song song;
        
        // Request the song information from the API
        try {
            response = Unirest.get(
                String.format(GET_SONG, id)
            )
                .header(HEADER_KEY_HOST, HEADER_VALUE_HOST)
                .header(HEADER_KEY_API_KEY, HEADER_VALUE_API_KEY)
                .asJson()
            ;
        } catch (UnirestException e) {
            throw new Exception(
                String.format("Could not get song %d", id),
                e
            );
        }
        
        // Pull the Song JSON from the response
        try {
            songJson = response
                .getBody()
                .getObject()
                .getJSONObject("response")
                .getJSONObject("song")
            ;
        } catch (JSONException e) {
            throw new Exception(
                String.format("Invalid Song ID %d", id),
                e
            );
        }
        
        // Wrap the information as a Song object
        song = new Song(
            songJson.getInt("id"),
            songJson.getString("title"),
            songJson.getJSONObject("primary_artist").getString("name")
        );
        return song;
    }
    
    /**
     * Searches songs by a partial string query
     * @param query the string to search
     * @return an array of the resulting songs
     * @throws Exception
     */
    public Song[] searchSongs(String query) throws Exception {
        HttpResponse<JsonNode> response;
        JSONArray songsJson;
        Song[] songs;
        
        // Request the song information from the API
        try {
            response = Unirest.get(SEARCH_SONGS)
                .header(HEADER_KEY_HOST, HEADER_VALUE_HOST)
                .header(HEADER_KEY_API_KEY, HEADER_VALUE_API_KEY)
                .routeParam("q", query)
                .asJson()
            ;
        } catch (UnirestException e) {
            throw new Exception(
                String.format("Could not get songs q=%s", query),
                e
            );
        }
        
        // Get the array of song hits
        songsJson = response
            .getBody()
            .getObject()
            .getJSONObject("response")
            .getJSONArray("hits")
        ;
        
        // Wrap necessary information into an array of Song objects
        songs = new Song[songsJson.length()];
        for (int i = 0; i < songsJson.length(); i++) {
            JSONObject songJson = songsJson
                .getJSONObject(i)
                .getJSONObject("result")
            ;
            
            songs[i] = new Song(
                songJson.getInt("id"),
                songJson.getString("full_title"),
                songJson
                    .getJSONObject("primary_artist")
                    .getString("name")
            );
        }
        
        return songs;
    }
}