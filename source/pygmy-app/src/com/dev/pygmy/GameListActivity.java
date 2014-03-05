package com.dev.pygmy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GameListActivity extends Activity {
	ListView game_list;
	
	//Example change that with BD games infos
	//Icon can be an URL, as use in profile picture.
	String[] game_name = { "Jeux 1", "Jeux 2", "Jeux 3", "Jeux 4", "Jeux 5",
			"Jeux 6", "Jeux 7" };
	String[] info = { "By Bastien : Cliking as many as you can.", 
			"By Bastien : Cliking as many as you can.", 
			"By Bastien : Cliking as many as you can.", 
			"By Bastien : Cliking as many as you can.", 
			"By Bastien : Cliking as many as you can.",
			"By Bastien : Cliking as many as you can.", 
			"By Bastien : Cliking as many as you can." };
	Integer[] imageId = { R.drawable.logo_home_page, R.drawable.logo_home_page,
			R.drawable.logo_home_page, R.drawable.logo_home_page,
			R.drawable.logo_home_page, R.drawable.logo_home_page,
			R.drawable.logo_home_page };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		
		//Creating list
		ListCreator myadapter = new ListCreator(GameListActivity.this, game_name, imageId,info);
		game_list = (ListView) findViewById(R.id.list);
		game_list.setAdapter(myadapter);
		
		
		game_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//TODO:: Change by starting activing game_page with the game clicked
				Toast.makeText(GameListActivity.this,
						"You Clicked at " + game_name[+position], Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}