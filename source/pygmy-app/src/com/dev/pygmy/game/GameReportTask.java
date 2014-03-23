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
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dev.pygmy.PygmyApp;
import com.dev.pygmy.util.Utils;

/**
 * Worker thread responsible for submitting a game report
 * @author Pygmy
 *
 */
public class GameReportTask extends AsyncTask<String, String, String> {
	
	private final String REPORT_URL = Utils.BASE_URL + "/scripts/report.php";

	private Context mContext;
	private List<String> mReasons;

	public GameReportTask(Context context, List<String> reasons) {
		mContext = context;
		mReasons = reasons;
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(REPORT_URL);
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		
		param.add(new BasicNameValuePair("report", mReasons.get(0)));
		param.add(new BasicNameValuePair("name", params[0]));

		InputStream is = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
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
		Toast.makeText(mContext, "Report submitted", Toast.LENGTH_SHORT).show();
	}

}