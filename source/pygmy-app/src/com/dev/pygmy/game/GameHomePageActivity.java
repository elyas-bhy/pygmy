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
import android.os.Bundle;
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

	Button dwnld;
	Button play;

	public String gamesInfoUrl = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/gamesInfo.php";
	public String reportUrl = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/report.php";

	private TextView titleView, summaryView;

	int id;
	String gameName;
	String filename;
	int version;

	String filePath;
	String destPath;
	String destPathVersion;

	ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dwnld = (Button) findViewById(R.id.downloadButton);
		play = (Button) findViewById(R.id.playButton);

		// Retrieve informations of the game selected
		Bundle extras;

		if (savedInstanceState == null) {
			extras = getIntent().getExtras();
			if (extras == null) {
				id = 0;
				gameName = null;
				filename = null;
				version = 0;
			} else {
				id = extras.getInt("id");
				gameName = extras.getString("gameName");
				filename = extras.getString("filename");
				version = extras.getInt("version");
			}
		}

		File checkFile = new File(getFilesDir().getPath() + "/" + gameName);
		File updateFileVersion = new File(getFilesDir().getPath() + "/"
				+ gameName + "/" + version);
		checkDownload(checkFile);
		updateVersion(updateFileVersion, checkFile);

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

	public void onPlayClicked(View view) {
		// TODO
	}

	public void onInviteClicked(View view) {
		// TODO
	}

	public void onDownloadClicked(View view) {

		// create a folder (gameName) in the pygmy files repository
		File gameFolder = new File(getFilesDir().getPath() + "/" + gameName);
		gameFolder.mkdirs();

		// create a folder to indicate the version of the game
		File versionFolder = new File(getFilesDir().getPath() + "/" + gameName
				+ "/" + version);
		versionFolder.mkdirs();

		// path of the file we want to download
		filePath = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/files/"
				+ gameName + "/" + filename;
		destPath = gameFolder + "/" + filename;

		// create an animation to show the progress of the download
		dialog = ProgressDialog.show(GameHomePageActivity.this, "",
				"Downloading game...", true);
		new Thread(new Runnable() {
			public void run() {
				downloadFile(filePath, destPath);
			}
		}).start();
		Toast.makeText(this, "Download Done", TOAST_DELAY).show();

		// change the visibility of the buttons
		play.setEnabled(true);
		play.isClickable();
		dwnld.setVisibility(View.GONE);
	}

	public void onReportClicked(View view) {

		new LoadDataFromDatabase(spin, reportUrl, gameName);
		Toast.makeText(this, "Report Done", TOAST_DELAY).show();
	}

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

	// check if the game is already installed on the device
	public void checkDownload(File game) {
		if (game.exists()) {
			play.setEnabled(true);
			play.isClickable();
			dwnld.setVisibility(View.GONE);
		} else {

			play.setEnabled(false);
			dwnld.setVisibility(View.VISIBLE);
		}
	}

	// check if the most recent version of the game is installed on the device
	public void updateVersion(File version, File gameFolder) {
		if (version.exists()) {
			play.setEnabled(true);
			play.isClickable();
			dwnld.setVisibility(View.GONE);
		} else {
			deleteDirectory(gameFolder);
			play.setEnabled(false);
			dwnld.setVisibility(View.VISIBLE);

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
}