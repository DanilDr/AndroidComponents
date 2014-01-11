package com.danildr.androidcomponents;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class WordListView extends ExpandableListView {
	private List<String> groupList;
    private List<String> childList;
    private Map<String, List<String>> wordCollection;
    private Context context;
	
	public WordListView(Context context) {
		super(context);
		this.context = context;
	}

	public WordListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		createGroupList();
        createCollection();
        final ExpandableListAdapter expListAdapter = new WordListAdapter(
                context, groupList, wordCollection);
        this.setAdapter(expListAdapter);
	}
	
	public WordListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	private void createGroupList() {
		groupList = new ArrayList<String>();
		groupList.add("HP");
        groupList.add("Dell");
        groupList.add("Lenovo");
        groupList.add("Sony");
        groupList.add("HCL");
        groupList.add("Samsung");
	}
	
	private void createCollection() {
		String[] hpModels = { "HP Pavilion G6-2014TX", "ProBook HP 4540",
        "HP Envy 4-1025TX" };
		String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
		String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
		        "ThinkPad X Series", "Ideapad Z Series" };
		String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
		        "VAIO S Series", "VAIO YB Series" };
		String[] dellModels = { "Inspiron", "Vostro", "XPS" };
		String[] samsungModels = { "NP Series", "Series 5", "SF Series" };
		
		wordCollection = new LinkedHashMap<String, List<String>>();
		
		for (String laptop : groupList) {
		    if (laptop.equals("HP")) {
		        loadChild(hpModels);
		    } else if (laptop.equals("Dell"))
		        loadChild(dellModels);
		    else if (laptop.equals("Sony"))
		        loadChild(sonyModels);
		    else if (laptop.equals("HCL"))
		        loadChild(hclModels);
		    else if (laptop.equals("Samsung"))
		        loadChild(samsungModels);
		    else
		        loadChild(lenovoModels);
		
		    wordCollection.put(laptop, childList);
		}
	}
	
    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }
}
