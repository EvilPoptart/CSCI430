package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameObject extends Global{
	private int MAX_H_SPEED = 30;		//max horiz speed
	private int MAX_Y_SPEED = 30;
	private int runSpeed = 10;		    //button press increment
	private int jumpSpeed = 20;
	
	private int positionX;
	private int positionY;
	private int sizeX;
	private int sizeY;
	private float velocityX;
	private float velocityY;
	
	private boolean solid;
	private boolean visible;
	private int typeOf;		//0:player, 1:AI, 2: object
	private boolean prev_Colide;
	
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
		prev_Colide = false;
		
		spriteRect = new Rect(posX, posY, (posX + sizeX), (posY + sizeY));
	}
	
	public void tickUpdate()
	{	
		prev_Colide = false;
		//position update, only for player and AI, not static objects
		if(typeOf == 0 || typeOf == 1)
		{
			positionX += velocityX;
			positionY += velocityY;	
		
			//velocity update for time
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
			
			//Left Wall Collision
			if(positionX < 0)
			{
				positionX = 0;
				velocityX = 0;
			}
			
			//Right Wall Collision
			if(positionX > Global.metrics.widthPixels)
			{
				positionX = Global.metrics.widthPixels - sizeX;
				velocityX = 0;
			}
			
			//if on floor remove velocity
			if(positionY > (Global.metrics.heightPixels - sizeY))
			{
				positionY = Global.metrics.heightPixels - sizeY;
				velocityY = 0;
			}
			
			if (colision())
			{
				prev_Colide = true;
				Log.d("Collision","Detected");
				spriteRect.offsetTo(positionX, positionY);
			}
			else
			{
				velocityY += 5;
				prev_Colide = false;
				spriteRect.offsetTo(positionX, positionY);
			}
		}
	}
	
	private boolean colision() {
		//replace this with loop for all objects ID >= 2
		if( Rect.intersects(this.getSprite(), Global.worldObjects.get(2).getSprite()))
		{
			/* need to figure out how to only collide with top OR bottom OR left OR right
			 * right now it will always "pop" player to top of object
			 * 
			 * the +1 is to prevent hopping when the player is on a surface
			 */
			if(prev_Colide == false)	//stops hopping, allows jumping
			{
				this.positionY = (Global.worldObjects.get(2).getPosY() - this.sizeY);
				velocityY = 0;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void jumps()
	{
		Log.d("ButtonPress", "Y Velocity: " + Float.toString(velocityY));
		if(velocityY <= 5)	//assume on jumpable platform if velY <= 5
		{
			velocityY += jumpSpeed;
			
			if(velocityY > MAX_Y_SPEED)
				velocityY = MAX_Y_SPEED; 
			if(velocityY > ((-1)*MAX_Y_SPEED))
				velocityY = (-1)*MAX_Y_SPEED;
		}
		prev_Colide = false;
	}
	public void runLefts()
	{
		velocityX -= runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
		prev_Colide = false;
	}
	public void runRights()
	{
		velocityX += runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
		prev_Colide = false;
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
	public void setVelocityY(int v)
	{
		velocityY = v;
	}
}
