package gameframework.game;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.ObservableValue;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;

/**
 * To be implemented with respect to a specific game. Expected to initialize the
 * universe and the gameBoard
 */

public abstract class PygmyGameLevel implements GameLevel {
	
	protected Canvas canvas;
	
	protected GameMap gameMap;
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected ObservableValue<Boolean> endOfGame;

	protected final PygmyGame game;
	protected List<GameRule> gameRules;

	public PygmyGameLevel(PygmyGame game, OverlapRulesApplier overlapRules) {
		this.game = game;
		this.gameRules = new ArrayList<GameRule>();
		game.setCurrentPlayer(game.getPlayers().get(0));
		canvas = game.getCanvas();

		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		/*MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(moveBlockerRules);
		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);*/
		
		universe = new GameUniverseDefaultImpl(overlapProcessor);
		overlapRules.setUniverse(universe);

		overlapProcessor.setOverlapRules(overlapRules);
		overlapProcessor.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);
	}

	@Override
	public void start() {
		endOfGame = game.endOfGame();
		init();
		gameBoard.paint();
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
	public Canvas getCanvas() {
		return canvas;
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

	// Adds a movable entity to the level
	public void addMovableEntity(MovableEntity entity) {
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard(entity, this);
		canvas.addKeyListener(keyStr);
		addEntity(entity);
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
		gameBoard.paint();
	}

	public void end() {
		
	}
	
}
