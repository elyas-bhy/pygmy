package com.lib.pygmy;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

public class PygmyGameContext {

	private Resources res;
	private PygmyGame game;
	private PygmyGameLevel currentLevel;
	
	private List<Player> players;
	private int currentPlayer;
	
	public PygmyGameContext(PygmyGame game, Resources resources) {
		this.game = game;
		this.res = resources;
		this.players = new ArrayList<Player>();
		this.currentPlayer = 0;
	}
	
	public PygmyGame getGame() {
		return game;
	}
	
	public Resources getResources() {
		return res;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(int minPlayers, int maxPlayers) {
		players.clear();
		players.add(new Player());
		players.add(new Player());
	}
	
	public GameLevel getCurrentLevel() {
		//return currentLevel;
		return game.getLevels().get(0);
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}
	
	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}
	
}
