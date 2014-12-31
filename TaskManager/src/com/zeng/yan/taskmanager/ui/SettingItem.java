package com.zeng.yan.taskmanager.ui;

import com.zeng.yan.taskmanager.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingItem extends RelativeLayout {

	private CheckBox cb_status;
	private TextView tv_desc;
	private TextView tv_title;
	
	private  String desc_on;
	private String desc_off;
	
	/**
	 * ��ʼ�������ļ�
	 * @param context
	 */
	private void iniView(Context context) {
		
		//��һ�������ļ�---��View ���Ҽ�����SettingItemView
		View.inflate(context,R.layout.setting_item_view, this);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		
	}
	public SettingItem(Context context) {
		super(context);
		iniView(context);
		// TODO Auto-generated constructor stub
	}

	public SettingItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		iniView(context);
		
	}

	public SettingItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		iniView(context);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.myTitleBar);
		
		String title = ta.getString(R.styleable.myTitleBar_titletext);
		desc_on =ta.getString(R.styleable.myTitleBar_desc_on);
		desc_off =ta.getString(R.styleable.myTitleBar_desc_off);
		ta.recycle();
		tv_title.setText(title);
		setDesc(desc_off);
	}

	/**
	 * У����Ͽؼ��Ƿ�ѡ��
	 */
	
	public boolean isChecked(){
		return cb_status.isChecked();
	}
	
	/**
	 * ������Ͽؼ���״̬
	 */
	
	public void setChecked(boolean checked){
		if(checked){
			setDesc(desc_on);
		}else{
			setDesc(desc_off);
		}
		cb_status.setChecked(checked);
	}
	
	/**
	 * ���� ��Ͽؼ���������Ϣ
	 */
	
	public void setDesc(String text){
		tv_desc.setText(text);
	}
	
	
}