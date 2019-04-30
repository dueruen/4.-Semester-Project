package racing.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.regex.Pattern;
import racing.common.data.TileType;
import racing.common.map.MapSPI;
import racing.gui.GuiManager;

/**
 * This screen is used to create a new map
 */
public class MapEditor extends BasicScreen {

    /**
     * MapSPI
     */
    private static MapSPI map;

    /**
     * The stage
     */
    private Stage stage;

    private ImageWrapper[][] tiles;

    public MapEditor() {

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
    public void show() {
        stage = new Stage(new ScreenViewport());
        Table table = new Table();

        table.setFillParent(true);
        table.bottom();
        stage.addActor(table);

        Table tileTable = new Table();
        table.add(tileTable).fillY().expandY().fillX().expandX();

        table.row().pad(10, 0, 10, 0);

        Table tileSelectTable = new Table();
        table.add(tileSelectTable);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(0);
        boolean first = true;

        for (TileType type : TileType.values()) {
            String[] pathArray = type.getImagePath().split(Pattern.quote("."));
            String path = pathArray[0] + "_sel." + pathArray[1];
            TextureRegionDrawable sh = new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get(path, Texture.class)));
            ImageButton ib = new ImageButton(new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get(type.getImagePath(), Texture.class))), sh, sh);
            if (first) {
                ib.setChecked(true);
                first = false;
            }
            buttonGroup.add(ib);
            tileSelectTable.add(ib).size(20, 20).pad(3);
        }

        table.row().pad(10, 0, 10, 0);

        Table butTable = new Table();
        table.add(butTable);

        TextButton menu = new TextButton("Menu", GuiManager.getInstance().getSkin());
        butTable.add(menu).fillX().uniformX();

        TextField colTextField = new TextField("", GuiManager.getInstance().getSkin());
        colTextField.setMessageText("Columns");
        butTable.add(colTextField).fillX().uniformX();

        TextField rowTextField = new TextField("", GuiManager.getInstance().getSkin());
        rowTextField.setMessageText("Rows");
        butTable.add(rowTextField).fillX().uniformX();

        TextButton drawMap = new TextButton("Draw", GuiManager.getInstance().getSkin());
        butTable.add(drawMap).fillX().uniformX();

        TextButton saveMap = new TextButton("Save", GuiManager.getInstance().getSkin());
        butTable.add(saveMap).fillX().uniformX();

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                GuiManager.getInstance().changeScreen(GameScreen.MENU);
            }
        });

        drawMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                tileTable.reset();

                String colS = colTextField.getText();
                String rowS = rowTextField.getText();
                int col = 0, row = 0;
                try {
                    col = Integer.parseInt(colS);
                    row = Integer.parseInt(rowS);
                } catch (NumberFormatException | NullPointerException nfe) {
                    colTextField.setText("");
                    rowTextField.setText("");
                }
                tiles = new ImageWrapper[row][col];
                for (int r = 0; r < row; r++) {
                    for (int c = 0; c < col; c++) {
                        String[] pathArray = TileType.GRASS.getImagePath().split(Pattern.quote("."));
                        String path = pathArray[0] + "_sel." + pathArray[1];
                        ImageWrapper ibw = new ImageWrapper(TileType.GRASS.getValue());
                        ibw.setDrawable(new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get(path, Texture.class))));
                        tiles[r][c] = ibw;
                        ibw.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                int tileNum = buttonGroup.getCheckedIndex();
                                ibw.setDrawable(new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get(TileType.values()[tileNum].getImagePath(), Texture.class))));
                            }
                        });
                        tileTable.add(ibw);
                    }
                    tileTable.row();
                }
            }
        });

        saveMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                int[][] types = new int[tiles.length][tiles[0].length];
                for (int r = 0; r < tiles.length; r++) {
                    for (int c = 0; c < tiles[0].length; c++) {
                        types[r][c] = tiles[r][c].tileType;
                    }
                }
                //throw new AbstractMethodError("Array: " + types.length + "  :: " + GuiManager.getInstance().getMap());
                map.saveMapToFile(types);
            }
        });
    }

    @Override
    public void render(float f) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(174, 176, 178, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    private class ImageWrapper extends Image {

        int tileType;

        public ImageWrapper(int tileType) {
            this.tileType = tileType;
        }
    }
}
