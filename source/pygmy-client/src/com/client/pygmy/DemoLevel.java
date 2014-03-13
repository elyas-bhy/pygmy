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

import java.util.HashMap;
import java.util.List;

import android.graphics.Point;

import com.client.pygmy.entity.MyChessEntity;
import com.client.pygmy.entity.Pawn;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;
import com.lib.pygmy.Res;

public class DemoLevel extends PygmyGameLevel {
	
	private HashMap<String, Object> parameters;

	public DemoLevel(PygmyGame game, OverlapRulesApplier overlapRules) {
		super(game, overlapRules);
		parameters = new HashMap<String, Object>();
	}

	/**
	 * Returns a HashMap with whole parameters for this game.
	 */
	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * Sets images for each entity in the initial position.
	 * @param context Context of the parent view
	 */
	@Override
	public void init() {
		List<Player> players = getContext().getGame().getPlayers();
		Player p1 = players.get(0);
		Player p2 = players.get(1);
		
		setDimensions(16, 16);
		addGameRule(new EndlessGameRule());

		// declare each black piece (entity) with the Entity class
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_rook, new Point(0, 0)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_knight, new Point(0, 1)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_bishop, new Point(0, 2)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_queen, new Point(0, 3)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_king, new Point(0, 4)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_bishop, new Point(0, 5)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_knight, new Point(0, 6)));
		addEntity(new MyChessEntity(this, p1, Res.drawable.black_rook, new Point(0, 7)));

		for (int i = 0; i < 8; i++) {
			addEntity(new Pawn(this, p1, Res.drawable.black_pawn, new Point(1, i)));
		}
		
		// declare each white piece (entity) with the Entity class
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_rook, new Point(7, 0)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_knight, new Point(7, 1)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_bishop, new Point(7, 2)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_queen, new Point(7, 3)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_king, new Point(7, 4)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_bishop, new Point(7, 5)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_knight, new Point(7, 6)));
		addEntity(new MyChessEntity(this, p2, Res.drawable.white_rook, new Point(7, 7)));

		for (int i = 0; i < 8; i++) {
			addEntity(new Pawn(this, p2, Res.drawable.white_pawn, new Point(6, i)));
		}
	}
	
}
