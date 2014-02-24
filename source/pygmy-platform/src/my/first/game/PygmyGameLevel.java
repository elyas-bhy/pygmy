package my.first.game;

import gameframework.base.Direction;
import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.ObservableValue;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameEntity;
import gameframework.game.GameLevel;
import gameframework.game.GameMap;
import gameframework.game.GameMovable;
import gameframework.game.GameMove;
import gameframework.game.GameRule;
import gameframework.game.GameUniverse;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPort;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.MoveBlockerRulesApplier;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;
import gameframework.game.OverlapRulesApplier;
import gameframework.game.Player;

import java.awt.Canvas;
import java.awt.Point;
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

	protected final Game game;
	protected List<GameRule> gameRules;

	private final int SPRITE_SIZE = 16;

	public PygmyGameLevel(Game game, OverlapRulesApplier overlapRules, 
			MoveBlockerRulesApplier moveBlockerRules) {
		this.game = game;
		this.gameRules = new ArrayList<GameRule>();
		game.setCurrentPlayer(game.getPlayers().get(0));
		canvas = game.getCanvas();

		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(moveBlockerRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
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
	public void setDimensions(int rows, int col) {
		gameMap = new GameMap(new int[rows][col]);
		for (int i = 0; i < col; ++i) {
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
	public void addMovableEntity(MovableEntity entity, int x, int y) {
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard(entity, this);
		canvas.addKeyListener(keyStr);
		entity.setPosition(new Point(x * SPRITE_SIZE, y * SPRITE_SIZE));
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
		gameBoard.paint();
		getCurrentPlayer().play(move);
		universe.processMove(move);
	}

	public void end() {
		
	}
	
}
