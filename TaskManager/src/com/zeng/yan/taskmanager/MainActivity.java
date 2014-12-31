package com.zeng.yan.taskmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

import com.zeng.yan.taskmanager.MainFragment.Mylistener;
import com.zeng.yan.taskmanager.ui.TitleBar;
import com.zeng.yan.taskmanager.ui.TitleBar.titleBarClickListener;

public class MainActivity extends FragmentActivity implements OnClickListener,
		Mylistener {
	// private ImageView mTabline;
	private ViewPager viewPager;
	private FragmentPagerAdapter fragmentPagerAdapter;
	private List<Fragment> fragments;
	private RadioButton myHome;
	private RadioButton query;
	private RadioButton setting;
	private TitleBar titleBar;

	// private int mCurrentPageIndex;
	// private int mScreen1_3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		/*
		 * mTabline = (ImageView) findViewById(R.id.id_iv_tabline); Display
		 * display = getWindow().getWindowManager().getDefaultDisplay();
		 * DisplayMetrics outMetrics = new DisplayMetrics();
		 * display.getMetrics(outMetrics); mScreen1_3 = outMetrics.widthPixels /
		 * 3; LayoutParams layoutParams = mTabline.getLayoutParams();
		 * layoutParams.width = mScreen1_3;
		 * mTabline.setLayoutParams(layoutParams);
		 */
		initView();
		
	}

	private void initView() {
		myHome = (RadioButton) findViewById(R.id.rb_home);
		query = (RadioButton) findViewById(R.id.rb_query);
		setting = (RadioButton) findViewById(R.id.rb_setting);
		viewPager = (ViewPager) findViewById(R.id.vp);
		fragments = new ArrayList<Fragment>();
		ChatFragment tab1 = new ChatFragment();
		QueryFragment tab2 = new QueryFragment();
		SetFragment tab3 = new SetFragment();

		fragments.add(tab1);
		fragments.add(tab2);
		fragments.add(tab3);
		fragmentPagerAdapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragments);
		viewPager.setAdapter(fragmentPagerAdapter);
		myHome.setTextColor(Color.parseColor(getString(R.string.maincolor)));
		myHome.setChecked(true);
		myHome.setOnClickListener(this);
		query.setOnClickListener(this);
		setting.setOnClickListener(this);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

				resetTextView();
				switch (position) {
				case 0:
					myHome.setTextColor(Color.parseColor(getString(R.string.maincolor)));
					myHome.setChecked(true);
					break;
				case 1:
					query.setTextColor(Color.parseColor(getString(R.string.maincolor)));
					query.setChecked(true);
					break;
				case 2:
					setting.setTextColor(Color.parseColor(getString(R.string.maincolor)));
					setting.setChecked(true);
					break;
				}
				// mCurrentPageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx) {
				// position :当前页面，及你点击滑动的页面
				// positionOffset:当前页面偏移的百分比
				// positionOffsetPx:当前页面偏移的像素位置
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		titleBar = (TitleBar) findViewById(R.id.myTitleBar);
		titleBar.setOnTitleBarClickListener(new titleBarClickListener() {

			@Override
			public void rightClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						AddTaskActivity.class);
				startActivity(intent);
			}

			@Override
			public void leftClick() {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "左点击了.",
				// Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void resetTextView() {
		myHome.setTextColor(Color.BLACK);
		query.setTextColor(Color.BLACK);
		setting.setTextColor(Color.BLACK);
	}

	@Override
	public void onClick(View v) {
		resetTextView();
		switch (v.getId()) {
		case R.id.rb_home:
			myHome.setTextColor(Color.parseColor(getString(R.string.maincolor)));
			viewPager.setCurrentItem(0);
			break;

		case R.id.rb_query:
			query.setTextColor(Color.parseColor(getString(R.string.maincolor)));
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_setting:
			setting.setTextColor(Color.parseColor(getString(R.string.maincolor)));
			viewPager.setCurrentItem(2);
			break;
		}

	}

	@Override
	public void setChange(int datePeriod) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(1);

//		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//		QueryFragment qf = (QueryFragment) fm.findFragmentByTag("");
		//Toast.makeText(this, datePeriod, Toast.LENGTH_SHORT).show();

		QueryFragment qf = (QueryFragment) getSupportFragmentManager()
				.findFragmentByTag("android:switcher:" + R.id.vp + ":1");

		if (qf != null) // 可能没有实例化

		{

			if (qf.getView() != null)

			{

				qf.setCondition(datePeriod);// 自定义方法更新

			}
		}

	}
}
