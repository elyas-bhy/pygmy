/**
 * 
 */
package com.dev.pygmy;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.dev.pygmy.util.PrefFragment;


public class SettingsActivity extends Activity {
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Display the fragment as the main content.
	        getFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new PrefFragment())
	                .commit();
	        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	 }
	 
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
				case android.R.id.home:
					finish();
					return true;
				default:
	    			return super.onOptionsItemSelected(item);
			}
		}
}
