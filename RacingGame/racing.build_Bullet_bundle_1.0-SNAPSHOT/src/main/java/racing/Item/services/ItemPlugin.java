/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.Item.services;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.item.Item;
import racing.common.item.ItemSPI;
import racing.common.services.IGamePluginService;

/**
 *
 * @author jeppe
 */
public class ItemPlugin implements IGamePluginService, ItemSPI {

    @Override
    public void start(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
  
    @Override
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
    
