package racing.bomb;

import static java.lang.Math.*;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.ItemPart;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.TimerPart;
import racing.common.item.ItemSPI;
import racing.common.services.IEntityProcessingService;

public class BombProcessingSystem implements IEntityProcessingService, ItemSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ItemPart.class) != null) {

                ItemPart itemPart = entity.getPart(ItemPart.class);
                if (itemPart.readyToUse(Bomb.class)) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    Entity item = createItem(entity, gameData);
                    itemPart.useCharge();
                    world.addEntity(item);
                }
            }
        }

        for (Entity bullet : world.getEntities(Bomb.class)) {

            TimerPart timerPart = bullet.getPart(TimerPart.class);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
        }
    }

    @Override
    public Entity createItem(Entity shooter, GameData gameData) {
        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        MovingPart shooterMovingPart = shooter.getPart(MovingPart.class);
        GameImage image = shooter.getImage();

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();

        Bomb bullet = new Bomb();
        GameImage img = new GameImage("items/bomb.png", 20, 20);
        bullet.setImage(img);

        float bx = (float) (x - (image.getWidth() / 2 + 40) * cos(radians));
        float by = (float) (y - (image.getWidth() / 2 + 40) * sin(radians));

        bullet.add(new PositionPart(bx + (image.getWidth() / 2), by + (image.getHeight() / 2), radians));
        bullet.add(new TimerPart(5));
        return bullet;
    }

    @Override
    public void addItemToEntity(Entity e) {
        ItemPart itemPart = e.getPart(ItemPart.class);
        itemPart.setChargesLeft(1);
        itemPart.setItemClass(Bomb.class);
    }

    @Override
    public void affectEntity(Entity e) {
        MovingPart movingPart = e.getPart(MovingPart.class);
        movingPart.setSpeed(0);
        movingPart.setPauseTimer(20);
    }

    @Override
    public Class getItemClass() {
        return Bomb.class;
    }
}
