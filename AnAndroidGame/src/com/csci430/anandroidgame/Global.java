package com.csci430.anandroidgame;

import java.util.Vector;
import android.util.DisplayMetrics;

public class Global extends FullscreenActivity {
	public static Vector<GameObject> worldObjects;
	public static Vector<GameObject> solidObjects;
	public static Vector<Level> levels;
	public static DisplayMetrics metrics;
	
	public Sound sounds;
	public static int playerIndex;
	public static int backgroundIndex;
	public static final int BLOCK_SIZE = 50;
	
	public static boolean currentlyRunning = false;

	Global(){
		worldObjects = new Vector<GameObject>();
		solidObjects = new Vector<GameObject>();
		levels = new Vector<Level>();	
	}
	public static void jump() {
		Global.worldObjects.get(playerIndex).jumps();
	}
	public static void runLeft() {
		Global.worldObjects.get(playerIndex).runLefts();
	}
	public static void runRight() {
		Global.worldObjects.get(playerIndex).runRights();
	}
	public static void startRunningRight() {
		Global.worldObjects.get(playerIndex).startRunRights();
	}
	public static void startRunningLeft() {
		Global.worldObjects.get(playerIndex).startRunLefts();
	}
	public static void setRunning(boolean areWeRunning) {
		currentlyRunning = areWeRunning;
	}
	public static boolean isRunning() {
		return currentlyRunning;
	}
}