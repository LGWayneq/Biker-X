//IN PROGRESS
package com.example.bikerx.ui.fullmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bikerx.R;

import java.util.ArrayList;
import java.util.List;

public class AmenityAdapter extends ArrayAdapter<AmenityType> {
    public AmenityAdapter(Context context, int resource, ArrayList<AmenityType> amenityList){
        super(context, resource, amenityList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        AmenityType amenityType = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.filter_dropdown,parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.filterAmenityName);

      //  tv.setText(toString(amenityType));

        return convertView;
    }
}
