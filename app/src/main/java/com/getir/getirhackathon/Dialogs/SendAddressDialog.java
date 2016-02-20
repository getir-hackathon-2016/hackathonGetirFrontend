package com.getir.getirhackathon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ListPopupWindow;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.getir.getirhackathon.MapFragmentActivity;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Emre on 20.02.2016.
 */
public class SendAddressDialog extends Dialog {
    private Context context;
    private FancyButton send_saved_address, send_current_location;
    public SendAddressDialog(final Context context) {
        super(context);
        this.context = context;
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.send_adress_layout);
        send_saved_address = (FancyButton) findViewById(R.id.send_saved_address);
        send_current_location = (FancyButton) findViewById(R.id.send_current_location);

        send_saved_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    send_saved_address.setGhost(false);
                    send_saved_address.setBackgroundColor(context.getResources().getColor(R.color.gray));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    send_saved_address.setGhost(true);
                }
                return false;
            }
        });

        send_current_location.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    send_current_location.setGhost(false);
                    send_current_location.setBackgroundColor(context.getResources().getColor(R.color.gray));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    send_current_location.setGhost(true);
                }
                return false;
            }
        });

        send_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapFragmentActivity.class);
                dismiss();
                context.startActivity(intent);
            }
        });

        send_saved_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getInstance().getAddresses() != null && User.getInstance().getAddresses().size() > 0){
                    dismiss();
                    AddressesListDialog dialog = new AddressesListDialog(context);
                    dialog.show();

                }
            }
        });

    }
}
