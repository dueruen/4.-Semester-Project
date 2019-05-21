package racing.collision;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import racing.common.player.Player;
import racing.common.npc.NPC;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.TilePart;
import racing.common.services.IPostEntityProcessingService;
import racing.common.item.Item;
import racing.common.item.ItemHandler;
import racing.common.map.Tile;

public class CollisionSystem implements IPostEntityProcessingService {

    /**
     * MapSPI
     */
    private static ItemHandler itemHandler;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            intersects(player, world);
        }

        for (Entity npc : world.getEntities(NPC.class)) {
            intersects(npc, world);
        }
    }

    /**
     * Check if entity intersects with static tiles
     *
     * @param entity
     * @param world
     */
    private void intersects(Entity entity, World world) {
        MovingPart entityMovingPart = entity.getPart(MovingPart.class);
        for (Entity e : world.getEntities()) {
            if (e == entity) {
                continue;
            }
            Class eClass = e.getClass();
            boolean isItem = e instanceof Item;

            if (isItem || eClass == Tile.class) {

                if (eClass == Tile.class) {
                    TilePart tilePart = e.getPart(TilePart.class);
                    if (!tilePart.getType().isIsStatic()) {
                        continue;
                    }
                }

                PositionPart entityPosition = entity.getPart(PositionPart.class);

                AffineTransform transform = new AffineTransform();
                transform.rotate(
                        entityPosition.getRadians(),
                        entityPosition.getX() + entity.getImage().getWidth() / 2,
                        entityPosition.getY() + entity.getImage().getHeight() / 2
                );

                Shape car = transform.createTransformedShape(new Rectangle(
                        Math.round(entityPosition.getX()),
                        Math.round(entityPosition.getY()),
                        Math.round(entity.getImage().getWidth()),
                        Math.round(entity.getImage().getHeight())
                ));

                PositionPart checkPosition = e.getPart(PositionPart.class);
                Rectangle staticTile = new Rectangle(
                        Math.round(checkPosition.getX()),
                        Math.round(checkPosition.getY()),
                        Math.round(e.getImage().getWidth()),
                        Math.round(e.getImage().getHeight())
                );

                if (!car.intersects(staticTile)) {
                    continue;
                }

                if (eClass == Tile.class) {
                    entityMovingPart.setSpeed(0);
                } else if (isItem && itemHandler != null) {
                    itemHandler.affectEntity(entity, eClass, e);

                }
            }
        }
    }

    /**
     * Declarative service set itemHandler service
     *
     * @param itemHandler itemHandler service
     */
    public void setItemService(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }

    /**
     * Declarative service remove itemHandler service
     *
     * @param itemHandler itemHandler service
     */
    public void removeItemService(ItemHandler itemHandler) {
        this.itemHandler = null;
    }
}