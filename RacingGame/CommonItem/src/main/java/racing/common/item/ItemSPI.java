package racing.common.item;

import racing.common.data.Entity;
import racing.common.data.GameData;

public interface ItemSPI {

    Entity createItem(Entity e, GameData gameData);

    void addItemToEntity(Entity e);

    void affectEntity(Entity e);

    Class getItemClass();
}
