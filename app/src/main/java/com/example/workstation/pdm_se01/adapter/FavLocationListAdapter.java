package com.example.workstation.pdm_se01.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.workstation.pdm_se01.model.LocationListModel.FavLocationModel;
import com.example.workstation.pdm_se01.R;

import java.util.List;

/**
 * Created by Tiago on 15/12/2016.
 */

public class FavLocationListAdapter extends ArrayAdapter<FavLocationModel> {
    List<FavLocationModel> modelItems = null;
    Context context;

    public FavLocationListAdapter(Context context, int resId,List<FavLocationModel>resource) {
        super(context, resId, resource);

        this.context = context;
        this.modelItems = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.location_list_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name.setText(modelItems.get(position).getLocation());
        if(modelItems.get(position).getCheck() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }

}