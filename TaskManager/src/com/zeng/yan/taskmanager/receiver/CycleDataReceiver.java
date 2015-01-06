package com.zeng.yan.taskmanager.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
		// ���û����ѭ��,��ȡ��ѭ������,�������ݿ�,�������ѭ��
		String date = dateFormat.format(new Date());
	
		if (operator.checkCycle(date) == false) {

			if (operator.cycleData(date)) {
				operator.addCycle(date);
				List<TaskDetails> tasks = operator.findAll(date);
				 AlarmUtils alarmUtils=new AlarmUtils(context);
				for (TaskDetails taskDetails : tasks) {
					 alarmUtils.setAlarm(taskDetails);
					System.out.println(taskDetails);
				}
			}
			Toast.makeText(context, "������", 0).show();
			System.out.println("***������**");
		}
		Toast.makeText(context, "�յ��㲥��", 0).show();
		System.out.println("***�յ��㲥��**");
	}

}