package racing.bullet;

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

public class BulletProcessingSystem implements IEntityProcessingService, ItemSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ItemPart.class) != null) {

                ItemPart itemPart = entity.getPart(ItemPart.class);
                if (itemPart.readyToUse(Bullet.class)) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    Entity item = createItem(entity, gameData);
                    itemPart.useCharge();
                    world.addEntity(item);
                }
            }
        }

        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
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
        float dt = gameData.getDelta();
        float speed = 500;

        Bullet bullet = new Bullet();
        GameImage img = new GameImage("items/bullet.png", 10, 10);
        bullet.setImage(img);

        float bx = (float) (x + (image.getWidth() / 2) * cos(radians));
        float by = (float) (y + (image.getWidth() / 2) * sin(radians));

        bullet.add(new PositionPart(bx + (image.getWidth() / 2), by + (image.getHeight() / 2), radians));
        bullet.add(new MovingPart(0, 150, speed, 5));
        bullet.add(new TimerPart(1));
        return bullet;
    }

    @Override
    public void addItemToEntity(Entity e) {
        System.out.println("ADD ITEM BULLET");
        ItemPart itemPart = e.getPart(ItemPart.class);
        itemPart.setChargesLeft(3);
        itemPart.setDelay(30);
        itemPart.setItemClass(Bullet.class);
    }
}
