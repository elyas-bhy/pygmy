package com.lib.pygmy;

public class Player {
	
	private String id;
	
	public Player(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public boolean play(GameMove move) {
		return true;
	}
}
