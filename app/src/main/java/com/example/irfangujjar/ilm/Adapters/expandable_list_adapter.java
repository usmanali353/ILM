package com.example.irfangujjar.ilm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.irfangujjar.ilm.R;

import java.util.HashMap;
import java.util.List;

public class expandable_list_adapter extends BaseExpandableListAdapter {
    public expandable_list_adapter(List<String> header_list, HashMap<String, List<String>> child_list) {
        this.header_list = header_list;
        this.child_list = child_list;
    }
    private List<String> header_list;
    private HashMap<String, List<String>> child_list;
    @Override
    public int getGroupCount() {
        return header_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_list.get(header_list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_list.get(header_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_header_layout, parent, false);
        }
        TextView heading= convertView.findViewById(R.id.heading);
        heading.setText(header_list.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ingredients_viewholder vh;
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.expanded_list_layout,parent,false);
            vh=new ingredients_viewholder();
            vh.ingredient_name=convertView.findViewById(R.id.ingredient);
            convertView.setTag(vh);
        }
        vh= (ingredients_viewholder) convertView.getTag();
        vh.ingredient_name.setText(child_list.get(header_list.get(groupPosition)).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
class ingredients_viewholder{
        TextView ingredient_name;
}
}
