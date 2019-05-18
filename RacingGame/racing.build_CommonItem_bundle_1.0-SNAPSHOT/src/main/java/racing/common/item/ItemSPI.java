package racing.common.item;

import racing.common.data.GameData;
import racing.common.data.World;


public interface ItemSPI {
 
  Item createItem(GameData gameData, World world);
  
  
}