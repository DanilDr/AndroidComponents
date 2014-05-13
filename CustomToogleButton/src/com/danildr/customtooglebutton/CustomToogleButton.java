package com.danildr.customtooglebutton;

/*
 * Пояснения:
 * 	buttonState
 * 		false - выключено
 * 		true - включено
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomToogleButton extends FrameLayout implements OnClickListener{
	
	private Boolean buttonState = false; // статус кнопки
	private String textOn;
	private String textOff;
	private Context context;
	private TextView mainText;
	private int textColor;

	public CustomToogleButton(Context context) {
		super(context);
	}

	public CustomToogleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
//		LayoutInflater layoutInflater = LayoutInflater.from(context);
//		layoutInflater.inflate(R.layout.maintooglebutton, this);
		
		Resources resources = (Resources) getResources();
		// инициализация текстовых сообщений
		textOn = resources.getString(R.string.buttonOn);
		textOff = resources.getString(R.string.buttonOff);
		
		// получение входных параметров
		TypedArray a = context.obtainStyledAttributes(attrs,
			    R.styleable.CustomToggleButton, 0, 0);
			 
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i)
		{
		    int attr = a.getIndex(i);
		    switch (attr)
		    {
		        case R.styleable.CustomToggleButton_toggleTextOn:
		        	textOn = a.getString(R.styleable.CustomToggleButton_toggleTextOn);
		            break;
		        case R.styleable.CustomToggleButton_toggleTextOff:
		        	textOff = a.getString(R.styleable.CustomToggleButton_toggleTextOff);
		            break;
		        case R.styleable.CustomToggleButton_toggleTextColor:
		        	textColor = a.getColor(R.styleable.CustomToggleButton_toggleTextColor, android.R.color.black);
		        	break;
//		        case R.styleable.CustomToogleBu:
//		        	break;
		    }
		}
		
		// получение текстового поля
		mainText = (TextView) this.findViewById(R.id.toggleButtonText);
		mainText.setTextColor(a.getColor(R.styleable.CustomToggleButton_toggleTextColor, 0xFF000000));
		
		a.recycle();
		// инициализация текста
		setText();
	} 

	public CustomToogleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}
	
	public void setChecked(Boolean status) {
		this.buttonState = status;
	}

	public void setTextOn(String inputTextOn) {
		textOn = inputTextOn;
	}
	
	public void setTextOff(String inputTextOff) {
		textOff = inputTextOff;
	}
	
	public void setTextOn(Integer inputTextOnId) {
		textOn = getResources().getString(inputTextOnId);
	}
	
	public void setTextOff(Integer inputTextOffId) {
		textOn = getResources().getString(inputTextOffId);
	}
	
	private void setText() {
		if (buttonState == true) {
			mainText.setText(textOn);
		} else {
			mainText.setText(textOff);
		}
	}
	
	@Override
	public void onClick(View v) {
		if (buttonState == false) {
			buttonState = true;
		} else {
			buttonState = false;
		}
		setText();
	}
}
