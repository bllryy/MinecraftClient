package com.bllry.client.core.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Manages event listeners and dispatches events
 */
public class EventManager {
    // Map of event classes to their listeners
    private final Map<Class<? extends Event>, List<EventData>> registeredEvents = new HashMap<>();

    /**
     * Register an object to receive events
     * @param object The object to register
     */
    public void register(Object object) {
        // Get all methods in the object's class
        for (Method method : object.getClass().getDeclaredMethods()) {
            // Check if the method has the EventTarget annotation
            if (method.isAnnotationPresent(EventTarget.class) && method.getParameterCount() == 1) {
                // Make the method accessible
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                // Get the event class from the method's parameter
                Class<?> eventClass = method.getParameterTypes()[0];

                // Make sure the parameter is an Event
                if (Event.class.isAssignableFrom(eventClass)) {
                    // Get the event's priority from the annotation
                    EventTarget annotation = method.getAnnotation(EventTarget.class);
                    int priority = annotation.priority();

                    // Create event data
                    @SuppressWarnings("unchecked")
                    EventData eventData = new EventData(object, method, priority, (Class<? extends Event>) eventClass);

                    // Add the event data to the map
                    if (!registeredEvents.containsKey(eventData.eventClass)) {
                        registeredEvents.put(eventData.eventClass, new ArrayList<>());
                    }

                    // Insert the event data in order of priority
                    List<EventData> eventDataList = registeredEvents.get(eventData.eventClass);
                    boolean added = false;

                    for (int i = 0; i < eventDataList.size(); i++) {
                        if (eventDataList.get(i).priority > priority) {
                            eventDataList.add(i, eventData);
                            added = true;
                            break;
                        }
                    }

                    if (!added) {
                        eventDataList.add(eventData);
                    }
                }
            }
        }
    }

    /**
     * Unregister an object from receiving events
     * @param object The object to unregister
     */
    public void unregister(Object object) {
        // Remove all event data for the object
        Iterator<Map.Entry<Class<? extends Event>, List<EventData>>> iterator = registeredEvents.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Class<? extends Event>, List<EventData>> entry = iterator.next();
            List<EventData> eventDataList = entry.getValue();

            eventDataList.removeIf(eventData -> eventData.object == object);

            if (eventDataList.isEmpty()) {
                iterator.remove();
            }
        }
    }

    /**
     * Post an event to all registered listeners
     * @param event The event to post
     * @return The event
     */
    public <T extends Event> T post(T event) {
        List<EventData> eventDataList = registeredEvents.get(event.getClass());

        if (eventDataList != null) {
            for (EventData eventData : eventDataList) {
                try {
                    eventData.method.invoke(eventData.object, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return event;
    }

    /**
     * Data class for storing event listener information
     */
    private static class EventData {
        private final Object object;
        private final Method method;
        private final int priority;
        private final Class<? extends Event> eventClass;

        public EventData(Object object, Method method, int priority, Class<? extends Event> eventClass) {
            this.object = object;
            this.method = method;
            this.priority = priority;
            this.eventClass = eventClass;
        }
    }
}