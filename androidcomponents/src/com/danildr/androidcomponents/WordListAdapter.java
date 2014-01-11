package com.danildr.androidcomponents;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class WordListAdapter extends BaseExpandableListAdapter {
	private Context context;
	private Map<String, List<String>> wordCollection; // словарь где ключ - первая буква, а значения - список слов
	private List<String> firstLetList; // список первых букв
	
	public WordListAdapter(Context context, List<String> firstLetList, Map<String, List<String>> wordCollection) {
		this.wordCollection = wordCollection;
		this.context = context;
		this.firstLetList = firstLetList;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return wordCollection.get(firstLetList.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		final String word = (String) getChild(groupPosition, childPosition);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_item, null);
		}
		
		TextView wordelemlist = (TextView) convertView.findViewById(R.id.wordelemlist);
		wordelemlist.setText(word);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return wordCollection.get(firstLetList.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return firstLetList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return firstLetList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpalnded, View convertView, ViewGroup parent) {
		String firstLetter = (String) getGroup(groupPosition);
		if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
		
		TextView firstLetterWord = (TextView) convertView.findViewById(R.id.firstLetterWord);
		firstLetterWord.setText(firstLetter);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
