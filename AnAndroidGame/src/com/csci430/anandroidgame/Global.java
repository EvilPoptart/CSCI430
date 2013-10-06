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
	private static int player;

	Global(){
		worldObjects = new Vector<GameObject>();
		levels = new Vector<Level>();	
	}
	public static void jump() {
		player = 0;
		Log.d("ButtonPress", "Button Pressed: Jump");
		Global.worldObjects.get(player).jumps();
	}
	public static void runLeft() {
		player = 0;
		Log.d("ButtonPress", "Button Pressed: Left");
		Global.worldObjects.get(player).runLefts();
	}
	public static void runRight() {
		player = 0;
		Log.d("ButtonPress", "Button Pressed: Right");
		Global.worldObjects.get(player).runRights();
	}
}