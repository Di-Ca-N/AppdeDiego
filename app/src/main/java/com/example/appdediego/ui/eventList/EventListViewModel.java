package com.example.appdediego.ui.eventList;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appdediego.models.dao.EventDAO;
import com.example.appdediego.models.pojo.Event;

import java.util.ArrayList;

public class EventListViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Event>> mEvents;

    public EventListViewModel(Application application) {
        super(application);
        mEvents = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Event>> getEvents() {
        EventDAO eventDAO = new EventDAO(getApplication().getApplicationContext());
        mEvents.setValue(eventDAO.getEvents());
        return mEvents;
    }

    public void deleteEvent(Event event) {
        EventDAO eventDAO = new EventDAO(getApplication().getApplicationContext());
        eventDAO.delete(event);
        getEvents();
    }
}