package my.first.game;



import gameframework.game.EternalGameRule;
import gameframework.game.Game;

import java.awt.Point;

import pacman.entity.Wall;
import pacman.rule.PacmanMoveBlockers;
import pacman.rule.PacmanOverlapRules;

public class MyLevel extends Levels {
	
	public MyLevel(Game g) {
		super(g);
	}

	public void initRules() {
		setOverlapRules((new PacmanOverlapRules(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE),
				new Point(14 * SPRITE_SIZE, 15 * SPRITE_SIZE), endOfGame)));		
	}
	
	public void initMoveBlockers() {
		setMoveBlockers(new PacmanMoveBlockers());
	}
	
	@Override
	public void init() {	
		super.init();		
		
		setDimensions(16,16);
		addMyEntities();
		myGameRule();
	
	}
	
	public void addMyEntities() {
		addMovableEntity(new MyMovableEntity(canvas,"images/pac1.gif"),10,10);
		addEntity(new Wall(canvas, 15,15 ));
		addEntity(new Wall(canvas, 30,30 ));
		addEntity(new Wall(canvas, 45,45 ));
		addEntity(new Wall(canvas, 60,60 ));
		
	}
	
	public void myGameRule() {
		addGameRule(new EternalGameRule());
	}
}

