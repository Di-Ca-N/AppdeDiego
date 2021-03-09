package com.example.appdediego.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appdediego.R;

public class TimeLineFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeline_fragment, container, false);
        TextView textView = view.findViewById(R.id.text_slideshow);
        textView.setText(R.string.calendar_text_view);
        return view;
    }
}