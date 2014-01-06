package com.danildr.mycomponents;


import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	private Context context;
	private List<String> days;
	
	public CalendarAdapter(Context context, List<String> days){
		this.context = context;
		this.days = days;
	}

	@Override
	public int getCount() {
		return this.days.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView gridView;
		if (convertView == null) {
			gridView = new TextView(this.context);
		} else {
			gridView = (TextView)convertView;
		}
		gridView.setText(this.days.get(position));
		gridView.setTextColor(Color.WHITE);
		gridView.setGravity(Gravity.CENTER);
		return gridView;
	}

}
