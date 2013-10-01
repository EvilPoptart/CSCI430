package com.csci430.anandroidgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


/*
 * A GameElement that will be Drawn to the screen, that will be Animated.
 */
@SuppressLint("NewApi")
public class Moving extends Graphic {
	
	/*
	 * Movement Variables
	 * ========================================================================
	 * 
	 * mXVel:			The object's speed and direction along the X-axis. Positive -> Left. Negative -> Right.
	 * mYVel:			The object's speed and direction along the Y-axis. Positive -> Down. Negative -> Up.
	 * mFriction:		Friction coefficient. Used to slow (accelerate backwards) the object along the X-axis.
	 * mGravity:		Gravity coefficient. Used to accelerate the object downward along the Y-axis.
	 * mActualFloor:	The actual X-axis height of the floor.
	 * mRelFloor:		The relative X-axis height of the floor. This takes our height into account.
	 * mJumpSpeed:		The velocity to be applied to mYVel when the object jumps
	 * mRunAcceleration:The speed at which your horizontal movement increases while running left or right
	 * mMaxFallSpeed:	The maximum downward movement speed (max positive of mYVel)
	 * mMaxRunSpeed:	The maximum horizontal movement speed (absolute max of mXVel)
	 */
	protected int mXVel;
	protected int mYVel;
	protected int mFriction;
	protected int mGravity;
	protected int mJumpSpeed;
	protected int mRunAcceleration;
	protected int mMaxFallSpeed;
	protected int mMaxRunSpeed;
	
	/*
	 * Constructors
	 * ========================================================================
	 */

	/*
	 * Bitmap
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
		
		mFriction = 1;
		mGravity = 1;
		mMaxFallSpeed = 20;
		mMaxRunSpeed = 10;
		mJumpSpeed = -mGravity * 20;
		mRunAcceleration = 1;

    	
		mActualFloor = 500;
		mRelFloor = mActualFloor - mHeight;
		
		// Create rectangle that will be drawn for this object
		mSourceRect = new Rect(mXPos, mYPos, (mXPos + mWidth), (mYPos + mHeight));
	}
	
	/*
	 * Development Constructor.
	 * TODO: remove once we are no longer using solid color rectangles for testing purposes
	 * 
	 * width:		This object's width
	 * height:		This object's height
	 * initXPos:	Initial X-axis position
	 * initYPos:	Initial Y-axis position
	 * initXVel:	Initial X-axis velocity
	 * initYVel:	Initial Y-axis velocity
	 * paint:		The paint object that controls color for our rect
	 */

    @SuppressLint("NewApi")
    /* 
     * We're aware of the NewApi error caused by
     * 	display.getSize(size);
     * and have chosen to keep it to support devices running > Android 3.2 (API 13)
     */
	@SuppressWarnings("deprecation")
    /* 
     * We're aware of the deprecation error caused by
     * 	height = display.getHeight();
     * and have chosen to keep it to support devices running < Android 3.2 (API 13)
     */
	public Moving(
			int width,
			int height,
			int initXPos,
			int initYPos,
			int initXVel,
			int initYVel,
			Paint paint,
			Context context) {
		// Initialize relevant variables
		mWidth = width;
		mHeight = height;
		mXPos = initXPos;
		mYPos = initYPos;
		mXVel = initXVel;
		mYVel = initYVel;
		mPaint = paint;
		mFriction = 1;
		mGravity = 1;
		mMaxFallSpeed = 20;
		mMaxRunSpeed = 10;
		mJumpSpeed = -mGravity * 20;
		mRunAcceleration = 1;
		
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
    	int screenHeight = 0;
    	
    	// Check our working API version and run the appropriate commands
    	if (android.os.Build.VERSION.SDK_INT >= 13) {
        	// Get device height and width
            Point size = new Point();
    	    display.getSize(size);
    	    screenHeight = size.y;
    	} else {
        	screenHeight = display.getHeight();  // deprecated
    	}
    	
		mActualFloor = screenHeight;
		mRelFloor = mActualFloor - mHeight;
		
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
		
		Log.d("Player Pos", "(" + mXPos + ", " + mYPos + ")");
		
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
	 * Get-ers
	 */
	
	public int getXVel() {
		return mXVel;
	}
	public int getYVel() {
		return mYVel;
	}
	
	/* 
	 * Set-ers
	 */

	/*
	 * Update X-axis velocity. Used to adjust the object's speed.
	 * 
	 * (velAdjustment > 0):  Increases leftward speed / Decrease rightward speed
	 * 
	 */
	public void updateXVel(int xVelAdjustment) {
		Log.d("Work darn you!", "Vel: (" + mXVel + ", " + mYVel + ")");
		mXVel += xVelAdjustment;
		Log.d("Work darn you!", "Vel: (" + mXVel + ", " + mYVel + ")");
	}
	
	/*
	 * Set X-axis velocity.
	 */
	public void setXVel(int newXVel) {
		mXVel = newXVel;
	}
	
	/*
	 * Update Y-axis velocity. Used to adjust the object's speed. 
	 * 
	 * (velAdjustment > 0):  Increases downward speed / Decrease upward speed
	 * (velAdjustment < 0):  Increases upward speed / Decrease downward speed
	 */
	public void updateYVel(int yVelAdjustment) {
		mYVel += yVelAdjustment;
	}

	// Set Y-axis velocity.
	public void setYVel(int newYVel) {
		mYVel = newYVel;
	}

	
	/*
	 * Movement Functions
	 * ========================================================================
	 */
	
	/*
	 * Apply gravity to this object.
	 * 
	 * So long as we're above the floor, and going slower than mMaxFallSpeed,
	 * continue to accelerate downwards.
	 */
	public void applyGravity() {
		// If we're above the ground, accelerate up to terminal velocity.
		if (aboveFloor()) {
			// Don't exceed terminal velocity.
			// mMaxFallSpeed is a negative integer.
			if (mYVel < mMaxFallSpeed) {
				mYVel += mGravity;
			}
		}
		// If we're below the ground: Reset to floor and stop falling.
		else if (belowFloor()) {
			mYVel = 0;
			mYPos = mRelFloor;
		}
	}
	
	/*
	 * Cause the object to jump.
	 * 
	 * Limitations: Object must be on the floor to jump.
	 */
	public void jump() {
		// Make sure we're on the ground
		if (onFloor()) {
			mYVel += mJumpSpeed;
		}
	}

	/*
	 * Run to the left (increase mXVel by a negative value)
	 */
	public void runLeft() {
		updateXVel(-mRunAcceleration);
	}

	/*
	 * Run to the right (increase mXVel by a positive value)
	 */
	public void runRight() {
		updateXVel(mRunAcceleration);
	}
	
}
