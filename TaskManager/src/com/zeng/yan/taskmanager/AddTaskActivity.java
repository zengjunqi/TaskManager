package com.zeng.yan.taskmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddTaskActivity extends Activity {

	private Spinner sp_cycle, sp_reminder;
	private EditText et_content, et_startTimer, et_endTimer;
	private String[] cycle={"不重复","每天","每周","每月","每年"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.task_add);
		sp_cycle = (Spinner) findViewById(R.id.sp_cycle);
		sp_reminder = (Spinner) findViewById(R.id.sp_reminder);
		ArrayAdapter adpt=new ArrayAdapter(this,android.R.layout.simple_spinner_item,cycle);
		adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_cycle.setAdapter(adpt);
	}
}
