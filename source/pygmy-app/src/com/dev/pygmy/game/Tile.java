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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Tile {
	Point positionPixel;
	Point tileSize;
	Paint color;
	
	public Tile() {
		positionPixel = new Point();
		tileSize = new Point();
	}
	
	// FIXME: Delete this constructor before merge
	public Tile(int posX, int posY, int tileSquare) {
		positionPixel = new Point();
		positionPixel.x = posX;
		positionPixel.y = posY;
		
		tileSize = new Point();
		tileSize.x = tileSquare;
	}
	
	/**
	 * Saves coordinates in pixels of a rectangular tile.
	 * @param posX	position X in pixels.
	 * @param posY  position Y in pixels.
	 * @param tileRectangleDimensionX	size of the horizontal side.
	 * @param tileRectangleDimensionY	size of the vertical side;
	 */
	public Tile(int posX, int posY, int tileSizeX, int tileSizeY) {
		positionPixel = new Point();
		positionPixel.x = posX;
		positionPixel.y = posY;
		
		tileSize = new Point();
		tileSize.x = tileSizeX;
		tileSize.y = tileSizeY;
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
		return tileSize.x;
	}
	
	/**
	 * @return the dimensions of a tile with a rectangular shape.
	 */
	public Point getTileRectangleDimensions() {
		return tileSize;
	}
	
	public void setColor(Paint color) {
		this.color = color;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(positionPixel.x, positionPixel.y,
				positionPixel.x + tileSize.x,
				positionPixel.y + tileSize.y,
				color);
	}
	
	public void drawOverlay(Canvas canvas) {
		Paint color = new Paint();
		color.setColor(Color.GREEN);
		color.setStrokeWidth((int)(tileSize.x*6.5)/100);

		// Draw tile's outline
		// top
		canvas.drawLine(positionPixel.x, positionPixel.y, 
				positionPixel.x+tileSize.x, positionPixel.y, color);
		// bottom
		canvas.drawLine(positionPixel.x, positionPixel.y+tileSize.y,
				positionPixel.x+tileSize.x, positionPixel.y+tileSize.y, color);
		// left
		canvas.drawLine(positionPixel.x, positionPixel.y, 
				positionPixel.x, positionPixel.y+tileSize.y, color);
		// right
		canvas.drawLine(positionPixel.x+tileSize.x, positionPixel.y,
				positionPixel.x+tileSize.x, positionPixel.y+tileSize.y, color);
	}
	
	public void setDimensions(int posX, int posY, int sizeX, int sizeY) {
		positionPixel.x = posX;
		positionPixel.y = posY;
		tileSize.x = sizeX;
		tileSize.y = sizeY;
	}
}