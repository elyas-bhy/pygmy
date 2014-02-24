package my.first.game;

import gameframework.game.OverlapRulesApplier;
import gameframework.game.PygmyGame;
import gameframework.game.PygmyGameLevel;

import java.awt.Point;

import my.first.game.entity.MyMovableEntity;
import my.first.game.entity.Wall;

public class DemoLevel extends PygmyGameLevel {

	/*public DemoLevel(Game g, OverlapRulesApplier overlapRules,
			MoveBlockerRulesApplier moveBlockerRules) {
		super(g, overlapRules, moveBlockerRules);
	}*/
	
	public DemoLevel(PygmyGame game, OverlapRulesApplier overlapRules) {
		super(game, overlapRules);
	}

	@Override
	public void init() {
		setDimensions(16, 16);
		addGameRule(new EternalGameRule());
		addMovableEntity(new MyMovableEntity(this, "images/pac1.gif"), new Point(10, 10));
		addEntity(new Wall(this, 16, 16));
		addEntity(new Wall(this, 32, 32));
		addEntity(new Wall(this, 48, 48));
		addEntity(new Wall(this, 64, 64));
	}
	
}
