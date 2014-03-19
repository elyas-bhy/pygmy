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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.pygmy.MainActivity;
import com.dev.pygmy.R;
import com.dev.pygmy.SettingsActivity;
import com.dev.pygmy.util.ImageDownloader;
import com.dev.pygmy.util.Utils;

public class GameHomePageActivity extends Activity {

	private final static int TOAST_DELAY = 2000;

	private final String BASE_URL = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper";
	private String gamesInfoUrl = BASE_URL + "/scripts/gamesInfo.php";
	private String reportUrl = BASE_URL + "/scripts/report.php";

	private Spinner spinner;
	private Button button;
	
	private TextView titleView, summaryView;

	private String LAST_GAME = "Last_Game";
	private String IMAGE = "Icon";
	private String PREVIOUS_LAST_GAME = "Previous_Last_Game";
	private String DEFAULT_IMAGE = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/gamesImages/Default/logo_home_page.png";

	private int id;
	private boolean downloaded = false;
	private String gameName;
	private String filename;
	private String version;
	private String image;
	private int minPlayer;
	private int maxPlayer;

	private String previousGame;
	private String previousImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		button = (Button) findViewById(R.id.play_downloadButton);

		// Retrieve informations of the game selected
		Bundle extras;
		if (savedInstanceState == null) {
			extras = getIntent().getExtras();
			id = extras.getInt("id");
			gameName = extras.getString("gameName");
			filename = extras.getString("filename");
			version = extras.getString("version");
			image = extras.getString("image");
			minPlayer = extras.getInt("minPlayer");
			maxPlayer = extras.getInt("maxPlayer");
		}

		SharedPreferences previousLastGame = getSharedPreferences(LAST_GAME,
				MODE_PRIVATE);
		previousGame = previousLastGame.getString(LAST_GAME, "Never play before");
		previousImage = previousLastGame.getString(IMAGE, DEFAULT_IMAGE);

		// Check if the game is already on the device or not
		checkDownload();

		spinner = (Spinner) findViewById(R.id.spinner);
		titleView = (TextView) findViewById(R.id.name_game);
		summaryView = (TextView) findViewById(R.id.name_resume);
		
		ImageView gameIconImage = (ImageView) findViewById(R.id.logo_image_gamepage);
		URL imageUrl = null;
		try {
			imageUrl = new URL(image);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		ImageDownloader downloader = new ImageDownloader();
		downloader.download(imageUrl.toString(), gameIconImage);

		new LoadDataFromDatabase(titleView, summaryView, gamesInfoUrl, gameName).execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
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
		new LoadDataFromDatabase(spinner, reportUrl, gameName).execute();
		Toast.makeText(this, "Report Done", TOAST_DELAY).show();
	}

	public void onPlayDownloadClicked(View view) {
		if (!downloaded) {
			DownloadTask downloadtask = new DownloadTask(GameHomePageActivity.this);
			downloadtask.execute(gameName, version, filename);
			downloaded = true;
			button.setText("Play");
		} else {
			// Play is pressed
			putGamePreferences();
			Intent data = new Intent();
			data.putExtra(MainActivity.EXTRA_GAME_ID, gameName);
			data.putExtra(MainActivity.EXTRA_GAME_VERSION, version);
			setResult(MainActivity.RC_SELECT_GAME, data);
			finish();
		}
	}


	// check if the most recent version of the game is installed on the device
	private void checkDownload() {
		File gameFolder = new File(Utils.getGamePath(this, gameName));
		File versionFolder = new File(Utils.getGamePath(this, gameName, version));

		if (gameFolder.exists() && versionFolder.exists()) {
			downloaded = true;
			button.setText("Play");
		} else if (gameFolder.exists() && !versionFolder.exists()) {
			deleteDirectory(gameFolder);
			downloaded = false;
		} else {
			downloaded = false;
		}
	}

	// delete old version of a game
	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	public void putGamePreferences() {
		SharedPreferences lastGame = getSharedPreferences(LAST_GAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = lastGame.edit();
		editor.putString(LAST_GAME, gameName).commit();
		editor.putString(IMAGE, image).commit();

		SharedPreferences previousLastGame = getSharedPreferences(PREVIOUS_LAST_GAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor2 = previousLastGame.edit();
		editor2.putString(PREVIOUS_LAST_GAME, previousGame).commit();
		editor2.putString(IMAGE, previousImage).commit();
	}
	
}