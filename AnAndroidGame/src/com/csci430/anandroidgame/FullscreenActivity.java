package com.csci430.anandroidgame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.view.*;
import android.media.*;


public class FullscreenActivity extends Activity {
    protected static final Context context = null;
	public Button buttonJump1;
	public Button buttonJump2;
	public Button buttonLeft;
	public Button buttonRight;
	public static Global placeHolder;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Call the function onCreate() from the class we're extending (Activity)
    	super.onCreate(savedInstanceState);
    	
    	// Hide title. Must be called before setContentView()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Tell system to use the layout defined in our XML file.
        setContentView(R.layout.activity_fullscreen);
        
        //Used in screen Sizes throughout the application
        Global.metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(Global.metrics);    
        
        // Define our buttons
		buttonJump1 = (Button) findViewById(R.id.buttonJump1);
		buttonJump2 = (Button) findViewById(R.id.buttonJump2);
		buttonRight = (Button) findViewById(R.id.buttonRight);
		buttonLeft  = (Button) findViewById(R.id.buttonLeft);
		final MediaPlayer jumpsound = MediaPlayer.create(this, R.raw.jump);
		
		placeHolder = new Global();
		Global.worldObjects.clear();
		
		/*
		 * Handles player jumping (upper left)
		 */
		buttonJump1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{            
        		jumpsound.start();
            	Global.jump();
			}
        });

		/*
		 * Handles player jumping (upper right)
		 */
		buttonJump2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
        		jumpsound.start();
            	Global.jump();
			}
        });
		
		/*
		 * Increases player's rightward movement speed up to the MAX_SPEED.
		 */
		buttonRight.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Global.startRunningRight();
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					Global.setRunning(false);
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
					Global.startRunningLeft();
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					Global.setRunning(false);
				}
				return false;
			}
        });	
	}
    
}