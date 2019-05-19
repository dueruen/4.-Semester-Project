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
import racing.common.item.Item;
import racing.common.item.ItemSPI;
import racing.common.services.IEntityProcessingService;

public class BulletProcessingSystem implements IEntityProcessingService, ItemSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ItemPart.class) != null) {

                ItemPart itemPart = entity.getPart(ItemPart.class);
                //Shoot if isShooting is true, ie. space is pressed.
                if (itemPart.isUsing()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    //Add entity radius to initial position to avoid immideate collision.
                   
                    Entity item = createItem(entity, gameData);
                    itemPart.setIsUsing(false);
                    world.addEntity(item);
                }
            }
        }

        for (Entity bullet : world.getEntities(Item.class)) {
            
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                System.out.println("REMOVE");
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
        }
    }

//    @Override
//    public void process(GameData gameData, World world) {
//        for (Entity player : world.getEntities(Player.class)) {
//            PositionPart positionPart = player.getPart(PositionPart.class);
//            MovingPart movingPart = player.getPart(MovingPart.class);
//            
//            movingPart.setUp(gameData.getKeys().isDown(GameKeys.SPACE));
//            
//            if  (player.getCanShoot() == true){
//                world.addEntity(createItem(player, gameData));
//            }
//            
//                
//  
//            
//                         
//                
//            }
//        }
    @Override
    public Entity createItem(Entity shooter, GameData gameData) {
        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        MovingPart shooterMovingPart = shooter.getPart(MovingPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float dt = gameData.getDelta();
        float speed = 350;

        Entity bullet = new Item();
        GameImage img = new GameImage("items/bullet.png", 10, 10);
        bullet.setImage(img);
        
        //bullet.setRadius(2);
        //float bx = (float) cos(radians) * shooter.getRadius() * bullet.getRadius();
        //float by = (float) sin(radians) * shooter.getRadius() * bullet.getRadius();
        //float bx = (float) cos(radians); //* shooter.get * bullet.getRadius();
        //float by = (float) sin(radians);// * shooter.getRadius() * bullet.getRadius();

        //bullet.add(new PositionPart(bx + x, by + y, radians));
        bullet.add(new PositionPart(x, y, radians));
        //bullet.add(new LifePart(1));
        bullet.add(new MovingPart(0, 50, speed, 5));
        bullet.add(new TimerPart(1));

//        bullet.setShapeX(new float[2]);
//        bullet.setShapeY(new float[2]);
        return bullet;
    }

//    public Entity createItem(Entity entity, GameData gameData) {
//        PositionPart shooterPos = entity.getPart(PositionPart.class);
//
//        float x = shooterPos.getX();
//        float y = shooterPos.getY();
//        float radians = shooterPos.getRadians();
//        float speed = 350;
//        float deacceleration = 10;
//        float acceleration = 100;
//        float rotationSpeed = 0;
//
//        Entity bulletEntity = new Item();
//        GameImage img = new GameImage("Itmes/Bullet.png", 100, 50);
//        bulletEntity.setImage(img);
//        MovingPart bulletPos = new MovingPart(deacceleration, acceleration, speed, rotationSpeed);
//        bulletPos.setUp(true);
//        bulletEntity.add(bulletPos);
//        bulletEntity.add(new PositionPart(x, y, radians));
//
//        return bulletEntity;
//    }
}
