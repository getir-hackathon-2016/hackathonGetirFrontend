package com.getir.getirhackathon.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.getir.getirhackathon.R;

/**
 * Created by Emre on 20.02.2016.
 */
public class UserMainFragment extends Fragment {

    private GridView gridView;
    private Spinner spinnerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static UserMainFragment newInstance(String text) {

        UserMainFragment f = new UserMainFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
