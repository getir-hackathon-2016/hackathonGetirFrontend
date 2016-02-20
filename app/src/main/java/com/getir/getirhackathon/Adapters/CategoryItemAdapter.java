package com.getir.getirhackathon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.getir.getirhackathon.Objects.ServiceUser;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Emre on 20.02.2016.
 */
public class CategoryItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ServiceUser> array;

    public CategoryItemAdapter(Context context, ArrayList<ServiceUser> array){
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        final ServiceUser currentServiceUser = array.get(position);

        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_gridview_item, null);

            holder.title = (TextView) convertView.findViewById(R.id.name);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.choose_button = (FancyButton) convertView.findViewById(R.id.choose_button);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(currentServiceUser.getName());
        holder.price.setText(currentServiceUser.getName());
        holder.category.setText(currentServiceUser.getCategory().getName());
        holder.choose_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.choose_button.setGhost(false);
                    holder.choose_button.setBackgroundColor(context.getResources().getColor(R.color.gray));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.choose_button.setGhost(true);
                }
                return false;
            }
        });
        holder.choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.socket != null){
                    final JSONObject jObject = new JSONObject();
                    try {
                        jObject.put("userId", User.getInstance().getId());
                        jObject.put("latitude", User.getInstance().getLatitude());
                        jObject.put("longitude", User.getInstance().getLongitude());
                        jObject.put("courierId", currentServiceUser.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Util.socket.emit("lockCousier", jObject);
                }

                //Util.socket.emit();
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public TextView title;
        public TextView price;
        public TextView category;
        public FancyButton choose_button;
    }
}
