package com.getir.getirhackathon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.getir.getirhackathon.MapFragmentActivity;
import com.getir.getirhackathon.Objects.Address;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Emre on 20.02.2016.
 */
public class CurrentLocationAddDialog extends Dialog {
    Context context;
    EditText name, address_text, town, country;
    FancyButton save;
    public CurrentLocationAddDialog(final Context context, final double latitude, final double longitude) {
        super(context);
        this.context = context;
        setContentView(R.layout.current_location_add_layout);

        name = (EditText) findViewById(R.id.name);
        address_text = (EditText) findViewById(R.id.adress);
        town = (EditText) findViewById(R.id.town);
        country = (EditText) findViewById(R.id.country);

        save = (FancyButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = new Address();
                address.setName(name.getText().toString());
                address.setAddress(address_text.getText().toString());
                address.setTown(town.getText().toString());
                address.setCountry(country.getText().toString());
                address.setLatitude(latitude);
                address.setLongitude(longitude);

                User.getInstance().addresses.add(address);
                dismiss();
                ((MapFragmentActivity) context).finish();
            }
        });

        save.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    save.setGhost(false);
                    save.setBackgroundColor(context.getResources().getColor(R.color.black));
                    save.setTextColor(context.getResources().getColor(R.color.white));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    save.setGhost(true);
                    save.setTextColor(context.getResources().getColor(R.color.black));
                }
                return false;
            }
        });
    }
}
