package racing.common.npc;

import racing.common.data.GameData;
import racing.common.data.World;

/**
 * SPI for NPC
 */
public interface NPCSPI {
  
    NPC[] create(GameData gameData, World world, int amount);
    void removeAll(GameData gameData, World world);
    
}
