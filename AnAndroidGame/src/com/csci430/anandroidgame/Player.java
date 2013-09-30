package com.csci430.anandroidgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.csci430.anandroidgame.GameElement;

public class Player extends GameElement {
	private int mScore;
	private int mPWidth = 35;
	private int mPHeight = 60;

	private int mPFriction = 1;
	private int mPGravity = 1;
	private int mPMaxRunSpeed = 1;
	private int mPJumpSpeed = 20;
	
	public Player(float x, float y) {
		mScore = 0;
		mWidth = mPWidth;
		mHeight = mPHeight;
		mPosX = x;
		mPosY = y;
		mVelX = 0;
		mVelY = 0;
		
		mFriction = mPFriction;
		mGravity = mPGravity;
		mMaxRunSpeed = mPMaxRunSpeed;
		mJumpSpeed = mPJumpSpeed;
		mFloor = 400 - mPHeight;
		setVelY(mPGravity);
	}
	
	public Player(float x, float y, int vX, int vY) {
		mScore = 0;
		mWidth = mPWidth;
		mHeight = mPHeight;
		mPosX = x;
		mPosY = y;
		mVelX = vX;
		mVelY = vY;
		
		mFriction = mPFriction;
		mGravity = mPGravity;
		mMaxRunSpeed = mPMaxRunSpeed;
		mJumpSpeed = mPJumpSpeed;
		mFloor = 400 - mPHeight;
		setVelY(mPGravity);
	}
	
	public void jump() {
		updateVelY(-mPJumpSpeed);
	}
}
