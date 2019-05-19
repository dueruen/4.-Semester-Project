package racing.itemsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.TileType;
import racing.common.data.World;
import racing.common.data.entityparts.ItemPart;
import racing.common.data.entityparts.TilePart;
import racing.common.item.ItemHandler;
import racing.common.item.ItemSPI;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.services.IPostEntityProcessingService;

/**
 *
 */
public class ItemProcessingService implements IPostEntityProcessingService, ItemHandler {

    /**
     * MapSPI
     */
    private static MapSPI map;
    
    Random generator = new Random();

    /**
     * list of ItemSPI
     */
    private static Map<Class, ItemSPI> items;

    /**
     * Declarative service set map service
     *
     * @param item item service
     */
    public void setItemService(ItemSPI item) {
        if (items == null) {
            items = new HashMap<>();
        }
        System.out.println("ItemSPI class: " + item.getClass());
        System.out.println("Set item class: " + item.getItemClass());
        items.put(item.getItemClass(), item);
    }

    /**
     * Declarative service remove item service
     *
     * @param item item service
     */
    public void removeItemService(ItemSPI item) {
        items.remove(item.getClass());
    }

    @Override
    public void addRandomItemToEntity(Entity e) {
        ItemPart ip = e.getPart(ItemPart.class);
        if (ip.getItemClass() == null) {
            int randomNum = (int) (Math.random() * items.size());
            Object[] values = items.values().toArray();
            ((ItemSPI)values[generator.nextInt(values.length)]).addItemToEntity(e);
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            ItemPart ip = e.getPart(ItemPart.class);
            if (ip == null) {
                continue;
            }
            Tile tile = map.getTile(e, world);
            TilePart tilePart = tile.getPart(TilePart.class);
            if (tilePart.getType() == TileType.ITEM) {
                addRandomItemToEntity(e);
            }
            ip.process(gameData, e);
        }
    }

    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.map = map;
    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }

    @Override
    public void affectEntity(Entity e, Class itemClass) {
        ItemSPI spi = items.get(itemClass);
        spi.affectEntity(e);
    }
}
