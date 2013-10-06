package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class GameObject extends Global{
	private int MAX_H_SPEED = 60;		//max horiz speed
	private int MAX_Y_SPEED = 40;
	private int runSpeed = 10;		    //button press increment
	private int jumpSpeed = 10;
	
	private int positionX;
	private int positionY;
	private int sizeX;
	private int sizeY;
	private float velocityX;
	private float velocityY;
	
	private boolean solid;
	private boolean visible;
	private boolean collided;
	private int typeOf;		//0:player, 1:AI, 2: object
	
	private Paint paint;
	private Context context;
	private SurfaceHolder surfH;
	private Rect spriteRect;
	
	int spriteSheetLocation;

	GameObject()
	{
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;
		
		solid = false;
		visible = true;
		typeOf = 2;		//0:player, 1:AI, 2: object
		
		spriteSheetLocation = 0;	//for animations and such
	}
	
	//Player Create: type = 0;
	GameObject(int type, int sX, int sY, int posX, int posY, Paint p, SurfaceHolder sh)
	{
		typeOf = type;
		sizeX = sX;
		sizeY = sY;
		positionX = posX;
		positionY = posY;
		paint = p;
		surfH = sh;
		
		if(type == 0)
		{
			velocityY = 3;
		}
		collided = false;
		spriteRect = new Rect(posX, posY, (posX + sizeX), (posY + sizeY));
	}
	
	public void tickUpdate()
	{
		if(typeOf == 0 || typeOf == 1)
		{
			positionX += velocityX;
			positionY += velocityY;	
		}
		
		if(velocityX > 0)
		{
			velocityX -= 1;
			if(velocityX < 0 )
				velocityX = 0;
		}
		else
		{
			velocityX += 1;
			if(velocityX > 0 )
				velocityX = 0;
		}
		if(positionX < 0)
		{
			positionX = 0;
			velocityX = 0;
		}
		if(positionX > Global.metrics.widthPixels)
		{
			positionX = Global.metrics.widthPixels - sizeX;
			velocityX = 0;
		}
		
		
		//wrap in collision detection
		if(collided == false){	
			velocityY += 5;
			if(positionY > (Global.metrics.heightPixels - sizeY))
			{
				positionY = Global.metrics.heightPixels - sizeY;
				velocityY = 0;
			}
		}
		// /wrap
		
		
		spriteRect.offsetTo(positionX, positionY);
	}
	public void jumps()
	{
		collided = false;
		if(velocityY == 0)	//assume on jumpable platform if velY == 0
		{
			velocityY += jumpSpeed;
			
			if(velocityY > MAX_Y_SPEED)
				velocityY = MAX_Y_SPEED; 
			if(velocityY > ((-1)*MAX_Y_SPEED))
				velocityY = (-1)*MAX_Y_SPEED;
		}
	}
	public void runLefts()
	{
		velocityX -= runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
	}
	public void runRights()
	{
		velocityX += runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
	}
	public int getPosX()
	{
		return positionX;
	}
	public int getPosY()
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
	public void setCtx(Context ctx)
	{
		context = ctx;
	}
	public Context getCtx()
	{
		return context;
	}
	public void setSH(SurfaceHolder sh)
	{
		surfH = sh;
	}
	public SurfaceHolder getSH()
	{
		return surfH;
	}
	public Rect getSprite()
	{
		return spriteRect;
	}
	public void moveSpriteTo(int x, int y)
	{
		spriteRect.offsetTo(x, y);
	}
	public Paint getPaint()
	{
		return paint;
	}
	public void setPaint(Paint p)
	{
		paint = p;
	}
	public void setPosY(int posY)
	{
		positionY = posY;
	}
	public int getSizeY()
	{
		return sizeY;
	}
	public void setColY()
	{
		collided = true;
		velocityY = 0;
	}
}
