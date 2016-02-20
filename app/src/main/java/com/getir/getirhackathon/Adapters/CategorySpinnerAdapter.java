package com.getir.getirhackathon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.getir.getirhackathon.Objects.Category;
import com.getir.getirhackathon.R;

import java.util.ArrayList;

/**
 * Created by Emre on 20.02.2016.
 */
public class CategorySpinnerAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Category> categoryArrayList;

    public CategorySpinnerAdapter(Context mContext, ArrayList<Category> categoryArray){
        this.mContext = mContext;
        categoryArrayList = categoryArray;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_spinner_item, null);

            Category currentCategory = categoryArrayList.get(position);

            holder.name = (TextView) convertView.findViewById(R.id.spinner_name);
            holder.name.setText(currentCategory.getName());

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder {
        public TextView name;

    }
}
