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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.pygmy.PygmyApp;
import com.dev.pygmy.R;
import com.dev.pygmy.SettingsActivity;

public class GameHomePageActivity extends Activity {

	private final static int TOAST_DELAY = 2000;
	Spinner spin;

	public String gamesInfoUrl = 
			"http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/gamesInfo.php";
	public String reportUrl = 
			"http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/scripts/report.php";

	private TextView titleView, summaryView;
	
	String gameName;
	String filename;
	
	String filePath;
	String destPath;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Retrieve game selected in the list
		Bundle extras;

		if (savedInstanceState == null) {
			extras = getIntent().getExtras();
			if (extras == null) {
				gameName = null;
				filename = null;
			} else {
				gameName = extras.getString("gameName");
				filename = extras.getString("filename");
			}
		}
		
		
		filePath = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/files/"+filename;
		destPath = getApplicationContext().getFilesDir().getPath()+"/"+filename;

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

	public void onRandomClicked(View view) {
		new Thread(new Runnable() {
            public void run() {
            	
        		PygmyApp.logE("FILEPATH : "+filePath);
        		PygmyApp.logE("DESTPATH : "+destPath);
                 downloadFile(filePath, destPath);
            }
          }).start(); 
	}

	public void onReportClicked(View view) {

		new LoadDataFromDatabase(spin, reportUrl, gameName);
		Toast.makeText(this, "Report Done", TOAST_DELAY).show();
	}
	
	public void downloadFile(String url, String dest){
		 try {
           File dest_file = new File(dest);
           URL u = new URL(url);
           URLConnection conn = u.openConnection();
           int contentLength = conn.getContentLength();
           DataInputStream stream = new DataInputStream(u.openStream());
           byte[] buffer = new byte[contentLength];
           stream.readFully(buffer);
           stream.close();
           DataOutputStream fos = new DataOutputStream(new FileOutputStream(dest_file));
           fos.write(buffer);
           fos.flush();
           fos.close();
            
       } catch(FileNotFoundException e) {
           
           return; 
       } catch (IOException e) {
           
           return; 
       }
	}
}