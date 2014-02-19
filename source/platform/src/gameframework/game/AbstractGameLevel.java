package gameframework.game;

import gameframework.base.ObservableValue;

import java.util.ArrayList;
import java.util.List;

/**
 * To be implemented with respect to a specific game. Expected to initialize the
 * universe and the gameBoard
 */

public abstract class AbstractGameLevel implements GameLevel {
	
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected ObservableValue<Integer> score[];
	protected ObservableValue<Integer> life[];
	protected ObservableValue<Boolean> endOfGame;

	protected final Game g;
	
	protected List<GameRule> gameRules;

	protected abstract void init();

	public AbstractGameLevel(Game g) {
		this.g = g;
		this.gameRules = new ArrayList<GameRule>();
		this.score = g.score();
		this.life = g.life();
		g.setCurrentPlayer(g.getPlayers().get(0));
	}
	
	@Override
	public Player getCurrentPlayer() {
		return g.getCurrentPlayer();
	}
	
	@Override
	public void start() {
		endOfGame = g.endOfGame();
		init();
		gameBoard.paint();
	}
	
	public void tryMove(GameMovable entity, String move) {
		if (entity.isLegalMove(move)) {
			for (GameRule rule : gameRules) {
				if (!rule.check()) {
					System.out.println(rule.getMessage());
					end();
				}
			}
			takeTurn(entity, move);
		} else {
			// throw new IllegalMoveException();
		}
	}

	public void takeTurn(GameMovable entity, String move) {
		gameBoard.paint();
		getCurrentPlayer().play(entity, move);
		entity.oneStepMove();
		universe.processAllOverlaps();
	}

	public void end() {
		
	}
	
	public void addGameRule(GameRule rule) {
		gameRules.add(rule);
	}

}