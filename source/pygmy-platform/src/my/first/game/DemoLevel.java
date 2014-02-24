package my.first.game;

import gameframework.game.Game;
import gameframework.game.MoveBlockerRulesApplier;
import gameframework.game.OverlapRulesApplier;

import java.awt.Point;

public class DemoLevel extends PygmyGameLevel {

	/*public DemoLevel(Game g, OverlapRulesApplier overlapRules,
			MoveBlockerRulesApplier moveBlockerRules) {
		super(g, overlapRules, moveBlockerRules);
	}*/
	
	public DemoLevel(Game g, OverlapRulesApplier overlapRules) {
		super(g, overlapRules);
	}

	@Override
	public void init() {
		setDimensions(16, 16);
		addGameRule(new EternalGameRule());
		addMovableEntity(new MyMovableEntity(canvas, "images/pac1.gif"), new Point(10, 10));
		addEntity(new Wall(canvas, 16, 16));
		addEntity(new Wall(canvas, 32, 32));
		addEntity(new Wall(canvas, 48, 48));
		addEntity(new Wall(canvas, 64, 64));
	}
	
}
