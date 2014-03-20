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

package com.lib.pygmy;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.util.Log;

import com.lib.pygmy.base.Movable;
import com.lib.pygmy.base.Overlap;
import com.lib.pygmy.base.Overlappable;

public class OverlapProcessorImpl implements OverlapProcessor, Serializable {
	
	private static final long serialVersionUID = -271498426157796206L;
	
	/**
	 * These two lists contain all overlappables for which we want to compute
	 * overlaps. We distinguish between movable and non-movable because two
	 * non-movables never overlap.
	 */
	private ConcurrentLinkedQueue<Overlappable> overlappablesNonMovable;
	private ConcurrentLinkedQueue<Overlappable> overlappablesMovable;

	private OverlapRulesApplier overlapRules;
	private GameUniverse universe;

	public OverlapProcessorImpl() {
		overlappablesNonMovable = new ConcurrentLinkedQueue<Overlappable>();
		overlappablesMovable = new ConcurrentLinkedQueue<Overlappable>();
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void addOverlappable(Overlappable p) {
		if (p instanceof Movable) {
			overlappablesMovable.add(p);
		} else {
			overlappablesNonMovable.add(p);
		}
	}

	public void removeOverlappable(Overlappable p) {
		if (p instanceof Movable) {
			overlappablesMovable.remove(p);
		} else {
			overlappablesNonMovable.remove(p);
		}
	}

	public void setOverlapRules(OverlapRulesApplier overlapRules) {
		this.overlapRules = overlapRules;
	}

	public void processOverlap(GameMove move) {
		GameEntity entity = move.getEntity();
		universe.removeGameEntity(entity);
		entity.oneStepMove(move);
		GameEntity dst = universe.getGameEntities().get(entity.getCurrentTile());
		if (entity instanceof Overlappable && dst instanceof Overlappable) {
			Vector<Overlap> overlaps = new Vector<Overlap>();
			overlaps.add(new Overlap((Overlappable)entity, (Overlappable)dst));
			overlapRules.applyOverlapRules(overlaps);
		}
		universe.getGameEntities().put(entity.getCurrentTile(), entity);
	}

	@Override
	public void clear() {
		overlappablesMovable.clear();
		overlappablesNonMovable.clear();
	}
	
}