package com.zeng.yan.taskmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDBHelper extends SQLiteOpenHelper {

	public TaskDBHelper(Context context) {
		super(context, "tasks.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table mytasks (_id integer primary key autoincrement,content varchar(200),"
				+ "cycle integer,date varchar(20),startTime varchar(20),endTime varchar(20),reminder integer,"
				+ "reminderdate varchar(20),type integer,time integer,  updatetime datetime default (datetime(CURRENT_TIMESTAMP,'localtime')))");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
