package com.zeng.yan.taskmanager.db;

import java.util.ArrayList;
import java.util.List;

import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.utils.CalendarUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TaskDBOperator {

	private TaskDBHelper dbHelper;

	public TaskDBOperator(Context context) {
		dbHelper = new TaskDBHelper(context);
	}

	public List<TaskDetails> findPart(int offset, int maxno, String startDate,
			String endDate) {

		List<TaskDetails> result = new ArrayList<TaskDetails>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"select * from mytasks where date>= ? and date<=? order by date desc limit ? offset ?",
						new String[] { String.valueOf(startDate),
								String.valueOf(endDate), String.valueOf(maxno),
								String.valueOf(offset) });
		while (cursor.moveToNext()) {
			TaskDetails info = new TaskDetails();

			info.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
		 info.setDate(CalendarUtils.getDateAndWeek(cursor.getString(cursor.getColumnIndex("date"))));
			info.setStartTime(cursor.getString(cursor
					.getColumnIndex("startTime")));
			info.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
			info.setContent(cursor.getString(cursor.getColumnIndex("content")));
			info.setCycle(cursor.getInt(cursor.getColumnIndex("cycle")));
			info.setReminder(cursor.getInt(cursor.getColumnIndex("reminder")));
			info.setReminderDate(cursor.getString(cursor.getColumnIndex("reminderdate")));
			result.add(info);
		}
		cursor.close();
		db.close();
		return result;
	}

	public TaskDetails findTaskById(int id){

		TaskDetails info = new TaskDetails();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from mytasks where _id=? ", new String[]{String.valueOf(id)});
		if(cursor.moveToNext()){
			info.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
			info.setDate(cursor.getString(cursor.getColumnIndex("date")));
			info.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
			info.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
			info.setContent(cursor.getString(cursor.getColumnIndex("content")));
			info.setCycle(cursor.getInt(cursor.getColumnIndex("cycle")));
			info.setReminder(cursor.getInt(cursor.getColumnIndex("reminder")));
			info.setReminderDate(cursor.getString(cursor.getColumnIndex("reminderdate")));
		}
		cursor.close();
		db.close();
		return info;
	}

	public void add(TaskDetails info) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("content", info.getContent());
		values.put("date", info.getDate());
		values.put("endTime", info.getEndTime());
		values.put("startTime", info.getStartTime());
		values.put("cycle", info.getCycle());
		values.put("reminder", info.getReminder());
		values.put("reminderdate", info.getReminderDate());
		db.insert("mytasks", null, values);
		db.close();
	}

	public void update(TaskDetails info) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("content", info.getContent());
		values.put("date", info.getDate());
		values.put("endTime", info.getEndTime());
		values.put("startTime", info.getStartTime());
		values.put("cycle", info.getCycle());
		values.put("reminder", info.getReminder());
		values.put("reminderdate", info.getReminderDate());
		db.update("mytasks", values, "_id=?",
				new String[] { String.valueOf(info.get_id()) });
		db.close();
	}

	public void delete(String id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("mytasks", "_id=?", new String[] { id });
		db.close();
	}
}
