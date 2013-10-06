package com.csci430.anandroidgame;

import java.util.Vector;

public class GameObject extends Global{
	private final int MAX_H_SPEED = 20;		//max horiz speed
	private final int runSpeed = 5;		//button press increment
	private final int jumpSpeed = 10;
	
	private int positionX;
	private int positionY;
	private float velocityX;
	private float velocityY;
	
	private bool solid;
	private bool visible;
	private int typeOf;		//0:player, 1:AI, 2: object
	
	int spriteSheetLocationX;
	int spriteSheetLocationY;
}

public void jump()
{
	//TODO: if on floor, velocityY += jump
	velocity += jumpSpeed;
}

public void left()
{
	velocityX -= runSpeed;
	if (velocityX > MAX_H_SPEED)
		velocityX = MAX_H_SPEED;
}
 public void right()
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

public bool isSolid()
{
	return solid;
}

public void setSolid(bool sol)
{
	solid = sol;
}

public bool isVisible()
{
	return visible();
}

public void setVisible(bool vis)
{
	visible = vis;
}
