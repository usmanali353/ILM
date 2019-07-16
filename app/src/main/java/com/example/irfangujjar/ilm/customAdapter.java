package com.example.irfangujjar.ilm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class customAdapter extends BaseAdapter
{
    String[] title;
    String[] description;
    String [] example;
    int[] audio;

    Context context;

    public customAdapter(String[] title, String[] description, String[] example, int[] audio, ramoozeAuqaf ramoozeAuqaf) {
        this.context=ramoozeAuqaf;
        this.description=description;
        this.title=title;
        this.example=example;
        this.audio=audio;


    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.design,parent,false);
        TextView tvtitl=v.findViewById(R.id.tvTitlRamuz);
        TextView tvdescr=v.findViewById(R.id.tvdesclRamuz);
        TextView tvexample=v.findViewById(R.id.tvexample);


        tvtitl.setText(title[position]);
        tvdescr.setText(description[position]);
        tvexample.setText(example[position]);

        return v;
    }
}