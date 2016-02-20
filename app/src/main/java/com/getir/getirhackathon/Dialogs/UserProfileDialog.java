package com.getir.getirhackathon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.getir.getirhackathon.Objects.ServiceUser;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

/**
 * Created by Emre on 20.02.2016.
 */
public class UserProfileDialog extends Dialog {
    private TextView user_icon_text, name_text, info_text, info_icon, phone_icon, phone_text;
    private Context context;
    private User currentUser;
    public UserProfileDialog(Context context) {
        super(context);
        this.context = context;
        currentUser = User.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.profile_dialog);
        user_icon_text = (TextView) findViewById(R.id.user_icon_text);
        user_icon_text.setTypeface(Util.getFontAwesome(context));

        name_text = (TextView) findViewById(R.id.name_text);
        info_text = (TextView) findViewById(R.id.info_text);
        info_icon = (TextView) findViewById(R.id.info_icon);
        phone_text = (TextView) findViewById(R.id.phone_text);
        phone_icon = (TextView) findViewById(R.id.phone_icon);

        phone_icon.setTypeface(Util.getFontAwesome(context));
        info_icon.setTypeface(Util.getFontAwesome(context));

        name_text.setText(currentUser.getName());
        info_text.setText(currentUser.getInfo());
        phone_text.setText(String.valueOf(currentUser.getPhone()));
    }
}
