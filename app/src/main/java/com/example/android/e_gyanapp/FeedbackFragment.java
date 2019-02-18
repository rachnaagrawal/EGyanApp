package com.example.android.e_gyanapp;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends android.support.v4.app.Fragment {


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_feedback, container, false);
        final DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());

        final TextInputLayout txt_name=v.findViewById(R.id.txt_name);
        final EditText name=v.findViewById(R.id.name);

        final TextInputLayout txt_number=v.findViewById(R.id.txt_mailid);
        final EditText number=v.findViewById(R.id.mailid);

        final TextInputLayout txt_desc=v.findViewById(R.id.txt_desc);
        final EditText desc=v.findViewById(R.id.desc);

        Button submit=v.findViewById(R.id.submit);
        //Button show=findViewById(R.id.show);
        Button Cancel=v.findViewById(R.id.cancel_button);

        //final ListView lst = (ListView)findViewById(R.id.lst1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.insertNote(name.getText().toString(), number.getText().toString(), desc.getText().toString());
                Toast.makeText(getActivity().getApplicationContext(), "Thankyou for the Feedback", Toast.LENGTH_LONG).show();

            }
        });

        return v;
    }

}
