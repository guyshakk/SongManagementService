package com.example.demo;

import java.util.List;

public interface ManagementService {
	
	public Song create(Song song);

	public Song getSongById(String songId);

	public void updateSong(String songId, Song update);

	public void deleteAllSongs();
	
	public List<Song> readSongsBy(String criteriaType, String criteriaValue, int size, int page,
			String sortBy, String sortOrder);

	public List<Song> readAllSongs(String sortBy, String sortOrder, int size, int page);
}
