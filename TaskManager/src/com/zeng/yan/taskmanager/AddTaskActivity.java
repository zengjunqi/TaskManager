package com.zeng.yan.taskmanager;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.db.TaskDBOperator;
import com.zeng.yan.taskmanager.ui.TitleBar;
import com.zeng.yan.taskmanager.ui.TitleBar.titleBarClickListener;

public class AddTaskActivity extends Activity implements OnClickListener {

	private Spinner sp_cycle, sp_reminder;
	private EditText et_content, et_date, et_startTimer, et_endTimer;
	private Button btnAdd;
	private String[] cycle = { "不重复", "每天", "每周", "每月", "每年" };
	private String[] reminder = { "不提醒", "按时提醒", "提前5分钟", "提前10分钟", "提前15分钟",
			"提前30分钟", "提前1小时" };
	private TitleBar tBar;
	private String oper;
	TaskDBOperator dbOperator;
	private int taskId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.task_add);
		sp_cycle = (Spinner) findViewById(R.id.sp_cycle);
		sp_reminder = (Spinner) findViewById(R.id.sp_reminder);
		et_startTimer = (EditText) findViewById(R.id.et_startTimer);
		et_endTimer = (EditText) findViewById(R.id.et_endTimer);
		et_content = (EditText) findViewById(R.id.et_taskcontent);
		et_date = (EditText) findViewById(R.id.et_tastDate);
		ArrayAdapter adpt = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, cycle);
		adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_cycle.setAdapter(adpt);

		ArrayAdapter adpt1 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, reminder);
		adpt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_reminder.setAdapter(adpt1);

		et_date.setOnClickListener(this);
		et_startTimer.setOnClickListener(this);
		et_endTimer.setOnClickListener(this);
		initDateAndTime();
		tBar = (TitleBar) findViewById(R.id.myTitleBar);
		tBar.setOnTitleBarClickListener(new titleBarClickListener() {

			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		 dbOperator = new TaskDBOperator(getApplicationContext());
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnAdd.setOnClickListener(this);
		
		Intent it=getIntent();
		 oper=it.getStringExtra("oper");
		 if ("update".equals(oper)) {
			 taskId=it.getIntExtra("id", 0);
			initTaskDetails(taskId);
			btnAdd.setText("保存");
			tBar.setTitle("修改事项");
			Log.i("zeng", oper+"=="+taskId);
		}
		
				
	}

	private void initTaskDetails(int id) {
	 TaskDetails taskDetails=dbOperator.findTaskById(id);
	 et_content.setText(taskDetails.getContent());
	 et_date.setText(taskDetails.getDate());
	 et_startTimer.setText(taskDetails.getStartTime());
	 et_endTimer.setText(taskDetails.getEndTime());
	 sp_cycle.setSelection(taskDetails.getCycle());
	 sp_reminder.setSelection(taskDetails.getReminder());
	}

	/**
	 * 初始化当前日期与时间给控件当默认值
	 */
	private void initDateAndTime() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int cyear = calendar.get(Calendar.YEAR);
		int cmonth = calendar.get(Calendar.MONTH);// 系统日期从0开始算起
		int cday = calendar.get(Calendar.DAY_OF_MONTH);
		int month = cmonth + 1;
		String monthString = String.valueOf(month);
		if (monthString.length() == 1) {
			monthString = "0" + monthString;
		}
		String dayString = String.valueOf(cday);
		if (dayString.length() == 1) {
			dayString = "0" + dayString;
		}
		et_date.setText(cyear + "-" + monthString + "-" + dayString);

		String hourString = String.valueOf(hour);
		if (hourString.length() == 1) {
			hourString = "0" + hourString;
		}
		String minuteString = String.valueOf(minutes);
		if (minuteString.length() == 1) {
			minuteString = "0" + minuteString;
		}

		et_startTimer.setText(hourString + ":" + minuteString);
		et_endTimer.setText(hourString + ":" + minuteString);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {

		case R.id.et_tastDate:

			intent = new Intent(AddTaskActivity.this, DatePickerActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.et_startTimer:

			intent = new Intent(AddTaskActivity.this, TimerPickerActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.et_endTimer:

			intent = new Intent(AddTaskActivity.this, TimerPickerActivity.class);
			startActivityForResult(intent, 2);
			break;
		case R.id.btn_add:
			if (TextUtils.isEmpty(et_content.getText())) {
				Toast.makeText(this, "事项内容必须填写.", 0).show();
				Animation shake = AnimationUtils.loadAnimation(this,
						R.anim.shake);
				et_content.startAnimation(shake);
			}else {
				try {
					TaskDetails task=getMyTask();
					 if ("update".equals(oper)) {
						 dbOperator.update(task);
					 }
					 else {
						
						 dbOperator.add(task);
						 Toast.makeText(this, "添加成功!!!", Toast.LENGTH_SHORT).show();
					}					 
					finish();
				} catch (Exception e) {
					Toast.makeText(this, "添加失败!!!", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}

	private TaskDetails getMyTask() {
		
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setContent(et_content.getText().toString());
		taskDetails.setCycle(sp_cycle.getSelectedItemPosition());
		taskDetails.setDate(et_date.getText().toString());
		taskDetails.setEndTime(et_endTimer.getText().toString());
		taskDetails.setReminder(sp_reminder.getSelectedItemPosition());
		taskDetails.setStartTime(et_startTimer.getText().toString());
		 if ("update".equals(oper)) {
			 taskDetails.set_id(taskId);
			 
		 }
		return taskDetails;
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null)
			return;
		switch (resultCode) {
		case 0:
			String date = data.getStringExtra("date");
			et_date.setText(date);
			break;
		case 1:
			String time = data.getStringExtra("time");
			if (requestCode == 1) {
				et_startTimer.setText(time);
			} else {
				et_endTimer.setText(time);
			}
			break;

		default:
			break;
		}
	}
}
