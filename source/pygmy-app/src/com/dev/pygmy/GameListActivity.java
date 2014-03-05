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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GameListActivity extends Activity {
	private ListView listView;

	// Example change that with DB games infos
	// Icon can be an URL, as use in profile picture.
	private String[] gameName = { "Jeux 1", "Jeux 2", "Jeux 3", "Jeux 4", 
			"Jeux 5", "Jeux 6", "Jeux 7" };
	
	private String[] info = { "By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can." };
	
	private Integer[] imageId = { R.drawable.logo_home_page, R.drawable.logo_home_page,
			R.drawable.logo_home_page, R.drawable.logo_home_page,
			R.drawable.logo_home_page, R.drawable.logo_home_page,
			R.drawable.logo_home_page };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);

		// Creating list
		GameListAdapter myadapter = new GameListAdapter(GameListActivity.this,
				gameName, imageId, info);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(myadapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Launch selected game
				Toast.makeText(GameListActivity.this, 
						"You clicked at " + gameName[+position], Toast.LENGTH_SHORT).show();
			}
		});
	}
}