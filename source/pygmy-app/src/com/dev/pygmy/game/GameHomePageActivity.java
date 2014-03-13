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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.pygmy.R;
import com.dev.pygmy.SettingsActivity;

public class GameHomePageActivity extends Activity {

	private final static int TOAST_DELAY = 2000;
	Spinner spin;

	Button button;

	public String gamesInfoUrl = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/gamesInfo.php";
	public String reportUrl = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/report.php";

	private TextView titleView, summaryView;

	int id;
	String gameName;
	String filename;
	String version;
	int minPlayer;
	int maxPlayer;

	String filePath;
	String destPath;
	String destPathVersion;
	
	String LAST_GAME = "Last_Game";

	boolean download = false;

	ProgressDialog dialog = null;

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
			minPlayer = extras.getInt("minPlayer");
			maxPlayer = extras.getInt("maxPlayer");
		}

		File checkFile = new File(getFilesDir().getPath() + "/" + gameName);
		File updateFileVersion = new File(getFilesDir().getPath() + "/"
				+ gameName + "/" + version);
		
		// check if the game is already on the device or not
		checkDownload(updateFileVersion, checkFile);

		spin = (Spinner) findViewById(R.id.spinner);
		titleView = (TextView) findViewById(R.id.name_game);
		summaryView = (TextView) findViewById(R.id.name_resume);

		new LoadDataFromDatabase(titleView, summaryView, gamesInfoUrl, gameName);
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

	public void onReportClicked(View view) {

		new LoadDataFromDatabase(spin, reportUrl, gameName);
		Toast.makeText(this, "Report Done", TOAST_DELAY).show();
	}

	public void onPlayDownloadClicked(View view) {
		if (!download) {
			// create a folder (gameName) in the pygmy files repository
			File gameFolder = new File(getFilesDir().getPath() + "/" + gameName);
			gameFolder.mkdirs();

			// create a folder to indicate the version of the game
			File versionFolder = new File(getFilesDir().getPath() + "/"
					+ gameName + "/" + version);
			versionFolder.mkdirs();

			// path of the file we want to download
			filePath = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/files/"
					+ gameName + "/" + filename;
			destPath = gameFolder + "/" + version + "/" + filename;

			// create an animation to show the progress of the download
			dialog = ProgressDialog.show(GameHomePageActivity.this, "",
					"Downloading game...", true);
			new Thread(new Runnable() {
				public void run() {
					downloadFile(filePath, destPath);
				}
			}).start();
			Toast.makeText(this, "Download Done", TOAST_DELAY).show();

			download = true;

			// change the visibility of the button
			button.setText("Play");
		} else {
			// if PLAY is pressed
			// TODO
			putGamePreferences();
			Toast.makeText(this, "PLAY event", TOAST_DELAY).show();
		}
	}

	// download .jar on the device
	public void downloadFile(String url, String dest) {
		try {
			// retrieve files .jar on the server with url and save this on the
			// device
			File dest_file = new File(dest);
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			int contentLength = conn.getContentLength();
			DataInputStream stream = new DataInputStream(u.openStream());
			byte[] buffer = new byte[contentLength];
			stream.readFully(buffer);
			stream.close();
			DataOutputStream fos = new DataOutputStream(new FileOutputStream(
					dest_file));
			fos.write(buffer);
			fos.flush();
			fos.close();

			hideProgressIndicator();

		} catch (FileNotFoundException e) {
			hideProgressIndicator();
			return;
		} catch (IOException e) {
			hideProgressIndicator();
			return;
		}
	}

	void hideProgressIndicator() {
		runOnUiThread(new Runnable() {
			public void run() {
				dialog.dismiss();
			}
		});
	}

	// check if the most recent version of the game is installed on the device
	public void checkDownload(File version, File gameFolder) {
		if (gameFolder.exists() && version.exists()) {
			download = true;
			button.setText("Play");
		} else if (gameFolder.exists() && !version.exists()) {
			deleteDirectory(gameFolder);
			download = false;
		} else {
			download = false;
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
	
	public void putGamePreferences(){
		SharedPreferences lastGame = getSharedPreferences (LAST_GAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = lastGame.edit();
		editor.putString(LAST_GAME,  gameName).commit();
		}
}

