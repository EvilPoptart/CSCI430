package com.csci430.anandroidgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.view.*;
import java.util.Timer;
import java.util.TimerTask;


public class FullscreenActivity extends Activity {
    public Button buttonJump1;
	public Button buttonJump2;
	public Button buttonLeft;
	public Button buttonRight;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Call the function onCreate() from the class we're extending (Activity)
    	super.onCreate(savedInstanceState);
    	
    	// Hide title. Must be called before setContentView()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        // Hide status bar. Must be called before setContentView()
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
		Timer updateCycle = new Timer();
		
		/*
		 * Handles player jumping (upper left)
		 */
		buttonJump1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
            	Global.jump();
			}
        });

		/*
		 * Handles player jumping (upper right)
		 */
		buttonJump2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
            	Global.jump();
			}
        });
		
		/*
		 * Increases player's rightward movement speed up to the MAX_SPEED.
		 */
		buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
            	Global.runRight();
			}
        });
		
		/*
		 * Increases player's leftward movement speed up to the MAX_SPEED.
		 */
		buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{	
            	Global.runLeft();
            }
        });
		
		/*
		 * Handles the main draw loop. Refreshes the screen at a fixed rate.
		 */
		updateCycle.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run()
        	{
        		updateCycle();
				//Redraw
        	}        	
        },0,50);	
    }

	//these two functions are used to keep the UI and timer in sync  http://steve.odyfamily.com/?p=12
    private void updateCycle(){
    	this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
    	public void run() {
    		if(Global.worldObjects.size() > 0){
	    		for (int i = 0; i < Global.worldObjects.size(); i++)
	    		{
	    			Global.worldObjects.get(i).tickUpdate();
	    		}
    		}
    	}
    };
}