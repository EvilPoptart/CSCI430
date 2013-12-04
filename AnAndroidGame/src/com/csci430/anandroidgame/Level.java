package com.csci430.anandroidgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Level extends Activity {
	protected static Context context = null;
	public Button buttonJump1;
	public Button buttonJump2;
	public Button buttonLeft;
	public Button buttonRight;

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("KillPlayer", "Level Activity Resumed");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("KillPlayer", "Level Activity Paused");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("KillPlayer", "Level Activity Stopped");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("KillPlayer", "Level Activity Destroyed");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("KillPlayer", "Level Activity Destroyed");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call the function onCreate() from the class we're extending
		// (Activity)
		super.onCreate(savedInstanceState);
		Log.d("KillPlayer", "Level Activity Created");

		Global.curLevelId = Integer.parseInt(getIntent().getStringExtra(
				"levelId"));

		// Hide title. Must be called before setContentView()
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Tell system to use the layout defined in our XML file.
		if (Global.curLevelId == 0) {
			// Tutorial activity has visible buttons
			setContentView(R.layout.activity_level_tutorial);
		} else {
			// Normal level activity has INvisible buttons
			setContentView(R.layout.activity_level);
		}

		// Used in screen Sizes throughout the application
		GameThread.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(GameThread.metrics);

		// Define our buttons
		buttonJump1 = (Button) findViewById(R.id.buttonJump1);
		buttonJump2 = (Button) findViewById(R.id.buttonJump2);
		buttonRight = (Button) findViewById(R.id.buttonRight);
		buttonLeft = (Button) findViewById(R.id.buttonLeft);
		final MediaPlayer jumpsound = MediaPlayer.create(this, R.raw.jump);

		/*
		 * Handles player jumping (upper left)
		 */
		buttonJump1.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (Global.levelCompleted) {
						// Stop Game
						GameThread.setRunning(false);
						loadVictoryMenu();
					}
					jumpsound.start();
					GameThread.jump();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// Do nothing
				}
				return false;
			}
		});

		/*
		 * Handles player jumping (upper right)
		 */
		buttonJump2.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (Global.levelCompleted) {
						// Stop Game
						GameThread.setRunning(false);
						loadVictoryMenu();
					}
					jumpsound.start();
					GameThread.jump();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// Do nothing
				}
				return false;
			}
		});

		/*
		 * Increases player's rightward movement speed up to the MAX_SPEED.
		 */
		buttonRight.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("ButtonRight", "Down");
					GameThread.startRunningRight();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Log.d("ButtonRight", "Up");
					GameThread.setIsRunning(false);
				}
				return false;
			}
		});

		/*
		 * Increases player's leftward movement speed up to the MAX_SPEED.
		 */
		buttonLeft.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					GameThread.startRunningLeft();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					GameThread.setIsRunning(false);
				}
				return false;
			}
		});
	}

	public void loadDefeatMenu() {
		// KillPlayer Testing Code
		Intent defeatMenuIntent = new Intent(this, DefeatMenu.class);
		this.startActivity(defeatMenuIntent);
		// END KillPlayer Testing Code
	}

	public void loadVictoryMenu() {
		Intent victoryMenuIntent = new Intent(this, VictoryMenu.class);
		this.startActivity(victoryMenuIntent);
	}
}