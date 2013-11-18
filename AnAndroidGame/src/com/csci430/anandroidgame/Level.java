package com.csci430.anandroidgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.view.*;
import android.media.*;


public class Level extends Activity {
    protected static final Context context = null;
	public Button buttonJump1;
	public Button buttonJump2;
	public Button buttonLeft;
	public Button buttonRight;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Call the function onCreate() from the class we're extending (Activity)
    	super.onCreate(savedInstanceState);
    	Log.d("Startup", "Level");
    	Log.d("Startup", "level-1");
    	
		Intent intent = getIntent();
    	Log.d("Startup", "level-2");
		Global.curLevelId = Integer.parseInt(intent.getStringExtra("levelId")); //if it's a string you stored.
    	Log.d("Startup", "level-3");
    	
    	// Hide title. Must be called before setContentView()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	Log.d("Startup", "level-4");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	Log.d("Startup", "level-5");

        // Tell system to use the layout defined in our XML file.
        setContentView(R.layout.activity_level);
    	Log.d("Startup", "level-6");
        
        //Used in screen Sizes throughout the application
        GameThread.metrics = new DisplayMetrics();
    	Log.d("Startup", "level-7");
        getWindowManager().getDefaultDisplay().getMetrics(GameThread.metrics);    
    	Log.d("Startup", "level-8");
        
        // Define our buttons
		buttonJump1 = (Button) findViewById(R.id.buttonJump1);
    	Log.d("Startup", "level-9");
		buttonJump2 = (Button) findViewById(R.id.buttonJump2);
    	Log.d("Startup", "level-10");
		buttonRight = (Button) findViewById(R.id.buttonRight);
    	Log.d("Startup", "level-11");
		buttonLeft  = (Button) findViewById(R.id.buttonLeft);
    	Log.d("Startup", "level-12");
		final MediaPlayer jumpsound = MediaPlayer.create(this, R.raw.jump);
    	Log.d("Startup", "level-13");
		
		
		/*
		 * Handles player jumping (upper left)
		 */
		buttonJump1.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (Global.levelCompleted) {
						// Stop Game
						GameThread.setIsRunning(false);
						loadVictoryMenu();
					}
	        		jumpsound.start();
	            	GameThread.jump();
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					// Do nothing
				}
				return false;
			}
        });
    	Log.d("Startup", "level-14");

		/*
		 * Handles player jumping (upper right)
		 */
		buttonJump2.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (Global.levelCompleted) {
						// Stop Game
						Log.d("gamethread", "stopping");
						GameThread.setRunning(false);
						loadVictoryMenu();
					}
	        		jumpsound.start();
	            	GameThread.jump();
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					// Do nothing
				}
				return false;
			}
        });
    	Log.d("Startup", "level-15");
		
		/*
		 * Increases player's rightward movement speed up to the MAX_SPEED.
		 */
		buttonRight.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					GameThread.startRunningRight();
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					GameThread.setIsRunning(false);
				}
				return false;
			}
        });
    	Log.d("Startup", "level-16");
		
		/*
		 * Increases player's leftward movement speed up to the MAX_SPEED.
		 */
		buttonLeft.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					GameThread.startRunningLeft();
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					GameThread.setIsRunning(false);
				}
				return false;
			}
        });	
    	Log.d("Startup", "level-17");
	}
    
	public void loadVictoryMenu() {
		Intent victoryMenuIntent = new Intent(this, VictoryMenu.class);
		this.startActivity(victoryMenuIntent);
	}
}