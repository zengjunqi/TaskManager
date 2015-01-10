package com.zeng.yan.taskmanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerActivity extends Activity {
	private DatePicker datePicker;
	private Button btOk, btCancel;
	private String selectDateString = "";
	String monthString = "";
	String dayString;
	private OnDateSetListener changerListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		// new DatePickerDialog()
		changerListener=new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				System.out.println("year"+year+"monthOfYear"+monthOfYear+"monthOfYear"+dayOfMonth);
				int ccmonth = monthOfYear + 1;
				 monthString = String.valueOf(ccmonth);
				if (monthString.length() == 1) {
					monthString = "0" + monthString;
				}
				 dayString = String.valueOf(dayOfMonth);
				if (dayString.length() == 1) {
					dayString = "0" + dayString;
				}
				selectDateString = year + "-" + monthString + "-" + dayString;
				Intent data = new Intent();
				data.putExtra("date", selectDateString);
				setResult(0, data);
				System.out.println(selectDateString);
				finish();
				
			}
		};
		new DatePickerDialog(DatePickerActivity.this,
				changerListener, mYear, mMonth, mDay).show();

	/*	setContentView(R.layout.datapicker);
		datePicker = (DatePicker) findViewById(R.id.dpPicker);
		Calendar c = Calendar.getInstance();// 获得系统当前日期
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);// 系统日期从0开始算起
		int cday = c.get(Calendar.DAY_OF_MONTH);
		int ccmonth = cmonth + 1;
		 monthString = String.valueOf(ccmonth);
		if (monthString.length() == 1) {
			monthString = "0" + monthString;
		}
		 dayString = String.valueOf(cday);
		if (dayString.length() == 1) {
			dayString = "0" + dayString;
		}
		selectDateString = cyear + "-" + monthString + "-" + dayString;
		// datePicker.updateDate(cyear, cmonth-1, cday);
		// Log.i("zeng", selectDateString);
		datePicker.init(cyear, cmonth, cday, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				// 获取一个日历对象，并初始化为当前选中的时间
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, monthOfYear, dayOfMonth);
				int month = monthOfYear + 1;
				 monthString = String.valueOf(month);
					if (monthString.length() == 1) {
						monthString = "0" + monthString;
					}
					 dayString = String.valueOf(dayOfMonth);
					if (dayString.length() == 1) {
						dayString = "0" + dayString;
					}
				selectDateString = year + "-" + monthString + "-" + dayString;
				// Log.i("zeng","=="+ selectDateString);
				// Log.i("zeng","**"+ monthOfYear);
			}
		});

		btOk = (Button) findViewById(R.id.btn_pickOK);
		btCancel = (Button) findViewById(R.id.btn_pickCancel);
		btOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				datePicker.clearFocus();//为了能在编辑状态下也取到值
				Intent data = new Intent();
				data.putExtra("date", selectDateString);
				setResult(0, data);
				// 当前页面关闭掉
				finish();
			}
		});
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});*/
	}

}
