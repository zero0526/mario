package com.mygdx.mariobros.Sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.mariobros.MarioBros;
import com.mygdx.mariobros.Screens.PlayScreen;
import org.w3c.dom.ls.LSOutput;

public class Mario extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    public Mario(World world,PlayScreen screen){
        this.world=world;
        defineMario();
    }
    public void defineMario(){
        BodyDef bdef=new BodyDef();
        bdef.type= BodyDef.BodyType.DynamicBody;
        bdef.position.set(32/ MarioBros.PPM,32/MarioBros.V_WIDTH);
        b2body=world.createBody(bdef);


        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(5/MarioBros.PPM);

        fdef.shape=shape;
        b2body.createFixture(fdef);
    }

}
