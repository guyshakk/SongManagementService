package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorsComparator implements Comparator<Song>{
	
	@Override
	public int compare(Song o1, Song o2) {

		List<Map<String, String>> arr1 = o1.getAuthors();
		List<Map<String, String>> arr2 = o2.getAuthors();
		List<String> authorsNames1 = getAuthorsNames(arr1);
		List<String> authorsNames2 = getAuthorsNames(arr2);
		for (int i = 0; i < Math.min(arr1.size(), arr2.size()); i++) {
			if (!authorsNames1.get(i).equals(authorsNames2.get(i))) {
				return authorsNames1.get(i).compareTo(authorsNames2.get(i));
			}
		}
		return arr1.size() - arr2.size();
	}

	private List<String> getAuthorsNames(List<Map<String, String>> arr) {
		
		List<String> names = new ArrayList<>();
		for (Map<String, String> map : arr) {
			if (map.containsKey("name")) {
				names.add(map.get("name"));
			}
		}
		return names
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}

}
