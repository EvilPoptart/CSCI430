package com.csci430.anandroidgame;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

//@SuppressWarnings("unused")
class GameThread extends Thread {

	private SurfaceHolder sh;
	protected Context ctx;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	int cameraX = 9;
	int curFrame = 0;
	int curTime = 0;

	// GameThread's thread will run while run == true
	private static boolean run = false;
	private static GameObject player;
	private static GameObject backGround;
	public static Vector<GameObject> worldObjects;
	public static Vector<GameObject> solidObjects;
	public static Vector<GameObject> collectibleObjects;
	public static Vector<GameObject> numberObjects;
	public static DisplayMetrics metrics;

	public Sound sounds;
	public static int playerIndex;
	public static int backgroundIndex;
	public static int doorIndex;
	public static final int BLOCK_SIZE = 47;
	public static final int LEVEL_WIDTH = 35;
	public static final int LEVEL_HEIGHT = 15;

	public static boolean currentlyRunning = false;

	// ref: http://obviam.net/index.php/sprite-animation-with-android/
	// desired fps
	private final static int MAX_FPS = 50;
	// maximum number of frames to be skipped
	private final static int MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int FRAME_PERIOD = 1000 / MAX_FPS;
	
	public static int levelMaxTime;

	public GameThread(SurfaceHolder surfaceHolder, Context context,
			Handler handler) {
		sh = surfaceHolder;
		ctx = context;
		worldObjects = new Vector<GameObject>();
		solidObjects = new Vector<GameObject>();
		collectibleObjects = new Vector<GameObject>();
		numberObjects = new Vector<GameObject>();
	}

	public static void jump() {
		worldObjects.get(playerIndex).jumps();
	}

	public static void runLeft() {
		worldObjects.get(playerIndex).runLefts();
	}

	public static void runRight() {
		worldObjects.get(playerIndex).runRights();
	}

	public static void startRunningRight() {
		worldObjects.get(playerIndex).startRunRights();
	}

	public static void startRunningLeft() {
		worldObjects.get(playerIndex).startRunLefts();
	}

	public static void setIsRunning(boolean areWeRunning) {
		currentlyRunning = areWeRunning;
	}

	public static boolean isRunning() {
		return currentlyRunning;
	}

	public void doStart() {
		synchronized (sh) {
			genLevel();
		}
	}

	void genLevel() {
		switch (Global.curLevelId) {
		// Level 1
		case 1:
			genPlayer();
			genBG(0xffA9FFE1);
			genDoor(30, 9);
			genNumbers();

			genPlatform(3, 5, 3, "grass");
			genPlatform(3, 9, 6, "grass");
			genPlatform(3, 13, 8, "grass");
			genPlatform(2, 18, 8, "grass");
			genPlatform(1, 22, 8, "grass");
			genPlatform(1, 25, 8, "grass");
			genPlatform(3, 28, 8, "grass");
			genPlatform(12, 13, 3, "grass");

			genCoin(5, 4);
			genCoin(7, 4);
			genCoin(9, 7);
			genCoin(11, 7);
			genCoin(22, 9);
			genCoin(25, 9);

			genPlatform(LEVEL_WIDTH + 2, -1, 0, "grass");
			Sound.track1(ctx);
			levelMaxTime = 4;
			break;
		// Level 2
		case 2:
			genPlayer();
			genBG(0xffA9FFE1);
			genDoor(27, 8);
			genNumbers();

			genPlatform(2, 7, 3, "grass");
			genPlatform(2, 3, 6, "grass");
			genPlatform(6, 7, 8, "grass");

			genPlatform(1, 16, 4, "grass");
			genPlatform(1, 19, 5, "grass");
			genPlatform(1, 22, 6, "grass");
			genPlatform(3, 26, 7, "grass");

			genCoin(7, 5);
			genCoin(3, 7);
			genCoin(10, 9);
			genCoin(11, 9);
			genCoin(16, 5);
			genCoin(19, 6);
			genCoin(22, 7);
			genCoin(24, 9);

			genPlatform(LEVEL_WIDTH + 2, -1, 0, "grass");
			levelMaxTime = 4;
			break;
		default:
			genPlayer();
			genBG(0xffA9FFE1);
			genDoor(5, 1);
			levelMaxTime = 10;
			break;
		}
	}

	private void genNumbers() {
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "zero"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "one"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "two"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "three"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "four"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "five"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "six"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "seven"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "eight"));
		numberObjects.add(new GameObject(6, 1, 1, sh, ctx, "nine"));
	}

	void genCoin(int posX, int posY) {
		collectibleObjects.add(new GameObject(4, 1, 1, posX, posY, sh, ctx,
				"coin"));
	}

	void genPlayer() {
		int fps = 8;
		player = new GameObject(0, 1, 1, 0, 10, fps, sh, ctx, "player");
		worldObjects.add(player);
		playerIndex = worldObjects.size() - 1;
	}

	void genBG(int color) {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		backGround = new GameObject(2, LEVEL_WIDTH + 2, LEVEL_HEIGHT, -1, 0,
				sh, ctx, paint);
		worldObjects.add(backGround);
		backgroundIndex = worldObjects.size() - 1;
	}

	void genDoor(int posX, int posY) {
		worldObjects.add(new GameObject(3, 1, 1, posX, posY, sh, ctx,
				"door_open_mid"));
		doorIndex = worldObjects.size() - 1;
		worldObjects.add(new GameObject(3, 1, 1, posX, posY + 1, sh, ctx,
				"door_open_top"));

	}

	void genPlatform(int sizeX, int posX, int posY, String tileSetName) {
		if (sizeX > 0) {
			if (tileSetName == "grass") {
				switch (sizeX) {
				case 1:
					solidObjects.add(new GameObject(2, 1, 1, posX, posY, sh,
							ctx, "grass"));
					break;
				case 2:
					solidObjects.add(new GameObject(2, 1, 1, posX, posY, sh,
							ctx, "grass_left"));
					solidObjects.add(new GameObject(2, 1, 1, posX + 1, posY,
							sh, ctx, "grass_right"));
					break;
				default:
					solidObjects.add(new GameObject(2, 1, 1, posX, posY, sh,
							ctx, "grass_left"));
					for (int i = 0; i < sizeX - 2; i++) {
						solidObjects.add(new GameObject(2, 1, 1, posX + 1 + i,
								posY, sh, ctx, "grass_mid"));
					}
					solidObjects.add(new GameObject(2, 1, 1, posX + sizeX - 1,
							posY, sh, ctx, "grass_right"));
					break;
				}
			}
		}
	}

	public void run() {
		// ref: http://obviam.net/index.php/sprite-animation-with-android/
		long beginTime; // the time when the cycle begun
		long timeDiff; // the time it took for the cycle to execute
		int sleepTime; // ms to sleep (<0 if we're behind)
		int framesSkipped; // number of frames being skipped

		sleepTime = 0;
		while (run) {
			Canvas c = null;
			try {
				c = sh.lockCanvas(null);
				synchronized (sh) {
					// ref:
					// http://obviam.net/index.php/sprite-animation-with-android/
					beginTime = System.currentTimeMillis();
					framesSkipped = 0; // resetting the frames skipped
					doDraw(c);
					// calculate how long did the cycle take
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int) (FRAME_PERIOD - timeDiff);

					if (sleepTime > 0) {
						// if sleepTime > 0 we're OK
						try {
							// send the thread to sleep for a short period
							// very useful for battery saving
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
						}
					}

					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// we need to catch up
						// this.gamePanel.update(); // update without rendering
						sleepTime += FRAME_PERIOD; // add frame period to check
													// if in next frame
						framesSkipped++;
					}
				}
			} finally {
				if (c != null) {
					sh.unlockCanvasAndPost(c);
				}
			}
		}
	}

	public static void setRunning(boolean b) {
		run = b;
	}

	public void setSurfaceSize(int width, int height) {
		synchronized (sh) {
			doStart();
		}
	}

	private void doDraw(Canvas canvas) {
		canvas.save();
		if (worldObjects.size() > 0) {
			curFrame += 1;
			if (curFrame % MAX_FPS == 0) {
				curFrame = 0;
				curTime += 1;
			}
			if (curTime == levelMaxTime) {
				// Kill the player
				Global.playerAlive = false;
			}
			
			worldObjects.get(playerIndex).tickUpdate(); // update player
														// (currently only
														// checks object.0
														// (player) vs
														// object.2("solid Objects"))

			// Move the canvas around the x axis while the player is
			// sufficiently away from the edges of the level.
			// If the player is at the far left, cameraX will be 0.
			if (worldObjects.get(playerIndex).getPosX() > (BLOCK_SIZE * 5)) {
				// Move camera with the player
				cameraX = (BLOCK_SIZE * 5 - worldObjects.get(playerIndex)
						.getPosX());
				// Stop the camera if we're at the rightmost side of the level
				if (worldObjects.get(playerIndex).getPosX() > (BLOCK_SIZE
						* LEVEL_WIDTH - (metrics.widthPixels - BLOCK_SIZE * 5))) {
					cameraX = (metrics.widthPixels - BLOCK_SIZE * LEVEL_WIDTH);
				}
			}

			Log.d("cameraX", "is: " + -cameraX);
			// Set the camera to an appropriate x-offset
			canvas.translate(cameraX, 0);

			// DrawBackgroud
			canvas.drawRect(worldObjects.get(backgroundIndex).getSprite(),
					worldObjects.get(backgroundIndex).getPaint());
			
			for (int i = 0; i < worldObjects.get(0).getScoreAsImages().length; i++) {
				//canvas.drawText("" + worldObjects.get(0).getScoreAsImages()[i], -cameraX + 100 - (i * 14), 30, paint);
				canvas.drawBitmap(numberObjects.get(worldObjects.get(0).getScoreAsImages()[i]).getSpriteGraphic(),
						-cameraX + 100 - (i * 19),
						10, null);
			}
			canvas.drawText("Time: " + (levelMaxTime - curTime), -cameraX + 200, 30, paint); 

			// Draw door
			canvas.drawBitmap(worldObjects.get(doorIndex).getSpriteGraphic(),
					worldObjects.get(doorIndex).getPosX(),
					worldObjects.get(doorIndex).getPosY(), null);
			canvas.drawBitmap(worldObjects.get(doorIndex + 1)
					.getSpriteGraphic(), worldObjects.get(doorIndex + 1)
					.getPosX(), worldObjects.get(doorIndex + 1).getPosY(), null);

			// Draw collectible objects
			for (int i = 0; i < collectibleObjects.size(); i++) {
				canvas.drawBitmap(collectibleObjects.get(i).getSpriteGraphic(),
						collectibleObjects.get(i).getPosX(), collectibleObjects
								.get(i).getPosY(), null);
			}

			// Draw solid Objects
			for (int i = 0; i < solidObjects.size(); i++) {
				canvas.drawBitmap(solidObjects.get(i).getSpriteGraphic(),
						solidObjects.get(i).getPosX(), solidObjects.get(i)
								.getPosY(), null);
			}

			// DrawPlayer
			if (currentlyRunning) {
				worldObjects.get(playerIndex).updateAnimation(
						System.currentTimeMillis());
				canvas.drawBitmap(worldObjects.get(playerIndex)
						.getSpriteGraphic(),
						worldObjects.get(playerIndex).spriteSheetLoc,
						worldObjects.get(playerIndex).getSprite(), null);
			} else {
				canvas.drawBitmap(worldObjects.get(playerIndex)
						.getSpriteGraphic(),
						worldObjects.get(playerIndex).spriteSheetLoc,
						worldObjects.get(playerIndex).getSprite(), null);
			}

		}
		canvas.restore();
	}
}