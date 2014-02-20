package my.first.game;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.game.AbstractGameLevel;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameEntity;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;

import pacman.rule.PacmanMoveBlockers;
import pacman.rule.PacmanOverlapRules;

public  class Levels extends AbstractGameLevel {

	Canvas canvas;
	public int[][] map = {};
	private PacmanOverlapRules por=null;
	public int map_rows=0;
	public int map_columns=0;
	private MoveBlockerChecker mbc;
	private PacmanMoveBlockers pmb;

	public static final int SPRITE_SIZE = 16;

	@Override
	protected void init() {
		initRules();
		initMoveBlockers();
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		
		mbc = new MoveBlockerCheckerDefaultImpl();
		mbc.setMoveBlockerRules(pmb);
		
	
		
		overlapProcessor.setOverlapRules(por);

		
		universe = new GameUniverseDefaultImpl(mbc, overlapProcessor);
		por.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

	
	}
	
	public void setOverlapRules(PacmanOverlapRules por) {
		this.por = por;
	}
	
	public void setMoveBlockers(PacmanMoveBlockers pmb) {
		this.pmb = pmb;
	}
	

 	
	
	
	
	//Add movable entity to the level
	public void addMovableEntity(MovableEntities me, int posx, int posy) {
		GameMovableDriverDefaultImpl pacDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard(me, this);
		pacDriver.setmoveBlockerChecker(mbc);
		pacDriver.setStrategy(keyStr);
		canvas.addKeyListener(keyStr);
		me.setDriver(pacDriver);
		me.setPosition(new Point(posx* SPRITE_SIZE, posy * SPRITE_SIZE));
		universe.addGameEntity(me);
	}
	

	public Levels(Game g) {
		super(g);
		canvas = g.getCanvas();
		start();
	}
	
	public void setUpMap(){
		map=new int[map_columns][map_rows];
		for (int i = 0; i < map_columns; ++i) {
			for (int j = 0; j <map_rows; ++j) {
					map[i][j]=5;
			}
		}	
	}
	
	public void setDimensions(int rows,int col) {
		this.map_rows = rows;
		this.map_columns = col;
		setUpMap();
	}
	
	public void addEntity(GameEntity gm){
		universe.addGameEntity(gm);
	}
	


	@Override
	public int[][] getMap() {
		return map;
	}

	@Override
	public void initRules() {	
	}

	@Override
	public void initMoveBlockers() {
	}


}


