package ru.demkiin.racesimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.Array;
import ru.demkiin.racesimulator.Door.DoorState;


public class WorldRenderer3D {

    private World world;
    private PerspectiveCamera camera;

    private Model cube;
    private Model gem;
    private Model key;
    private Model girl;

    private Texture floorTexture;
    private Texture wallTexture;
    private Texture exitTexture;
    private Texture lockedDoorTexture;
    private Texture gemTexture;
    private Texture keyTexture;
    private int gemRotation = 0;
    private OrthographicCamera hudCam;
    private SpriteBatch hudBatch;
    private BitmapFont hudFont;
    private ObjLoader loader;
    public ModelInstance instance;
    public ModelBatch modelBatch;
    public Environment environment;
    public Array<ModelInstance> instances = new Array<ModelInstance>();

    public WorldRenderer3D(World world) {
        this.world = world;

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        loader = new ObjLoader();

        cube = loader.loadModel(Gdx.files.internal("models/cube.obj"));
        gem = loader.loadModel(Gdx.files.internal("models/gem.obj"));
        key = loader.loadModel(Gdx.files.internal("models/dtKey.obj"));
       // girl = loader.loadModel(Gdx.files.internal("models/victoria-standing.obj"));

        floorTexture = new Texture(Gdx.files.internal("textures/grass.png"), true);
        floorTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        wallTexture = new Texture(Gdx.files.internal("textures/wall.png"), true);
        wallTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        exitTexture = new Texture(Gdx.files.internal("textures/exit.png"), true);
        exitTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        lockedDoorTexture = new Texture(Gdx.files.internal("textures/locked.png"), true);
        lockedDoorTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        gemTexture = new Texture(Gdx.files.internal("textures/gem.png"), true);
        gemTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        keyTexture = new Texture(Gdx.files.internal("textures/key.png"), true);
        keyTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);

        camera = new PerspectiveCamera(70, 6f, 4f);
        camera.near = 0.01f;
        camera.direction.set(0, 2, -1);

        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, RaceSimulator.WIDTH, RaceSimulator.HEIGHT);
        hudBatch = new SpriteBatch();
        hudFont = new BitmapFont();
    }

    public void render() {
        renderPlayArea();
        renderHud();
    }


    public void dispose(){
        modelBatch.dispose();
        cube.dispose();
    }


    private void renderPlayArea() {
        if (gemRotation < 360) gemRotation++;
        else gemRotation = 0;

        camera.position.set(world.getPlayer().getCentrePos().x * 2f, (world.getPlayer().getCentrePos().y * 2f) -0, 0.75f);
        camera.rotate(world.getPlayer().getRotation(), 0, 0, 1);
        camera.update();
        camera.rotate(-world.getPlayer().getRotation(), 0, 0, 1);

        GL20 gl = Gdx.graphics.getGL20();
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gl.glClearColor(0.63686f, 0.76436f, 0.92286f, 1.0f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL20.GL_DEPTH_TEST);
        gl.glEnable(GL20.GL_TEXTURE_2D);

        instances.clear();

        for (int i = 0; i < world.getMap().getMap().size; i++) {
            for (int j = 0; j < world.getMap().getMap().get(i).length(); j++) {

                ModelInstance instance2 = new ModelInstance(cube);
                instance2.transform.setToTranslation((j-1) * 2f, i * 2f, -2);
                instance2.transform.scale(1,1,1);

                instance2.materials.get(0).set(TextureAttribute.createDiffuse(floorTexture));
                instances.add(instance2);
            }
        }

        for(int i=0; i < world.getWalls().size; i++ ) {

            ModelInstance instance2 = new ModelInstance(cube);
            instance2.transform.setToTranslation(world.getWalls().get(i).centrePosX * 2f, world.getWalls().get(i).centrePosY * 2f, 0);
            instance2.transform.scale(world.getWalls().get(i).width, world.getWalls().get(i).height, 1.0f);

           // instance2.materials.get(0).set(ColorAttribute.createDiffuse(1, 0.6f, 1, 1));
            instance2.materials.get(0).set(TextureAttribute.createDiffuse(wallTexture));
            instances.add(instance2);
        }

        for(int i=0; i < world.getEnemys().size; i++ ) {

            ModelInstance instance2 = new ModelInstance(key);
            instance2.transform.setToTranslation(world.getEnemys().get(i).centrePos.x * 2f, world.getEnemys().get(i).centrePos.y * 2f, 0);
            instance2.transform.scale(world.getEnemys().get(i).width * 0.5f, world.getEnemys().get(i).height * 0.5f, 0.25f);
            instance2.materials.get(0).set(TextureAttribute.createDiffuse(keyTexture));
            instances.add(instance2);
        }


        for(int i=0; i < world.getExits().size; i++ ) {


            ModelInstance instance2 = new ModelInstance(cube);
            instance2.transform.setToTranslation(world.getExits().get(i).centrePosX * 2f, world.getExits().get(i).centrePosY * 2f, 0);
            instance2.transform.scale(world.getExits().get(i).width, world.getExits().get(i).height, 1.0f);

            instance2.materials.get(0).set(TextureAttribute.createDiffuse(exitTexture));


            instances.add(instance2);
        }

        for(int i=0; i < world.getDoors().size; i++ ) {
            if (world.getDoors().get(i).state.equals(DoorState.LOCKED)) {


                ModelInstance instance2 = new ModelInstance(cube);
                instance2.transform.setToTranslation(world.getDoors().get(i).centrePosX * 2f, world.getDoors().get(i).centrePosY * 2f, 0);
                instance2.transform.scale(world.getDoors().get(i).width, world.getDoors().get(i).height, 1.0f);
                instance2.materials.get(0).set(TextureAttribute.createDiffuse(lockedDoorTexture));
                instances.add(instance2);
            }
        }

        for(int i=0; i < world.getGirls().size; i++ ) {

                ModelInstance instance2 = new ModelInstance(girl);
                instance2.transform.setToTranslation(world.getGirls().get(i).centrePosX * 2f, world.getGirls().get(i).centrePosY * 2f, 0);
                instance2.transform.scale(world.getGirls().get(i).width, world.getGirls().get(i).height, 1.0f);
                instance2.materials.get(0).set(ColorAttribute.createDiffuse(1, 0.6f, 1, 1));
                instances.add(instance2);

        }



        for(int i=0; i < world.getKeys().size; i++ ) {
            ModelInstance instance2 = new ModelInstance(key);
            instance2.transform.setToTranslation(world.getKeys().get(i).centrePosX * 2f, world.getKeys().get(i).centrePosY * 2f, 0);
            instance2.transform.scale(world.getKeys().get(i).width * 0.5f, world.getKeys().get(i).height * 0.5f, 0.25f);
            instance2.materials.get(0).set(TextureAttribute.createDiffuse(keyTexture));
            instances.add(instance2);
        }

        for(int i=0; i < world.getGems().size; i++ ) {
            ModelInstance instance2 = new ModelInstance(gem);

            instance2.transform.setToTranslation(world.getGems().get(i).centrePosX * 2f, world.getGems().get(i).centrePosY * 2f, 0f);
            instance2.transform.scale(world.getGems().get(i).width, world.getGems().get(i).height, 0.5f);
            instance2.materials.get(0).set(TextureAttribute.createDiffuse(gemTexture));
            instances.add(instance2);

        }

        modelBatch.begin(camera);
        modelBatch.render(instances);
        modelBatch.end();
    }

    private void renderHud() {
        hudCam.position.set(RaceSimulator.WIDTH/2, RaceSimulator.HEIGHT/2, 0.0f);
        hudCam.update();

      //  hudCam.apply(Gdx.gl10);

        hudBatch.setProjectionMatrix(hudCam.combined);
        hudBatch.begin();
        hudFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        hudFont.draw(hudBatch, "Level: "+world.getLevel(), 15, 305);
        hudFont.draw(hudBatch, "Keys Held: "+world.getInventoryKeys(), 15, 275);
        hudFont.draw(hudBatch, "Remaining Gems: "+world.getGems().size, 15, 260);
        hudFont.draw(hudBatch, "Remaining Keys: "+world.getKeys().size, 15, 245);
        hudFont.draw(hudBatch, "Position:"+world.GetPlayerPosition(), 15, 230);
        hudBatch.end();
    }
}
