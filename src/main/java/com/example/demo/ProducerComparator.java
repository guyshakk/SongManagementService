package com.example.demo;

import java.util.Comparator;
import java.util.Map;

public class ProducerComparator implements Comparator<Song>{
	
	@Override
	public int compare(Song o1, Song o2) {
		return o1.getProducer().compareTo(o2.getProducer());
	}

}
