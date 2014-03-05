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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.pygmy.R;
import com.dev.pygmy.SettingsActivity;

public class GameHomePageActivity extends Activity {

	private final String TAG = "Pygmy";
	private final static int TOAST_DELAY = 2000;

	private boolean rep = false;
	private TextView titleView, summaryView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		titleView = (TextView) findViewById(R.id.name_game);
		summaryView = (TextView) findViewById(R.id.name_resume);
		new GameFetchTask().execute();
	}

	private class GameFetchTask extends AsyncTask<String, String, Void> {
		
		private final String gamesInfoUrl = 
				"http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/gamesInfo.php";
		private final String reportUrl = 
				"http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/report.php";

		private InputStream is = null;
		private String result = "";
		private String gameName = "Pygmy Game";  // Replace with selected game
		
		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected Void doInBackground(String... params) {
			Spinner spin = (Spinner) findViewById(R.id.spinner);
			String item = spin.getSelectedItem().toString();

			HttpClient httpClient = new DefaultHttpClient();

			if (rep) {
				HttpPost httpPost = new HttpPost(reportUrl);
				ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("report", item));
				param.add(new BasicNameValuePair("name", gameName));

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(param));

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();

					// read content
					is = httpEntity.getContent();

				} catch (Exception e) {
					Log.e(TAG, "Error in HTTP connection: " + e.getMessage());
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
					Log.e(TAG, "Error converting result: " + e.getMessage());
				}
				
			} else {
				HttpPost httpPost = new HttpPost(gamesInfoUrl);

				ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("name", gameName));

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(param));

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();

					// Read content
					is = httpEntity.getContent();

				} catch (Exception e) {
					Log.e(TAG, "Error in HTTP connection: " + e.getMessage());
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
					Log.e(TAG, "Error converting result: " + e.getMessage());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			JSONObject json;
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					String title = json.getString("name");
					String resume = json.getString("resume");
					titleView.append(title + "\t\t" + "\n");
					summaryView.append(resume + "\t\t" + "\n");
				}

			} catch (Exception e) {
				Log.e(TAG, "Error parsing data: " + e.getMessage());
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.default_actionbar, menu);
		return true;
	}

	public void onPlayClicked(View view) {
		// TODO
	}

	public void onInviteClicked(View view) {
		// TODO
	}

	public void onRandomClicked(View view) {
		// TODO
	}

	public void onReportClicked(View view) {
		rep = true;
		new GameFetchTask().execute();
		Toast.makeText(this, "Report Done", TOAST_DELAY).show();
	}
}