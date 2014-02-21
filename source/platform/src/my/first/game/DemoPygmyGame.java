package my.first.game;

import gameframework.game.Game;
import gameframework.game.GameDefaultImpl;
import gameframework.game.GameLevel;
import gameframework.game.OverlapRulesApplier;

import java.awt.Point;
import java.util.ArrayList;

import pacman.rule.PacmanMoveBlockers;
import pacman.rule.PacmanOverlapRules;

public class DemoPygmyGame extends AbstractPygmyGame {
	
	public static void main(String[] args) {
		GameDefaultImpl g = new GameDefaultImpl();
		g.setPlayers(1, 10);
		g.setBoardDimensions(10, 10);
		g.setTitle("DemoGame");
		
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();
		
		OverlapRulesApplier rules = new PacmanOverlapRules(new Point(224, 272), new Point(224, 240));
		PygmyGameLevel level1 = new DemoLevel(g, rules, new PacmanMoveBlockers());
		
		levels.add(level1); // only one level is available at this time
		g.setLevels(levels);
		//g.start();
		level1.start();
	}

	@Override
	public void initGame() {
		Game game = getGame();
		game.setPlayers(1, 10);
		game.setBoardDimensions(10, 10);
		game.setTitle("DemoGame");
		
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();
		
		OverlapRulesApplier rules = new PacmanOverlapRules(new Point(224, 272), new Point(224, 240));
		PygmyGameLevel level1 = new DemoLevel(game, rules, new PacmanMoveBlockers());
		
		levels.add(level1); // only one level is available at this time
		game.setLevels(levels);
		//game.start();
	}

}
