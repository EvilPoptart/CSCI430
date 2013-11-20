package com.csci430.anandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainMenu extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide title. Must be called before setContentView()
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main_menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	public void loadLevelMenu(View view) {
		Intent levelMenuIntent = new Intent(this, LevelMenu.class);
		this.startActivity(levelMenuIntent);
	}

	public void loadSettingsMenu(View view) {
		Intent settingsMenuIntent = new Intent(this, SettingsMenu.class);
		this.startActivity(settingsMenuIntent);
	}

}
