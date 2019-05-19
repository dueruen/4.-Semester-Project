package racing.common.item;

import java.util.List;
import racing.common.data.Entity;

/**
 *
 */
public interface ItemHandler {

    List<ItemSPI> getActiveItemSPI();

    void addRandomItemToEntity(Entity e);
}
