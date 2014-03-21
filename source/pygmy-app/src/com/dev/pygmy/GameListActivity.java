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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.pygmy.game.GameHomePageActivity;

public class GameListActivity extends Activity {
	
	public final class GameHolder {
		public Integer id;
		public String name;
		public String info;
		public String fileName;
		public String version;
		public String image;
		public Integer minPlayers;
		public Integer maxPlayers;
	}

	private ListView listView;
	private List<GameHolder> games;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		games = new ArrayList<GameHolder>();

		// Creating list
		listView = (ListView) findViewById(R.id.list);

		new FetchGamesTask().execute();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(GameListActivity.this, GameHomePageActivity.class);
				GameHolder game = games.get(position);
				PygmyApp.logE("name : " + game.name);
				intent.putExtra("id", game.id);
				intent.putExtra("gameName", game.name);
				intent.putExtra("filename", game.fileName);
				intent.putExtra("version", game.version);
				intent.putExtra("image", game.image);
				intent.putExtra("minPlayer", game.minPlayers);
				intent.putExtra("maxPlayer", game.maxPlayers);
				startActivityForResult(intent, MainActivity.RC_SELECT_GAME);
			}
		});
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		PygmyApp.logE("request : "+ request);
		PygmyApp.logE("response : " + response);
		PygmyApp.logE("intent data list : " + data);
		switch (request) {
		case MainActivity.RC_SELECT_GAME:
			setResult(MainActivity.RC_SELECT_GAME, data);
			finish();
			break;
		}
	}

	private class FetchGamesTask extends AsyncTask<String, String, Void> {

		private InputStream is;
		private String result = "";

		@Override
		protected Void doInBackground(String... params) {
			String gamesListURL = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/gamesList.php";

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(gamesListURL);
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(param));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();  // Read content
			} catch (Exception e) {
				PygmyApp.logE("Error in HTTP connection: " + e.getMessage());
			}
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			} catch (Exception e) {
				PygmyApp.logE("Error converting result: " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			try {
				JSONArray array = new JSONArray(result);
				JSONObject json;
				GameHolder game;
				for (int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					game = new GameHolder();
					
					game.id = json.getInt("id_game");
					game.name = json.getString("name");
					game.info = json.getString("resume");
					game.fileName = json.getString("filename");
					game.version = json.getString("version");
					game.image = json.getString("image");
					game.minPlayers = json.getInt("min_player");
					game.maxPlayers = json.getInt("max_player");
					games.add(game);
				}
				
				GameListAdapter adapter = new GameListAdapter(GameListActivity.this, games);
				listView.setAdapter(adapter);
			} catch (Exception e) {
				PygmyApp.logE("Error parsing data: " + e.getMessage());
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			break;
		}
		return false;
	}
	
}