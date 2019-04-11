/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import racing.Core;
import racing.common.data.TileType;
import racing.core.managers.GameInputProcessor;

/**
 *
 * @author yodamaster42
 */
public class MainScreen implements Screen {

    private Core parent;
    BitmapFont font = new BitmapFont();
//    private MapSPI map;
    
    public MainScreen(Core parent) {
        this.parent = parent;
        TileType[][] tiles = {
            {TileType.GRASS,TileType.GRASS,TileType.GRASS},  
            {TileType.ROAD,TileType.ROAD,TileType.ROAD},
            {TileType.ROAD,TileType.WATER,TileType.ROAD},
            {TileType.ROAD,TileType.FINISHLINE,TileType.ROAD},
            {TileType.GRASS,TileType.GRASS,TileType.GRASS},
        };
        parent.map.createMap(tiles, parent.gameData, parent.world);
    }

    @Override
    public void show() {
        parent.gameData.setDisplayWidth(Gdx.graphics.getWidth());
        parent.gameData.setDisplayHeight(Gdx.graphics.getHeight());

        parent.cam = new OrthographicCamera(parent.gameData.getDisplayWidth(), parent.gameData.getDisplayHeight());
        parent.cam.translate(parent.gameData.getDisplayWidth() / 2, parent.gameData.getDisplayHeight() / 2);
        parent.cam.update();

        parent.sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(parent.gameData));
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        parent.gameData.setDelta(Gdx.graphics.getDeltaTime());
        parent.gameData.getKeys().update();
//        batch.begin();
//        font.setColor(Color.WHITE);
//        font.draw(batch, "GAME VIEW ", 100, 100);
//        batch.end();
        parent.update();
        parent.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
//    //TODO: Dependency injection via Declarative Services
//    public void setMapService(MapSPI map) {
//        this.map = map;
//    }
//
//    public void removeMapService() {
//        this.map = null;
//    }
}
