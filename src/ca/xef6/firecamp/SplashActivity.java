package ca.xef6.firecamp;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ViewFlipper;

public class SplashActivity extends Activity {
	ViewFlipper vf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		vf = (ViewFlipper) findViewById(R.id.splash_flipper);
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case 1:
			vf.setDisplayedChild(0);
			break;
		case 2:
			vf.setDisplayedChild(1);
			break;
		case 3:
			vf.setDisplayedChild(2);
			break;
		case 4:
			vf.setDisplayedChild(3);
			break;
		case 5:
			vf.setDisplayedChild(4);
			break;
		case 6:
			vf.setDisplayedChild(5);
			break;
		case 7:
			vf.setDisplayedChild(6);
			break;
		}

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
			}
		}, DELAY);
	}

	/** Time the splash screen is visible, in milliseconds. */
	private static final int DELAY = 3000;
}