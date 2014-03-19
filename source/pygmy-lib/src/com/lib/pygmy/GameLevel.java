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

import java.util.List;

public interface GameLevel {

	public void start();
	
	public void init();

	public String getCurrentPlayerId();
	
	public GameUniverse getUniverse();
	
	public PygmyGameContext getContext();

	public int getNumberRows();
	
	public int getNumberColumns();

	public void setDimensions(int rows, int col);

	public int getBoardType();
	
	public void setBoardType(int type);
	
	public List<Integer> getColors();
	
	public void setColors(List<Integer> colors);

	public void addGameRule(GameRule rule);

	public void addEntity(GameEntity entity);

	public void tryMove(GameMove move);

	public void end();
	
}