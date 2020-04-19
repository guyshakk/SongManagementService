package com.example.demo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

	/**Validate song element;
	 * Check if song already exists;
	 * Save song and return it if it doesn't already exist and syntactically correct**/
	@Override
	public Song create(Song song) {
		if (!validate(song))
			throw new IncorrectInputException("Wrong input supplied - not a valid Song object");
		Song s = null;
		String id = song.getSongId();
		try {
			s = getSongById(id);
		} catch (EntityNotInDBException e) {
		}
		if (s != null)
			throw new TakenSongIdException("SongId " + id + " already exists");
		Song newSong = this.restTemplate
				.postForObject(this.url + "/{id}",
						song,
						Song.class,
						id);
		return newSong;
	}

	@Override
	public Song getSongById(String id) {
		try {
			return this.restTemplate
					.getForObject(this.url + "/{id}", 
							Song.class, 
							id);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
				throw new EntityNotInDBException();
			else throw e;
		}
	}

	@Override
	public void updateSong(String id, Song update) {
		Song currentSong = getSongById(id);
		if (update.getAuthors() != null)
			currentSong.setAuthors(update.getAuthors());
		if (update.getGenres() != null)
			currentSong.setGenres(update.getGenres());
		if (update.getLyrics() != null)
			currentSong.setLyrics(update.getLyrics());
		if (update.getName() != null)
			currentSong.setName(update.getName());
		if (update.getPerformer() != null)
			currentSong.setPerformer(update.getPerformer());
		if (update.getProducer() != null)
			currentSong.setProducer(update.getProducer());
		if (update.getPublishedYear() > 0 &&  update.getPublishedYear() <= Calendar.getInstance().get(Calendar.YEAR))
			currentSong.setPublishedYear(update.getPublishedYear());
		this.restTemplate.
				put(this.url + "/{id}",
					currentSong,
					id);
	}

	@Override
	public void deleteAllSongs() {
		this.restTemplate.delete(this.url);
	}
	
	/**Get the correct page of Songs with criteriaValue in criteriaType field sorted**/	
	@Override
	public List<Song> readSongsBy(String criteriaType, String criteriaValue, int size, int page,
			String sortBy, String sortOrder) {
		
		validatePagination(page, size);
		validateSortAttributes(sortBy, sortOrder);
		try {
			return Arrays
					.asList
					(this.restTemplate
							.getForObject
							(this.url+"/"+criteriaType+"/{criteriaValue}/{sortBy}/{sortOrder}/{page}/{size}",
									Song[].class, 
									criteriaValue,
									sortBy,
									sortOrder,
									page,
									size));
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
				throw new MissingFieldException("Not all songs in storage have attribute " + criteriaType + "; unable to complete action");
			}
			else
				throw e;
		}
	}

	private void validatePagination(int page, int size) {
		if (page < 0 || size < 1 || page > Integer.MAX_VALUE || size > 100) {
			throw new InvalidPaginationDataException("Invalid pagination data. Please supply page bigger than 0 and size between 0 and 100");
		}
	}
	
	private void validateSortAttributes(String sortBy, String sortOrder) {
		
		//Check whether sortBy value is one of the song Attributes
		if (!Arrays.asList
				(SongAttributes.values())
				.stream()
				.map(obj -> obj.toString().toLowerCase())
				.collect(Collectors.toList())
				.contains
				(sortBy.toLowerCase())) {
			throw new UnsupportedSortByException(sortBy);
		}
		
		//Check whether sortOrder value is asc or desc
		if (!Arrays.asList
				(SortOrder.values())
				.stream()
				.map(obj -> obj.toString().toLowerCase())
				.collect(Collectors.toList())
				.contains
				(sortOrder.toLowerCase())) {
			
			throw new UnsupportedSortOrderException(sortOrder);
		}
	}

	/**Get all songs with pagination**/
	@Override
	public List<Song> readAllSongs(String sortBy, String sortOrder, int size, int page) {
		
		validatePagination(page, size);
		validateSortAttributes(sortBy, sortOrder);
		return Arrays.asList(this.restTemplate
				.getForObject(this.url+"/all/{sortBy}/{sortOrder}/{page}/{size}", 
						Song[].class,
						sortBy,
						sortOrder,
						page,
						size));
	}

	
	/**Validate the value of the song to be saved before saving it**/
	private boolean validate(Song song) {
        return song.getSongId() != null &&
        		!song.getSongId().trim().isEmpty() &&
        		song.getAuthors() != null && 
        		!song.getAuthors().isEmpty() && //song with no author is not possible
        		song.getGenres() != null &&
        		!song.getGenres().isEmpty() && //song with no genre is not possible
        		song.getLyrics() != null &&
        		!song.getLyrics().trim().isEmpty() && //song must have lyrics
        		song.getName() != null &&
        		!song.getName().trim().isEmpty() && //song must have name
        		song.getPerformer() != null &&
        		!song.getPerformer().trim().isEmpty() && //song must have performer
        		song.getProducer() != null &&
        		!song.getProducer().trim().isEmpty() && //song must have producer
        		song.getPublishedYear() > 0 &&
        		song.getPublishedYear() <= Calendar.getInstance().get(Calendar.YEAR); //song must be in 0<song's year<=current year
    }

}
