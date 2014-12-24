package com.zeng.yan.taskmanager.ui;

import com.zeng.yan.taskmanager.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBar extends RelativeLayout {

	private ImageView iv1;
	private ImageView iv2;
	private TextView tv_title;
	private Drawable dleft, dright;
	private boolean vleft, vright;
	private String tilte;
	private titleBarClickListener listener;

	public interface titleBarClickListener {
		public void leftClick();

		public void rightClick();
	}

	public void setOnTitleBarClickListener(titleBarClickListener listener) {
		this.listener = listener;
	}

	public TitleBar(Context context) {
		super(context);
		iniView(context);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniView(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniView(context);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.myTitleBar);
		dleft = ta.getDrawable(R.styleable.myTitleBar_leftivsrc);
		dright = ta.getDrawable(R.styleable.myTitleBar_rightivsrc);
		vleft = ta.getBoolean(R.styleable.myTitleBar_leftivVisable, false);
		vright = ta.getBoolean(R.styleable.myTitleBar_rightivVisable, false);
		tilte = ta.getString(R.styleable.myTitleBar_titletext);
		ta.recycle();
		iv1.setBackgroundDrawable(dleft);
		iv2.setBackgroundDrawable(dright);
		if (vleft == true) {
			iv1.setVisibility(View.VISIBLE);
		} else {
			iv1.setVisibility(View.GONE);
		}
		if (vright == true) {
			iv2.setVisibility(View.VISIBLE);
		} else {
			iv2.setVisibility(View.GONE);
		}
		tv_title.setText(tilte);
	}

	/**
	 * 初始化布局文件
	 * 
	 * @param context
	 */
	private void iniView(Context context) {

		// 把一个布局文件---》View 并且加载在SettingItemView
		View.inflate(context, R.layout.ui_title_bar, this);
		iv1 = (ImageView) this.findViewById(R.id.top_iv1);
		iv2 = (ImageView) this.findViewById(R.id.top_iv2);
		tv_title = (TextView) this.findViewById(R.id.top_tv);
		
		iv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.leftClick();
			}
		});

		iv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.rightClick();
			}
		});

	}
}
