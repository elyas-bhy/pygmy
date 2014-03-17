package com.lib.pygmy;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.res.Resources;

import com.lib.pygmy.base.ObservableValue;

/**
 * Create a basic game application
 */
public abstract class PygmyGame implements Game, Observer {
	
	protected ObservableValue<Boolean> endOfGame = null;
	
	private PygmyGameContext context;
	private GameLevel currentPlayedLevel = null;
	private List<GameLevel> gameLevels;
	
	public PygmyGame(Resources resources) {
		context = new PygmyGameContext(this, resources);
	}

	@Override
	public abstract void initGame();

	public void start() {
		endOfGame = new ObservableValue<Boolean>(false);
		endOfGame.addObserver(this);
		
		GameLevel level = gameLevels.get(0);
		context.setCurrentLevel(level);
		level.start();
	}

	@Override
	public void nextPlayer(String state) {
		context.nextPlayer(state);
	}
	
	@Override
	public void onPlayerMove(GameMove move) {
		context.onPlayerMove(move);
	}

	@Override
	public PygmyGameContext getContext() {
		return context;
	}
	
	@Override
	public List<String> getPlayerIds() {
		return context.getPlayerIds();
	}

	@Override
	public void setPlayerIds(List<String> playerIds) {
		context.setPlayers(playerIds);
	}

	@Override
	public String getCurrentPlayerId() {
		return context.getCurrentPlayerId();
	}
	
	@Override
	public List<GameLevel> getLevels() {
		return gameLevels;
	}

	@Override
	public void setLevels(List<GameLevel> levels) {
		gameLevels = levels;
	}
	
	@Override
	public GameLevel getCurrentLevel() {
		return context.getCurrentLevel();
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				//informationValue.setText("You win");
				currentPlayedLevel.end();
			}
		}
	}

	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public PygmyGame getGame() {
		return this;
	}
}