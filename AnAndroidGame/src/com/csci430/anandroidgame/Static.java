package com.csci430.anandroidgame;

import android.graphics.Paint;

/*
 * A GameElement that will be Drawn to the screen, that will be Static (not Animated).
 */
public class Static extends Moving {
	/*
	 * Without initial velocity, without bitmap. Solid color rectangle
	 * 
	 * width:		This object's width
	 * height:		This object's height
	 * initXPos:	Initial X-axis position
	 * initYPos:	Initial Y-axis position
	 * initXVel:	Initial X-axis velocity (assumed to be 0)
	 * initYVel:	Initial Y-axis velocity (assumed to be 0)
	 * bitmap:		The bitmap image to be displayed
	 */
	public Static(
			int width,
			int height,
			int initXPos,
			int initYPos,
			Paint paint) {
		super(
			width, 
			height,
			initXPos,
			initYPos,
			paint);
	}
}
