package com.csci430.anandroidgame;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.BitmapRegionDecoder;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;



//@SuppressWarnings("unused")
class GameThread extends Thread{
	  private boolean run = false;
	  private SurfaceHolder sh;
	  protected Context ctx;
	  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	  int cameraX = 9;

	  
	  private static GameObject player;
	  private static GameObject backGround;
	  private static GameObject solidObject;
	  public static Vector<GameObject> worldObjects;
	  public static Vector<GameObject> solidObjects;
	  public static Vector<Level> levels;
	  public static DisplayMetrics metrics;
		
	  public Sound sounds;
	  public static int playerIndex;
	  public static int backgroundIndex;
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
		levels = new Vector<Level>();
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
	    	//player
	    	paint.setColor(Color.BLUE);
		    paint.setStyle(Style.FILL);
		    player = new GameObject(0, 1, 1, 0, 10, 5, sh, ctx, "player");
		    worldObjects.add(player);
		    playerIndex = worldObjects.size() - 1;
		    // /player
		    
		    //background
		    int skyBlue = 0xffA9FFE1;
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(skyBlue);
			paint.setStyle(Style.FILL);  
		    //backGround = new GameObject(2, 0, 0, paint, sh);
		    backGround = new GameObject(2, LEVEL_WIDTH, LEVEL_HEIGHT, 0, 0, sh, ctx, paint);
		    worldObjects.add(backGround);
		    backgroundIndex = worldObjects.size() -1;
		    // /background
		    
		    //floor
    		for (int i = 0; i < LEVEL_WIDTH; i++) {
    		    generatePlatform(1, 1, i, 0, ctx, sh, "grass_mid");	
    		}
    		
    		//Platforms
    		int platformWidth;
    		generatePlatform(1, 1, 7, 2, ctx, sh, "grass_left");
		    generatePlatform(1, 1, 8, 2, ctx, sh, "grass_right");
    		
		    generatePlatform(1, 1, 10, 4, ctx, sh, "grass_left");
		    generatePlatform(1, 1, 11, 4, ctx, sh, "grass_right");
    		
		    generatePlatform(1, 1, 13, 6, ctx, sh, "grass_left");
		    generatePlatform(1, 1, 14, 6, ctx, sh, "grass_right");

		    generatePlatform(1, 1, 16, 6, ctx, sh, "grass_left");
		    generatePlatform(1, 1, 17, 6, ctx, sh, "grass_mid");
		    generatePlatform(1, 1, 18, 6, ctx, sh, "grass_right");
		    
		    platformWidth = 10;
    		generatePlatform(1, 1, 14, 2, ctx, sh, "grass_left");
    		for (int i = 0; i < platformWidth; i++) {
    		    generatePlatform(1, 1, (15 + i), 2, ctx, sh, "grass_mid");
    		}
		    generatePlatform(1, 1, (15 + platformWidth), 2, ctx, sh, "grass_right");
			
		    Sound.track1(ctx);
	    }
	  }
	  
	  void generatePlatform(int sizeX, int sizeY, int posX, int posY, Context ctx, SurfaceHolder shIn, String tileSetName)
	  {
		  solidObject = new GameObject(2, sizeX, sizeY, posX, posY, shIn, ctx, tileSetName);
		  solidObjects.add(solidObject);
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
	    
	  public void setRunning(boolean b) { 
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
			  worldObjects.get(playerIndex).tickUpdate();	//update player (currently only checks object.0 (player) vs object.2("solid Objects"))
	  		  
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
			  
			  // Set the camera to an appropriate x-offset
			  canvas.translate(cameraX, 0);
			  
			  //DrawBackgroud
			  canvas.drawRect(worldObjects.get(backgroundIndex).getSprite(), worldObjects.get(backgroundIndex).getPaint());
			  //canvas.drawBitmap(worldObjects.get(backgroundIndex).getSpriteGraphic(), worldObjects.get(backgroundIndex).getPosX(), worldObjects.get(backgroundIndex).getPosY(), null);
		
			  //DrawPlayer
			  worldObjects.get(playerIndex).updateAnimation(System.currentTimeMillis());
			  
			  
			  canvas.drawBitmap(worldObjects.get(playerIndex).getSpriteGraphic(), worldObjects.get(playerIndex).spriteSheetLoc, worldObjects.get(playerIndex).getSprite(), null);
				
			  
			  //Draw Platforms
			  for (int i = 0; i < solidObjects.size(); i++) {
				  //canvas.drawRect(solidObjects.get(i).getSprite(), solidObjects.get(i).getPaint());
				  canvas.drawBitmap(solidObjects.get(i).getSpriteGraphic(), solidObjects.get(i).getPosX(), solidObjects.get(i).getPosY(), null);
			  }
		  }
		  canvas.restore();
	  }
	}