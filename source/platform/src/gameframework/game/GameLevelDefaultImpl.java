package gameframework.game;

import gameframework.base.ObservableValue;

import java.util.ArrayList;
import java.util.List;

/**
 * To be implemented with respect to a specific game. Expected to initialize the
 * universe and the gameBoard
 */

public abstract class GameLevelDefaultImpl implements GameLevel {
	
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected ObservableValue<Integer> score[];
	protected ObservableValue<Integer> life[];
	protected ObservableValue<Boolean> endOfGame;

	protected final Game g;
	
	protected List<GameRule> gameRules;

	protected abstract void init();

	public GameLevelDefaultImpl(Game g) {
		this.g = g;
		this.gameRules = new ArrayList<GameRule>();
		this.score = g.score();
		this.life = g.life();
	}
	
	@Override
	public void start() {
		endOfGame = g.endOfGame();
		g.setCurrentPlayer(g.getPlayers().get(0));
		init();
	}

	public void run() {
		gameBoard.paint();
		
		for (GameRule rule : gameRules) {
			if (!rule.check()) {
				System.out.println(rule.getMessage());
				end();
			}
		}
		g.getCurrentPlayer().play("");
		universe.processAllOverlaps();
	}

	public void end() {
		
	}
	
	public void addGameRule(GameRule rule) {
		gameRules.add(rule);
	}

	protected void overlap_handler() {
	}

}
