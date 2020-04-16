package com.example.demo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenresComparator implements Comparator<Song> {
	
	@Override
	public int compare(Song o1, Song o2) {
		List<String> arr1 = o1.getGenres();
		List<String> arr2 = o2.getGenres();
		arr1 = arr1
				.stream()
				.sorted()
				.collect(Collectors.toList());
		arr2 = arr2
				.stream()
				.sorted()
				.collect(Collectors.toList());
		for (int i = 0; i < Math.min(arr1.size(), arr2.size()); i++) {
			if (!arr1.get(i).equals(arr2.get(i))) {
				return arr1.get(i).compareTo(arr2.get(i));
			}
		}
		return arr1.size() - arr2.size();
	}

}
