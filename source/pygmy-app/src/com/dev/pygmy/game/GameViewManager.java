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

import android.app.Activity;
import android.widget.FrameLayout;

import com.dev.pygmy.R;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.util.TurnData;

/**
 * This class manages the different graphic layers of a game
 * @author Pygmy
 */
public class GameViewManager {
	
	private Activity activity;
	private PygmyGame game;
	private FrameLayout mainLayout;
	
	private GameBoardView gameBoardView;
	private EntityView entityView;
	private static TileOverlayView tileOverlayView;
	
	/**
	 * Constructs each of the different views
	 */
	public GameViewManager(Activity activity, PygmyGame game) {
		this.activity = activity;
		this.game = game;
		this.mainLayout = (FrameLayout) activity.findViewById(R.id.gameplay_layout);
		initViews();
	}
	
	/**
	 * Sets each future view used in the game. 
	 */
	private void initViews() {
		if (game != null) {
			game.initGame();
			game.start();
			
			gameBoardView = new GameBoardView(activity, game);
			tileOverlayView = new TileOverlayView(activity);
			entityView = new EntityView(activity, game);
		}
	}
	
	/**
	 * Updates the entityView state according to the passed response
	 */
	public void updateData(TurnData data) {
		mainLayout.removeAllViews();
		if (game != null) {
			entityView.updateData(data);
			mainLayout.addView(gameBoardView);
			mainLayout.addView(tileOverlayView);
			mainLayout.addView(entityView);
		} else {
			mainLayout.addView(activity.findViewById(R.id.creation_error));
		}
	}
	
	/**
	 * Clear the overlay
	 */
	public static void resetOverlay() {
		getOverlay().setCoordinates(0, 0, 0, 0);
		redrawOverlay();
	}
	
	/**
	 * Invalidates the overlay in order to redraw it
	 */
	public static void redrawOverlay() {
		tileOverlayView.invalidate();
	}
	
	/**
	 * Returns the overlay used to show the future position of an entity
	 */
	public static TileOverlayView getOverlay() {
		return tileOverlayView;
	}
	
}