package com.csci430.anandroidgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class GameObject {
	// Limits and increments
	// ==========================================
	// Horizontal speed limit
	private int MAX_H_SPEED = (int) (GameThread.BLOCK_SIZE * 0.1);
	// Vertical speed limit
	private int MAX_Y_SPEED = (int) (GameThread.BLOCK_SIZE * 0.4);
	// Initial jump speed
	private int jumpSpeed = (int) (GameThread.BLOCK_SIZE * 0.4);
	// Running speed increment
	private int runSpeed = 1;
	// Gravity increment
	private int gravity = 1;

	// Where the object exists in space
	// ==========================================
	private int positionX;
	private int positionY;
	private int sizeX;
	private int sizeY;
	private float velocityX;
	private float velocityY;

	// Animation
	// ==========================================
	// number of frames in animation
	private int frameNr;
	// the current frame
	private int currentFrame;
	// the time of the last frame update
	private long frameTicker;
	// milliseconds between each frame (1000/fps)
	private int framePeriod;

	// Sprite
	// ==========================================
	// the width of the sprite to calculate the cut out rectangle
	private int spriteWidth;
	// the height of the sprite
	private int spriteHeight;
	private Rect spriteRect;
	private Bitmap sprite;
	public Rect spriteSheetLoc;
	int spriteSheetLocation;

	// Type: see Global.java for definitions
	private int typeOf;
	// Is this object solid?
	private boolean solid;
	// Should we display this object?
	private boolean visible;

	// Used to display solid color
	private Paint paint;
	// Important, but not sure how to describe it.
	private Context context;
	// Important, but not sure how to describe it.
	private SurfaceHolder surfH;

	// Score
	private int score;

	GameObject() {
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;

		solid = false;
		visible = true;
		typeOf = 2; // 0:player, 1:AI, 2: object

		spriteSheetLocation = 0; // for animations and such
	}

	// Animated objects
	GameObject(int type, int sX, int sY, int posX, int posY, int fps,
			SurfaceHolder sh, Context ctx, String tileSetName) {
		typeOf = type;
		surfH = sh;

		// TODO: String comparisons are probably needlessly inefficient for this
		// task. I didn't want
		// to use a switch statement with integers because it would be much less
		// readable/usable.
		// Java needs ruby symbols =/
		// Or enums? That may work.
		if (tileSetName == "player") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.p1_spritesheet);
			sizeX = 48;
			sizeY = 64;
			positionX = 0;
			positionY = 0;
			spriteRect = new Rect(positionX, positionY, (positionX + sizeX),
					(positionY + sizeY));
			spriteSheetLoc = new Rect(0, 0, sizeX, sizeY);

			frameNr = 11;
			setCurrentFrame(0);
			framePeriod = 1000 / fps;
			frameTicker = 0l;

			spriteWidth = sprite.getWidth() / frameNr;
			spriteHeight = sprite.getHeight() / 3;
		}
		// If the specified tileSetName is not found, display a blue lock.
		else {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.lock_blue);
		}
	}

	// Object with bitmap
	GameObject(int type, int sX, int sY, int posX, int posY, SurfaceHolder sh,
			Context ctx, String tileSetName) {
		typeOf = type;
		sizeX = sX * GameThread.BLOCK_SIZE;
		sizeY = sY * GameThread.BLOCK_SIZE;
		positionX = posX * GameThread.BLOCK_SIZE;
		positionY = GameThread.metrics.heightPixels
				- ((posY + sY) * GameThread.BLOCK_SIZE);
		surfH = sh;

		spriteRect = new Rect(positionX, positionY, (positionX + sizeX),
				(positionY + sizeY));

		// TODO: String comparisons are probably needlessly inefficient for this
		// task. I didn't want
		// to use a switch statement with integers because it would be much less
		// readable/usable.
		// Java needs ruby symbols =/
		// Or enums? That may work.
		if (tileSetName == "player") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.p3_jump);
		} else if (tileSetName == "grass") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.grass);
		} else if (tileSetName == "grass_left") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.grass_left);
		} else if (tileSetName == "grass_mid") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.grass_mid);
		} else if (tileSetName == "grass_right") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.grass_right);
		} else if (tileSetName == "door_open_mid") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.door_open_mid);
		} else if (tileSetName == "door_open_top") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.door_open_top);
		} else if (tileSetName == "coin") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.coin);
		} else if (tileSetName == "spikes") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.spikes);
		}
		// If the specified tileSetName is not found, display a blue lock.
		else {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.lock_blue);
		}
	}

	// Used for numbers
	GameObject(int type, int sX, int sY, SurfaceHolder sh, Context ctx,
			String tileSetName) {
		typeOf = type;
		sizeX = sX * GameThread.BLOCK_SIZE;
		sizeY = sY * GameThread.BLOCK_SIZE;
		surfH = sh;

		spriteRect = new Rect(positionX, positionY, (positionX + sizeX),
				(positionY + sizeY));

		// TODO: String comparisons are probably needlessly inefficient for this
		// task. I didn't want
		// to use a switch statement with integers because it would be much less
		// readable/usable.
		// Java needs ruby symbols =/
		// Or enums? That may work.
		if (tileSetName == "zero") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_0);
		} else if (tileSetName == "one") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_1);
		} else if (tileSetName == "two") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_2);
		} else if (tileSetName == "three") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_3);
		} else if (tileSetName == "four") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_4);
		} else if (tileSetName == "five") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_5);
		} else if (tileSetName == "six") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_6);
		} else if (tileSetName == "seven") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_7);
		} else if (tileSetName == "eight") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_8);
		} else if (tileSetName == "nine") {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.hud_9);
		}
		// If the specified tileSetName is not found, display a blue lock.
		else {
			sprite = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.coin);
		}
	}

	// Objects with paint
	GameObject(int type, int sX, int sY, int posX, int posY, SurfaceHolder sh,
			Context ctx, Paint p) {
		typeOf = type;
		sizeX = sX * GameThread.BLOCK_SIZE;
		sizeY = sY * GameThread.BLOCK_SIZE;
		positionX = posX * GameThread.BLOCK_SIZE;
		positionY = GameThread.metrics.heightPixels
				- ((posY + sY) * GameThread.BLOCK_SIZE);
		paint = p;
		surfH = sh;

		spriteRect = new Rect(positionX, positionY, (positionX + sizeX),
				(positionY + sizeY));
	}

	// for objects == screen size
	GameObject(int type, int posX, int posY, Paint p, SurfaceHolder sh) {
		typeOf = type;
		sizeX = GameThread.metrics.widthPixels;
		sizeY = GameThread.metrics.heightPixels;
		positionX = posX * GameThread.BLOCK_SIZE;
		positionY = posY * GameThread.BLOCK_SIZE;
		paint = p;
		surfH = sh;

		spriteRect = new Rect(positionX, positionY, (positionX + sizeX),
				(positionY + sizeY));
	}

	public void tickUpdate() {
		// position update, only for player and AI, not static objects
		if (typeOf == 0 || typeOf == 1) {
			// Create a ghost to be used for collision detection.
			// The ghost will move one tick ahead of the moving game object
			// to let us know if the object is about to hit anything.
			Rect ghost;
			String ourCurPosition = spriteRect.flattenToString();
			ghost = Rect.unflattenFromString(ourCurPosition);

			// Move the ghost to where we will be
			ghost.offset((int) velocityX, (int) velocityY);

			// Check for collision with the door
			// Door_mid will always be index 2 in worldObjects\
			if (!Global.levelCompleted) {
				if (ghost.intersect(GameThread.worldObjects.get(2).spriteRect)) {
					Global.levelCompleted = true;
				}
			} else if (!ghost
					.intersect(GameThread.worldObjects.get(2).spriteRect)) {
				Global.levelCompleted = false;
			}

			// Test for collision with coins, gems, hearts, keys
			int collectibleObjColIndex = collectibleCollision(ghost);
			if (collectibleObjColIndex != -1) {
				// Get the object and act on the collision appropriately based
				// on type.
				GameObject collectibleColObject = GameThread.collectibleObjects
						.get(collectibleObjColIndex);
				switch (collectibleColObject.getType()) {
				case 4:
					// Delete the coin
					GameThread.collectibleObjects
							.remove(collectibleObjColIndex);
					// Increase our score
					GameThread.worldObjects.get(0).incrScore(5);
					Sound.soundCoin(context);
					break;
				default:
					break;
				}
			}

			// Test for collision with spikes
			int harmfulObjColIndex = harmfulCollision(ghost);
			if (harmfulObjColIndex != -1) {
				// We've hit something harmful. Die.
				Global.playerAlive = false;
			}

			// Recreate the ghost.
			// Player falls through the ground when intersecting a door without
			// these two lines
			ghost = Rect.unflattenFromString(ourCurPosition);
			ghost.offset((int) velocityX, (int) velocityY);

			// If the ghost collides with anything, put us against the object
			int solidColObjectIndex = solidCollision(ghost);
			if (solidColObjectIndex != -1) {
				GameObject solidColObject = GameThread.solidObjects
						.get(solidColObjectIndex);

				// find the direction of collision
				if ((this.positionY + this.sizeY) <= solidColObject.positionY) {
					// top
					// put our feet on the ground
					this.positionY = solidColObject.positionY - this.sizeY;
					velocityY = 0;
					positionX += velocityX;
				} else if (this.positionY >= (solidColObject.positionY + solidColObject.sizeY)) {
					// bottom
					// put our head on the object
					this.positionY = solidColObject.positionY
							+ solidColObject.sizeY;
					velocityY = 0;
					positionX += velocityX;
				} else if (this.positionX >= (solidColObject.positionX + solidColObject.sizeX)) {
					// right
					// put ourselves next to the object
					this.positionX = solidColObject.positionX
							+ solidColObject.sizeX;
					velocityX = 0;
					positionY += velocityY;
					velocityY += gravity;
				} else if ((this.positionX + this.sizeX) <= solidColObject.positionX) {
					// left
					// put ourselves next to the object
					this.positionX = solidColObject.positionX - this.sizeX;
					velocityX = 0;
					positionY += velocityY;
					velocityY += gravity;
				}
				colWall();
				onFloor();
				spriteRect.offsetTo(positionX, positionY);
			}
			// otherwise, keep moving
			else {
				positionX += velocityX;
				positionY += velocityY;
				velocityY += gravity;
				colWall();
				onFloor();
				spriteRect.offsetTo(positionX, positionY);
			}

			// Friction!
			// velocity update for time
			if (velocityX > 0) {
				velocityX -= 1;
				if (velocityX < 0)
					velocityX = 0;
			} else {
				velocityX += 1;
				if (velocityX > 0)
					velocityX = 0;
			}
			// colWall();
		}
	}

	private void onFloor() {
		if (positionY > (GameThread.metrics.heightPixels - sizeY)) {
			positionY = GameThread.metrics.heightPixels - sizeY;
			velocityY = 0;
		}
	}

	private void colWall() {
		// Left Wall Collision
		if (positionX < 0) {
			positionX = 0;
			velocityX = 0;
		}

		// Right Wall Collision
		if (positionX > GameThread.BLOCK_SIZE * GameThread.LEVEL_WIDTH - sizeX) {
			positionX = GameThread.BLOCK_SIZE * GameThread.LEVEL_WIDTH - sizeX;
			velocityX = 0;
		}
	}

	/**
	 * Tests for collision with solidObjects. If there is a collision, returns
	 * index Else returns -1
	 */
	private int harmfulCollision(Rect ghost) {
		for (int i = 0; i < GameThread.harmfulObjects.size(); i++) {
			if (ghost.intersect(GameThread.harmfulObjects.get(i).spriteRect)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Tests for collision with solidObjects. If there is a collision, returns
	 * index Else returns -1
	 */
	private int solidCollision(Rect ghost) {
		for (int i = 0; i < GameThread.solidObjects.size(); i++) {
			if (ghost.intersect(GameThread.solidObjects.get(i).spriteRect)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Tests for collision with collectibleObjects. If there is a collision,
	 * returns index Else returns -1
	 */
	private int collectibleCollision(Rect ghost) {
		for (int i = 0; i < GameThread.collectibleObjects.size(); i++) {
			if (ghost
					.intersect(GameThread.collectibleObjects.get(i).spriteRect)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Boolean version of solidCollision. Tests for collision with solidObjects
	 * If there is a collision, returns true Else returns false
	 */
	private boolean solidCollisionTest(Rect ghost) {
		for (int i = 0; i < GameThread.solidObjects.size(); i++) {
			if (ghost.intersect(GameThread.solidObjects.get(i).spriteRect)) {
				return true;
			}
		}
		return false;
	}

	public void jumps() {
		// if(velocityY <= gravity) //assume on jumpable platform if velY <= 5
		String ourCurPosition = spriteRect.flattenToString();
		Rect ghost = Rect.unflattenFromString(ourCurPosition);
		ghost.offset(0, 1);
		if (solidCollisionTest(ghost)) {
			velocityY += jumpSpeed;

			if (velocityY > MAX_Y_SPEED)
				velocityY = MAX_Y_SPEED;
			if (velocityY > ((-1) * MAX_Y_SPEED))
				velocityY = (-1) * MAX_Y_SPEED;
		}
	}

	public void runLefts() {
		velocityX -= runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
	}

	public void runRights() {
		velocityX += runSpeed;
		if (velocityX > MAX_H_SPEED)
			velocityX = MAX_H_SPEED;
	}

	// ref: http://stackoverflow.com/a/7852459
	public void startRunRights() {
		GameThread.setIsRunning(true);
		new Thread(new Runnable() {
			public void run() {
				while (GameThread.isRunning()) {
					// if we can run faster, do so
					if (velocityX < MAX_H_SPEED) {
						velocityX += runSpeed;
					}
					// if we're moving too fast, reset to MAX_H_SPEED
					else if (velocityX > MAX_H_SPEED) {
						velocityX = MAX_H_SPEED;
					}
					// else, velocityX == MAX_H_SPEED, and we do nothing
				}
			}
		}).start();
	}

	public void startRunLefts() {
		GameThread.setIsRunning(true);
		new Thread(new Runnable() {
			public void run() {
				while (GameThread.isRunning()) {
					// if we can run faster, do so
					if (velocityX > -MAX_H_SPEED) {
						velocityX -= runSpeed;
					}
					// if we're moving too fast, reset to MAX_H_SPEED
					else if (velocityX < -MAX_H_SPEED) {
						velocityX = -MAX_H_SPEED;
					}
					// else, velocityX == -MAX_H_SPEED, and we do nothing
				}
			}
		}).start();
	}

	public int getPosX() {
		return positionX;
	}

	public int getPosY() {
		return positionY;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean sol) {
		solid = sol;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean vis) {
		visible = vis;
	}

	public int getType() {
		return typeOf;
	}

	public void setCtx(Context ctx) {
		context = ctx;
	}

	public Context getCtx() {
		return context;
	}

	public void setSH(SurfaceHolder sh) {
		surfH = sh;
	}

	public SurfaceHolder getSH() {
		return surfH;
	}

	public Rect getSprite() {
		return spriteRect;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint p) {
		paint = p;
	}

	public void setPosY(int posY) {
		positionY = posY;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setVelocityY(int v) {
		velocityY = v;
	}

	public void setSprite(Bitmap input) {
		sprite = input;
	}

	public Bitmap getSpriteGraphic() {
		return sprite;
	}

	// ref: http://obviam.net/index.php/sprite-animation-with-android/
	public void setSprite(String frameDescription) {
	}

	public void updateAnimation(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			setCurrentFrame(getCurrentFrame() + 1);
			if (getCurrentFrame() >= frameNr) {
				setCurrentFrame(0);
			}
		}

		// define the rectangle to cut out sprite
		switch (getCurrentFrame()) {
		case 0:
			this.spriteSheetLoc.offsetTo(0, 0);
			break;
		case 1:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 1, 0);
			break;
		case 2:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 2, 0);
			break;
		case 3:
			this.spriteSheetLoc.offsetTo(0, (this.sizeY + 1) * 1);
			break;
		case 4:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 1,
					(this.sizeY + 1) * 1);
			break;
		case 5:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 2,
					(this.sizeY + 1) * 1);
			break;
		case 6:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 3, 0);
			break;
		case 7:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 4, 0);
			break;
		case 8:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 3,
					(this.sizeY + 1) * 1);
			break;
		case 9:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 5, 0);
			break;
		case 10:
			this.spriteSheetLoc.offsetTo((this.sizeX + 1) * 4,
					(this.sizeY + 1) * 1);
			break;
		default:
			this.spriteSheetLoc.offsetTo(0, 0);
			break;
		}

	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getScore() {
		return score;
	}

	public int[] getScoreAsImages() {
		if (score == 0) {
			int[] digit = new int[1];
			digit[0] = 0;
			return digit;

		} else {
			int tmpScore = score;
			int places = 0;
			while (tmpScore > 0) {
				tmpScore = tmpScore / 10;
				places += 1;
			}
			int[] digits = new int[places];
			tmpScore = score;
			int i = 0;
			while (tmpScore > 0) {
				digits[i] = tmpScore % 10;
				tmpScore = tmpScore / 10;
				i += 1;
			}
			return digits;
		}
	}

	public void incrScore(int score) {
		this.score += score;
	}

	public void decrScore(int score) {
		this.score -= score;
		if (this.score < 0) {
			this.score = 0;
		}
	}
}
