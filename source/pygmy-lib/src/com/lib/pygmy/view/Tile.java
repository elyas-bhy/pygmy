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

package com.lib.pygmy.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Tile {
	
	private Point position;
	private Point coordinates;
	private Paint color;
	private int width;
	private int height;
	
	/**
	 * Saves coordinates in pixels of a rectangular tile.
	 * @param posX	 position X in pixels
	 * @param posY   position Y in pixels
	 * @param width	 width of the tile
	 * @param height height of the tile
	 */
	public Tile(int posX, int posY, int width, int height) {
		this.coordinates = new Point(posX, posY);
		this.width = width;
		this.height = height;
	}
	
	public Tile(int posX, int posY, int size) {
		this(posX, posY, size, size);
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * @return coordinates (x,y) in pixels for a tile.
	 */
	public Point getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(int posX, int posY, int width, int height) {
		coordinates.x = posX;
		coordinates.y = posY;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the width of the tile.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height of the tile.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the dimensions of a tile with a rectangular shape.
	 */
	/*public Point getTileRectangleDimensions() {
		return size;
	}*/
	
	public void setColor(Paint color) {
		this.color = color;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(coordinates.x, coordinates.y,
				coordinates.x + width,
				coordinates.y + height,
				color);
	}
	
	public void drawOverlay(Canvas canvas) {
		Paint color = new Paint();
		color.setColor(Color.GREEN);
		color.setStrokeWidth(5);

		// Draw tile's outline
		// top
		canvas.drawLine(coordinates.x, coordinates.y, 
				coordinates.x + width, coordinates.y, color);
		// bottom
		canvas.drawLine(coordinates.x, coordinates.y + height,
				coordinates.x + width, coordinates.y + height, color);
		// left
		canvas.drawLine(coordinates.x, coordinates.y, 
				coordinates.x, coordinates.y + height, color);
		// right
		canvas.drawLine(coordinates.x + width, coordinates.y,
				coordinates.x + width, coordinates.y + height, color);
	}
	
}