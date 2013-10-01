package com.csci430.anandroidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
	protected int mActualFloor;
	protected int mRelFloor;
	
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
	
	public Graphic(
			int width,
			int height,
			int initXPos,
			int initYPos,
			Paint paint) {
		mWidth = width;
		mHeight = height;
		mXPos = initXPos;
		mYPos = initYPos;
		mPaint = paint;
		
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

	
	/* 
	 * Set-ers
	 * ========================================================================
	 */

	public void setPos(int newXPos, int newYPos) {
		mXPos = newXPos;
		mYPos = newYPos;
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
	 */
	
	/*
	 * Draw the object on the canvas
	 */
	public void draw(Canvas c) {
		c.drawRect(mSourceRect, mPaint);
	}

}
