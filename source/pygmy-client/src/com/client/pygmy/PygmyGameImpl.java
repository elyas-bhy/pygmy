package com.client.pygmy;

import java.util.ArrayList;

import com.lib.pygmy.GameLevel;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;

public class PygmyGameImpl extends PygmyGame {

	public PygmyGameImpl() {
		super();
	}

	@Override
	public void initGame() {
		PygmyGame game = getGame();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();
		
		OverlapRulesApplier rules = new DemoOverlapRules();
		PygmyGameLevel level1 = new DemoLevel(game, rules);
		levels.add(level1); // only one level is available at this time

		try {
			game.setLevels(levels);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
