package com.zeng.yan.taskmanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zeng.yan.taskmanager.R;
import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.db.TaskDBOperator;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class MyRemoteViewService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		// TODO Auto-generated method stub
		return new MyRemoteViewsFactory(this, intent);
	}

	private class MyRemoteViewsFactory implements
			RemoteViewsService.RemoteViewsFactory {
		private Context mContext;
		private int mAppWidgetId;
		private String date;
		private List<TaskDetails> data;

		public MyRemoteViewsFactory(Context context, Intent intent) {
			mContext = context;
			 mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
		                AppWidgetManager.INVALID_APPWIDGET_ID);
			date = intent.getStringExtra("date");
			System.out.println("GridRemoteViewsFactory date:"
					+ date);
			//data = intent.get("data");
		}

		/**
		 * 初始化GridView的数据
		 * 
		 * @author skywang
		 */
		private void initViewData() {
			TaskDBOperator helper = new TaskDBOperator(mContext);
			data = helper.findPart(0, 100, date, date);

		}

		@Override
		public void onCreate() {
			initViewData();
			System.out.println("onCreate date:"
					+ date);
		}
		
	
		@Override
		public RemoteViews getViewAt(int position) {
			RemoteViews rv = new RemoteViews(mContext.getPackageName(),
					R.layout.widget_lv_item);

			// 设置 第position位的“视图”的数据
			//System.out.println("****" + data.get(position).getContent());
			rv.setTextViewText(R.id.wg_content, data.get(position).getContent());
			rv.setTextViewText(R.id.wg_datetime, data.get(position)
					.getStartTime() + "-" + data.get(position).getEndTime());
			// 设置 第position位的“视图”对应的响应事件
			Intent fillInIntent = new Intent();
			fillInIntent
					.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, position);
			rv.setOnClickFillInIntent(R.id.itemLayout, fillInIntent);

			return rv;
		}

		@Override
		public void onDataSetChanged() {
			TaskDBOperator helper = new TaskDBOperator(mContext);
			data = helper.findPart(0, 100, "2015-01-01", "2015-01-01");
			
			System.out.println("onDataSetChanged");
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			data.clear();
		}

		@Override
		public int getCount() {
			if (data != null) {
				return data.size();
			}
			return 0;
		}

		@Override
		public RemoteViews getLoadingView() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}
		

	}
}
