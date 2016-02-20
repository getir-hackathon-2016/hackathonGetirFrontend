package com.getir.getirhackathon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.getir.getirhackathon.Objects.Address;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emre on 20.02.2016.
 */
public class AddressesListDialog extends Dialog {

    ArrayList<Address> addresses;
    Context context;
    ListView list_view;

    public AddressesListDialog(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.addresses_list_layout);
        list_view = (ListView) findViewById(R.id.list_view);

        String[] addressNames = new String[addresses.size()];
        for(int i = 0; i<addressNames.length; i++){
            addressNames[i] = addresses.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, addressNames);


        // Assign adapter to ListView
        list_view.setAdapter(adapter);

        // ListView Item Click Listener
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                addresses.get(position);
                if(Util.socket != null){
                    final JSONObject jObject = new JSONObject();
                    try {
                        jObject.put("id", User.getInstance().getId());
                        jObject.put("latitude", addresses.get(position).getLatitude());
                        jObject.put("longitude", addresses.get(position).getLongitude());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Util.socket.emit("getAvailableCouriers", jObject);
                }
            }
        });
    }
}
