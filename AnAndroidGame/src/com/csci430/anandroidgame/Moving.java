package com.csci430.anandroidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


/*
 * A GameElement that will be Drawn to the screen, that will be Animated.
 */
public class Moving extends Graphic {
	/*
	 * Constructors
	 * ========================================================================
	 */
	
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
	public Moving(
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
			0, // initXVel
			0, // initYVel
			paint);
	}
	
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
	public Moving(
			int width,
			int height,
			int initXPos,
			int initYPos,
			Bitmap bitmap) {
		// Initialize relevant variables
		mWidth = width;
		mHeight = height;
		mXPos = initXPos;
		mYPos = initYPos;
		mXVel = 0;
		mYVel = 0;
		mBitmap = bitmap;

		// Create rectangle that will be drawn for this object
		mSourceRect = new Rect(mXPos, mYPos, (mXPos + mWidth), (mYPos + mHeight));
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
	public Moving(
			int width,
			int height,
			int initXPos,
			int initYPos,
			int initXVel,
			int initYVel,
			Bitmap bitmap) {
		// Initialize relevant variables
		mWidth = width;
		mHeight = height;
		mXPos = initXPos;
		mYPos = initYPos;
		mXVel = initXVel;
		mYVel = initYVel;
		mBitmap = bitmap;
		
		// Create rectangle that will be drawn for this object
		mSourceRect = new Rect(mXPos, mYPos, (mXPos + mWidth), (mYPos + mHeight));
	}
	
	
	/*
	 * Drawing
	 * ========================================================================
	 */
	
	/*
	 * Update the object's position on the canvas, but don't draw it.
	 */
	public void updatePos() {
		applyGravity();
		mXPos += mXVel;
		mYPos += mYVel;
		
		// Move the object to its new position
		mSourceRect.offsetTo(mXPos, mYPos);
		//updateXPos();
		//updateYPos();
	}
	
	/*
	 * Update the object's position on the canvas and draw it.
	 */
	public void updateAndDraw(Canvas c) {
		updatePos();
		draw(c);
	}
	
	/*
	 * Draw the object on the canvas
	 */
	public void draw(Canvas c) {
		c.drawRect(mSourceRect, mPaint);
	}
}
