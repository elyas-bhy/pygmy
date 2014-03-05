package com.lib.pygmy;

import java.util.ArrayList;
import java.util.List;

import com.lib.pygmy.base.ObservableValue;

/**
 * To be implemented with respect to a specific game. Expected to initialize the
 * universe and the gameBoard
 */

public abstract class PygmyGameLevel implements GameLevel {
	
	protected GameMap gameMap;
	protected GameUniverse universe;
	protected ObservableValue<Boolean> endOfGame;

	protected final Game game;
	protected List<GameRule> gameRules;

	public PygmyGameLevel(Game game, OverlapRulesApplier overlapRules) {
		this.game = game;
		this.gameRules = new ArrayList<GameRule>();
		
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		universe = new PygmyGameUniverse(overlapProcessor);
		overlapRules.setUniverse(universe);

		overlapProcessor.setOverlapRules(overlapRules);
		overlapProcessor.setUniverse(universe);
	}

	@Override
	public void start() {
		endOfGame = game.endOfGame();
		init();
	}
	
	public abstract void init();
	
	@Override
	public Player getCurrentPlayer() {
		return game.getCurrentPlayer();
	}

	@Override
	public GameMap getMap() {
		return gameMap;
	}
	
	@Override
	public GameUniverse getUniverse() {
		return universe;
	}
	
	@Override
	public PygmyGameContext getContext() {
		return game.getContext();
	}

	@Override
	public void setDimensions(int rows, int cols) {
		gameMap = new GameMap(rows, cols);
		for (int i = 0; i < cols; ++i) {
			for (int j = 0; j < rows; ++j) {
				gameMap.setValue(i, j, 5);
			}
		}
	}
	
	@Override
	public void addGameRule(GameRule rule) {
		gameRules.add(rule);
	}

	public void addEntity(GameEntity entity) {
		universe.addGameEntity(entity);
	}
	
	public void tryMove(GameMove move) {
		if (move.getEntity().isLegalMove(move)) {
			for (GameRule rule : gameRules) {
				if (!rule.check()) {
					System.out.println(rule.getMessage());
					end();
				}
			}
			makeMove(move);
		} else {
			// throw new IllegalMoveException();
		}
	}

	private void makeMove(GameMove move) {
		getCurrentPlayer().play(move);
		universe.processMove(move);
		game.nextPlayer();
	}

	public void end() {
		
	}
	
}
