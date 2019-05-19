package racing.common.data;

import racing.common.data.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The basic game entity extended by other entities
 * 
 */
public class Entity implements Serializable {

    /**
     * Id used to distinguish entities
     */
    private final UUID ID = UUID.randomUUID();

    /**
     * Image data
     */
    private GameImage image;
    
    /**
     * A map of all entity parts assigned to the entity 
     */
    private Map<Class, EntityPart> parts;

    /**
     * No-arg constructor
     */
    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    /**
     * Add entity part to entity
     * @param part the entity part
     */
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    /**
     * Remove entity part from entity
     * @param partClass the entity part
     */
    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    /**
     * Get entity part from entity
     * @param <E> the type
     * @param partClass the class of the entity part
     * @return the found entity part
     */
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public String getID() {
        return ID.toString();
    }

    public GameImage getImage() {
        return image;
    }

    public void setImage(GameImage image) {
        this.image = image;
    }
}
