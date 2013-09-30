package com.csci430.anandroidgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class GameElement {
	// Physical Attributes
	protected int mWidth;
	protected int mHeight;
	
	// Position and movement
	protected float mPosX;
	protected float mPosY;
	protected int mVelX;
	protected int mVelY;
	protected int mFloor;
	
	protected int mFriction;
	protected int mGravity;
	protected int mMaxFallSpeed = 15;
	protected int mMaxRunSpeed;
	protected int mJumpSpeed;
	
	public GameElement() {}
	
	/*
	 * Drawing
	 */
	
	public void update() {
		updatePosX();
		updatePosY();
	}
	
	public void updateAndDraw(Canvas c) {
		updatePosX();
		// if we're in the air, fall.
		if (mPosY < mFloor) {
			updateVelY(mGravity);
		}
		else if (mPosY > mFloor) {
			// If we've hit/gone through the floor, land.
			mVelY = 0;
			mPosY = mFloor;
		}
		updatePosY();
		draw(c);
	}
	
	public void draw(Canvas c) {
		// All GameElements are solid rectangles in this version. Eventually they'll all be 
		// rectangles with sprites in them.
	    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Style.FILL);
        // Arguments are in pairs of (x, y) for the top-left and bottom-right.
        // An easier to read version is:
        // 	c.drawRect(x1, y1, x2, y2, paint)
        //  Where (x1, y1) is the top-left corner, and (x2, y2) is the
        //  bottom-right corner.
        c.drawRect(mPosX, mPosY, mPosX + mWidth, mPosY + mHeight, mPaint);
        //c.drawRect(100, 150, 200, 250, mPaint);
	}
	
	/*
	 * Get
	 */
	
	public float getPosX() {
		return mPosX;
	}
	
	public float getPosY() {
		return mPosY;
	}
	
	public int getVelX() {
		return mVelX;
	}
	
	public int getVelY() {
		return mVelY;
	}
	
	/* 
	 * Set
	 */
	
	public void setPos(float x, float y) {
		mPosX = x;
		mPosY = y;
	}
	
	public void setVelX(int vel) {
		mVelX = vel;
	}
	
	public void setVelY(int vel) {
		mVelY = vel;
	}
	
	/*
	 * Update pos and vel
	 */
	
	public void updatePosX() {
		mPosX += mVelX;
	}
	
	public void updatePosY() {
		// Don't fall through the floor.
		if (mPosY > mFloor) {
			// Put our feet back on the floor.
			mPosY = mFloor;
			// Stop falling.
			mVelY = 0;
		}
		mPosY += mVelY;
	}
	
	public void updateVelX(int i) {
		mVelX += i;
	}
	
	public void updateVelY(int i) {
		mVelY += i;
		if (mVelY > mMaxFallSpeed) {
			mVelY = mMaxFallSpeed;
		}
	}

}
