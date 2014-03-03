package com.lib.pygmy;

import java.util.ArrayList;
import java.util.List;

public class PygmyGameContext {

	private PygmyGame game;
	private PygmyGameLevel currentLevel;
	
	private List<Player> players;
	private int currentPlayer;
	
	public PygmyGameContext(PygmyGame game) {
		this.game = game;
		this.players = new ArrayList<Player>();
		this.currentPlayer = 0;
	}
	
	public PygmyGame getGame() {
		return game;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(int minPlayers, int maxPlayers) {
		players.clear();
		players.add(new Player());
		players.add(new Player());
	}
	
	public PygmyGameLevel getCurrentLevel() {
		return currentLevel;
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}
	
	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}
	
}
