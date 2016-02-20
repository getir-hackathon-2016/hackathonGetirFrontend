package com.getir.getirhackathon.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.getir.getirhackathon.Adapters.CategorySpinnerAdapter;
import com.getir.getirhackathon.Objects.Category;
import com.getir.getirhackathon.R;

import java.util.ArrayList;

/**
 * Created by Emre on 20.02.2016.
 */
public class UserMainFragment extends Fragment {

    private GridView gridView;
    private Spinner spinnerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_main_fragment_layout, null);
        //gridView = (GridView) v.findViewById(R.id.gridView);
        spinnerList = (Spinner) v.findViewById(R.id.spinnerList);

        String[] names = {"All", "Category 1", "Category 2"};

        //spinnerList.setAdapter(new CategorySpinnerAdapter(getActivity(), categoriesArray));
        //spinnerList.setSelection(0);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerList.setAdapter(spinnerArrayAdapter);
        return v;
    }

    private void initView(){
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        gridView = (GridView) inflater.findViewById(R.id.gridView);
//        spinnerList = (Spinner) getView().findViewById(R.id.spinnerList);
//
//        ArrayList<Category> categoriesArray = new ArrayList<>();
//        Category category1 = new Category();
//        category1.setName("Category 1");
//        Category category2 = new Category();
//        category1.setName("Category 2");
//        categoriesArray.add(category1);
//        categoriesArray.add(category2);
//
//        //spinnerList.setAdapter(new CategorySpinnerAdapter(getActivity(), categoriesArray));
//        //gridView.setAdapter(new CategorySpinnerAdapter);
    }
}
