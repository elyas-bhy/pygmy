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

import java.lang.reflect.Constructor;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.client.pygmy.PygmyGameImpl;
import com.dev.pygmy.R;

/**
 * This class represents the whole game board including their pieces/entities.
 */
public class GameBoardInterfaceActivity extends Activity {

	static final String TAG = "GameBoardInterfaceActivity";

	private GameBoardView gameBoardView = null;
	private EntityView entityView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout mainLayout = createMainLayout();
		setContentView(mainLayout);

		PygmyGameImpl game = null;
		try {
			Class<?> clazz = Class.forName("com.client.pygmy.PygmyGameImpl");
			Constructor<?> constructor = clazz.getConstructor(Resources.class);
			game = (PygmyGameImpl) constructor.newInstance(getResources());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		// Gets parameters of the game board.
		if (game != null) {
			game.initGame();
			game.start();
			Map<String, Object> gameParams = game.getParameters();

			gameBoardView = new GameBoardView(getApplicationContext(), gameParams);
			mainLayout.addView(gameBoardView);
			
			entityView = new EntityView(this, game);
			mainLayout.addView(entityView);
		} else {
			mainLayout.addView(findViewById(R.id.creation_error));
		}
	}

	private FrameLayout createMainLayout() {
		FrameLayout mainLayout = new FrameLayout(this);
		LayoutParams gerenalLayoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mainLayout.setLayoutParams(gerenalLayoutParams);
		mainLayout.setBackgroundColor(getResources().getColor(R.color.grey_metal));

		return mainLayout;
	}
}