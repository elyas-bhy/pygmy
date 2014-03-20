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
		game.setLevels(levels);
	}

}