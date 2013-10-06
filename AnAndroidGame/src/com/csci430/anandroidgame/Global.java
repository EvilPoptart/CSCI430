package com.csci430.anandroidgame;

import java.util.Vector;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

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
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay()
	    if (android.os.Build.VERSION.SDK_INT >= 13) {	
	    	Point size = new Point();
	    	display.getSize(size);
	    	int width = size.x;
	    	int height = size.y;
	    } else {
	    	screenWidth = display.getWidth();  // deprecated
	       	screenHeight = display.getHeight();  // deprecated
	    }
	    
		worldObjects = new Vector<GameObject>();
		levels = new Vector<Level>();	
	
	}
};

public void updateObject()
{
	//for each worldObjects update position and velocities
}

public void updateGraphics()
{
	//graphicControl.update()
}