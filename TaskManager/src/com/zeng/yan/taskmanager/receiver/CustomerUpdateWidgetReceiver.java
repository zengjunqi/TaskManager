package com.zeng.yan.taskmanager.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zeng.yan.taskmanager.R;
import com.zeng.yan.taskmanager.service.MyRemoteViewService;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class CustomerUpdateWidgetReceiver extends BroadcastReceiver {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Override
	public void onReceive(Context context, Intent intent) {
		AppWidgetManager awm = AppWidgetManager.getInstance(context);
		String conditonDate = dateFormat.format(new Date());
		ComponentName provider = new ComponentName(context, DailyWidget.class);
		int[] appids = awm.getAppWidgetIds(provider);
		RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.process_widget);
		rv.setTextViewText(R.id.tv_month, conditonDate + " 日程");

		// Intent serviceIntent = new Intent(context,
		// MyRemoteViewService.class);
		// serviceIntent.putExtra("date", conditonDate);
		// rv.setRemoteAdapter(R.id.wd_lv, serviceIntent);

		awm.notifyAppWidgetViewDataChanged(appids, R.id.wd_lv);
		awm.updateAppWidget(provider, rv);

		System.out.println("自定议更新widget了.....");
	}

}
