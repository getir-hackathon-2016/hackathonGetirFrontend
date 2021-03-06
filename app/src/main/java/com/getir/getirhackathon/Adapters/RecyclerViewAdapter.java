package com.getir.getirhackathon.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getir.getirhackathon.Dialogs.OrderDialog;
import com.getir.getirhackathon.Objects.ServiceUser;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<ServiceUser> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView price;
        public TextView duration;
        public TextView distance;
        public RelativeLayout relative_layout;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            price = (TextView) v.findViewById(R.id.price);
            duration = (TextView) v.findViewById(R.id.duration);
            distance = (TextView) v.findViewById(R.id.distance);
            relative_layout = (RelativeLayout) v.findViewById(R.id.relative_layout);
        }
    }

    public void add(int position, ServiceUser item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(ServiceUser item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(List<ServiceUser> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = mDataset.get(position).getName();
        holder.txtHeader.setText(mDataset.get(position).getName());
        holder.txtFooter.setText(mDataset.get(position).getInfo());
        holder.price.setText(String.valueOf(mDataset.get(position).getPrice().getTl()) + "TL");
        holder.duration.setText(String.valueOf(mDataset.get(position).getDistance().getDurationInSeconds() / 60 + context.getResources().getString(R.string.minute)));
        holder.duration.setText(String.valueOf(mDataset.get(position).getDistance().getDistanceInCentimeters() / 100 + context.getResources().getString(R.string.meters)));
//        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //sendOrder();
//                if(sendOrder(mDataset.get(position))){
//                    //holder.relative_layout.setBackgroundColor(Color.GREEN);
//                }
//            }
//        });

        holder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendOrder(mDataset.get(position))){
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public boolean sendOrder(ServiceUser courier) {
        final JSONObject jObject = new JSONObject();
        try {
            jObject.put("userId", User.getInstance().getId());
            jObject.put("courierId", courier.getId());
            jObject.put("longitude", User.getInstance().getLongitude());
            jObject.put("latitude", User.getInstance().getLatitude());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("newOrder", e.getLocalizedMessage());
            Crashlytics.log("newOrder" + "-" + e.getLocalizedMessage());
        }
        if (Util.socket != null) {
            Util.socket.emit("newOrder", jObject);
            OrderDialog dialog = new OrderDialog(context, courier.getId());
            dialog.show();
            Log.i("newOrder", jObject.toString());
            return true;
        }else{
            return false;
        }

    }

}