package ru.fuzzysearch;

import java.io.Serializable;

public class Alphabet implements Serializable {

	private static final long serialVersionUID = 8049521430325352952L;
	
	public Alphabet() {
		this.min = 'A';
		this.max = 'Z';
		
		chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	}

	public int mapChar(char ch) {
		if (ch < min || ch > max) return -1;
		return ch - min;
	}

	public char[] chars() {
		return chars;
	}

	public int size() {
		return chars.length;
	}

	private final char min;
	private final char max;
	private final char[] chars;
}
