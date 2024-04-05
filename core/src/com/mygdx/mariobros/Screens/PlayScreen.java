package com.mygdx.mariobros.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.mariobros.MarioBros;
import com.mygdx.mariobros.Scenes.Hud;
import com.mygdx.mariobros.Sprites.Mario;
import com.mygdx.mariobros.Tools.B2WorldCreator;

public class PlayScreen implements Screen {
    private MarioBros game;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;//1 loại cam su dung cho phep chieu chinh xac khi cam di chuyen cap nhat ma tran chieu de dam bao hien thi dung
    private Viewport gameport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d varialbies
    private World world;
    private Box2DDebugRenderer b2dr;
    private Mario player;
    public PlayScreen(MarioBros game){
        atlas=new TextureAtlas("Mario_And_Enemies.pack");

        //ket tap voi marioBros
        this.game=game;
        // tao cam used to follow through cam world
        gamecam=new OrthographicCamera();
        //create viewport to maintain virtual aspect ratio
        gameport=new FitViewport(MarioBros.V_WIDTH/MarioBros.PPM,MarioBros.V_HEIGHT/MarioBros.PPM,gamecam);
        //create our game hub scores/timers..
        hud=new Hud(game.batch);

        //load our map and set up render
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("maps/level1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,1/MarioBros.PPM);

        //initially set our cam in the center of map
        gamecam.position.set(MarioBros.V_WIDTH/2/MarioBros.PPM,MarioBros.V_HEIGHT/2/MarioBros.PPM,0);



        //create our box 2d setting gravity
        world=new World(new Vector2(0,-10),true);//gravity-true=khong di chuyen khong tuong tac voin cac thang khac chi là fixture
        //ket tap voi player
        player=new Mario(world,this);
        //allow for debug lines of our box2d world
        b2dr=new Box2DDebugRenderer();
        //create bounds for fixture
        new B2WorldCreator(world,map);

    }
    public TextureAtlas  getAtlas(){
        return this.atlas;
    }
    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {//input.keys.up dai dien mui ten di len
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
//              applyLinearIMpulse()//dung de ap dung 1 luc len vat the
//              newvector2 vector luc
//              player.b2body.getworldcenter diem dat luc o trong tam vat
//              true danh thuc vat
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)&&player.b2body.getLinearVelocity().x<=2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)&&player.b2body.getLinearVelocity().x>=-2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
        }
    }
    public void update(float dt){//cho nay update het cai game
        handleInput(dt);
        //takes 1 step in the physics simulation(60 times per second
        world.step(1/60f,6,2);//8

        //player.update(dt);
        //update gamecam cam di chuyen nguoi co dinh tren man
        gamecam.position.x=player.b2body.getPosition().x;
        gamecam.update();//cap nhat cam bat cu khi nao no di chuyen

        renderer.setView(gamecam);//chi tao ra cac anh trong camera

    }
    @Override
    public void render(float delta) {//ham nay duoc goi trong loop game v la khong thoi gian hien thi 1 anh tren man
        update(delta);//ham nay co delta san

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();//render ra cai map

        //render our Box2DDebugLines
        b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(world,gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);//them hinh chieu camera dung trong stage vao batch de chuan bi ve
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
