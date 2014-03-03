package com.client.pygmy;

import com.lib.pygmy.AbstractPygmyGame;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;

import java.awt.Point;
import java.util.ArrayList;

public class PygmyGameImpl extends AbstractPygmyGame {

	@Override
	public void initGame() {
		PygmyGame game = getGame();
		game.setPlayers(2, 4);
		game.setBoardDimensions(10, 10);
		game.setTitle("DemoGame");

		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		OverlapRulesApplier rules = new DemoOverlapRules(new Point(224, 272),
				new Point(224, 240));
		PygmyGameLevel level1 = new DemoLevel(game, rules);
		levels.add(level1); // only one level is available at this time

		game.setLevels(levels);
		level1.start();
	}

}
