package com.zeng.yan.taskmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimerPickerActivity extends Activity {
	private TimePicker timePicker;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.timepicker);
	timePicker = (TimePicker) findViewById(R.id.tpPicker);

	
	timePicker.setIs24HourView(true);
	timePicker
			.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					Toast.makeText(TimerPickerActivity.this,
							hourOfDay + "–° ±" + minute + "∑÷÷”",
							Toast.LENGTH_SHORT).show();
				}
			});

}
}
