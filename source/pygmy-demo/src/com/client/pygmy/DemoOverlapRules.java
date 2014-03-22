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

import com.client.pygmy.entity.MyChessEntity;
import com.client.pygmy.entity.Pawn;
import com.lib.pygmy.GameUniverse;
import com.lib.pygmy.PygmyOverlapRulesApplier;

/**
 * Allows to specify the actions to take when an overlap occurs
 * between to game entities
 * @author Pygmy
 *
 */
public class DemoOverlapRules extends PygmyOverlapRulesApplier {
	
	private static final long serialVersionUID = -1258944691352234655L;
	protected GameUniverse universe;

	public DemoOverlapRules() {
		
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	
	public void overlapRule(Pawn e1, Pawn e2) {
		universe.removeGameEntity(e2);
	}
	
	public void overlapRule(Pawn e1, MyChessEntity e2) {
		universe.removeGameEntity(e2);
	}
	
	public void overlapRule(MyChessEntity e1, MyChessEntity e2) {
		universe.removeGameEntity(e2);
	}
	
}