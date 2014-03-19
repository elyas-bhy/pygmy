package com.lib.pygmy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.lib.pygmy.base.ObservableValue;

/**
 * Create a basic game application
 */
public abstract class PygmyGame implements Game, Observer, Serializable {
	
	private static final long serialVersionUID = -6084846314735557726L;

	protected ObservableValue<Boolean> endOfGame;
	
	private PygmyGameContext context;
	private GameLevel currentPlayedLevel;
	private List<GameLevel> gameLevels;
	
	public PygmyGame() {
		context = new PygmyGameContext(this);
		gameLevels = new ArrayList<GameLevel>();
	}

	@Override
	public abstract void initGame();

	@Override
	public void start() {
		endOfGame = new ObservableValue<Boolean>(false);
		endOfGame.addObserver(this);
		
		GameLevel level = gameLevels.get(0);
		context.setCurrentLevel(level);
		level.start();
	}

	@Override
	public void nextPlayer() {
		context.nextPlayer();
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

	@Override
	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				//informationValue.setText("You win");
				currentPlayedLevel.end();
			}
		}
	}

	@Override
	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}
	
	public PygmyGame getGame() {
		return this;
	}
	
}