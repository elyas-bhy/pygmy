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

import java.util.Vector;

import com.lib.pygmy.GameUniverse;
import com.lib.pygmy.PygmyGameEntity;
import com.lib.pygmy.PygmyOverlapRulesApplier;
import com.lib.pygmy.base.Overlap;

public class DemoOverlapRules extends PygmyOverlapRulesApplier {
	
	protected GameUniverse universe;

	public DemoOverlapRules() {
		
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		super.applyOverlapRules(overlappables);
	}
	
//	public void overlapRule(PygmyGameEntity e1, PygmyGameEntity e2) {
//		universe.removeGameEntity(e2);
//	}
}
