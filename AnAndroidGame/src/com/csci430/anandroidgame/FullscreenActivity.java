package com.csci430.anandroidgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.*;
import java.util.Timer;
import java.util.TimerTask;


public class FullscreenActivity extends Activity {

	public final double RUN_SPEED = 10.00;			//run speed modifier
    public final double JUMP_SPEED = 5.00;			//jump speed
	//private int playerXpos;
    //private int playerYpos;
    private double playerXspeed;
    private double playerYspeed;
    public Button buttonJump1;
	public Button buttonJump2;
	public Button buttonLeft;
	public Button buttonRight;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        
		buttonJump1 = (Button) findViewById(R.id.buttonJump1);
		buttonJump2 = (Button) findViewById(R.id.buttonJump2);
		buttonRight = (Button) findViewById(R.id.buttonRight);
		buttonLeft = (Button) findViewById(R.id.buttonLeft);
		Timer updateCycle = new Timer();
		
		
		buttonJump1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
				//if on ground
				playerYspeed = playerYspeed + JUMP_SPEED;
            }
        });
		
		buttonJump2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
				//if on ground
				playerYspeed = playerYspeed + JUMP_SPEED;
            }
        });
		
		buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
				//if not at max
				playerXspeed = playerXspeed + RUN_SPEED;
            }
        });
		
		buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{	
				//if not at max
				playerXspeed = playerXspeed - RUN_SPEED;
            }
        });
		
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
		public void run() 
		{
		/*
			update position
				X based on speed
				Reduce Y unless collision with ground
			update speeds
			
		*/
		}
	};
}