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
	public final double MAX_SPEED = 15;
	public final double GRAVITY = 3;				//falling acceleration
	public final double SLIP = 5;					//horiz slow when button not pressed

	
	
	private int playerXpos;
	private int playerYpos;
	private double playerXspeed;
    private double playerYspeed;
    private double FLOOR = 10;						//TODO: replace with floor function for map
    public Button buttonJump1;
	public Button buttonJump2;
	public Button buttonLeft;
	public Button buttonRight;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// ?
    	super.onCreate(savedInstanceState);
    	// Sets application dimensions to cover the device's entire screen.
        setContentView(R.layout.activity_fullscreen);
        
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
				if(playerYpos == FLOOR) {
					playerYspeed = playerYspeed + JUMP_SPEED;
					//TODO: PLAY SOUND HERE (Jump)
				}
			}
        });

		/*
		 * Handles player jumping (upper right)
		 */
		buttonJump2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
            	if(playerYpos == FLOOR) {
					playerYspeed = playerYspeed + JUMP_SPEED;
					//TODO: PLAY SOUND HERE (Jump)
				}
			}
        });
		
		/*
		 * Increases player's rightward movement speed up to the MAX_SPEED.
		 */
		buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
				if(playerXspeed <= MAX_SPEED)
					playerXspeed = playerXspeed + RUN_SPEED;
				if(playerXspeed > MAX_SPEED)
					playerXspeed = MAX_SPEED;
			}
        });
		
		/*
		 * Increases player's leftward movement speed up to the MAX_SPEED.
		 */
		buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{	
				if(playerXspeed >= MAX_SPEED)
					playerXspeed = playerXspeed - RUN_SPEED;
				if(playerXspeed > MAX_SPEED)
					playerXspeed = (MAX_SPEED * -1);
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
			playerXpos = playerXpos + (int)playerXspeed;		//update postion on screen
			if((playerYpos + (int)playerYspeed) <= FLOOR) {
				playerYpos = 10;
				playerYspeed = 0;
				//TODO: play sound here (land)
			}
			
			playerYspeed -= GRAVITY;							//update speeds for next cycle
			
			if(playerYspeed < -15)				//MAX fall speed
			{
				if(playerYspeed < -15)
					playerYspeed = -15;
			}	
			
			if(playerXspeed > 0){				//X speed correction 
				playerXspeed -= SLIP;
				if(playerXspeed < 0)
					playerXspeed = 0;
			}
			else {
				playerXspeed += SLIP;
				if(playerXspeed > 0)
					playerXspeed = 0;
			}
    	}
	};
}