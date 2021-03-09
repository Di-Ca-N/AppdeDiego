package com.example.appdediego.ui.eventForm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appdediego.models.dao.EventDAO;
import com.example.appdediego.models.pojo.Event;


public class EventFormViewModel extends AndroidViewModel {
    public EventFormViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveEvent(Event event) {
        EventDAO eventDAO = new EventDAO(getApplication().getApplicationContext());
        if (event.getId() == null) {
            eventDAO.save(event);
        } else {
            eventDAO.update(event);
        }
    }
}