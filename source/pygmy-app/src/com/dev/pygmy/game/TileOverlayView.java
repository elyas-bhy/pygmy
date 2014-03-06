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

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * This class represents the view which shows the tile 
 * in which a entity is flying over.
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
	
	public void setDimensions(int posX, int posY, int sizeX, int sizeY) {
		tile.setDimensions(posX, posY, sizeX, sizeY);
	}
}