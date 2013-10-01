package com.csci430.anandroidgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceH;
    private final Paint mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mCtx = null;
    private GameThread mThread;

    public GameView(Context context) {
        super(context);
        mSurfaceH = getHolder();
        mSurfaceH.addCallback(this);
        mPaint1.setColor(Color.BLUE);
        mPaint1.setStyle(Style.FILL);
        mPaint2.setColor(Color.GREEN);
        mPaint2.setStyle(Style.FILL);
        mCtx = context;
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceH = getHolder();
        mSurfaceH.addCallback(this);
        mPaint1.setColor(Color.BLUE);
        mPaint1.setStyle(Style.FILL);
        mPaint2.setColor(Color.GREEN);
        mPaint2.setStyle(Style.FILL);
        mCtx = context;
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSurfaceH = getHolder();
        mSurfaceH.addCallback(this);
        mPaint1.setColor(Color.BLUE);
        mPaint1.setStyle(Style.FILL);
        mPaint2.setColor(Color.GREEN);
        mPaint2.setStyle(Style.FILL);
        mCtx = context;
        setFocusable(true);
    }

    public GameThread getThread() {
        return mThread;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mSurfaceH.lockCanvas();
        mSurfaceH.unlockCanvasAndPost(canvas);

        mThread = new GameThread(mSurfaceH, mCtx, new Handler());
        mThread.setRunning(true);
        mThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {   
        mThread.setSurfaceSize(width, height);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //TODO: Catch the exception
            }
        }
    }

	class GameThread extends Thread {
	    private int mCanvasWidth = 200;
	    private int mCanvasHeight = 400;
	    private volatile int mVelocity = 1;
	    private boolean mRun = false;
	
	    private float mObjX;
	    private float mObjY;
	    private float mHeadingX;
	    private float mHeadingY;
	    
	    // Player creation
	    WindowManager wm = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    Point size = new Point();
	    Player p1;
	    Graphic bg;
	    	
	    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		    mSurfaceH = surfaceHolder;
		    mCtx = context;
	    }
	
	    @SuppressLint("NewApi")
	        public void doStart() {
	        synchronized (mSurfaceH) {
	            // Start bubble in centre and create some random motion
	            mObjX = mCanvasWidth / 2;
	            mObjY = mCanvasHeight / 2;
	            //mHeadingX = (float) (-1 + (Math.random() * 2));
	            //mHeadingY = (float) (-1 + (Math.random() * 2));
	            mHeadingX = 1;
	            mHeadingY = 0;

	    	    display.getSize(size);
	    	    int width = size.x;
	    	    int height = size.y;
	    	    p1 = new Player(35, 60, 0, 0, mPaint1);
	    	    bg = new Graphic(400, height, 0, 0, mPaint2);
	        }
	    }
	
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
	
	    public void setRunning(boolean b) { 
	        mRun = b;
	    }
	    
	    public void setSurfaceSize(int width, int height) {
	        synchronized (mSurfaceH) {
	            mCanvasWidth = width;
	            mCanvasHeight = height;
	            doStart();
	        }
	    }
	    
	    private void doDraw(Canvas canvas) {
	    	// Lock the canvas for editing
	        canvas.save();
	        // If we're at top, botton, left, or right edge
	        if (mObjX <= 0 || mObjX >= mCanvasWidth || mObjY <= 0 || mObjY >= mCanvasHeight) {
	            // Invert our direction
	            mVelocity = -mVelocity;
	        }
	        
	        mObjX = mObjX + (mHeadingX * mVelocity);
	        mObjY = mObjY + (mHeadingY * mVelocity);
	        // Unlock the canvas
	        canvas.restore();

        	// The background will never be updated, so just call draw()
	        bg.draw(canvas);
	        
	        // Update and draw the player
	        p1.updateAndDraw(canvas);
	        
	        //canvas.drawCircle(mObjX, mObjY, 50, mPaint);
	    }
    }
}