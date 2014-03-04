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

import android.graphics.Bitmap;

/**
 * Interface of board game pieces (entities)
 * @author Pygmy
 */
public interface Entity {
	
	/**
	 * @return the initial position of the entity on the board.
	 */
	public int[] getBoundingPosition();

	/**
	 * Sets the X position of the image of the entity on the board 
	 * @param posX
	 */
	public abstract void setX(int posX);

	/**
	 * Returns the X position of the entity
	 */
	public abstract int getX();

	/**
	 * Sets the Y position of the image of the entity on the board 
	 * @param posY
	 */
	public abstract void setY(int posY);

	/**
	 * Returns the Y position of the entity
	 */
	public abstract int getY();

	/**
	 * Returns the identifier of the entity
	 */
	public abstract int getId();

	/**
	 * Returns the image file of the entity
	 */
	public abstract Bitmap getBitmap();

}