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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Tile {
	Point positionPixel;
	Point tileDimensions;
	Paint color;
	
	// FIXME: Delete this constructor before merge
	public Tile(int posX, int posY, int tileSquare) {
		positionPixel = new Point();
		positionPixel.x = posX;
		positionPixel.y = posY;
		
		tileDimensions = new Point();
		tileDimensions.x = tileSquare;
	}
	
	/**
	 * Saves coordinates in pixels of a rectangular tile.
	 * @param posX	position X in pixels.
	 * @param posY  position Y in pixels.
	 * @param tileRectangleDimensionX	size of the horizontal side.
	 * @param tileRectangleDimensionY	size of the vertical side;
	 */
	public Tile(int posX, int posY, int tileDimensionX, int tileDimensionY) {
		positionPixel = new Point();
		positionPixel.x = posX;
		positionPixel.y = posY;
		
		tileDimensions = new Point();
		tileDimensions.x = tileDimensionX;
		tileDimensions.y = tileDimensionY;
	}
	
	/**
	 * @return coordinates (x,y) in pixels for a tile.
	 */
	public Point getCoord() {
		return positionPixel;
	}
	
	/**
	 * @return the size of one side of a square tile.
	 */
	public int getTileSquareSize() {
		return tileDimensions.x;
	}
	
	/**
	 * @return the dimensions of a tile with a rectangular shape.
	 */
	public Point getTileRectangleDimensions() {
		return tileDimensions;
	}
	
	public void setColor(Paint color) {
		this.color = color;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(positionPixel.x, positionPixel.y,
				positionPixel.x + tileDimensions.x,
				positionPixel.y + tileDimensions.y,
				color);
	}
}