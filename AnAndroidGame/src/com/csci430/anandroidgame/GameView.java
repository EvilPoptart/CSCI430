package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// ref: https://www.linux.com/learn/tutorials/707993-how-to-draw-2d-object-in-android-with-a-canvas
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	GameThread thread;
	private SurfaceHolder sh;
	private GameThread mThread;
	private Context ctx = null;

	/*
	 * Constructors
	 * ========================================================================
	 */

	public GameView(Context context) {
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		ctx = context;
		setFocusable(true);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sh = getHolder();
		sh.addCallback(this);
		ctx = context;
		setFocusable(true);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		sh = getHolder();
		sh.addCallback(this);
		ctx = context;
		setFocusable(true);
	}

	/*
	 * Get-ers
	 */

	public GameThread getThread() {
		return mThread;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = sh.lockCanvas();
		sh.unlockCanvasAndPost(canvas);

		thread = new GameThread(sh, ctx, new Handler());
		GameThread.setRunning(true);
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		GameThread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}