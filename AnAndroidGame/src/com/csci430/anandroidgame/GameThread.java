package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
//import android.util.Log;
import android.view.SurfaceHolder;



//@SuppressWarnings("unused")
class GameThread extends Thread{
	  private boolean run = false;
	  private SurfaceHolder sh;
	  protected Context ctx;
	  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	  private static GameObject player;
	  private static GameObject backGround;
	  private static GameObject thingy;
	  
	  
	  public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		sh = surfaceHolder;
	    ctx = context;
	  }
	  
	  public void doStart() {
	    synchronized (sh) {
	    	//player
	    	paint.setColor(Color.BLUE);
		    paint.setStyle(Style.FILL);
		    player = new GameObject(0, 35, 60, 100, 100, paint, sh);
		    // /player
		    
		    //background
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.GREEN);
			paint.setStyle(Style.FILL);  
		    backGround = new GameObject(2, Global.metrics.widthPixels, Global.metrics.heightPixels, 0, 0, paint, sh);
		    // /background
		    
		    //Other Objects
		    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    paint.setColor(Color.GRAY);
			paint.setStyle(Style.FILL);  
		    thingy = new GameObject(2, 200, 50, 0, (Global.metrics.heightPixels - 50), paint, sh);
		    
		    //Load into array for use.
		    Global.worldObjects.add(player);
		    Global.worldObjects.add(backGround);
		    Global.worldObjects.add(thingy);
	    	
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
		    
		  canvas.drawRect(Global.worldObjects.get(1).getSprite(), Global.worldObjects.get(1).getPaint());
		  canvas.drawRect(Global.worldObjects.get(0).getSprite(), Global.worldObjects.get(0).getPaint());
		  canvas.drawRect(Global.worldObjects.get(2).getSprite(), Global.worldObjects.get(2).getPaint());
		  
		  
		  canvas.restore();
	  }
	  
	}