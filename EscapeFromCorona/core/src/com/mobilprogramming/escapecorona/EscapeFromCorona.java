package com.mobilprogramming.escapecorona;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class EscapeFromCorona extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture background2;

	private TextureRegion readyResim,gameOverResmi;

	Texture bird;
	Texture pig1;
	Texture pig2;
	Texture pig3;
	float birdX = 0;
	float birdY = 0;
	int gameState=0;
	float velocity = 0;
	float gravity=0.1f;
	float enemyVelocity=7;
	Random random;
	int score=0;
	int scoredEnemy=0;
	BitmapFont font;
	BitmapFont font2;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int numberofEnemies = 4;


	float [] enemyX =new float[numberofEnemies];
	float [] enemyOffSet1 = new float[numberofEnemies];
	float [] enemyOffSet2 = new float[numberofEnemies];
	float [] enemyOffSet3 = new float[numberofEnemies];
	float distance = 0;

	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	//Sound sound;
	//Sound sound1;



	@Override
	public void create () {
		batch=new SpriteBatch();
		background = new Texture("bact.png");
		background2= new Texture("bact2.png");

		readyResim = new TextureRegion(new Texture("startbuton.png"));
		gameOverResmi = new TextureRegion(new Texture("indir2.jpg"));

		bird=  new Texture( "corona.png");
		pig1= new Texture("Red_Virus.png");
		pig2= new Texture("Red_Virus.png");
		pig3= new Texture("Red_Virus.png");

		distance=Gdx.graphics.getWidth() /2;
		random = new Random();

		birdX=Gdx.graphics.getWidth()/3 - bird.getHeight() /3;
		birdY=Gdx.graphics.getHeight()/3;

		shapeRenderer= new ShapeRenderer();
		birdCircle= new Circle();
		enemyCircles1 = new Circle[numberofEnemies];
		enemyCircles2 = new Circle[numberofEnemies];
		enemyCircles3 = new Circle[numberofEnemies];

		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(6);

		font2 = new BitmapFont();
		font2.setColor(Color.RED);
		font2.getData().setScale(6);


		for(int i=0; i<numberofEnemies; i++) {
			enemyOffSet1[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);
			enemyOffSet2[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);
			enemyOffSet3[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);

			enemyX[i] = Gdx.graphics.getWidth()-pig1.getWidth()/2 + i*distance;

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}

		  // sound= Gdx.audio.newSound(Gdx.files.internal("raw/fd.wav"));
		   //sound1= Gdx.audio.newSound(Gdx.files.internal("raw/fg.wav"));

		//Preferences prefs = Gdx.app.getPreferences("game preferences");
}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {
			batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
			font.draw(batch,String.valueOf(score),100,200);
			if(enemyX[scoredEnemy]<Gdx.graphics.getWidth()/3 - bird.getHeight() /3){
				score++;
				if(scoredEnemy<numberofEnemies-1) {
					scoredEnemy++;
				}
				else {
					scoredEnemy=0;
				}
			}

			if (Gdx.input.justTouched()) {

				velocity = -7;

				//sound.play();



			}
			for (int i = 0; i < numberofEnemies; i++) {
				if (enemyX[i] < 0) {
					enemyX[i] = enemyX[i] + numberofEnemies * distance;

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}
				batch.draw(pig1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet1[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
				batch.draw(pig2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
				batch.draw(pig3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);

				enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);



			}
			if(birdY > 0 && birdY < Gdx.graphics.getHeight() / 1.09){
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			} else {
				gameState = 2;
			}
		} else if(gameState==0) {
			batch.draw(background2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(readyResim, Gdx.graphics.getWidth()/2- readyResim.getRegionWidth()/2,Gdx.graphics.getHeight()/2 -readyResim.getRegionHeight()/2);
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}else if (gameState==2) {
			//sound1.play();


			//font2.draw(batch,"Game Over", 100,Gdx.graphics.getHeight()/2);


			//font2.draw(batch,"Game Over", 100,Gdx.graphics.getHeight()/2);
			batch.draw(gameOverResmi,Gdx.graphics.getWidth()/2- gameOverResmi.getRegionWidth()/2,Gdx.graphics.getHeight()/2 -gameOverResmi.getRegionHeight()/2);

			if (Gdx.input.justTouched()) {
				gameState=0;
				birdY=Gdx.graphics.getHeight()/3;

				for(int i=0; i<numberofEnemies; i++) {
					enemyOffSet1[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);

					enemyX[i] = Gdx.graphics.getWidth()-pig1.getWidth()/2 + i*distance;

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}
				velocity=0;
				scoredEnemy=0;
				score=0;
			}


		}



		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		for (int i = 0; i<numberofEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
			// shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
			if(Intersector.overlaps(birdCircle,enemyCircles1[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState=2;
			}
		}
		//shapeRenderer.end();
	}
	@Override
	public void dispose () {
     // sound.dispose();
     // sound1.dispose();
}
}
