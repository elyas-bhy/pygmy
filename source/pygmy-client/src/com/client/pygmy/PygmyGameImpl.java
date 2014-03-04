package com.client.pygmy;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

import com.lib.pygmy.AbstractPygmyGame;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;

public class PygmyGameImpl extends AbstractPygmyGame {

	public PygmyGameImpl(Resources resources) {
		super(resources);
	}
	
	// TODO delegate to GameLevel
	public HashMap<String,Object> getParameters() {
		HashMap<String,Object> parameters = new HashMap<String, Object>();
		
		Paint[] colors = new Paint[2];
		colors[0] = new Paint(Color.CYAN);
		colors[1] = new Paint(Color.WHITE);
		//colors[0].setColor(R.color.green_dark);
		//colors[1].setColor(R.color.green_light);
		
		int numberOfSquaresByRow = 8;
		int numberOfSquaresByColumn = 8;
		int numberOfPieces = 32;
		
		parameters.put("colors", colors);
		parameters.put("numberRow", numberOfSquaresByRow);
		parameters.put("numberColumn", numberOfSquaresByColumn);
		parameters.put("numberPieces", numberOfPieces);
		return parameters;
	}

	@Override
	public void initGame() {
		PygmyGame game = getGame();
		game.setPlayers(2, 4);
		game.setBoardDimensions(6, 6);
		game.setTitle("DemoGame");

		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		OverlapRulesApplier rules = new DemoOverlapRules();
		PygmyGameLevel level1 = new DemoLevel(game, rules);
		levels.add(level1); // only one level is available at this time

		game.setLevels(levels);
	}

}
