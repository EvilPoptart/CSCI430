package com.csci430.anandroidgame;

public class Global {
	public static int curLevelId;
	public static boolean levelCompleted = false;

	// TODO: Needs to be implemented in GameObject constructors
	public enum TYPE {
		PLAYER(2), AI(1), PLATFORM(2), DOOR(3), COIN(4);

		private final int value;

		private TYPE(int value) {
			this.value = value;
		}

		public int getID() {
			return value;
		}
	}

	public enum SPRITE {
		// PLAYER
		// GRASS
		// GRASS_LEFT
		// GRASS_MID
		// GRASS_RIGHT
		// DOOR_OPEN_MID
		// DOOR_OPEN_TOP
		// COIN
		// LOCK_BLUE
	}

	public enum POINT_VALUE {
		// COIN
		// GEM?
		// DEATH
		// HEART
		// +1 LIFE
	}

}
