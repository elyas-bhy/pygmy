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
import java.lang.reflect.Method;
import java.util.Vector;

import com.lib.pygmy.base.Overlap;
import com.lib.pygmy.base.Overlappable;

public abstract class PygmyOverlapRulesApplier implements
		OverlapRulesApplier, Serializable {
	
	private static final long serialVersionUID = -7248823804089586622L;

	public void applyOverlapRules(Vector<Overlap> overlaps) {
		for (Overlap col : overlaps) {
			applySpecificOverlapRule(col.getOverlappable1(),
					col.getOverlappable2());
		}
	}

	protected void applySpecificOverlapRule(Overlappable e1, Overlappable e2) {
		Method m;

		try {
			m = getClass().getDeclaredMethod("overlapRule", e1.getClass(),
					e2.getClass());
		} catch (NoSuchMethodException e) {
			// automatic commutativity handling
			reverseParameters(e1, e2);
			return;
		}
		invoke(m, e1, e2);
	}

	protected void reverseParameters(Overlappable e1, Overlappable e2) {
		Method m;
		try {
			m = getClass().getDeclaredMethod("overlapRule", e2.getClass(),
					e1.getClass());
		} catch (NoSuchMethodException e) {
			return;
		}
		invoke(m, e2, e1);
	}

	protected void invoke(Method m, Overlappable e1, Overlappable e2) {
		try {
			m.invoke(this, e1, e2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Reflective invocation exception", e);
		}
	}
	
}