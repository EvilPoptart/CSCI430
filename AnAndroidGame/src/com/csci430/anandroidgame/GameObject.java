package com.csci430.anandroidgame;

public class GameObject extends Global{
	private final int MAX_H_SPEED = 20;		//max horiz speed
	private final int MAX_Y_SPEED = 40;
	private final int runSpeed = 5;		//button press increment
	private final int jumpSpeed = 10;
	
	private int positionX;
	private int positionY;
	private float velocityX;
	private float velocityY;
	
	private boolean solid;
	private boolean visible;
	private int typeOf;		//0:player, 1:AI, 2: object
	
	int spriteSheetLocationX;
	int spriteSheetLocationY;

	GameObject()
	{
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;
		
		solid = false;
		visible = true;
		typeOf = 2;		//0:player, 1:AI, 2: object
		
		//int spriteSheetLocationX;
		//int spriteSheetLocationY;

	}
	
	public void tickUpdate()
	{
		//TODO: position and speed update for moving objects	
	}
	public void jump()
	{
		//TODO: if on floor, velocityY += jump
		velocityY += jumpSpeed;
		if(velocityY > MAX_Y_SPEED)
			velocityY = MAX_Y_SPEED; 
		if(velocityY > ((-1)*MAX_Y_SPEED))
			velocityY = (-1)*MAX_Y_SPEED;
	}
	public void runLeft()
	{
		velocityX -= runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
	}
	public void runRight()
	{
		velocityX += runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
	}
	public int posX()
	{
		return positionX;
	}
	public int posY()
	{
		return positionY;
	}
	public boolean isSolid()
	{
		return solid;
	}
	public void setSolid(boolean sol)
	{
		solid = sol;
	}
	public boolean isVisible()
	{
		return visible;
	}
	public void setVisible(boolean vis)
	{
		visible = vis;
	}

	public int getType()
	{
		return typeOf;
	}
}
