package com.example.appdediego.ui.eventList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appdediego.R;
import com.example.appdediego.models.pojo.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventListFragment extends Fragment {

    private EventListViewModel eventListViewModel;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventListViewModel = new ViewModelProvider(this).get(EventListViewModel.class);
        View root = inflater.inflate(R.layout.event_list_fragment, container, false);

        listView = root.findViewById(R.id.eventsListView);

        eventListViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayAdapter<Event> adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_list_item_1, events);
            for (Event event : events) {
                stringBuilder.append(event.getTitle());
                stringBuilder.append("\n");
            }
            listView.setAdapter(adapter);
        });

        registerForContextMenu(listView);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Event event = (Event) parent.getItemAtPosition(position);
            showEvent(event);
        });


        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view ->
                Navigation.findNavController(root)
                        .navigate(R.id.action_nav_home_to_nav_create_event)
        );

        return root;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.event_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Event event = (Event) listView.getAdapter().getItem(info.position);

        switch (item.getItemId()) {
            case R.id.delete_event:
                deleteEvent(event);
                return true;
            case R.id.show_event:
                showEvent(event);
                return true;
            case R.id.edit_event:
                editEvent(event);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void showEvent(Event event) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_home_to_showEvent, bundle);
    }

    public void editEvent(Event event) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_home_to_nav_create_event, bundle);
    }

    public void deleteEvent(Event event) {
        eventListViewModel.deleteEvent(event);
    }
}