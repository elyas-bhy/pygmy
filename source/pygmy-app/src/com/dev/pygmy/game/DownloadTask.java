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

public class DownloadTask extends AsyncTask<String, Void, Void> {
	
	private final static int TOAST_DELAY = 2000;
	private ProgressDialog dialog;
	private Context mContext;
	
	public DownloadTask(Context context){
		this.mContext=context;
	}
	
		@Override
		protected void onPreExecute() {
			// Create an animation to show the progress of the download
			dialog = ProgressDialog.show(mContext, "",
					"Downloading game...", true);
		}

		@Override
		protected Void doInBackground(String... urls) {
			String url = urls[0];
			String dest = urls[1];
			try {
				// Retrieve .jar files on the server with url and save this on
				// the
				// device
				File destFile = new File(dest);
				URL u = new URL(url);
				URLConnection conn = u.openConnection();
				int contentLength = conn.getContentLength();
				DataInputStream stream = new DataInputStream(u.openStream());
				byte[] buffer = new byte[contentLength];
				stream.readFully(buffer);
				stream.close();
				DataOutputStream fos = new DataOutputStream(
						new FileOutputStream(destFile));
				fos.write(buffer);
				fos.flush();
				fos.close();

				dialog.dismiss();
			} catch (FileNotFoundException e) {
				dialog.dismiss();
			} catch (IOException e) {
				dialog.dismiss();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(mContext, "Download Done",
					TOAST_DELAY).show();
	
		}
	}