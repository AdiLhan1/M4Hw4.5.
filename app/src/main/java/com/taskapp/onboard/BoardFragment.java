package com.taskapp.onboard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.taskapp.MainActivity;
import com.taskapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_board, container, false);
        View layout=view.findViewById(R.id.relativeLayout);
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView textTitle=view.findViewById(R.id.text_title1);
        Button btnstart=view.findViewById(R.id.btn_start);
        int pos=getArguments().getInt("pos");
        switch (pos){
            case 0:
                imageView.setImageResource(R.drawable.pikabu1);
                layout.setBackgroundResource(R.drawable.background2);
                textTitle.setText("Привет");
                btnstart.setVisibility(View.GONE);
                break;
            case 1:
                imageView.setImageResource(R.drawable.pikabu2);
                layout.setBackgroundResource(R.drawable.background1);
                textTitle.setText("Как дела?");
                btnstart.setVisibility(View.GONE);
                break;
            case 2:
                imageView.setImageResource(R.drawable.pikabu3);
                layout.setBackgroundResource(R.drawable.background3);
                textTitle.setText("Что делаешь?");
                btnstart.setVisibility(View.VISIBLE);
                break;
        }
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
                preferences.edit().putBoolean("isShown",true).apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

}
