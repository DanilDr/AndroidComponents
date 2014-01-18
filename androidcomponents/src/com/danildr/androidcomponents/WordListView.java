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
		groupList.add("А");
        groupList.add("Б");
        groupList.add("В");
        groupList.add("Г");
        groupList.add("Д");
        groupList.add("Е");
	}
	
	private void createCollection() {
		String[] wordList1 = { "Арбуз", "Абажур", "Армян" };
		String[] wordList2 = { "Балалайка", "Бубреныш", "Буренка" };
		String[] wordList3 = { "Веник", "Вобла", "Волк", "Воланд" };
		String[] wordList4 = { "Гвоздь", "Гадалка", "Горком", "Горка" };
		String[] wordList5 = { "Дуло", "Душка", "Дятел" };
		String[] wordList6 = { "Ешка", "Ебазавр", "Епел" };
		
		wordCollection = new LinkedHashMap<String, List<String>>();
		
		for (String laptop : groupList) {
		    if (laptop.equals("А")) {
		        loadChild(wordList1);
		    } else if (laptop.equals("Б"))
		        loadChild(wordList2);
		    else if (laptop.equals("В"))
		        loadChild(wordList3);
		    else if (laptop.equals("Г"))
		        loadChild(wordList4);
		    else if (laptop.equals("Д"))
		        loadChild(wordList5);
		    else
		        loadChild(wordList6);
		
		    wordCollection.put(laptop, childList);
		}
	}
	
    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }
}
