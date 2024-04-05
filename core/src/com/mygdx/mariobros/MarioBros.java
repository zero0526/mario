package com.mygdx.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mariobros.Screens.PlayScreen;

public class MarioBros extends Game{//game chiu trach nhiem quan li man hinh va cung cap 1 so phuong thuc
	public  static final int V_WIDTH=400;
	public static final int V_HEIGHT=200;
	public SpriteBatch batch;//access la public vi ca project chi dung 1 batch
	public static final float PPM=100;//pixels per meter
	@Override
	public void create () {//ham nay duoc goi khi chuong trinh bat dau chay
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));//dung de chuyen doi cac man hinh
	}

	@Override
	public void render () {//goi lien tuc 60 lan 1s
		super.render();
	}
	
	@Override
	public void dispose () {//goi khi tat chuong trinh
		batch.dispose();
	}
}
