package com.danildr.androidcomponents;


import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarAdapter extends BaseAdapter {
	private Context context;
	private List<DateCellInfo> days;
	private Integer curyear;
	private Integer curmonth;
	
	public CalendarAdapter(Context context, List<DateCellInfo> days, Integer curyear, Integer curmonth){
		this.context = context;
		this.days = days;
		this.curyear = curyear;
		this.curmonth = curmonth;		
	}

	@Override
	public int getCount() {
		return this.days.size();
	}

	@Override
	public Object getItem(int arg0) {
		return days.get(arg0);
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
			gridView = (View) inflater.inflate(R.layout.date_grid, null);
		} else {
			gridView = (View) convertView;
		}
		
		TextView gridDateText = (TextView) gridView.findViewById(R.id.dateGridText);
		final DateCellInfo curDateCell = days.get(position);
		if (curDateCell.date != null) {
			Integer curDate = curDateCell.date.get(Calendar.DATE);
			gridDateText.setText(curDate.toString());
			if (curDateCell.available == false) {
				gridView.setBackgroundColor(Color.GRAY);
			}
		} else {
			gridDateText.setText(curDateCell.gridStr);
		}
		return gridView;
	}
}
