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

import com.dev.pygmy.PygmyApp;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Worker thread responsible for fetching data of a specified game
 * and displaying it
 * @author Pygmy
 *
 */
public class GameFetchTask extends AsyncTask<String, String, String> {

	private final String databaseUrl;
	private final String gameName;
	private String title;
	private String summary;
	
	private TextView titleView, summaryView;
	private Spinner spinner;

	public GameFetchTask(String url, String game, Spinner sp) {
		this.databaseUrl = url;
		this.gameName = game;
		this.spinner = sp;
	}


	@Override
	protected String doInBackground(String... params) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(databaseUrl);
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

		if (spinner != null) {
			String item = spinner.getSelectedItem().toString();
			param.add(new BasicNameValuePair("report", item));
		}
		param.add(new BasicNameValuePair("name", gameName));

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
				title = json.getString("name");
				summary = json.getString("resume");
				titleView.setText(title);
				summaryView.setText(summary);
			}
		} catch (Exception e) {
			PygmyApp.logE("Error parsing data: " + e.getMessage());
		}
	}

}