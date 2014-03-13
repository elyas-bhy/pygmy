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

import java.util.Map;

import android.app.Activity;
import android.widget.FrameLayout;

import com.dev.pygmy.R;
import com.dev.pygmy.util.TurnData;
import com.lib.pygmy.PygmyGame;

public class GameViewManager {
	
	private Activity context;
	private PygmyGame game;
	private FrameLayout mainLayout;
	
	private GameBoardView gameBoardView;
	private EntityView entityView;
	private static TileOverlayView tileOverlayView;
	
	public GameViewManager(Activity context, PygmyGame game) {
		this.context = context;
		this.game = game;
		this.mainLayout = (FrameLayout) context.findViewById(R.id.gameplay_layout);
		initViews();
	}
	
	private void initViews() {
		if (game != null) {
			game.initGame();
			game.start();
			Map<String, Object> gameParams = game.getParameters();

			gameBoardView = new GameBoardView(context, gameParams);
			tileOverlayView = new TileOverlayView(context);
			entityView = new EntityView(context, game);
		}
	}
	
	public void updateData(TurnData data) {
		mainLayout.removeAllViews();
		if (game != null) {
			entityView.updateData(data);
			mainLayout.addView(gameBoardView);
			mainLayout.addView(tileOverlayView);
			mainLayout.addView(entityView);
		} else {
			mainLayout.addView(context.findViewById(R.id.creation_error));
		}
	}
	
	public static void resetOverlay() {
		getOverlay().setCoordinates(0, 0, 0, 0);
		redrawOverlay();
	}
	
	public static void redrawOverlay() {
		tileOverlayView.invalidate();
	}
	
	public static TileOverlayView getOverlay() {
		return tileOverlayView;
	}
	
}