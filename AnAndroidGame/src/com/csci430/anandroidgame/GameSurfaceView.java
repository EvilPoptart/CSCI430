package com.csci430.anandroidgame;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Canvas;
import android.graphics.Color;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	  private SurfaceHolder sh;
	  private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	  
	  public GameSurfaceView(Context context) {
	    super(context);
	    sh = getHolder();
	    sh.addCallback(this);
	    paint.setColor(Color.BLUE);
	    paint.setStyle(Style.FILL);
	  }
	  
	  public void surfaceCreated(SurfaceHolder holder) {
	    Canvas canvas = sh.lockCanvas();
	    canvas.drawColor(Color.BLACK);
	    canvas.drawCircle(100, 200, 50, paint);
	    sh.unlockCanvasAndPost(canvas);
	  }
	  
	  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	  }
	  
	  public void surfaceDestroyed(SurfaceHolder holder) {
	  }
}
