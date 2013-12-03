package com.csci430.anandroidgame;

import android.content.Context;
import android.media.MediaPlayer;

public class Sound {
	// private soundbyte
	// private soundbyte
	// private background

	public static void track1(Context context) {
		MediaPlayer track1 = MediaPlayer.create(context, R.raw.song3);
		track1.start();
	}
	
	public static void track2(Context context) {
		MediaPlayer track2 = MediaPlayer.create(context, R.raw.song2);
		track2.start();
	}
	
	public static void track3(Context context) {
		MediaPlayer track3 = MediaPlayer.create(context, R.raw.mainsong);
		track3.start();
	}
	
	public static void trackMenu(Context context) {
		MediaPlayer trackMenu = MediaPlayer.create(context, R.raw.gameover);
		trackMenu.start();
	}
	
	public static void soundJump(Context context) {
		MediaPlayer soundJump = MediaPlayer.create(context, R.raw.jump);
		soundJump.start();
	}
	
	public static void soundDeath(Context context) {
		MediaPlayer soundDeath = MediaPlayer.create(context, R.raw.dead);
		soundDeath.start();
	}
	
	public static void soundCoin(Context context) {
		MediaPlayer soundCoin = MediaPlayer.create(context, R.raw.coin);
		soundCoin.start();
	}
	
	public static void soundWin(Context context) {
		MediaPlayer soundWin = MediaPlayer.create(context, R.raw.fanfare);
		soundWin.start();
	}
	
	public static void soundKey(Context context) {
		MediaPlayer soundKey = MediaPlayer.create(context, R.raw.key);
		soundKey.start();
	}
	
	public static void soundLand(Context context) {
		MediaPlayer soundLand = MediaPlayer.create(context, R.raw.land);
		soundLand.start();
	}
	
	public static void soundUnlock(Context context) {
		MediaPlayer soundUnlock = MediaPlayer.create(context, R.raw.unlock);
		soundUnlock.start();
	}
	
}