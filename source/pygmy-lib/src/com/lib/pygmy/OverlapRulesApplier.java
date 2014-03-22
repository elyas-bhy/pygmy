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

import com.lib.pygmy.base.Overlap;

import java.util.Vector;

/**
 * Handles entity overlaps and applies the appropriate rules 
 * whenever appropriate
 * @author Pygmy
 *
 */
public interface OverlapRulesApplier {
	
	/**
	 * Sets the managed universe by this instance
	 * @param universe
	 */
	public void setUniverse(GameUniverse universe);

	/**
	 * Updates the universe according to the passed overlaps
	 * @param overlaps
	 */
	public void applyOverlapRules(Vector<Overlap> overlaps);
	
}
