package com.danildr.androidcomponents;


import java.util.List;

import android.content.Context;
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
		View gridView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.date_grid, null);
		} else {
			gridView = (View)convertView;
		}
		
		TextView gridDateText = (TextView) gridView.findViewById(R.id.dateGridText);
		gridDateText.setText(this.days.get(position));
		return gridView;
	}

}
