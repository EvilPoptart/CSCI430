package com.csci430.anandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LevelMenu extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	Log.d("Startup", "LevelMenu");

    	// Hide title. Must be called before setContentView()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_level_menu);
		
		// TODO: remove these. They were added by the activity creation script.
		//final View controlsView = findViewById(R.id.fullscreen_content_controls);
		//final View contentView = findViewById(R.id.fullscreen_content);

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.level_1);
		findViewById(R.id.level_2);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	
	public void loadLevelOne(View view) {
		Intent levelOneIntent = new Intent(this, Level.class);
		levelOneIntent.putExtra("levelId", "1"); 
		this.startActivity(levelOneIntent);
	}
	
	public void loadLevelTwo(View view) {
		Intent levelTwoIntent = new Intent(this, Level.class);
		levelTwoIntent.putExtra("levelId", "2"); 
		this.startActivity(levelTwoIntent);
	}

}
