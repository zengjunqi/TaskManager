package com.zeng.yan.taskmanager.bean;

public class TaskDetails {
	private int _id;
	private String content;
	private int cycle;
	private String date;
	private String startTime;
	private String endTime;
	private int reminder;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getReminder() {
		return reminder;
	}
	public void setReminder(int reminder) {
		this.reminder = reminder;
	}
	
}
