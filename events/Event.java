package events;

/**
 * @author ersonjaymujar
 */

public class Event {

    private String name;
    private Object source;
    private Object userData;

    public Event() {
        this.name = null;
        this.source = null;
        this.userData = null;
    }

    public Event(String name) {
        this.name = name;
        this.source = null;
        this.userData = null;
    }

    public Event(String name, Object source) {
        this.name = name;
        this.source = source;
        this.userData = null;
    }

    public Event(String name, Object source, Object userData) {
        this.name = name;
        this.source = source;
        this.userData = userData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}

