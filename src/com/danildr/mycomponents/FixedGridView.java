package com.danildr.mycomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class FixedGridView extends GridView {
	
	private boolean fSetHeight = false;
	private int heightSpec;

	public FixedGridView(Context context) {
		super(context);
	}

	public FixedGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixedGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		try {
			if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
				heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			}
			else {
				heightSpec = heightMeasureSpec;
			}
			super.onMeasure(widthMeasureSpec, heightSpec);
		} catch (Exception e) {
			super.onMeasure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
		}
	}

}
