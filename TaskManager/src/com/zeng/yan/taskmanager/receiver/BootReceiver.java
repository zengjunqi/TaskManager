package com.zeng.yan.taskmanager.receiver;

import com.zeng.yan.taskmanager.AlarmActivity;
import com.zeng.yan.taskmanager.utils.AlarmUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	  private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
	  private final String ACTION_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
	  private final String ACTION_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String value;
		if (intent.getAction().equals(ACTION_BOOT)) {
		Toast.makeText(context, "开机了", 0).show();
		System.out.println("开机了");
//			setIntent(context,"开机了");
			setAlarm(context);
			//Intent intent2=new Intent(context,CycleDataActivity.class);
			//context.startActivity(intent2);
		}else if (intent.getAction().equals(ACTION_MOUNTED)) {
//			Toast.makeText(context, "SD挂载了", 0).show();
//			System.out.println("SD挂载了");
//			setIntent(context,"SD挂载了");
		}else if (intent.getAction().equals(ACTION_UNMOUNTED)) {
//			Toast.makeText(context, "SDxie载了", 0).show();
//			System.out.println("SDxie载了");
//			setIntent(context,"SDxie载了");
		}
		
		

	}
	
	private void setAlarm(Context context) {
		AlarmUtils alarmUtils=new AlarmUtils(context);
		//alarmUtils.cancleAlarm();
		alarmUtils.setCycleDataAlarm();
	}
	

}
