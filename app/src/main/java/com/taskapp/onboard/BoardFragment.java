package com.taskapp.onboard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taskapp.R;

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
        int pos=getArguments().getInt("pos");
        switch (pos){
            case 0:
                imageView.setImageResource(R.drawable.pikabu1);
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textTitle.setText("Привет");
                break;
            case 1:
                imageView.setImageResource(R.drawable.pikabu2);
                layout.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                textTitle.setText("Как дела?");
                break;
            case 2:
                imageView.setImageResource(R.drawable.pikabu3);
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                textTitle.setText("Что делаешь?");
                break;
        }

        return view;
    }

}
