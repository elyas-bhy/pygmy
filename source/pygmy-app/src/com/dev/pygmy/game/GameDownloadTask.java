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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dev.pygmy.util.Utils;

/**
 * Worker thread responsible for downloading the binaries
 * of the specified game
 * @author Pygmy
 *
 */
public class GameDownloadTask extends AsyncTask<String, Void, Void> {

	private final static int TOAST_DELAY = 2000;
	
	private OnPostExecutor onPostExecutor;
	private ProgressDialog mDialog;
	private Context mContext;

	public GameDownloadTask(Context context) {
		mContext = context;
	}

	@Override
	protected void onPreExecute() {
		// Create an animation to show the progress of the download
		mDialog = ProgressDialog.show(mContext, "", "Downloading game...", true);
	}

	@Override
	protected Void doInBackground(String... params) {
		String gameName = params[0];
		String version = params[1];
		String filename;
		if (params.length == 3) {
			filename = params[2];
		} else {
			filename = "game.jar";
		}
		
		// Create a folder (gameName) in the pygmy files repository
		File gameFolder = new File(Utils.getGamePath(mContext, gameName));
		gameFolder.mkdirs();

		// Create a folder to indicate the version of the game
		File versionFolder = new File(Utils.getGamePath(mContext, gameName, version));
		versionFolder.mkdirs();
		
		try {
			// Retrieve .jar files on the server with url and save this on
			// the device
			URL u = new URL(getGameUrl(gameName, filename));
			URLConnection conn = u.openConnection();
			int contentLength = conn.getContentLength();
			DataInputStream stream = new DataInputStream(u.openStream());
			byte[] buffer = new byte[contentLength];
			stream.readFully(buffer);
			stream.close();
			
			File destFile = new File(Utils.getGamePath(mContext, gameName, version, filename));
			DataOutputStream fos = new DataOutputStream(new FileOutputStream(destFile));
			fos.write(buffer);
			fos.flush();
			fos.close();

			mDialog.dismiss();
		} catch (FileNotFoundException e) {
			mDialog.dismiss();
		} catch (IOException e) {
			mDialog.dismiss();
		}
		return null;
	}
	
	public interface OnPostExecutor {
		public void onPostExecute();
	}

	@Override
	protected void onPostExecute(Void result) {
		Toast.makeText(mContext, "Download done", TOAST_DELAY).show();
		if (onPostExecutor != null) {
			onPostExecutor.onPostExecute();
		}
	}
	
	private String getGameUrl(String gameName, String filename) {
		return Utils.BASE_URL + "/files/" + gameName + "/" + filename;
	}

	public void setOnPostExecutor(OnPostExecutor onPostExecutor) {
		this.onPostExecutor = onPostExecutor;
	}
	
}