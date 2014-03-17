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
import android.widget.Spinner;
import android.widget.TextView;

public class LoadDataFromDatabase extends AsyncTask<String, String, Void> {

	private final String databaseUrl;
	private final String gameName;
	private String title;
	private String summary;
	private String result = "";
	
	private InputStream is;
	private TextView titleView, summaryView;
	private Spinner spin;

	public LoadDataFromDatabase(Spinner sp, String url, String game) {
		this.spin = sp;
		this.databaseUrl = url;
		this.gameName = game;
	}

	public LoadDataFromDatabase(TextView title, TextView summary, String url,
			String game) {
		this.titleView = title;
		this.summaryView = summary;
		this.gameName = game;
		this.databaseUrl = url;
	}

	@Override
	protected Void doInBackground(String... params) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(databaseUrl);
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

		if (spin != null) {
			String item = spin.getSelectedItem().toString();
			param.add(new BasicNameValuePair("report", item));
		}

		// sent variable gameName to the PHP script
		param.add(new BasicNameValuePair("name", gameName));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			// read content
			is = httpEntity.getContent();

		} catch (Exception e) {
			PygmyApp.logE("Error in HTTP connection: " + e.getMessage());
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
			PygmyApp.logE("Error converting result: " + e.getMessage());
		}

		return null;
	}

	protected void onPostExecute(Void v) {
		
		// retrieve results of the script php 
		JSONObject json;
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				json = array.getJSONObject(i);
				title = json.getString("name");
				summary = json.getString("resume");
				titleView.append(title + "\t\t" + "\n");
				summaryView.append(summary + "\t\t" + "\n");
			}

		} catch (Exception e) {
			PygmyApp.logE("Error parsing data: " + e.getMessage());
		}
	}

}
