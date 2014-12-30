package com.zeng.yan.taskmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.zeng.yan.taskmanager.ui.TitleBar;
import com.zeng.yan.taskmanager.ui.TitleBar.titleBarClickListener;

public class BackRestoreActivity extends Activity {
	private TitleBar titleBar;
	private ListView lv;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.back_restore);
		lv = (ListView) findViewById(R.id.br_lv);
		btn = (Button) findViewById(R.id.btn_backup);
		titleBar = (TitleBar) findViewById(R.id.myTitleBar);
		titleBar.setOnTitleBarClickListener(new titleBarClickListener() {

			@Override
			public void rightClick() {
				// TODO Auto-generated method stub

			}

			@Override
			public void leftClick() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lv.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 0;
			}
		});
	}
}
