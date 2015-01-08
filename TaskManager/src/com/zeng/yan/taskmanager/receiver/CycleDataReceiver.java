package com.zeng.yan.taskmanager.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zeng.yan.taskmanager.AlarmActivity;
import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.db.TaskDBOperator;
import com.zeng.yan.taskmanager.utils.AlarmUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CycleDataReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		TaskDBOperator operator = new TaskDBOperator(context);
		// 如果没有跑循环,则取得循环数据,更新数据库,并标记已循环
		String date = dateFormat.format(new Date());
		String value="收到广播";

		if (operator.checkCycle(date) == false) {

			if (operator.cycleData(date)) {
				 value="收到广播并处理数据";
				List<TaskDetails> tasks = operator.findAll(date);
				AlarmUtils alarmUtils = new AlarmUtils(context);
				for (TaskDetails taskDetails : tasks) {
					alarmUtils.setAlarm(taskDetails);
					 value="收到广播并处理数据,且有设置闹钟";
				}
				if (tasks.size() > 0) {
					operator.addCycle(date);
					
				}
			}
			Toast.makeText(context, "*更新了", 0).show();
			System.out.println("***更新了**");
		}
		System.out.println("***收到广播了**");
		Toast.makeText(context, "**收到广播了*", 0).show();
//		Intent itIntent = new Intent(context, AlarmActivity.class);
//		itIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		itIntent.putExtra("content", value);
//		context.startActivity(itIntent);
	}

}
