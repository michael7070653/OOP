package pepse.util;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Observer class for observing events
 * and notifying the observers
 * when the event is triggered
 * the event is a string
 */
public class Observer {
    private final HashMap<String, ArrayList<executeFunc>> events;

    /**
     * Constructor for the observer
     * initializes the events
     */
    public Observer() {
        events = new HashMap<>();
    }

    /**
     * Adds an event to the observer
     *if the event is already added it does nothing
     * @param event the event to be added
     */

    public void addEvent(String event) {
        if(!events.containsKey(event)) {
            events.put(event, new ArrayList<>());
        }
    }


    /**
     * Registers an action to an event
     * if the event is not added it does nothing
     * @param event the event to register the action to
     * @param action the action to be executed
     */
    public void registerEvent(String event, executeFunc action) {
        if(!events.containsKey(event)) {
            return;
        }
        events.get(event).add(action);
    }


    /**
     * Unregisters an action from an event
     * if the event is not added it does nothing
     * @param event the event to unregister the action from
     * @param action the action to be unregistered
     */
    public void unregisterEvent(String event, executeFunc action) {
        if(!events.containsKey(event)) {
            return;
        }
        events.get(event).remove(action);
    }

    /**
     * Notifies the observers of an event
     * if the event is not added it does nothing
     * @param event the event to notify the observers of
     */
    public void notify(String event) {
        if(!events.containsKey(event)) {
            return;
        }
        for(executeFunc e: events.get(event)) {
            e.execute();
        }
    }


}
