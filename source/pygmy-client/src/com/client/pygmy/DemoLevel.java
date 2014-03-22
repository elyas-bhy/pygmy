/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.client.pygmy;

import java.util.ArrayList;
import java.util.List;

import com.lib.pygmy.BoardType;
import com.lib.pygmy.EntityType;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;
import com.lib.pygmy.util.Color;
import com.lib.pygmy.util.Point;

/**
 * This class represents a game level.
 * You should always extend {@link PygmyGameLevel} class
 * @author Pygmy
 *
 */
public class DemoLevel extends PygmyGameLevel {

	public DemoLevel(PygmyGame game, OverlapRulesApplier overlapRules) {
		super(game, overlapRules);
	}
	
	// Entry-point method for initializing a game level.
	// Override this to setup your level configuration.
	@Override
	public void init() {
		
		// Set your level dimensions.
		// Note that rows and columns cannot exceed a size of 16
		setDimensions(4, 4);
		
		// Select your board type
		setBoardType(BoardType.CHECKER_BOARD);

		// Select your grid colors.
		// You need to specify two colors for checker boards
		List<Color> colors = new ArrayList<Color>();
		colors.add(new Color(255, 173, 179, 250));  // stale blue
		colors.add(new Color(255, 255, 255, 255));  // white
		setColors(colors);
		
		// Specify the level's rules
		addGameRule(new DemoGameRule());
		
		// Retrieve player IDs
		List<String> playerIds = getContext().getGame().getPlayerIds();
		String p1 = playerIds.get(0);
		String p2 = playerIds.get(1);

		// Add your entities to the level's universe
		// using addEntity(GameEntity) method
		addEntity(new Pawn(this, p1, EntityType.BLACK_PAWN, new Point(0, 0)));
		addEntity(new Pawn(this, p2, EntityType.WHITE_PAWN, new Point(3, 3)));
	}

}