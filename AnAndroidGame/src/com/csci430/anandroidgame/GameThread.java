package com.csci430.anandroidgame;

import java.util.Vector;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;



//@SuppressWarnings("unused")
class GameThread extends Thread{
	  private boolean run = false;
	  private SurfaceHolder sh;
	  protected Context ctx;
	  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	  
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
	  public static final int BLOCK_SIZE = 50;
		
	  public static boolean currentlyRunning = false;
	  
	  
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
		    player = new GameObject(0, 1, 1, 0, 10, paint, sh, ctx);
		    worldObjects.add(player);
		    playerIndex = worldObjects.size() - 1;
		    // /player
		    
		    //background
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.DKGRAY);
			paint.setStyle(Style.FILL);  
		    backGround = new GameObject(2, 0, 0, paint, sh);
		    worldObjects.add(backGround);
		    backgroundIndex = worldObjects.size() -1;
		    // /background
		    

		    generatePlatform(2, 21, 1, 0, 0, ctx, sh, Color.LTGRAY);	//floor
		    generatePlatform(2, 3, 1, 4, 2, ctx, sh, Color.LTGRAY);	//Platforms
			generatePlatform(2, 3, 1, 8, 4, ctx, sh, Color.LTGRAY);
			generatePlatform(2, 3, 1, 12, 6, ctx, sh, Color.LTGRAY);
			generatePlatform(2, 3, 1, 16, 8, ctx, sh, Color.YELLOW);	//victoryPlatform
		    
		    Sound.track1(ctx);
	    }
	  }
	  
	  void generatePlatform(int type, int sizeX, int sizeY, int posX, int posY, Context ctx, SurfaceHolder shIn, int color)
	  {
		  paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		  paint.setColor(color);
		  paint.setStyle(Style.FILL);
		  solidObject = new GameObject(type, sizeX, sizeY, posX, posY, paint, shIn, ctx);
		  solidObjects.add(solidObject);
	  }
	  
	  
	  public void run() {
	    while (run) {
	      Canvas c = null;
	      try {
	        c = sh.lockCanvas(null);
	        synchronized (sh) {
	          doDraw(c);
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
			  
			  //DrawBackgroud
			  canvas.drawRect(worldObjects.get(backgroundIndex).getSprite(), worldObjects.get(backgroundIndex).getPaint());
		
			  //DrawPlayer
			  canvas.drawBitmap(worldObjects.get(playerIndex).getSpriteGraphic(), worldObjects.get(playerIndex).getPosX(), worldObjects.get(playerIndex).getPosY(), null);
			  
			  //Draw Platforms
			  for (int i = 0; i < solidObjects.size(); i++) {
				  canvas.drawRect(solidObjects.get(i).getSprite(), solidObjects.get(i).getPaint());
			  }
		  }
		  canvas.restore();
	  }
	}