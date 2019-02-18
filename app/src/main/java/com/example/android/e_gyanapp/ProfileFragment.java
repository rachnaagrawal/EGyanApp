package com.example.android.e_gyanapp;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
 TextView textName;
 TextView textEmail;
 TextView textEnrollment;
 SharedPreferences msharedpreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        textName=(TextView)v.findViewById(R.id.TextName);
        textEmail=(TextView)v.findViewById(R.id.TextEmail);
        textEnrollment=(TextView)v.findViewById(R.id.TextEnrollment);

        SqliteHelper sqlitehelper = new SqliteHelper(getActivity());

        msharedpreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String email=msharedpreferences.getString("EmailKey","");
        String enroll=msharedpreferences.getString("EnrollKey","");
        String name=msharedpreferences.getString("NameKey","");



        textEmail.setText(email);
        textEnrollment.setText(enroll);
        textName.setText(name);

        return v;

    }

}
