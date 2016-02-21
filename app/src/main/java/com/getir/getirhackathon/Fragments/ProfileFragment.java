package com.getir.getirhackathon.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

/**
 * Created by Emre on 20.02.2016.
 */
public class ProfileFragment extends Fragment {

    private TextView profile_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_layout, container, false);
        profile_text = (TextView) v.findViewById(R.id.profile_text);
        profile_text.setTypeface(Util.getFontAwesome(getActivity()));

        return v;
    }

    public static ProfileFragment newInstance(String text) {

        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
