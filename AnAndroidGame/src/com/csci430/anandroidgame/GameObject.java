package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameObject extends Global {
	private int MAX_H_SPEED = 7;		//max horiz speed
	private int MAX_Y_SPEED = 60;
	private int runSpeed = 1;		    //button press increment
	private int jumpSpeed = 45;
	
	private int positionX;
	private int positionY;
	private int sizeX;
	private int sizeY;
	private float velocityX;
	private float velocityY;
	
	private boolean solid;
	private boolean visible;
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
		
		spriteRect = new Rect(posX, posY, (posX + sizeX), (posY + sizeY));
	}
	
	public void tickUpdate()
	{	
		//position update, only for player and AI, not static objects
		if(typeOf == 0 || typeOf == 1)
		{
			// Create a ghost to be used for collision detection.
			// The ghost will move one tick ahead of the moving game object
			// to let us know if the object is about to hit anything.
			Rect ghost;
			String ourCurPosition = spriteRect.flattenToString();
			ghost = Rect.unflattenFromString(ourCurPosition);
			
			// Move the ghost to where we will be
			ghost.offset((int)velocityX, (int)velocityY);
			
			// If the ghost collides with anything, put us against the object
			int colObjectIndex = collision(ghost);
			if(colObjectIndex != -1)
			{
				Log.i("Collision", "Future collision detected with " + colObjectIndex);
				GameObject colObject = Global.solidObjects.get(colObjectIndex);		
				Log.i("Player Position", "(" + this.positionX + ", " + this.positionY + ")");
				Log.i("Object Position", "(" + colObject.positionX + ", " + colObject.positionY + ")");

				// find the direction of collision
				if ((this.positionY + this.sizeY) <= colObject.positionY) {
					Log.i("Collision", "Top!");
					// top
					// put our feet on the ground
					this.positionY = colObject.positionY - this.sizeY;
					velocityY = 0;
					positionX += velocityX;
				}
				else if (this.positionY > (colObject.positionY + colObject.sizeY)) {
					Log.i("Collision", "Bottom!");
					// bottom
					// put our head on the object
					this.positionY = colObject.positionY + colObject.sizeY;
					velocityY = 0;
					positionX += velocityX;
				}
				else if (this.positionX > colObject.positionX) {
					Log.i("Collision", "Right!");
					// right
					// put ourselves next to the object
					this.positionX = colObject.positionX + colObject.sizeX;
					velocityX = 0;
					positionY += velocityY;
					velocityY +=5;
				}
				else {
					Log.i("Collision", "Left!");
					// left
					// put ourselves next to the object
					this.positionX = colObject.positionX - this.sizeX;
					velocityX = 0;
					positionY += velocityY;
					velocityY +=5;
				}
				colWall();
				onFloor();
				spriteRect.offsetTo(positionX, positionY);
			}
			// otherwise, keep moving
			else
			{
				positionX += velocityX;
				positionY += velocityY;
				velocityY +=5;
				colWall();
				onFloor();
				spriteRect.offsetTo(positionX, positionY);
			}
			
			// Friction!
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
			colWall();
		}
	}
	private void onFloor(){
		if(positionY > (Global.metrics.heightPixels - sizeY))
		{
			positionY = Global.metrics.heightPixels - sizeY;
			velocityY = 0;
		}
	}
	private void colWall(){
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
	}
	
	/*
	 * Possible site of major bug!
	 * 
	 * If the hardware isn't able to iterate over all solidObjects quickly enough, we could 
	 * miss a collision. That probably won't happen for a while. If it does, we'll need to 
	 * compartmentalize the level and check the nearest three compartments (left, center, right)
	 * for collision.
	 *   |        0          |         1         |         2         |         3         |
	 *   |                   |       check       |       check       |       check       |
	 *   |                   |                   |                   |                   |
	 *   |                   |                   |                   |                   |
	 *   |                   |                   |         O         |                   |
	 *   |                   |                   |        /|\        |                   |
	 *   |___________________|___________________|________/_\________|___________________|____
	 */
	private int collision(Rect ghost) {
		for (int i = 0; i < Global.solidObjects.size(); i++) {
			if(ghost.intersect(Global.solidObjects.get(i).spriteRect))
			{	
				return i;
			}
		}
		return -1;
	}
	
	public void jumps()
	{
		if(velocityY <= 5)	//assume on jumpable platform if velY <= 5
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
	// ref: http://stackoverflow.com/a/7852459
	public void startRunRights() {
		Global.setRunning(true);
		new Thread(new Runnable() {
			public void run() {
				while(Global.isRunning()) {
					// if we can run faster, do so
					if (velocityX < MAX_H_SPEED) {
						velocityX += runSpeed;
					}
					// if we're moving too fast, reset to MAX_H_SPEED
					else if (velocityX > MAX_H_SPEED) {
						velocityX = MAX_H_SPEED;
					}
					// else, velocityX == MAX_H_SPEED, and we do nothing
				}
			}
		}).start();
	}
	public void startRunLefts() {
		Global.setRunning(true);
		new Thread(new Runnable() {
			public void run() {
				while(Global.isRunning()) {
					// if we can run faster, do so
					if (velocityX > -MAX_H_SPEED) {
						velocityX -= runSpeed;
					}
					// if we're moving too fast, reset to MAX_H_SPEED
					else if (velocityX < -MAX_H_SPEED) {
						velocityX = -MAX_H_SPEED;
					}
					// else, velocityX == -MAX_H_SPEED, and we do nothing
				}
			}
		}).start();
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
