package com.csci430.anandroidgame;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;



//@SuppressWarnings("unused")
class GameThread extends Thread{
	  private static boolean run = false;
	  private SurfaceHolder sh;
	  protected Context ctx;
	  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	  int cameraX = 9;
	  
	  private static GameObject player;
	  private static GameObject backGround;
	  public static Vector<GameObject> worldObjects;
	  public static Vector<GameObject> solidObjects;
	  public static DisplayMetrics metrics;
		
	  public Sound sounds;
	  public static int playerIndex;
	  public static int backgroundIndex;
	  public static int doorIndex;
	  public static final int BLOCK_SIZE = 47;
	  public static final int LEVEL_WIDTH = 29;
	  public static final int LEVEL_HEIGHT = 15;
		
	  public static boolean currentlyRunning = false;
	  
	  // ref: http://obviam.net/index.php/sprite-animation-with-android/
	  // desired fps
	  private final static int 	MAX_FPS = 50;	
	  // maximum number of frames to be skipped
	  private final static int	MAX_FRAME_SKIPS = 5;	
	  // the frame period
	  private final static int	FRAME_PERIOD = 1000 / MAX_FPS;
	  
	  
	  public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		sh = surfaceHolder;
	    ctx = context;
		worldObjects = new Vector<GameObject>();
		solidObjects = new Vector<GameObject>();
	  }
	  
	  public static void jump() {
			worldObjects.get(playerIndex).jumps();
		}
		public static void runLeft() {
			worldObjects.get(playerIndex).runLefts();
		}
		public static void runRight() {
			worldObjects.get(playerIndex).runRights();
		}
		public static void startRunningRight() {
			worldObjects.get(playerIndex).startRunRights();
		}
		public static void startRunningLeft() {
			worldObjects.get(playerIndex).startRunLefts();
		}
		public static void setIsRunning(boolean areWeRunning) {
			currentlyRunning = areWeRunning;
		}
		public static boolean isRunning() {
			return currentlyRunning;
		}
	  
	  public void doStart() {
	    synchronized (sh) {
	    	genLevel();
	    	Log.d("l1", "end of doStart()");
	    }
	  }
	  
	  void genLevel() {
		  switch (Global.curLevelId) {
		  case 1:
		    	genPlayer();
		    	genBG(0xffA9FFE1);
		    	genDoor(2, 2);
		    	generatePlatform(LEVEL_WIDTH+2, -1, 0, "grass");
			    Sound.track1(ctx);
			  break;
		  case 2: 
		    	genPlayer();
		    	genBG(0xffA9FFE1);
		    	genDoor(5, 1);
		    	generatePlatform(LEVEL_WIDTH+2, -1, 0, "grass");
			  break;
		  default:
	    	genPlayer();
	    	genBG(0xffA9FFE1);
	    	genDoor(5, 1);
			  break;
		  }
	  }
	  
	  void genPlayer() {
		    player = new GameObject(0, 1, 1, 0, 10, 5, sh, ctx, "player");
		    worldObjects.add(player);
		    playerIndex = worldObjects.size() - 1;
	  }
	  
	  void genBG(int color) {
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(color);
			paint.setStyle(Style.FILL);  
		    backGround = new GameObject(2, LEVEL_WIDTH, LEVEL_HEIGHT, 0, 0, sh, ctx, paint);
		    worldObjects.add(backGround);
		    backgroundIndex = worldObjects.size() -1;
	  }
	  
	  void genDoor(int posX, int posY) {
		    worldObjects.add(new GameObject(3, 1, 1, posX, posY, sh, ctx, "door_open_mid"));
		    doorIndex = worldObjects.size() - 1;
		    worldObjects.add(new GameObject(3, 1, 1, posX, posY+1, sh, ctx, "door_open_top"));
		  
	  }
	  void generatePlatform(int sizeX, int posX, int posY, String tileSetName)
	  {
		  if (sizeX > 0) {
			  if (tileSetName == "grass") {
				  switch (sizeX) {
				  case 1:
					  solidObjects.add(new GameObject(2, 1, 1, posX, posY, sh, ctx, "grass"));
					  break;
				  case 2:
					  solidObjects.add(new GameObject(2, 1, 1, posX, posY, sh, ctx, "grass_left"));
					  solidObjects.add(new GameObject(2, 1, 1, posX+1, posY, sh, ctx, "grass_right"));
					  break;
				  default:
					  solidObjects.add(new GameObject(2, 1, 1, posX, posY, sh, ctx, "grass_left"));
					  for (int i = 0; i < sizeX - 2; i ++) {
						  solidObjects.add(new GameObject(2, 1, 1, posX+1+i, posY, sh, ctx, "grass_mid"));
					  }
					  solidObjects.add(new GameObject(2, 1, 1, posX+sizeX-1, posY, sh, ctx, "grass_right"));
					  break;
				  }
			  }
		  }
	  }
	  
	  
	  public void run() {
		  // ref: http://obviam.net/index.php/sprite-animation-with-android/
		long beginTime;		// the time when the cycle begun
		long timeDiff;		// the time it took for the cycle to execute
		int sleepTime;		// ms to sleep (<0 if we're behind)
		int framesSkipped;	// number of frames being skipped 

		sleepTime = 0;
	    while (run) {
	      Canvas c = null;
	      try {
	        c = sh.lockCanvas(null);
	        synchronized (sh) {
	      	  // ref: http://obviam.net/index.php/sprite-animation-with-android/
				beginTime = System.currentTimeMillis();
				framesSkipped = 0;	// resetting the frames skipped
		          doDraw(c);				
				// calculate how long did the cycle take
				timeDiff = System.currentTimeMillis() - beginTime;
				// calculate sleep time
				sleepTime = (int)(FRAME_PERIOD - timeDiff);
				
				if (sleepTime > 0) {
					// if sleepTime > 0 we're OK
					try {
						// send the thread to sleep for a short period
						// very useful for battery saving
						Thread.sleep(sleepTime);	
					} catch (InterruptedException e) {}
				}
				
				while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
					// we need to catch up
					//this.gamePanel.update(); // update without rendering
					sleepTime += FRAME_PERIOD;	// add frame period to check if in next frame
					framesSkipped++;
				}
	        }
	      } finally {
	        if (c != null) {
	          sh.unlockCanvasAndPost(c);
	        }
	      }
	    }
	  }
	    
	  public static void setRunning(boolean b) { 
		  run = b;
	  }
	  
	  public void setSurfaceSize(int width, int height) {
		  synchronized (sh) {
			  doStart();
		  }
	  }
	  
	  private void doDraw(Canvas canvas) {
		  canvas.save();
		  if(worldObjects.size() > 0){
			  Log.d("asdf", "1");
			  worldObjects.get(playerIndex).tickUpdate();	//update player (currently only checks object.0 (player) vs object.2("solid Objects"))

			  Log.d("asdf", "2");
			  // Move the canvas around the x axis while the player is sufficiently away from the edges of the level.
			  // If the player is at the far left, cameraX will be 0.
			  if (worldObjects.get(playerIndex).getPosX() > (BLOCK_SIZE * 5)) {
				  // Move camera with the player
				  cameraX = (BLOCK_SIZE * 5 - worldObjects.get(playerIndex).getPosX());
				  // Stop the camera if we're at the rightmost side of the level
	  			  if (worldObjects.get(playerIndex).getPosX() > (BLOCK_SIZE * LEVEL_WIDTH - (metrics.widthPixels - BLOCK_SIZE * 5))) {
					  cameraX = (metrics.widthPixels - BLOCK_SIZE * LEVEL_WIDTH);
	  			  }
			  }
			  Log.d("asdf", "3");
			  
			  // Set the camera to an appropriate x-offset
			  canvas.translate(cameraX, 0);

			  Log.d("asdf", "4");
			  //DrawBackgroud
			  canvas.drawRect(worldObjects.get(backgroundIndex).getSprite(), worldObjects.get(backgroundIndex).getPaint());
			  
			  Log.d("asdf", "5");
			  // Draw door
			  canvas.drawBitmap(worldObjects.get(doorIndex).getSpriteGraphic(), worldObjects.get(doorIndex).getPosX(), worldObjects.get(doorIndex).getPosY(), null);
			  canvas.drawBitmap(worldObjects.get(doorIndex+1).getSpriteGraphic(), worldObjects.get(doorIndex+1).getPosX(), worldObjects.get(doorIndex+1).getPosY(), null);

			  Log.d("asdf", "6");
			  //Draw worldObjects
			  /*
			  if (worldObjects.size() > 4) {
				  for (int i = 4; i < worldObjects.size(); i++) {
					  canvas.drawBitmap(solidObjects.get(i).getSpriteGraphic(), solidObjects.get(i).getPosX(), solidObjects.get(i).getPosY(), null);
				  }
			  }
			  */
			  
			  //Draw solid Objects
			  for (int i = 0; i < solidObjects.size(); i++) {
				  canvas.drawBitmap(solidObjects.get(i).getSpriteGraphic(), solidObjects.get(i).getPosX(), solidObjects.get(i).getPosY(), null);
			  }
			  Log.d("asdf", "7");
			  
			  //DrawPlayer
			  worldObjects.get(playerIndex).updateAnimation(System.currentTimeMillis());
			  canvas.drawBitmap(worldObjects.get(playerIndex).getSpriteGraphic(), worldObjects.get(playerIndex).spriteSheetLoc, worldObjects.get(playerIndex).getSprite(), null);

			  Log.d("asdf", "8");

		  }
		  canvas.restore();
	  }
	}