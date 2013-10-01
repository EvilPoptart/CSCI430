package com.csci430.anandroidgame;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/*
 * A GameElement that will be Drawn to the screen.
 */
public class Graphic extends GameElement {
	/*
	 * An illustration of the Canvas.
	 * (0,0) is the upper left corner of the screen.
	 * 
	 * The box around the stick man is a bounding box. Bounding boxes are
	 * defined by two points: the upper-left and lower-right corners. These
	 * boxes are used in collision detection.
	 * 
	 *                (+x,0)
	 *  (0,0).------------>
	 *       |
	 *       | (mXPos, mYPos) -----
	 *       |                | O |
	 *       |                |/|\|
	 * (0,+y)|                |/ \|
	 *       |                ----- (mXPos + mWidth, mYPos + mHeight)
	 *       v
	 *       
	 *       
	 *       
	 * D-Pad movement example:
	 * 
	 *     (mYVel < 0) ^ 
	 *                 |
	 * (mXVel < 0) <--- ---> (mXVel > 0)
	 *                 |
	 *                 v (mYVel > 0)
	 */
	
	/*
	 * Position, where (mXPos, mYPos) corresponds to the upper left corner of the object
	 * ========================================================================
	 */
	protected int mXPos;
	protected int mYPos;
	
	/*
	 * Dimension
	 * ========================================================================
	 * 
	 * mWidth:	The width of this object
	 * mHeight:	The height of this object
	 */
	protected int mWidth;
	protected int mHeight;
	
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
	protected int mActualFloor;
	protected int mRelFloor;
	protected int mJumpSpeed;
	protected int mRunAcceleration;
	protected int mMaxFallSpeed;
	protected int mMaxRunSpeed;
	
	/*
	 * Appearance
	 * ========================================================================
	 */
	
	// ref: http://examples.javacodegeeks.com/android/games/canvas/sprite-animation-in-android/
	protected Bitmap mBitmap;
	protected Rect mSourceRect;
	
	protected Paint mPaint; // TODO: remove once we implement bitmaps
	
	/*
	 * Constructors
	 * ========================================================================
	 */
	
	// It's blank because this class is designed to be extended.
	public Graphic() {}

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
	public Graphic(
			int width,
			int height,
			int initXPos,
			int initYPos,
			int initXVel,
			int initYVel,
			Paint paint) {
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
		mActualFloor = 500;
		mRelFloor = mActualFloor - mHeight;
		mRunAcceleration = 1;
		
		// Create rectangle that will be drawn for this object
		mSourceRect = new Rect(mXPos, mYPos, (mXPos + mWidth), (mYPos + mHeight));
	}
	
	
	/*
	 * Get-ers
	 * ========================================================================
	 */

	public int getXPos() {
		return mXPos;
	}
	public int getYPos() {
		return mYPos;
	}
	public int getXVel() {
		return mXVel;
	}
	public int getYVel() {
		return mYVel;
	}
	
	/* 
	 * Set-ers
	 * ========================================================================
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
	
	public void setPos(int newXPos, int newYPos) {
		mXPos = newXPos;
		mYPos = newYPos;
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
	
	/* 
	 * Floor checks
	 * 
	 *   (mXPos, mYPos) -----  - (mFloor - mHeight)
	 *                  | O |  |
	 *                  |/|\|  | <--(mHeight)
	 * _________________|/_\|__|_______
	 *                              (mFloor)
	 */
	
	/* 
	 * Check if out feet are on the floor.
	 */
	public boolean onFloor() {
		/* 
		 *   (mXPos, mYPos) -----  - (mFloor - mHeight)
		 *                  | O |  |
		 *                  |/|\|  | <--(mHeight)
		 * _________________|/_\|__|_______
		 *                              (mFloor)
		 */
		return (mYPos == mRelFloor) ? true: false;
	}
	
	/*
	 * Check if we're above the floor.
	 */
	public boolean aboveFloor() {
		return (mYPos < mRelFloor) ? true : false;
	}
	
	/*
	 * Check if we're below the floor.
	 */
	public boolean belowFloor() {
		return (mYPos > mRelFloor) ? true : false;
	}
	
	/*
	 * Drawing
	 * ========================================================================
	 * 
	 * Drawing is handled in sub-Class Moving.
	 */
}
