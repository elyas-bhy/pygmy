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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.lib.pygmy.util.Point;

/**
 * Represents a gameboard tile. This class stores both absolute position (in pixels),
 * and relative position (relative to the board) of the tile.
 * @author Pygmy
 *
 */
public class Tile implements Serializable {
	
	private static final long serialVersionUID = 700976869561391811L;
	
	/**
	 * Relative position (relative to the board)
	 */
	private Point position;
	
	/**
	 * Absolute position (in pixels)
	 */
	private Point coordinates;
	
	private transient Paint color;
	private int width;
	private int height;
	
	public Tile(int posX, int posY, int width, int height) {
		this.coordinates = new Point(posX, posY);
		this.width = width;
		this.height = height;
	}
	
	public Tile(int posX, int posY, int size) {
		this(posX, posY, size, size);
	}
	
	/**
	 * Returns the relative position of the tile
	 * @return
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Sets the relative position of the tile
	 * @param position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * Returns the absolute position of the tile
	 * @return
	 */
	public Point getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Sets the absolute position of the tile
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 */
	public void setCoordinates(int posX, int posY, int width, int height) {
		setCoordinates(posX, posY);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets the absolute position of the tile
	 * @param posX
	 * @param posY
	 */
	public void setCoordinates(int posX, int posY) {
		coordinates.x = posX;
		coordinates.y = posY;
	}

	/**
	 * Returns the width of the tile
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the tile
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the tile's color
	 * @param color
	 */
	public void setColor(Paint color) {
		this.color = color;
	}
	
	/**
	 * Draws the tile on the specified canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		canvas.drawRect(coordinates.x, coordinates.y,
				coordinates.x + width,
				coordinates.y + height,
				color);
	}
	
	/**
	 * Draws the tile's overlay on the specified canvas
	 * @param canvas
	 */
	public void drawOverlay(Canvas canvas) {
		Paint color = new Paint();
		color.setColor(Color.GREEN);
		color.setStrokeWidth(5);
		
		// Top
		canvas.drawLine(coordinates.x, coordinates.y, 
				coordinates.x + width, coordinates.y, color);
		// Bottom
		canvas.drawLine(coordinates.x, coordinates.y + height,
				coordinates.x + width, coordinates.y + height, color);
		// Left
		canvas.drawLine(coordinates.x, coordinates.y, 
				coordinates.x, coordinates.y + height, color);
		// Right
		canvas.drawLine(coordinates.x + width, coordinates.y,
				coordinates.x + width, coordinates.y + height, color);
	}
	
}