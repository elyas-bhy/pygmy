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

import android.graphics.Color;
import android.graphics.Paint;

import com.client.pygmy.entity.MyChessEntity;
import com.client.pygmy.entity.Pawn;
import com.lib.pygmy.EntityType;
import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;
import com.lib.pygmy.util.Point;

public class DemoLevel extends PygmyGameLevel {

	public DemoLevel(PygmyGame game, OverlapRulesApplier overlapRules) {
		super(game, overlapRules);
	}

	/**
	 * Sets images for each entity in the initial position.
	 * @param context Context of the parent view
	 */
	@Override
	public void init() {
		List<String> playerIds = getContext().getGame().getPlayerIds();
		String p1 = playerIds.get(0);
		String p2 = playerIds.get(1);

		List<Paint> colors = new ArrayList<Paint>();
		Paint color1 = new Paint();
		Paint color2 = new Paint();
		color1.setColor(Color.CYAN);
		color2.setColor(Color.WHITE);
		colors.add(color1);
		colors.add(color2);

		setBoardType(0);
		setColors(colors);
		setDimensions(13, 15);

		addGameRule(new EndlessGameRule());

		// declare each black piece (entity) with the Entity class
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_ROOK, new Point(0, 0)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_KNIGHT, new Point(0, 1)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_BISHOP, new Point(0, 2)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_QUEEN, new Point(0, 3)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_KING, new Point(0, 4)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_BISHOP, new Point(0, 5)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_KNIGHT, new Point(0, 6)));
		addEntity(new MyChessEntity(this, p1, EntityType.BLACK_ROOK, new Point(0, 7)));

		for (int i = 0; i < 8; i++) {
			addEntity(new Pawn(this, p1, EntityType.BLACK_PAWN, new Point(1, i)));
		}

		// declare each white piece (entity) with the Entity class
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_ROOK, new Point(7, 0)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_KNIGHT, new Point(7, 1)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_BISHOP, new Point(7, 2)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_QUEEN, new Point(7, 3)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_KING, new Point(7, 4)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_BISHOP, new Point(7, 5)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_KNIGHT, new Point(7, 6)));
		addEntity(new MyChessEntity(this, p2, EntityType.WHITE_ROOK, new Point(7, 7)));

		for (int i = 0; i < 8; i++) {
			addEntity(new Pawn(this, p2, EntityType.WHITE_PAWN, new Point(6, i)));
		}

	}

}
