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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.pygmy.PygmyApp;
import com.dev.pygmy.game.GameHomePageActivity;

public class GameListActivity extends Activity {

	private ListView listView;

	ArrayList<String> gameName = new ArrayList<String>();
	ArrayList<String> info = new ArrayList<String>();
	ArrayList<String> imageId = new ArrayList<String>();
	ArrayList<String> fileName = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);

		// Creating list
		listView = (ListView) findViewById(R.id.list);

		new getServerListGame().execute();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getApplicationContext(),
						GameHomePageActivity.class);
				intent.putExtra("gameName", gameName.get(+position));
				intent.putExtra("filename", fileName.get(+position));
				startActivity(intent);
			}
		});
	}

	class getServerListGame extends AsyncTask<String, String, Void> {

		InputStream is = null;
		String result = "";

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

				// read content
				is = httpEntity.getContent();

			} catch (Exception e) {

				PygmyApp.logE("Error in http connection " + e.toString());
			}
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();

			} catch (Exception e) {
				PygmyApp.logE("Error converting result " + e.toString());
			}

			return null;

		}

		protected void onPostExecute(Void v) {

			try {
				JSONArray Jarray = new JSONArray(result);
				for (int i = 0; i < Jarray.length(); i++) {
					JSONObject Jasonobject = null;
					Jasonobject = Jarray.getJSONObject(i);

					String title = Jasonobject.getString("name");
					gameName.add(title);

					String resume = Jasonobject.getString("resume");
					info.add(resume);
					
					String file = Jasonobject.getString("filename");
					fileName.add(file);

					GameListAdapter adapter = new GameListAdapter(
							GameListActivity.this, gameName, info, imageId);

					listView.setAdapter(adapter);
				}

			} catch (Exception e) {
				PygmyApp.logE("Error parsing data " + e.toString());
			}
		}
	}

}