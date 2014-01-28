package ca.xef6.firecamp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
 
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		viewPager_.setCurrentItem(tab.getPosition());
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager_ = (ViewPager) findViewById(R.id.pager);
		viewPager_.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(true);
		addTabs(actionBar);
        viewPager_.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	 
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
	}
	
	private void addTab(ActionBar actionBar, int labelId) {
		actionBar.addTab(actionBar.newTab().setText(getResources().getString(labelId)).setTabListener(this));
	}
	
	private void addTabs(ActionBar actionBar) {
		addTab(actionBar, R.string.fragment_map);
		addTab(actionBar, R.string.fragment_events);
		addTab(actionBar, R.string.fragment_people);
		addTab(actionBar, R.string.fragment_profile);
	}
	
	private ViewPager viewPager_;
}