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

	Global(){
		worldObjects = new Vector<GameObject>();
		levels = new Vector<Level>();	
	}
	
	public static void jump() {
		Log.d("ButtonPress", "Button Pressed: Jump");
		Global.worldObjects.get(0).jumps();
	}
	public static void runLeft() {
		Log.d("ButtonPress", "Button Pressed: Left");
		Global.worldObjects.get(0).runLefts();
	}
	public static void runRight() {
		Log.d("ButtonPress", "Button Pressed: Right");
		Global.worldObjects.get(0).runRights();
	}
}