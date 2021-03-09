package com.example.appdediego.ui.showEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.appdediego.R;
import com.example.appdediego.models.pojo.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;


public class ShowEvent extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.show_event_fragment, container, false);

        Event event = (Event) getArguments().getSerializable("event");

        TextView title = root.findViewById(R.id.tvShowEventTitle);
        TextView date = root.findViewById(R.id.tvShowEventDate);
        TextView description = root.findViewById(R.id.tvShowEventDescription);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        title.setText(event.toString());
        date.setText(dateFormat.format(event.getDate()));
        description.setText(event.getDescription());

        FloatingActionButton fab = root.findViewById(R.id.fab_edit_event);
        fab.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("event", event);
            Navigation.findNavController(root)
                    .navigate(R.id.action_showEvent_to_nav_create_event, bundle);
        });

        return root;
    }
}