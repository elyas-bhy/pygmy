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
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.pygmy.util.GameHolder;
import com.dev.pygmy.util.ImageDownloader;

public class GameListAdapter extends ArrayAdapter<GameHolder> {

	private final Activity activity;
	private List<GameHolder> games;

	private static class ViewHolder {
		TextView name;
		TextView desc;
		ImageView icon;
	}

	public GameListAdapter(Activity context, List<GameHolder> games) {
		super(context, R.layout.game_list_item, games);
		this.activity = context;
		this.games = games;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder viewHolder = null;

		if (row == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(R.layout.game_list_item, null, true);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) row.findViewById(R.id.game_name);
			viewHolder.desc = (TextView) row.findViewById(R.id.game_dev_descr);
			viewHolder.icon = (ImageView) row.findViewById(R.id.game_icon);
			row.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) row.getTag();
		}

		GameHolder game = games.get(position);
		viewHolder.name.setText(game.name);
		viewHolder.desc.setText(game.summary);

		URL imageUrl = null;
		try {
			imageUrl = new URL(game.image);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		ImageDownloader downloader = new ImageDownloader();
		downloader.download(imageUrl.toString(), viewHolder.icon);
		return row;
	}

}