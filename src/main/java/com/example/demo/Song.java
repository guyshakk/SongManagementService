package com.example.demo;
 
import java.util.List;
import java.util.Map;


public class Song  {

	private String songId;
	private String name;
	private List<Map<String, String>> authors;
	private int publishedYear;
	private List<String> genres;
	private String lyrics;
	private String performer;
	private String producer;
	
	public Song() {
	}
	
	public Song(Song song) {
		super();
		this.songId=song.getSongId();
		this.name = song.getName();
		this.authors = song.getAuthors();
		this.publishedYear = song.getPublishedYear();
		this.genres = song.getGenres();
		this.lyrics = song.getLyrics();
		this.performer = song.getPerformer();
		this.producer = song.getProducer();
	}

	public Song(String name, List<Map<String, String>> authors, int publishedYear, List<String> genres, String lyrics,
			String performer, String producer) {
		super();
		this.name = name;
		this.authors = authors;
		this.publishedYear = publishedYear;
		this.genres = genres;
		this.lyrics = lyrics;
		this.performer = performer;
		this.producer = producer;
	}

	public Song(String songId, String name, List<Map<String, String>> authors, int publishedYear, List<String> genres,
			String lyrics, String performer, String producer) {
		super();
		this.songId = songId;
		this.name = name;
		this.authors = authors;
		this.publishedYear = publishedYear;
		this.genres = genres;
		this.lyrics = lyrics;
		this.performer = performer;
		this.producer = producer;
	}

	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, String>> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Map<String, String>> authors) {
		this.authors = authors;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

 
}
