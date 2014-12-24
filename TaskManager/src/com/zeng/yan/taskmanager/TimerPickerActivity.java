package com.zeng.yan.taskmanager;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimerPickerActivity extends Activity {
	private TimePicker timePicker;
	private Button btOk, btCancel;
	private String selectTimeString = "";
	String hourString = "";
	String minuteString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.timepicker);
		timePicker = (TimePicker) findViewById(R.id.tpPicker);
		timePicker.setIs24HourView(true);
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minutes);
		hourString = String.valueOf(hour);
			if (hourString.length() == 1) {
				hourString = "0" + hourString;
			}
			minuteString = String.valueOf(minutes);
			if (minuteString.length() == 1) {
				minuteString = "0" + minuteString;
			}
		selectTimeString = hourString + ":" + minuteString;
		timePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
					@Override
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						hourString = String.valueOf(hourOfDay);
						if (hourString.length() == 1) {
							hourString = "0" + hourString;
						}
						minuteString = String.valueOf(minute);
						if (minuteString.length() == 1) {
							minuteString = "0" + minuteString;
						}
					selectTimeString = hourString + ":" + minuteString;
					}
				});
		btOk = (Button) findViewById(R.id.btn_pickOK);
		btCancel = (Button) findViewById(R.id.btn_pickCancel);
		btOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("time", selectTimeString);
				setResult(1, data);
				// 当前页面关闭掉
				finish();
			}
		});
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
