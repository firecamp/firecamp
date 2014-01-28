package ca.xef6.firecamp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
			}
		}, DELAY);
	}
	
	/** Time the splash screen is visible, in milliseconds. */
	private static final int DELAY = 10;
}