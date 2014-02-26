package com.dev.pygmy;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@SuppressLint("ResourceAsColor")
public class GameBoardInterfaceActivity extends Activity {

	static final String TAG = "Pygmy";
	
	private GameBoardView gameBoardView = null;
	private EntityView entityView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FrameLayout mainLayout = createMainLayout();
		setContentView(mainLayout);
		
		gameBoardView = new GameBoardView(getApplicationContext());
		mainLayout.addView(gameBoardView);
		
		entityView = new EntityView(this);
		mainLayout.addView(entityView);
	}
	
	private FrameLayout createMainLayout() {
		FrameLayout mainLayout = new FrameLayout(this);
		LayoutParams gerenalLayoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mainLayout.setLayoutParams(gerenalLayoutParams);
		mainLayout.setBackgroundColor(R.color.blue);
		
		return mainLayout;
	}
}