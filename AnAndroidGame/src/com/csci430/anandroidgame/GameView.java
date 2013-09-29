package com.csci430.anandroidgame;

import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	SurfaceHolder _sh;
    
    public GameView (Context context) {
        super(context);
    }
 
    public GameView (Context context, AttributeSet attrs) {
        super(context);
    }
 
    @Override
    public void onDraw (Canvas canvas) {
        canvas.drawColor(Color.GREEN);
    }
 
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
    	}
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	Thread drawThread = new Thread(this);
    	drawThread.start();
    	}
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		Canvas c;
	    while (true) {
	        c = null;
	        try {
	            c = _sh.lockCanvas(null);
	            synchronized (_sh) {
	                onDraw(c);
	            }
	        } finally {
	            // do this in a finally so that if an exception is thrown
	            // during the above, we don't leave the Surface in an
	            // inconsistent state
	            if (c != null) {
	            	_sh.unlockCanvasAndPost(c);
	            }
	        }
	    }
	}
 
}