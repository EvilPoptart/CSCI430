package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.SurfaceHolder;



//@SuppressWarnings("unused")
class GameThread extends Thread{
	  private boolean run = false;
	  private SurfaceHolder sh;
	  protected Context ctx;
	  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	  private static GameObject player;
	  private static GameObject backGround;
	  private static GameObject floor;
	  private static GameObject platform1;
	  private static GameObject platform2;
	  private static GameObject platform3;
	  private static GameObject platform4;
	  
	  
	  public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		sh = surfaceHolder;
	    ctx = context;
	  }
	  
	  public void doStart() {
	    synchronized (sh) {
	    	//player
	    	paint.setColor(Color.BLUE);
		    paint.setStyle(Style.FILL);
		    player = new GameObject(0, 1, 1, 0, 10, paint, sh);
		    // /player
		    
		    //background
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.BLACK);
			paint.setStyle(Style.FILL);  
		    backGround = new GameObject(2, 0, 0, paint, sh);
		    // /background

		    //Other Objects
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.LTGRAY);
			paint.setStyle(Style.FILL);
		    floor = new GameObject(2, 21, 1, 0, 0, paint, sh);


		    //Other Objects
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.LTGRAY);
			paint.setStyle(Style.FILL);
			platform1 = new GameObject(2, 3, 1, 4, 2, paint, sh);

		    //Other Objects
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.LTGRAY);
			paint.setStyle(Style.FILL);
			platform2 = new GameObject(2, 3, 1, 8, 4, paint, sh);

		    //Other Objects
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.LTGRAY);
			paint.setStyle(Style.FILL);
			platform3 = new GameObject(2, 3, 1, 12, 6, paint, sh);

		    //Other Objects
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.YELLOW);
			paint.setStyle(Style.FILL);
			platform4 = new GameObject(2, 3, 1, 16, 8, paint, sh);

		    //Load into array for use.
		    Global.worldObjects.add(player);
		    Global.playerIndex = Global.worldObjects.size() - 1;
		    Global.worldObjects.add(backGround);
		    Global.backgroundIndex = Global.worldObjects.size() -1;

		    Global.solidObjects.add(floor);
		    Global.solidObjects.add(platform1);
		    Global.solidObjects.add(platform2);
		    Global.solidObjects.add(platform3);
		    Global.solidObjects.add(platform4);
		    
		    Sound.track1(ctx);
	    }
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
		  
		  Global.worldObjects.get(0).tickUpdate();	//update player (currently only checks object.0 vs object.2)
		    
		  canvas.drawRect(Global.worldObjects.get(Global.backgroundIndex).getSprite(), Global.worldObjects.get(Global.backgroundIndex).getPaint());
		  canvas.drawRect(Global.worldObjects.get(Global.playerIndex).getSprite(), Global.worldObjects.get(Global.playerIndex).getPaint());
			for (int i = 0; i < Global.solidObjects.size(); i++) {
				canvas.drawRect(Global.solidObjects.get(i).getSprite(), Global.solidObjects.get(i).getPaint());
			}
		  
		  
		  canvas.restore();
	  }
	  
	}