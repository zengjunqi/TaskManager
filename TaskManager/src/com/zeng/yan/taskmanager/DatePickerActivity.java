package com.zeng.yan.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;

public class DatePickerActivity extends Activity {
	private DatePicker datePicker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datapicker);
		datePicker = (DatePicker) findViewById(R.id.dpPicker);

		datePicker.init(2013, 8, 20, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				// 获取一个日历对象，并初始化为当前选中的时间
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, monthOfYear, dayOfMonth);
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy年MM月dd日  HH:mm");
				Toast.makeText(DatePickerActivity.this,
						format.format(calendar.getTime()), Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
