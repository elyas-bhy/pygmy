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

package com.dev.pygmy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.lib.pygmy.Tile;

/**
 * This class is responsible for drawing an overlay over a hovered tile
 * @author Pygmy
 * 
 */
public class TileOverlayView extends View {
	
	private Integer saved;
	private Tile tile;

	/**
	 * Constructs the view with the active tile.
	 * @param context		Context parent
	 */
	public TileOverlayView(Context context) {
		super(context);
		tile = new Tile(0,0,0,0);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if (saved == null) {
			saved = canvas.save();
		} else {
			canvas.restore();
		}
		
		if (tile != null) {
			tile.drawOverlay(canvas);
		}
	}
	
	/**
	 * Sets new coordinates and size, whole in pixels, for the tile on the board 	
	 */
	public void setCoordinates(int posX, int posY, int sizeX, int sizeY) {
		tile.setCoordinates(posX, posY, sizeX, sizeY);
	}
}