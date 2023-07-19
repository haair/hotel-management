package com.example.hotelmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.RoomTypeDrop;

import java.util.List;

public class CustomDropDownAdapter extends ArrayAdapter<RoomTypeDrop> {

    public CustomDropDownAdapter(@NonNull Context context, int resource, @NonNull List<RoomTypeDrop> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drop_item_selected, parent, false);
        TextView textView = convertView.findViewById(R.id.tv_selected);
        RoomTypeDrop roomTypeDrop = this.getItem(position);
        if (roomTypeDrop != null) {
            textView.setText(roomTypeDrop.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drop_item, parent, false);
        TextView textView = convertView.findViewById(R.id.tv_drop_item_name);
        RoomTypeDrop roomTypeDrop = this.getItem(position);
        if (roomTypeDrop != null) {
            textView.setText(roomTypeDrop.getName());
        }
        return convertView;
    }
}
