package com.zeng.yan.taskmanager.adapter;

import java.util.List;

import android.R.color;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zeng.yan.taskmanager.R;
import com.zeng.yan.taskmanager.bean.TaskDetails;

public class MySpnnerAdapter extends BaseAdapter {
	private Context context;
	private String[] list;

	public MySpnnerAdapter(Context context, String[] list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list == null) {
			return 0;
		}
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		if (list == null) {
			return null;
		}
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//View view = View.inflate(context, R.layout.sp_item, null);
		TextView tView = new TextView(context);
		tView.setTextColor(Color.parseColor("#000000"));
		tView.setGravity(Gravity.CENTER);
		tView.setTextSize(20);
		tView.setText(list[position]);
		return tView;
	}

}
