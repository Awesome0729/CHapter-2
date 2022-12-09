package events;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ersonjaymujar
 */
public class EventCenter {

    public static EventCenter sharedInstance = null;
    private final HashMap<String, CopyOnWriteArrayList<EventListener>> listenerMap;

    public EventCenter() {
        this.listenerMap = new HashMap<>();
    }

    public static EventCenter defaultCenter() {
        if (sharedInstance == null) {
            sharedInstance = new EventCenter();
        }
        return sharedInstance;
    }

    /**
     * @param eventName - listener listen for this event
     * @param listener  - listener for the eventName
     */
    public void addListener(String eventName, EventListener listener) {
        synchronized (this.listenerMap) {
            CopyOnWriteArrayList<EventListener> list = this.listenerMap.get(eventName);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
                this.listenerMap.put(eventName, list);
            }

            /**
             * Check if the passed @param listener is not yet added as a listener
             * of event @param eventName
             */
            if (!list.contains(listener)) {
                list.add(listener);
            }
        }
    }

    public void removeListener(String eventName, EventListener listener) {
        synchronized (this.listenerMap) {
            CopyOnWriteArrayList<EventListener> list = this.listenerMap.get(eventName);
            if (list == null) return;
            list.remove(listener);
            if (list.size() == 0) {
                this.listenerMap.remove(eventName);
            }
        }
    }

    public boolean hasListener(String eventName, EventListener listener) {
        synchronized (this.listenerMap) {
            CopyOnWriteArrayList<EventListener> list = this.listenerMap.get(eventName);
            return list != null && list.contains(listener);
        }
    }

    public void dispatchEvent(String eventName) {
        Event event = new Event(eventName);
        dispatchEvent(event);
    }

    public void dispatchEvent(String eventName, Object sender) {
        Event event = new Event(eventName, sender);
        dispatchEvent(event);
    }

    public void dispatchEvent(String eventName, Object sender, Object userData) {
        Event event = new Event(eventName, sender, userData);
        dispatchEvent(event);
    }

    public void dispose() {
        synchronized (this.listenerMap) {
            this.listenerMap.clear();
        }
    }

    private void dispatchEvent(Event event) {
        if (event == null) {
            return;
        }
        CopyOnWriteArrayList<EventListener> list;
        synchronized (this.listenerMap) {
            list = this.listenerMap.get(event.getName());
        }
        if (list == null) return;
        for (EventListener l : list) {
            l.onEvent(event);
        }
    }
}
