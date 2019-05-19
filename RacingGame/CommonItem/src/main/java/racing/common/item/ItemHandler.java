package racing.common.item;

import java.util.List;
import racing.common.data.Entity;

/**
 *
 */
public interface ItemHandler {

    void addRandomItemToEntity(Entity e);

    void affectEntity(Entity e, Class itemClass);
}
