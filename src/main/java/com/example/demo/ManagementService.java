package com.example.demo;

public interface ManagementService {
	
	public Song create(Song song);

	public Song getSongById(String songId);

	public void updateSong(String songId, Song update);

	public void deleteAllSongs();
}
