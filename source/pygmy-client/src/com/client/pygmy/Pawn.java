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

import com.lib.pygmy.EntityType;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.PygmyGameEntity;
import com.lib.pygmy.Tile;
import com.lib.pygmy.util.Point;

public class Pawn extends PygmyGameEntity {

	public Pawn(GameLevel level, String playerId, EntityType type, Point p) {
		super(level, playerId, type, p);
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		
	}
	
	@Override
	public boolean isLegalMove(GameMove move) {
		Tile src = getCurrentTile();
		Tile dst = move.getDestination();
		return (dst.getPosition().y - src.getPosition().y) == 0;
	}

}