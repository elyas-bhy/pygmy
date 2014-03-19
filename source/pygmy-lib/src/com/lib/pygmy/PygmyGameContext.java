package com.lib.pygmy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PygmyGameContext implements Serializable {
	
	private static final long serialVersionUID = -205963873187570678L;
	
	private Game game;
	private GameLevel currentLevel;
	
	private List<String> playerIds;
	private int currentPlayer;
	
	public PygmyGameContext(Game game) {
		this.game = game;
		this.playerIds = new ArrayList<String>();
		this.currentPlayer = 0;
	}
	
	public Game getGame() {
		return game;
	}

	public List<String> getPlayerIds() {
		return playerIds;
	}
	
	public void setPlayers(List<String> playerIds) {
		this.playerIds.clear();
		this.playerIds.addAll(playerIds);
	}
	
	public GameLevel getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(GameLevel level) {
		try {
			currentLevel = level;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public String getCurrentPlayerId() {
		return playerIds.get(currentPlayer);
	}
	
	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % playerIds.size();
	}
	
	public void onPlayerMove(GameMove move) {
		currentLevel.tryMove(move);
	}
	
}