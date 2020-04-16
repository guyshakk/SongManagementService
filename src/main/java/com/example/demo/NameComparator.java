package com.example.demo;

import java.util.Comparator;
import java.util.Map;

public class NameComparator implements Comparator<Song> {
	
	public int compare(Song o1, Song o2) {
		return o1.getName().compareTo(o2.getName());
	};
}
