package com.example.demo;

//import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagementController {
	ManagementService service;
	
	@Autowired
	public ManagementController(ManagementService service) {
		this.service = service;
	}
	
	@RequestMapping(
			path = "/songs",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Song createSong (@RequestBody Song song){
		Song s = null;
		try {
			s = this.service.getSongById(song.getSongId());
		} catch (Exception e) {
		} 
		if (s != null)
			throw new RuntimeException("Song already exists");
		return this.service
				.create(song);
	}
	
	@RequestMapping(
			path = "/songs/{songId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Song getDemoById (@PathVariable("songId") String songId) {
		return this.service
				.getSongById (songId);
	}
	
	@RequestMapping(
			path = "/songs/{songId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update (
			@PathVariable("songId") String songId, 
			@RequestBody Song update) {
		if (this.service.getSongById(songId) != null)
			this.service
				.updateSong(songId, update);
	}
	
	@RequestMapping(
			path = "/songs",
			method = RequestMethod.DELETE)
	public void deleteAll () {
		this.service
			.deleteAllSongs();
	}
}
