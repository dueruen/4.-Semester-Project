package racing.common.events;

import racing.common.data.Entity;
import java.io.Serializable;

/**
 * Game event
 * 
 */
public class Event implements Serializable{
    /**
     * Entity that made event
     */
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
