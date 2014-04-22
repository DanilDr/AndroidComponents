package com.danildr.androidcomponents;

import java.util.Calendar;
import java.util.Date;

public class DateCellInfo {
	public String gridStr = null;
	public Calendar date = null;
	public Boolean available = null;

	public DateCellInfo(String gridStr) {
		this.gridStr = gridStr;
	}
	
	public DateCellInfo(Calendar date) {
		this.date = date;
	}
	
	public DateCellInfo(Calendar date, Boolean available) {
		this.date = date;
		this.available = available;
	}
}
