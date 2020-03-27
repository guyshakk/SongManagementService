package com.example.demo;

public class KeyObjectPair {

	private String key;
	private Song object;

	public KeyObjectPair() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Song getObject() {
		return object;
	}

	public void setObject(Song object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "KeyObjectPair [key=" + key + ", object=" + object + "]";
	}

}
