package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;


class GameThread extends Thread{
	  private int canvasWidth = Global.screenWidth;
	  private int canvasHeight = Global.screenHeight;
	  private static final int SPEED = 2;
	  private boolean run = false;
	  
	  private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	  private Context ctx = null;
	  private SurfaceHolder sh = null;
	  
	  private float bubbleX;
	  private float bubbleY;
	  private float headingX;
	  private float headingY;
	    
	  public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		sh = surfaceHolder;
	    ctx = context;
	  }
	  public void doStart() {
	    synchronized (sh) {
	      // Start bubble in center and create some random motion
	      bubbleX = canvasWidth / 2;
	      bubbleY = canvasHeight / 2;
	      headingX = (float) (-1 + (Math.random() * 2));
	      headingY = (float) (-1 + (Math.random() * 2));
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
	      canvasWidth = width;
	      canvasHeight = height;
	      doStart();
	    }
	  }
	  private void doDraw(Canvas canvas) {
	    bubbleX = bubbleX + (headingX * SPEED);
	    bubbleY = bubbleY + (headingY * SPEED);
	    canvas.restore();
	    canvas.drawColor(Color.BLACK);
	    canvas.drawCircle(bubbleX, bubbleY, 50, paint);
	  }
	}