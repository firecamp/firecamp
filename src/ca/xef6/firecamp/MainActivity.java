package ca.xef6.firecamp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class MainActivity extends FragmentActivity implements TabListener {

	static final int TAB_COUNT = 4;

	final Class<?> tabFragments[] = new Class<?>[TAB_COUNT];

	// -------------------------------------------------------------------------
	private static final String TAG = "MainActivity";

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	private UiLifecycleHelper uiHelper;

	@Override
	public void onResume() {
		super.onResume();
		Log.w("MainActivity", "onResume called");
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.w("MainActivity", "onPause called");
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	// -------------------------------------------------------------------------

	private final int tabCaptionIds[] = new int[TAB_COUNT];

	private int currentTabPosition;
	private ViewPager viewPager;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			break;
		default:
			return false;
		}
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		currentTabPosition = tab.getPosition();
		viewPager.setCurrentItem(currentTabPosition);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		initializeTabOrder();
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),
				this));
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		addTabs(actionBar);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				currentTabPosition = position;
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

		});
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(bundle);
	}

	private void addTab(ActionBar actionBar, int captionId) {
		actionBar.addTab(actionBar.newTab()
				.setText(getResources().getString(captionId))
				.setTabListener(this));
	}

	private void addTabs(ActionBar actionBar) {
		for (int captionId : tabCaptionIds) {
			addTab(actionBar, captionId);
		}
	}

	private void initializeTabOrder() {
		int index = 0;
		tabFragments[index] = com.google.android.gms.maps.SupportMapFragment.class;
		tabCaptionIds[index] = R.string.tab_map;
		++index;
		tabFragments[index] = ca.xef6.firecamp.events.EventsFragment.class;
		tabCaptionIds[index] = R.string.tab_events;
		++index;
		tabFragments[index] = ca.xef6.firecamp.people.PeopleFragment.class;
		tabCaptionIds[index] = R.string.tab_people;
		++index;
		tabFragments[index] = ca.xef6.firecamp.profile.ProfileFragment.class;
		tabCaptionIds[index] = R.string.tab_profile;
	}

}

class ViewPagerAdapter extends FragmentPagerAdapter {

	private final MainActivity mainActivity;

	public ViewPagerAdapter(FragmentManager fragmentManager,
			MainActivity mainActivity) {
		super(fragmentManager);
		this.mainActivity = mainActivity;
	}

	@Override
	public Fragment getItem(int index) {
		Fragment fragment = null;
		try {
			fragment = (Fragment) mainActivity.tabFragments[index]
					.newInstance();
		} catch (ArrayIndexOutOfBoundsException exception) {
		} catch (IllegalAccessException exception) {
		} catch (InstantiationException exception) {
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return MainActivity.TAB_COUNT;
	}

}
