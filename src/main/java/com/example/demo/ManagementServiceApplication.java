package com.example.demo;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManagementServiceApplication implements ManagementService {

	private RestTemplate restTemplate;
	private String url;
	
	public ManagementServiceApplication() {
		this.restTemplate = new RestTemplate();
	}
	
	@Value("${storage.service.url}")
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Song create(Song song) {
		if (!validate(song))
			throw new RuntimeException("Wrong input supplied");
		song.setSongId(null);
		KeyObjectPair keyObject = this.restTemplate
				.postForObject(this.url,
						song, KeyObjectPair.class);
		Song newSong = keyObject.getObject();
		newSong.setSongId(keyObject.getKey());
		updateSong(keyObject.getKey(), newSong);
		return newSong;
	}

	@Override
	public Song getSongById(String id) {
		return this.restTemplate
				.getForObject(this.url + "/{id}", 
						Song.class, 
						id);
	}

	@Override
	public void updateSong(String songId, Song update) {
		if (!validate(update))
			throw new RuntimeException("Wrong input supplied");
		update.setSongId(songId);
		this.restTemplate.
				put(
						this.url + "/{id}",
						update,
						songId);
	}

	@Override
	public void deleteAllSongs() {
		this.restTemplate.delete(this.url);
	}
	
	private boolean validate(Song song) {
        return song.getAuthors() != null &&
        		!song.getAuthors().isEmpty() &&
        		song.getGenres() != null &&
        		!song.getGenres().isEmpty() &&
        		song.getLyrics() != null &&
        		!song.getLyrics().trim().isEmpty() &&
        		song.getName() != null &&
        		!song.getName().trim().isEmpty() &&
        		song.getPerformer() != null &&
        		!song.getPerformer().trim().isEmpty() &&
        		song.getProducer() != null &&
        		!song.getProducer().trim().isEmpty() &&
        		song.getPublishedYear() > 0 &&
        		song.getPublishedYear() <= Calendar.getInstance().get(Calendar.YEAR);
    }

}
