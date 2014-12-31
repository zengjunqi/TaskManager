package com.zeng.yan.taskmanager.ui;


import com.zeng.yan.taskmanager.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingClickView extends RelativeLayout {
	private TextView tv_desc;
	private ImageView iv_back;
	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniView(context);
	}

	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniView(context);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.myTitleBar);
		iv_back.setBackgroundDrawable(ta.getDrawable(R.styleable.myTitleBar_rightivsrc));
		tv_desc.setText(ta.getString(R.styleable.myTitleBar_titletext));
		ta.recycle();
	}

	public SettingClickView(Context context) {
		super(context);
		iniView(context);
	}

	private void iniView(Context context) {
		// TODO Auto-generated method stub
		View.inflate(context, R.layout.ui_setting_click, this);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		iv_back=(ImageView) this.findViewById(R.id.iv_right);
	}

}
