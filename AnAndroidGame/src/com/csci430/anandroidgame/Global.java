package com.csci430.anandroidgame;

import java.util.Vector;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.Display;

public class Global extends FullscreenActivity{
	public static int screenWidth;
	public static int screenHeight;
	public static Vector<GameObject> worldObjects;
	public static Vector<Level> levels;
	
	public Sound sounds;
	public Graphics graphicControl;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	Global(){
		//Display Size, API independent
		Display display = getWindowManager().getDefaultDisplay(); 
	    if (android.os.Build.VERSION.SDK_INT >= 13) {	
	    	Point size = new Point();
	    	display.getSize(size);
	    	screenWidth = size.x;
	    	screenHeight = size.y;
	    } else {
	    	screenWidth = display.getWidth();  // deprecated, but needed for older devices
	       	screenHeight = display.getHeight(); 
	    }
	    
	    
	    
		worldObjects = new Vector<GameObject>();
		levels = new Vector<Level>();	
	
	}


public void updateObject()
{
	//for each worldObjects update position and velocities
}

public void updateGraphics()
{
	//graphicControl.update()
}

}