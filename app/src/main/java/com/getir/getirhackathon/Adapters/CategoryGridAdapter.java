package com.getir.getirhackathon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;

import java.util.ArrayList;

/**
 * Created by Emre on 20.02.2016.
 */
public class CategoryGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<User> users;

    public CategoryGridAdapter(Context mContext, ArrayList<User> users){
        this.mContext = mContext;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        User currentUser = users.get(position);

        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_gridview_item, null);

            holder.title = (TextView) convertView.findViewById(R.id.name);
            holder.price = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();

        }

        holder.title.setText(currentUser.getName());
        holder.price.setText(currentUser.getName());

        return convertView;
    }


    public class ViewHolder {
        public TextView title;
        public TextView price;
    }

}
