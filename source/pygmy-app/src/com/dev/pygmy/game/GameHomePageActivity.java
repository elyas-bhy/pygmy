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
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.pygmy.MainActivity;
import com.dev.pygmy.PygmyApp;
import com.dev.pygmy.R;
import com.dev.pygmy.SettingsActivity;
import com.dev.pygmy.util.GameHolder;
import com.dev.pygmy.util.GamePreferences;
import com.dev.pygmy.util.ImageDownloader;
import com.dev.pygmy.util.Utils;

public class GameHomePageActivity extends Activity {

	private final static int TOAST_DELAY = 2000;

	private final String[] reportOptions = { " Offensive content",
			" Game not working ", " Incoherent content ", " Other " };
	
	private final String reportUrl = Utils.BASE_URL + "/scripts/report.php";
	private final String databaseUrl = Utils.BASE_URL + "/scripts/update.php";
	
	private Spinner spinner;
	private Button button;
	private AlertDialog reportDialog;
	
	private GameHolder mGame;
	private String latestVersion;
	private boolean downloaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		button = (Button) findViewById(R.id.play_downloadButton);
		mGame = new GameHolder();

		// Retrieve informations of the game selected
		Bundle extras = getIntent().getExtras();
		mGame.id = extras.getInt("id");
		mGame.name = extras.getString("gameName");
		mGame.summary = extras.getString("summary");
		mGame.filename = extras.getString("filename");
		mGame.version = extras.getString("version");
		mGame.image = extras.getString("image");
		mGame.minPlayers = extras.getInt("minPlayer");
		mGame.maxPlayers = extras.getInt("maxPlayer");

		new FetchUpdateTask().execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.set_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.set_report:
			onReportClick();
			return true;
		case android.R.id.home:
			setResult(RESULT_CANCELED);
			finish();
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

	public void onReportClicked(View view) {
		new GameFetchTask(reportUrl, mGame.name, spinner).execute();
		Toast.makeText(this, "Report sent", TOAST_DELAY).show();
	}

	public void onPlayDownloadClicked(View view) {
		if (!downloaded) {
			DownloadTask downloadtask = new DownloadTask(GameHomePageActivity.this);
			downloadtask.execute(mGame.name, mGame.version, mGame.filename);
			downloaded = true;
			button.setText("Play");
		} else {
			// Play is pressed
			putGamePreferences();
			Intent data = new Intent();
			data.putExtra(MainActivity.EXTRA_GAME_ID, mGame.name);
			data.putExtra(MainActivity.EXTRA_GAME_VERSION, mGame.version);
			setResult(MainActivity.RC_SELECT_GAME, data);
			finish();
		}
	}

	/**
	 * Checks if the most recent version of the game is installed on the device
	 */
	private void checkDownload() {
		File gameFolder = new File(Utils.getGamePath(this, mGame.name));
		File versionFolder = new File(Utils.getGamePath(this, mGame.name, mGame.version));
		
		if (gameFolder.exists() && versionFolder.exists() && mGame.version.equals(latestVersion)) {
			downloaded = true;
			button.setText("Play");
		} else {
			downloaded = false;
		}
	}
	
	// Save the games preferences on the device
	public void putGamePreferences() {
		GamePreferences previousGame = PygmyApp.persistence.getPreviousGame();
		PygmyApp.persistence.copyToLastGame(previousGame);
		previousGame.setId(mGame.id);
		previousGame.setName(mGame.name);
		previousGame.setSummary(mGame.summary);
		previousGame.setImage(mGame.image);
		previousGame.setVersion(mGame.version);
		previousGame.setFilename(mGame.filename);
		previousGame.setMinPlayers(mGame.minPlayers);
		previousGame.setMaxPlayers(mGame.maxPlayers);
	}
	
	private void onReportClick() {
		final ArrayList<Integer> selections = new ArrayList<Integer>();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Report: " + ((TextView) findViewById(R.id.name_game)).getText());
		builder.setMultiChoiceItems(reportOptions, null,
				new DialogInterface.OnMultiChoiceClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog,
							int indexSelected, boolean isChecked) {
						if (isChecked) {
							// If the user checked the item, add it to the
							// selected items
							selections.add(indexSelected);
						} else if (selections.contains(indexSelected)) {
							// Else, if the item is already in the array, remove it
							selections.remove(Integer.valueOf(indexSelected));
						}
					}
				})
				// Assign action buttons
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(GameHomePageActivity.this,
								"Report done", TOAST_DELAY).show();
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(GameHomePageActivity.this,
										"Cancel report", TOAST_DELAY).show();
							}
						});

		reportDialog = builder.create();
		reportDialog.show();
	}

	private class FetchUpdateTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(databaseUrl);
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

			param.add(new BasicNameValuePair("name", mGame.name));

			InputStream is = null;
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(param));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				// Read content
				is = httpEntity.getContent();
			} catch (Exception e) {
				PygmyApp.logE("Error in HTTP connection: " + e.getMessage());
			}

			String result = null;
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

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// Retrieve results of the PHP script
			JSONObject json;
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					latestVersion = json.getString("version");
				}
			} catch (Exception e) {
				PygmyApp.logE("Error parsing data: " + e.getMessage());
			}

			// Check if the game is already on the device or not
			checkDownload();

			((TextView) findViewById(R.id.name_game)).setText(mGame.name);
			((TextView) findViewById(R.id.name_resume)).setText(mGame.summary);

			ImageView gameIconImage = (ImageView) findViewById(R.id.logo_image_gamepage);
			URL imageUrl = null;
			try {
				imageUrl = new URL(mGame.image);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			ImageDownloader downloader = new ImageDownloader();
			downloader.download(imageUrl.toString(), gameIconImage);
		}
	}
	
}