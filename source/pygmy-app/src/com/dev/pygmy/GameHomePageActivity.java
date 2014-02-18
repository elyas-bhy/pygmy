/**
 * 
 */
package com.dev.pygmy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GameHomePageActivity extends Activity {

	ListView lvList;
	final static int TOAST_DELAY = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamehomepage);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		((TextView) findViewById(R.id.name_game)).setText("Game Test");
		((TextView) findViewById(R.id.name_resume)).setText("Game Resume Test");

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
		Spinner spin = (Spinner) findViewById(R.id.spinner);
		String valToSet = spin.getSelectedItem().toString();

		Context context = getApplicationContext();

		Toast toast = Toast.makeText(context, valToSet, TOAST_DELAY);
		toast.show();
	}
}
