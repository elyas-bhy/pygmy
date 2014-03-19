package com.lib.pygmy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PygmyGameContext implements Serializable {
	
	private static final long serialVersionUID = -205963873187570678L;
	
	private Game game;
	private GameLevel currentLevel;
	
	private List<String> playerIds;
	private String currentPlayerId;
	
	public PygmyGameContext(Game game) {
		this.game = game;
		this.playerIds = new ArrayList<String>();
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
		return currentPlayerId;
	}
	
	public void setCurrentPlayerId(String playerId) {
		currentPlayerId = playerId;
	}
	
	public void onPlayerMove(GameMove move) {
		currentLevel.tryMove(move);
	}
	
}