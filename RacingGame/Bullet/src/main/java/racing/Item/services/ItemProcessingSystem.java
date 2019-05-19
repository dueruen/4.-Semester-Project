/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.item.services;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.GameKeys;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.services.IEntityProcessingService;
import racing.common.item.Item;
import racing.common.player.Player;

public class ItemProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.SPACE));
            
            if  (player.getCanShoot() == true){
                world.addEntity(createItem(player, gameData));
            }
            
             movingPart.process(gameData, player);
             positionPart.process(gameData, player);
  
            
                         
                
            }
        }
    
     public Entity createItem(Entity entity, GameData gameData) {
      PositionPart shooterPos = entity.getPart(PositionPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float speed = 350;
        float deacceleration = 10;
        float acceleration = 100;
        float rotationSpeed = 0;
        
        
        
       Entity bulletEntity = new Item();
       GameImage img = new GameImage("Itmes/Bullet.png", 100, 50);
       bulletEntity.setImage(img);
       MovingPart bulletPos = new MovingPart(deacceleration, acceleration, speed, rotationSpeed);
       bulletPos.setUp(true);
       bulletEntity.add(bulletPos);
       bulletEntity.add(new PositionPart(x, y, radians));
       
       
       
       return bulletEntity;
    }
        
    }
    

