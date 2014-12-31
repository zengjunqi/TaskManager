package com.zeng.yan.taskmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Xml;

import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.db.TaskDBOperator;

public class BackupRestoreUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public interface BackUpCallBack {
		public void beforeBackup(int max);

		public void onBackup(int process);
	}

	public static void backupTasks(Context context,
			BackUpCallBack backUpCallBack) throws Exception {
		File filepath = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "TaskManager" + File.separator + "Backup");

		if (!filepath.exists()) {
			filepath.mkdirs();
		}
		File file = new File(filepath, dateFormat.format(new Date()) + ".xml");
		FileOutputStream fos = new FileOutputStream(file);

		XmlSerializer serializer = Xml.newSerializer();// 序列化器
		serializer.setOutput(fos, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, "tasks");

		TaskDBOperator helper = new TaskDBOperator(context);
		List<TaskDetails> tasks = helper.findAll();
		// pd.setMax(cursor.getCount());
		int max = tasks.size();
		backUpCallBack.beforeBackup(max);
		serializer.attribute(null, "max", max + "");
		int process = 0;
		for (TaskDetails taskDetails : tasks) {

			serializer.startTag(null, "task");
			serializer.startTag(null, "content");
			serializer.text(taskDetails.getContent());
			serializer.endTag(null, "content");

			serializer.startTag(null, "cycle");
			serializer.text(String.valueOf(taskDetails.getCycle()));
			serializer.endTag(null, "cycle");

			serializer.startTag(null, "date");
			serializer.text(taskDetails.getDate());
			serializer.endTag(null, "date");

			serializer.startTag(null, "startTime");
			serializer.text(taskDetails.getStartTime());
			serializer.endTag(null, "startTime");

			serializer.startTag(null, "endTime");
			serializer.text(taskDetails.getEndTime());
			serializer.endTag(null, "endTime");

			serializer.startTag(null, "reminder");
			serializer.text(String.valueOf(taskDetails.getReminder()));
			serializer.endTag(null, "reminder");

			serializer.startTag(null, "reminderdate");
			if (TextUtils.isEmpty(taskDetails.getReminderDate())) {
				serializer.text("");
			}else {
				serializer.text(taskDetails.getReminderDate());
			}
			serializer.endTag(null, "reminderdate");

			serializer.startTag(null, "type");
			serializer.text(String.valueOf(taskDetails.getType()));
			serializer.endTag(null, "type");

			serializer.startTag(null, "time");
			serializer.text(String.valueOf(taskDetails.getTime()));
			serializer.endTag(null, "time");

			serializer.startTag(null, "updatetime");
			serializer.text(taskDetails.getUpdateTime());
			serializer.endTag(null, "updatetime");

			serializer.endTag(null, "task");
			process++;
			backUpCallBack.onBackup(process);
		}

		serializer.endTag(null, "tasks");
		serializer.endDocument();
		fos.close();

	}

	public interface RestoreBack {
		public void beforeRestore(int max);

		public void onRestore(int process);
	}

	public static void restoreTasks(Context context, String filePath,
			RestoreBack callBack) throws Exception {
		File file = new File(filePath);
		if (file.exists()) {
			TaskDBOperator helper = new TaskDBOperator(context);
			helper.deleteAll();
			FileInputStream iStream = new FileInputStream(file);
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(iStream, "utf-8");
			int max;
			int etype = parser.getEventType();
			int process = 0;
			TaskDetails details=null;
			while (etype != XmlPullParser.END_DOCUMENT) {
				String node = parser.getName();
				switch (etype) {
				case XmlPullParser.START_DOCUMENT:// 文档开始,并不是根节点

					break;

				case XmlPullParser.START_TAG:
					if (node.equals("tasks")) {
						max = Integer.parseInt(parser.getAttributeValue(null,
								"max"));
						callBack.beforeRestore(max);
					} else if (node.equals("task")) {
						details = new TaskDetails();
						callBack.onRestore(process);
						process++;
					} else if (node.equals("content")) {
						details.setContent(parser.nextText());
					} else if (node.equals("cycle")) {
						details.setCycle(Integer.parseInt(parser.nextText()));
					} else if (node.equals("date")) {
						details.setDate(parser.nextText());
					} else if (node.equals("startTime")) {
						details.setStartTime(parser.nextText());
					} else if (node.equals("endTime")) {
						details.setEndTime(parser.nextText());
					} else if (node.equals("reminder")) {
						details.setReminder(Integer.parseInt(parser.nextText()));
					} else if (node.equals("reminderdate")) {
						details.setReminderDate(parser.nextText());
					} else if (node.equals("type")) {
						details.setType(Integer.parseInt(parser.nextText()));
					} else if (node.equals("time")) {
						details.setTime(Integer.parseInt(parser.nextText()));
					} else if (node.equals("updatetime")) {
						details.setUpdateTime(parser.nextText());
					}

					break;

				case XmlPullParser.END_TAG:
					if (node.equals("task")) {

						helper.add(details);
					}
					break;

				default:
					break;
				}
				etype = parser.next();
			}
			iStream.close();
		}

	}
}
