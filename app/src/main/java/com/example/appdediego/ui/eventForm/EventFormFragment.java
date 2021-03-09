package com.example.appdediego.ui.eventForm;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.appdediego.R;
import com.example.appdediego.models.pojo.Event;
import com.example.appdediego.utils.MinMaxFilter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class EventFormFragment extends Fragment {

    private EventFormViewModel mViewModel;
    private EditText titleInput, typeInput, dayInput, monthInput, yearInput, descriptionInput;
    private Event event;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event_fragment, container, false);
        titleInput = view.findViewById(R.id.editTextEventTitle);
        typeInput = view.findViewById(R.id.editTextType);
        dayInput = view.findViewById(R.id.editTextDateDay);
        monthInput = view.findViewById(R.id.editTextDateMonth);
        yearInput = view.findViewById(R.id.editTextDateYear);
        descriptionInput = view.findViewById(R.id.editTextDescription);

        dayInput.setFilters(new InputFilter[]{new MinMaxFilter(1, 31)});
        monthInput.setFilters(new InputFilter[]{new MinMaxFilter(1, 12)});
        yearInput.setFilters(new InputFilter[]{new MinMaxFilter(1, 2100)});

        event = (Event) getArguments().getSerializable("event");

        if (event != null) {
            TextView activityTitle = view.findViewById(R.id.createEventTitle);
            activityTitle.setText(R.string.event_form_edit_event);
            Calendar calendar = Calendar.getInstance();
            titleInput.setText(event.getTitle());
            typeInput.setText(event.getType());

            calendar.setTime(event.getDate());
            dayInput.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

            // Added 1 to month to convert from 0-based numeration
            monthInput.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
            yearInput.setText(String.valueOf(calendar.get(Calendar.YEAR)));

            descriptionInput.setText(event.getDescription());
        }

        Button buttonSave = view.findViewById(R.id.buttonSaveEvent);
        Button buttonCancel = view.findViewById(R.id.buttonCancelAddEvent);

        buttonSave.setOnClickListener(this::handleEventSave);
        buttonCancel.setOnClickListener(this::handleCancel);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EventFormViewModel.class);
    }

    public void handleEventSave(View view) {
        if (!validateInputs()) return;
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(yearInput.getText().toString()),
                // Removed 1 of month to convert to 0-based numeration
                Integer.parseInt(monthInput.getText().toString()) - 1,
                Integer.parseInt(dayInput.getText().toString())
        );
        if (event != null) {
            event.setTitle(titleInput.getText().toString());
            event.setDate(calendar.getTime());
            event.setType(typeInput.getText().toString());
            event.setDescription(descriptionInput.getText().toString());
        } else {
            event = new Event(
                    titleInput.getText().toString(),
                    calendar.getTime(),
                    descriptionInput.getText().toString(),
                    typeInput.getText().toString()
            );
        }
        mViewModel.saveEvent(event);

        Navigation.findNavController(view).navigate(R.id.action_nav_create_event_to_nav_home);
    }

    private boolean validateInputs() {
        try {
            String title = titleInput.getText().toString();
            String type = typeInput.getText().toString();
            String description = descriptionInput.getText().toString();
            String day = dayInput.getText().toString();
            String month = monthInput.getText().toString();
            String year = yearInput.getText().toString();

            String[] inputs = {title, type, description, day, month, year};

            for (String input: inputs) {
                if (input.equals("")) {
                    Toast.makeText(getContext(), "Nenhum campo pode ficar vazio", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            Toast.makeText(getContext(), "Confira os dados do evento", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void handleCancel(View view) {
        Navigation.findNavController(view).navigate(R.id.action_nav_create_event_to_nav_home);
    }
}