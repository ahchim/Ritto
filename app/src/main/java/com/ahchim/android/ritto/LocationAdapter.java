package com.ahchim.android.ritto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by Gold on 2017. 4. 11..
 */

public class LocationAdapter extends BaseAdapter {

    Context mContext;
    String[] locationArray;
    LayoutInflater layoutInflater;

    public LocationAdapter (Context context, String[] array){
        mContext = context;
        locationArray = array;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return locationArray.length;
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

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.location_item, null, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_location);
        textView.setText(locationArray[position]);


        return convertView;
    }
}
