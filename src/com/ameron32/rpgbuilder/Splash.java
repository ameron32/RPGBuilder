package com.ameron32.rpgbuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setFullScreen();
		setContentView(R.layout.splash);
		run();
	}

	private void setFullScreen() {
		// fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private void run() {
//		final String screen = screenChooser();

		Class activity;
//[t]
		try {
			activity = Class.forName("com.ameron32.rpgbuilder." + "CharacterBuilder");
			Intent openActivity = new Intent(Splash.this, activity);
			startActivity(openActivity);
			finish();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
//
//		
//		Thread timer = new Thread() {
//			public void run() {
//				try {
//					sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} finally {
//					try {
//						Class activity = Class.forName("com.ameron32.rpgbuilder."
//								+ screen);
//						Intent openActivity = new Intent(Splash.this, activity);
//						startActivity(openActivity);
//						finish();
//					} catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		};
//		timer.start();
	}

//	private String screenChooser() {
//		DisplayMetrics metrics = Splash.this.getResources().getDisplayMetrics();
//		int width = metrics.widthPixels;
//		int height = metrics.heightPixels;
//		double widthdpi = (double) metrics.xdpi;
//		double widthD = (double) width / widthdpi;
//		double ratio;
//		if (width > height) {
//			ratio = 9.0 / 16.0;
//		} else {
//			ratio = 16.0 / 9.0;
//		}
//		double screenSize = Math.sqrt((widthD * widthD)
//				/ +((ratio * widthD) * (ratio * widthD)));
//		String activityStringS;
///*		if (screenSize > 6.5) {
//			if (screenSize > 9.5) {
//				// splashten.xml
//				activityStringS = "CharacterBuilderTen";
//			} else {
//				// splashseven.xml
//				activityStringS = "CharacterBuilderSeven";
//			}
//		} else {
//			// splashfour.xml
//			activityStringS = "CharacterBuilder";
//		}*/
//
//		activityStringS = "CharacterBuilder";
//		return activityStringS;
//	}
}
