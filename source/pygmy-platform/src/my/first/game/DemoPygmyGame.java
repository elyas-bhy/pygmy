package my.first.game;

import gameframework.game.AbstractPygmyGame;
import gameframework.game.GameLevel;
import gameframework.game.OverlapRulesApplier;
import gameframework.game.PygmyGame;
import gameframework.game.PygmyGameLevel;

import java.awt.Point;
import java.util.ArrayList;

import pacman.rule.PacmanOverlapRules;

public class DemoPygmyGame {

	public static void main(String[] args) {
		DemoGame game = new DemoGame();
		game.initGame();
	}

	private static class DemoGame extends AbstractPygmyGame {

		@Override
		public void initGame() {
			PygmyGame game = getGame();
			game.setPlayers(2, 4);
			game.setBoardDimensions(10, 10);
			game.setTitle("DemoGame");

			ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

			OverlapRulesApplier rules = new PacmanOverlapRules(new Point(224, 272), new Point(224, 240));
			PygmyGameLevel level1 = new DemoLevel(game, rules);
			levels.add(level1); // only one level is available at this time
			
			game.setLevels(levels);
			level1.start();
		}
	}

}
