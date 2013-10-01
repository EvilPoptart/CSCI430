package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

public class Player extends Static {  // TODO: Change to extend Animated once we implement animated sprites
	protected static int mPlayerWidth = 35;
	protected static int mPlayerHeight = 60;

	/*
	 * Constructors
	 * ========================================================================
	 */
	
	/*
	 * Without initial velocity
	 * 
	 * width:		This object's width
	 * height:		This object's height
	 * initXPos:	Initial X-axis position
	 * initYPos:	Initial Y-axis position
	 * initXVel:	Initial X-axis velocity (assumed to be 0)
	 * initYVel:	Initial Y-axis velocity (assumed to be 0)
	 * bitmap:		The bitmap image to be displayed
	 */
	public Player(
			int width, // TODO: remove
			int height, // TODO: remove
			int initXPos,
			int initYPos,
			Paint paint,
			Context context) {
		super(mPlayerWidth, mPlayerHeight, initXPos, initYPos, paint, context);
	}

	/*
	 * With initial velocity
	 * 
	 * width:		This object's width
	 * height:		This object's height
	 * initXPos:	Initial X-axis position
	 * initYPos:	Initial Y-axis position
	 * initXVel:	Initial X-axis velocity
	 * initYVel:	Initial Y-axis velocity
	 * bitmap:		The bitmap image to be displayed
	 */
	public Player(
			int width,
			int height,
			int initXPos,
			int initYPos,
			int initXVel,
			int initYVel,
			Bitmap bitmap) {
		super(width, height, initXPos, initYPos, initXVel, initYVel, bitmap);
	}

}
