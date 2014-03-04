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

import android.graphics.Point;

public class Tile {
	final private String TAG = "Tile";
	
	int[] positionPixel;
	
	// (0,1) top left corner, (2,3) bottom right corner
	Point topLeft;
	Point bottomRight;
	
	public Tile(int x1, int y1, int x2, int y2) {
		positionPixel = new int[4];
		positionPixel[0] = x1;
		positionPixel[1] = y1;
		positionPixel[2] = x2;
		positionPixel[3] = y2;
		
		topLeft = new Point(x1, y1);
		bottomRight = new Point(x2, y2);
	}
	
	public int[] getCoord(int x, int y) {
		return positionPixel;
	}
}
