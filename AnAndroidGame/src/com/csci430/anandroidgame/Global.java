package com.csci430.anandroidgame;

import java.util.Vector;
import android.util.DisplayMetrics;
//import android.graphics.Paint;
import android.util.Log;

public class Global extends FullscreenActivity {
	public static Vector<GameObject> worldObjects;
	public static Vector<Level> levels;
	public static DisplayMetrics metrics;
	
	public Sound sounds;
	public static int playerIndex;
	public static int backgroundIndex;

	Global(){
		worldObjects = new Vector<GameObject>();
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
}