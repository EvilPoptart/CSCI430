package com.csci430.anandroidgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

// ref: https://www.linux.com/learn/tutorials/707993-how-to-draw-2d-object-in-android-with-a-canvas

/*
 * The thread that draws/updates the canvas
 */
class GameThread extends Thread {
    private boolean mRun = false;
    private SurfaceHolder mSurfaceH;
    protected Context mCtx;

    private final Paint mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    // Player creation
    Player p1;
    Graphic bg;

	/*
	 * Constructors
	 * ========================================================================
	 */
    
    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
	    mSurfaceH = surfaceHolder;
	    mCtx = context;
        mPaint1.setColor(Color.BLUE);
        mPaint1.setStyle(Style.FILL);
        mPaint2.setColor(Color.GREEN);
        mPaint2.setStyle(Style.FILL);
    }

    /*
     * Start the thread
     */
    @SuppressLint("NewApi")
    /* 
     * We're aware of the NewApi error caused by
     * 	display.getSize(size);
     * and have chosen to keep it to support devices running > Android 3.2 (API 13)
     */
	@SuppressWarnings("deprecation")
    /* 
     * We're aware of the deprecation error caused by
     * 	height = display.getHeight();
     * and have chosen to keep it to support devices running < Android 3.2 (API 13)
     */
	public void doStart() {
        synchronized (mSurfaceH) {
            WindowManager wm = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
        	int height = 0;
        	int width = 0;
        	
        	// Check our working API version and run the appropriate commands
        	if (android.os.Build.VERSION.SDK_INT >= 13) {
	        	// Get device height and width
	            Point size = new Point();
	    	    display.getSize(size);
	    	    width = size.x;
	    	    height = size.y;
        	} else {
	        	width = display.getWidth();  // deprecated
	        	height = display.getHeight();  // deprecated
        	}
    	    
    	    // Create our player
    	    p1 = new Player(35, 60, 200, 0, mPaint1, mCtx);
    	    
    	    // Create the background in lovely bright green
    	    bg = new Graphic(width, height, 0, 0, mPaint2, mCtx);
        }
    }
    
    /*
     * Running
	 * ========================================================================
     */

    /* 
     * Controls the running of this thread. As long as the thread is running, our game is also running.
     */
    public void run() {
        while (mRun) {
            Canvas c = null;
            try {
                c = mSurfaceH.lockCanvas(null);
                synchronized (mSurfaceH) {
                    doDraw(c);
                }
            } finally {
                if (c != null) {
                    mSurfaceH.unlockCanvasAndPost(c);
                }
            }
        }
    }

    /* 
     * Set-ers
	 * ========================================================================
     */
    
    public void setRunning(boolean b) { 
        mRun = b;
    }
    
    public void setSurfaceSize(int width, int height) {
        synchronized (mSurfaceH) {
            doStart();
        }
    }
    
    /*
     * Drawing
	 * ========================================================================
     */
    
    /* 
     * Draw all objects to the canvas.
     * 
     * TODO: instead of drawing the objects manually, iterate over an array of all objects
     * to be drawn and draw each in turn.
     */
    private void doDraw(Canvas canvas) {
    	// Lock the canvas for editing
        canvas.save();

    	// The background will never be updated, so just call draw()
        bg.draw(canvas);
        
        // Update and draw the player
        p1.updateAndDraw(canvas);

        // Unlock the canvas
        canvas.restore();
    }
}