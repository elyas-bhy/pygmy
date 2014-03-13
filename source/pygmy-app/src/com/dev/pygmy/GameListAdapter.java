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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.dev.pygmy.util.ImageDownloader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameListAdapter extends ArrayAdapter<String> {
	
	private final Activity activity;
	ArrayList<String> gamesName;
	ArrayList<String> gamesInfo;
	ArrayList<String> gamesIcon;
	
	public GameListAdapter(Activity context,
			ArrayList<String> gameName, ArrayList<String> gamesInfo, ArrayList<String> gameIcon) {
			super(context, R.layout.game_list_item, gameName);
			this.activity = context;
			this.gamesName = gameName;
			this.gamesInfo = gamesInfo;
			this.gamesIcon = gameIcon;
	}


	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.game_list_item, null, true);

		// Getting view ids
		TextView gameNameText = (TextView) row.findViewById(R.id.game_name);
		TextView gameInfoText = (TextView) row.findViewById(R.id.game_dev_descr);
		
		// If we want to add icon to a game
		final ImageView gameIconImage = (ImageView) row.findViewById(R.id.game_icon);

		// Setting infos on views
		gameNameText.setText(gamesName.get(pos));
		gameInfoText.setText(gamesInfo.get(pos));
		
		// If we want to add icon to a game
				URL imageUrl = null;
				try {
					imageUrl = new URL(gamesIcon.get(pos));

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				
				ImageDownloader mDownload = new ImageDownloader();
				mDownload.download(imageUrl.toString(), gameIconImage);
	
		return row;
	}
}