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

/**
 * Represents a game rule. Rules are used to verify if a game should end.
 * @author Pygmy
 *
 */
public interface GameRule {

	/**
	 * Returns whether the rule is verified
	 */
	public boolean check();
	
	/**
	 * Returns the message associated to this rule when it gets verified
	 */
	public String getMessage();
	
}