package com.zeng.yan.taskmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zeng.yan.taskmanager.AddTaskActivity;
import com.zeng.yan.taskmanager.AlarmActivity;
import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.receiver.CycleDataReceiver;

import android.R.integer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmUtils {

	private Context context;
	AlarmManager aManager;

	public AlarmUtils(Context context) {
		this.context = context;
		aManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
	}

	public  void setCycleDataAlarm() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
//		calendar.set(Calendar.HOUR_OF_DAY, 8);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
		Intent intent = new Intent(context, CycleDataReceiver.class); // 创建Intent对象
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager aManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		aManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), 1000 * 60*60*3, pi);//*60*24
		System.out.println("set alarm CycleDataReceiver");

	}
	
	public boolean setAlarm(TaskDetails taskDetails) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(taskDetails.getDate() + " "
					+ taskDetails.getReminderDate()));
			Intent intent = new Intent(context,
					AlarmActivity.class); // 创建Intent对象
			intent.putExtra("content", taskDetails.getContent());
			PendingIntent pi = PendingIntent.getActivity(context,
					taskDetails.get_id(), intent, Intent.FLAG_ACTIVITY_NEW_TASK);
			aManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					pi);

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
/*	private void cancleAlarm(int id) {
		Intent intent = new Intent(context,
				AlarmActivity.class); // 创建Intent对象
		PendingIntent pi = PendingIntent.getActivity(context,
				id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		aManager.cancel(pi);
	}*/


}
