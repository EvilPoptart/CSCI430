package com.csci430.anandroidgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.*;
import java.util.Timer;
import java.util.TimerTask;

import com.csci430.anandroidgame.GameView;

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
        
        // Create reference to our GameView. Needed to assign functionality to buttons.
        final GameView gameView = (GameView) findViewById(R.id.gameCamvas);
        
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
            	Log.d("ButtonPress", "Button Pressed: Jump1");
            	//gameView.getThread().p1.jump();
			}
        });

		/*
		 * Handles player jumping (upper right)
		 */
		buttonJump2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
            	Log.d("ButtonPress", "Button Pressed: Jump2");
            	//gameView.getThread().p1.jump();
			}
        });
		
		/*
		 * Increases player's rightward movement speed up to the MAX_SPEED.
		 */
		buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{
            	Log.d("ButtonPress", "Button Pressed: Right");
            	//gameView.getThread().p1.runRight();
			}
        });
		
		/*
		 * Increases player's leftward movement speed up to the MAX_SPEED.
		 */
		buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
			{	
            	Log.d("ButtonPress", "Button Pressed: Left");
            	//gameView.getThread().p1.runLeft();
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
    	}
    };
}