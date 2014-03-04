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

package com.dev.pygmy.game;

public class Tile {
	final private String TAG = "Tile";
	
	int[] positionPixel;
	int tileSize;
	
	public Tile(int x1, int y1, int tileSize) {
		positionPixel = new int[2];
		positionPixel[0] = x1;
		positionPixel[1] = y1;
		this.tileSize = tileSize;
	}
	
	public int[] getCoord() {
		return positionPixel;
	}
	
	public int getTileSize() {
		return tileSize;
	}
}
