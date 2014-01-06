package com.danildr.androidcomponents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarMainLayout extends RelativeLayout {
	private FixedGridView calGridView; // грид с датами
	private List<String> listDays; // лист для заполнения грида
	private Map<Integer, Integer> monthList; // лист с месяцами
	private Integer[] weekdayList; // список дней недели
	private Calendar firstDayCurMonth = new GregorianCalendar(); // дата первого числа выбранного месяца
	private int firstDayOfWeek; // первый день недели 1 (воскресень) или 2 (понедельник)
	private TextView monthText; // поле текущего месяца
	private TextView yearText; // поле текущего года
	
	public CalendarMainLayout(Context context, AttributeSet attr) {
		super(context, attr);
		final Context curcontext = context; // родительский Context
		LayoutInflater layoutInflater = LayoutInflater.from(curcontext);
		layoutInflater.inflate(R.layout.calendar_main, this);
		// получение полей текущего месяца и года 
		monthText = (TextView) findViewById(R.id.monthText);
		yearText = (TextView) findViewById(R.id.yearText);
				
		// Инициализация списка месяцев
		monthList = createMonthsList();
		
		weekdayList = new Integer[] { R.string.monday, R.string.tuesday, R.string.wednesdy,
				R.string.thursday, R.string.friday, R.string.saturday, R.string.sunday};
		
		calGridView = (FixedGridView) findViewById(R.id.calGridView);
		// получение текущей даты
		Calendar curdate = GregorianCalendar.getInstance();
		firstDayOfWeek = curdate.getFirstDayOfWeek() - 1;
		// получение номера первого дня месяца
		// получение первого числа месяца
		firstDayCurMonth.set(curdate.get(Calendar.YEAR), curdate.get(Calendar.MONTH), 1);
		
		createCalendar(firstDayCurMonth, curcontext);
			
		// инициализация кнопок смены месяцев
		RelativeLayout leftButton = (RelativeLayout) findViewById(R.id.leftButton);
		RelativeLayout rightButton = (RelativeLayout) findViewById(R.id.rightButton);
		
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				firstDayCurMonth.add(Calendar.MONTH, -1);
				createCalendar(firstDayCurMonth, curcontext);
			}
		});
		
		rightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				firstDayCurMonth.add(Calendar.MONTH, 1);
				createCalendar(firstDayCurMonth, curcontext);
			}
		});
		
		monthText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(curcontext);
				builder.setTitle(curcontext.getString(R.string.selectmonth));
				List<String> choiceArrayList = new ArrayList<String>();
				for (Map.Entry<Integer, Integer> entry : monthList.entrySet()) {
					choiceArrayList.add(curcontext.getString(entry.getValue()));
				}
				final String[] choiseList = choiceArrayList.toArray(new String[choiceArrayList.size()]);
				int selected = -1; // does not select anything
				builder.setSingleChoiceItems(choiseList, selected, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						firstDayCurMonth.set(Calendar.MONTH, which);
						createCalendar(firstDayCurMonth, curcontext);
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		yearText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(curcontext);
				builder.setTitle(curcontext.getString(R.string.selectyear));
				final String[] choiseList = { "2013", "2014" };
				int selected = -1; // does not select anything
				builder.setSingleChoiceItems(choiseList, selected, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						firstDayCurMonth.set(Calendar.YEAR, new Integer(choiseList[which]));
						createCalendar(firstDayCurMonth, curcontext);
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
		
	}
	
	private void createCalendar(Calendar showDate, Context curcontext) {
		// получение количества дней в месяце
		int daysInMonth = showDate.getActualMaximum(Calendar.DAY_OF_MONTH);
				
		listDays = new ArrayList<String>(); // инициализация массива с календарем
		// заполнение дней недели
		for (int i = 0; i < weekdayList.length; i++) {
			listDays.add(curcontext.getString(weekdayList[i]));
		}
		
		// заполнение пустых клеток для месяца
		// получение номера недели первого числа
		int firstDayNum = showDate.get(Calendar.DAY_OF_WEEK) - 1 - firstDayOfWeek;
		if (firstDayNum < 0) { firstDayNum = 7 + firstDayNum; }
		for (int i = 0; i < firstDayNum; i++) {
			listDays.add("");
		}
		
		// заполнение грида календаря
		for (int i = 1; i <= daysInMonth; i++) {
			listDays.add(new Integer(i).toString());
		}
		
		// построение грида
		calGridView.setAdapter(new CalendarAdapter(curcontext, listDays));
		
		// заполенение данных о годе и месяцах
		int curyear = firstDayCurMonth.get(Calendar.YEAR);
		yearText.setText(new Integer(curyear).toString());
		int curmonth = firstDayCurMonth.get(Calendar.MONTH);
		monthText.setText(curcontext.getString(monthList.get(curmonth)) + ", ");
	}
	
	private Map<Integer, Integer> createMonthsList() {
		
		monthList = new TreeMap<Integer, Integer>();
		
		monthList.put(Calendar.JANUARY, R.string.january);
		monthList.put(Calendar.FEBRUARY, R.string.february);
		monthList.put(Calendar.MARCH, R.string.march);
		monthList.put(Calendar.APRIL, R.string.april);
		monthList.put(Calendar.MAY, R.string.may);
		monthList.put(Calendar.JUNE, R.string.june);
		monthList.put(Calendar.JULY, R.string.july);
		monthList.put(Calendar.AUGUST, R.string.august);
		monthList.put(Calendar.SEPTEMBER, R.string.september);
		monthList.put(Calendar.OCTOBER, R.string.october);
		monthList.put(Calendar.NOVEMBER, R.string.november);
		monthList.put(Calendar.DECEMBER, R.string.december);
		
		return monthList;
	}

}
