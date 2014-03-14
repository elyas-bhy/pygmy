package com.client.pygmy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;

import com.lib.pygmy.GameLevel;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;

public class PygmyGameImpl extends PygmyGame {

	public PygmyGameImpl(Resources resources) {
		super(resources);
	}
	
	// TODO delegate to GameLevel
	public Map<String,Object> getParameters() {
		HashMap<String,Object> parameters = new HashMap<String, Object>();
		parameters.put("numberRows", 8);
		parameters.put("numberColumns", 8);
		parameters.put("numberPieces", 32);
		parameters.put("boardType", 0);
		return parameters;
	}

	@Override
	public void initGame() {
		PygmyGame game = getGame();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		OverlapRulesApplier rules = new DemoOverlapRules();
		PygmyGameLevel level1 = new DemoLevel(game, rules);
		levels.add(level1); // only one level is available at this time

		game.setLevels(levels);
	}

}
