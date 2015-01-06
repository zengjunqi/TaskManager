package com.zeng.yan.taskmanager.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zeng.yan.taskmanager.AddTaskActivity;
import com.zeng.yan.taskmanager.R;
import com.zeng.yan.taskmanager.service.MyRemoteViewService;
import com.zeng.yan.taskmanager.utils.CalendarUtils;

public class DailyWidget extends AppWidgetProvider {

	private static final String REFRESH_ACTION = "com.zeng.yan.taskmanager.refresh";
	private static final String ADD_ACTION = "com.zeng.yan.taskmanager.additem";
	private static final String PRE_ACTION = "com.zeng.yan.taskmanager.previous";
	private static final String NEXT_ACTION = "com.zeng.yan.taskmanager.next";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static String conditonDate = dateFormat.format(new Date());

	@Override
	public void onReceive(Context context, Intent intent) {
		// Intent i = new Intent(context,UpdateWidgetService.class);
		// context.startService(i);
		if (conditonDate == null) {
			conditonDate = dateFormat.format(new Date());
		}

		String action = intent.getAction();
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		int[] appids = appWidgetManager.getAppWidgetIds(new ComponentName(
				context, DailyWidget.class));

		if (action.equals(ADD_ACTION)) {

			Intent intent2 = new Intent(context, AddTaskActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);

		} else if (action.equals(REFRESH_ACTION)) {
			conditonDate = dateFormat.format(new Date());
			Toast.makeText(context, "Click Button", Toast.LENGTH_SHORT).show();
		} else if (action.equals(PRE_ACTION)) {
			conditonDate = CalendarUtils.calculateEndDate(conditonDate, -1);
			RemoteViews rv = new RemoteViews(context.getPackageName(),
					R.layout.process_widget);
			rv.setTextViewText(R.id.tv_month, conditonDate);
			appWidgetManager.notifyAppWidgetViewDataChanged(appids[0], R.id.wd_lv);
			appWidgetManager.updateAppWidget(appids[0], rv);
			
			Toast.makeText(context, "pre Button" + conditonDate,
					Toast.LENGTH_SHORT).show();
		} else if (action.equals(NEXT_ACTION)) {
			conditonDate = CalendarUtils.calculateEndDate(conditonDate, 1);
			Toast.makeText(context, "next Button" + conditonDate,
					Toast.LENGTH_SHORT).show();
			refreshData(context, appWidgetManager, appids);
		}
	
		//appWidgetManager.notifyAppWidgetViewDataChanged(appids, R.id.wd_lv); 
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// ��ȡAppWidget��Ӧ����ͼ
		conditonDate = dateFormat.format(new Date());
		refreshData(context, appWidgetManager, appWidgetIds);

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	private void refreshData(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			RemoteViews rv = new RemoteViews(context.getPackageName(),
					R.layout.process_widget);
			rv.setTextViewText(R.id.tv_month, conditonDate);
			Intent intent1 = new Intent().setAction(PRE_ACTION);
			PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,
					0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.query_iv1, pendingIntent1);

			Intent intent2 = new Intent().setAction(NEXT_ACTION);
			PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,
					0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.query_iv2, pendingIntent2);

			Intent intent = new Intent().setAction(ADD_ACTION);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.query_iv3, pendingIntent);

			Intent btIntent = new Intent().setAction(REFRESH_ACTION);
			PendingIntent btPendingIntent = PendingIntent.getBroadcast(context,
					0, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.query_iv4, btPendingIntent);

			Intent serviceIntent = new Intent(context,
					MyRemoteViewService.class);
			serviceIntent.putExtra("date", conditonDate);
			rv.setRemoteAdapter(R.id.wd_lv, serviceIntent);

			// Intent gridIntent = new Intent();
			// gridIntent.setAction(COLLECTION_VIEW_ACTION);
			// gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
			// appWidgetId);
			// PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
			// 0, gridIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			// // ����intentģ��
			// rv.setPendingIntentTemplate(R.id.wd_lv, pendingIntent);
			
			// ���ü��Ϲ������Լ��Ͻ��и���
			appWidgetManager.updateAppWidget(appWidgetId, rv);
			// AppWidgetManager manager = AppWidgetManager.getInstance(context);
			//appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[0], R.id.wd_lv);

		}
	}

	@Override
	public void onEnabled(Context context) {
		// Intent i = new Intent(context,UpdateWidgetService.class);
		// context.startService(i);
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		// Intent i = new Intent(context,UpdateWidgetService.class);
		// context.stopService(i);
		super.onDisabled(context);
	}
}