package com.csci430.anandroidgame;

import android.content.Context;
import android.media.*;

public class Sound extends Global{
	//private soundbyte
	//private soundbyte
	//private background


	public static void track1(Context context)
	{
		MediaPlayer track1 = MediaPlayer.create(context, R.raw.mainsong);
		track1.start();
	}
	
	public void playBackground()
	{
		//play background
	}
}