package com.lib.pygmy;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

public class PygmyGameContext {

	private Resources res;
	private Game game;
	private GameLevel currentLevel;
	
	private List<Player> players;
	private int currentPlayer;
	
	public PygmyGameContext(Game game, Resources resources) {
		this.game = game;
		this.res = resources;
		this.players = new ArrayList<Player>();
		this.currentPlayer = 0;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Resources getResources() {
		return res;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players.clear();
		this.players.addAll(players);
	}
	
	public GameLevel getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(GameLevel level) {
		currentLevel = level;
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}
	
	public void nextPlayer(String state) {
		currentPlayer = (currentPlayer + 1) % players.size();
		
//		showSpinner();
//		String nextParticipantId = getNextParticipantId();
//		// Create the next turn
//		mTurnData.turnCounter += 1;
//		mTurnData.data = state;
//
//		showSpinner();
//
//		getGamesClient().takeTurn(this, mMatch.getMatchId(),
//				mTurnData.persist(), nextParticipantId);
//
//		mTurnData = null;
	}
	
	public void onPlayerMove(GameMove move) {
		currentLevel.tryMove(move);
	}
	
}