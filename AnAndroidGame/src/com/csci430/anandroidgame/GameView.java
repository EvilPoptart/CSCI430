package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// ref: https://www.linux.com/learn/tutorials/707993-how-to-draw-2d-object-in-android-with-a-canvas
/*
 * Along with GameThread, GameView handles the displaying of our game.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceH;
    private Context mCtx = null;
    private GameThread mThread;

	/*
	 * Constructors
	 * ========================================================================
	 */
    
    public GameView(Context context) {
        super(context);
        mSurfaceH = getHolder();
        mSurfaceH.addCallback(this);
        mCtx = context;
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceH = getHolder();
        mSurfaceH.addCallback(this);
        mCtx = context;
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSurfaceH = getHolder();
        mSurfaceH.addCallback(this);
        mCtx = context;
        setFocusable(true);
    }
    
    /*
     * Get-ers
     */

    public GameThread getThread() {
        return mThread;
    }
    
    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
     * 
     * Overloading of function surfaceDestroyed.
     * Will be called when the surface (gameCamvas in /AnAndroidGame/res/layout/activity_fullscreen.xml) is created.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mSurfaceH.lockCanvas();
        mSurfaceH.unlockCanvasAndPost(canvas);

        mThread = new GameThread(mSurfaceH, mCtx, new Handler());
        mThread.setRunning(true);
        mThread.start();
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
     * 
     * Overloading of function surfaceDestroyed.
     * Will be called when the surface (gameCamvas in /AnAndroidGame/res/layout/activity_fullscreen.xml) is changed.
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {   
        mThread.setSurfaceSize(width, height);
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
     * 
     * Overloading of function surfaceDestroyed.
     * Will be called when the surface (gameCamvas in /AnAndroidGame/res/layout/activity_fullscreen.xml) is destroyed.
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            	Log.e("Exception", "Exception thrown: " + e);
            }
        }
    }
}