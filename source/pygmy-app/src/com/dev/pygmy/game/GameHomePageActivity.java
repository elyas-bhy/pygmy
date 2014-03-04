/**
 * 
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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.pygmy.R;
import com.dev.pygmy.SettingsActivity;


public class GameHomePageActivity extends Activity {

	ListView lvList;
	final static int TOAST_DELAY = 2000;
	TextView titleV, resumeV;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		new getServerDataGame().execute();
	}
	
	class getServerDataGame extends AsyncTask<String, String, Void>{

	    InputStream is = null ;
	    String result = "";
	    
	    // TEST AFFICHAGE UN JEU EN PARTICULIER
	    int i = 1;
	    String id_game = String.valueOf(i);
	    
		@Override
		protected Void doInBackground(String... params) {
			String gamesListURL = 
					"http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/gamesInfo.php";
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(gamesListURL);

		        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		        param.add(new BasicNameValuePair("id_game", id_game));

			    try {
				httpPost.setEntity(new UrlEncodedFormEntity(param));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				//read content
				is =  httpEntity.getContent();					

				} catch (Exception e) {

				Log.e("log_tag", "Error in http connection "+e.toString());
				}
			try {
			    BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line=br.readLine())!=null)
				{
				   sb.append(line+"\n");
				}
					is.close();
					result=sb.toString();				

						} catch (Exception e) {
							Log.e("log_tag", "Error converting result "+e.toString());
						}

					return null;

				}
		
		protected void onPostExecute(Void v) {

			try {
				JSONArray Jarray = new JSONArray(result);
				for(int i=0;i<Jarray.length();i++)
				{
				JSONObject Jasonobject = null;
				titleV = (TextView)findViewById(R.id.name_game);
				resumeV = (TextView)findViewById(R.id.name_resume);
				Jasonobject = Jarray.getJSONObject(i);

				String title = Jasonobject.getString("name");
				String resume = Jasonobject.getString("resume");
			    titleV.append(title+"\t\t"+"\n");
			    resumeV.append(resume+"\t\t"+"\n");

				}

			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data "+e.toString());
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
		// TO DO
	}

	public void onInviteClicked(View view) {
		// TO DO
	}

	public void onRandomClicked(View view) {
		// TO DO
	}

	public void onReportClicked(View view) {
		// TO DO
	}
}