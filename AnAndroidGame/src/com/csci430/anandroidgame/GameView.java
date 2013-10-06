package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// ref: https://www.linux.com/learn/tutorials/707993-how-to-draw-2d-object-in-android-with-a-canvas
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	GameThread thread;
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Context ctx = null;
	
	public GameView(Context context) {
		super(context);
	    sh = getHolder();
	    sh.addCallback(this);
	    paint.setColor(Color.BLUE);
	    paint.setStyle(Style.FILL);
		ctx = context;
		setFocusable(true); // make sure we get key events
	}
	
	public GameThread getThread() {
		return thread;
	}
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new GameThread(sh, ctx, new Handler());
		thread.setRunning(true);
		thread.start();
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {      
		thread.setSurfaceSize(width, height);
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
			while (retry) {
				try {
					thread.join();
					retry = false;
				} catch (InterruptedException e) {
			}
		}
	}
}