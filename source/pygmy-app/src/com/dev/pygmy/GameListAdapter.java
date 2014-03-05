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

package com.dev.pygmy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameListAdapter extends ArrayAdapter<String> {
	
	private final Activity activity;
	private final String[] gameName;
	private final String[] gamesInfo;
	private final Integer[] gameIcon;

	public GameListAdapter(Activity context, String[] web, Integer[] gameIcon,
			String[] gamesInfo) {
		super(context, R.layout.game_list_item, web);
		this.activity = context;
		this.gameName = web;
		this.gameIcon = gameIcon;
		this.gamesInfo = gamesInfo;

	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.game_list_item, null, true);

		// Getting view ids
		TextView gameNameText = (TextView) row.findViewById(R.id.game_name);
		TextView gameInfoText = (TextView) row.findViewById(R.id.game_dev_descr);
		ImageView gameIconImage = (ImageView) row.findViewById(R.id.game_icon);

		// Setting infos on views
		gameNameText.setText(gameName[pos]);
		gameIconImage.setImageResource(gameIcon[pos]);
		gameInfoText.setText(gamesInfo[pos]);
		return row;
	}
}