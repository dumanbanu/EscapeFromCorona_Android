package com.mobilprogramming.escapecorona;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

public class EscapeFromCorona extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture background2;

    Texture human;
    Texture virus1 , virus2 , virus3;

	Sound sound;
	Music music, musicb;

	boolean flag =true;
	private TextureRegion readyResim,gameOverResmi;

	float humanX = 0;
	float humanY = 0;
	int gameState=0;
	float velocity = 0;
	float gravity=0.1f;
	float enemyVelocity=7;
	Random random;
	int score=0;
	int scoredEnemy=0;
    int highscore;
    BitmapFont scoreFont,font,font2;
	Circle humanCircle;
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

	@Override
	public void create () {
		batch=new SpriteBatch();
		background = new Texture("background.png");
		background2= new Texture("background2.png");

		sound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
		music=Gdx.audio.newMusic(Gdx.files.internal("gamover.mp3"));
		musicb=Gdx.audio.newMusic(Gdx.files.internal("baa.ogg"));

		scoreFont = new BitmapFont(Gdx.files.internal("fonts/score3.fnt"));

		readyResim = new TextureRegion(new Texture("startbuton.png"));
		gameOverResmi = new TextureRegion(new Texture("game_over.png"));

		human=  new Texture( "human.png");
		virus1= new Texture("Red_Virus.png");
		virus2= new Texture("Red_Virus.png");
		virus3= new Texture("Red_Virus.png");

		distance=Gdx.graphics.getWidth() /2;
		random = new Random();

		humanX=Gdx.graphics.getWidth()/3 - human.getHeight() /3;
		humanY=Gdx.graphics.getHeight()/3;

		shapeRenderer= new ShapeRenderer();
		humanCircle= new Circle();
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

			enemyX[i] = Gdx.graphics.getWidth()-virus1.getWidth()/2 + i*distance;

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Preferences prefs = Gdx.app.getPreferences("mypreferences");
		this.highscore = prefs.getInteger("highscore",0);

		if(score>highscore) {
			prefs.putInteger("highscore",score);
			prefs.flush();
		}

		if (gameState == 1) {
			batch.draw(human, humanX, humanY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
			font.draw(batch,String.valueOf(score),100,200);
			if(enemyX[scoredEnemy]<Gdx.graphics.getWidth()/3 - human.getHeight() /3){
				score++;
				if(scoredEnemy<numberofEnemies-1) {
					scoredEnemy++;
				}else {
					scoredEnemy=0;
				}
			}
			if (Gdx.input.justTouched()) {
				sound.play(1.0f);
				velocity = -5;
			}
			for (	int i = 0; i < numberofEnemies; i++) {
				if (enemyX[i] < 0) {
					enemyX[i] = enemyX[i] + numberofEnemies * distance;

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}
				batch.draw(virus1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet1[i],
						                      Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);
				batch.draw(virus2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i],
						                      Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
				batch.draw(virus3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i],
						                      Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);

				enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] +
						                      Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] +
						                      Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] +
						                      Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 20);
			}
			if(humanY > 0 && humanY < Gdx.graphics.getHeight() / 1.09){
				velocity = velocity + gravity;
				humanY = humanY - velocity;
			} else {
				gameState = 2;
			}
		}else if(gameState==0) {
			batch.draw(background2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(readyResim, Gdx.graphics.getWidth()/2- readyResim.getRegionWidth()/2,Gdx.graphics.getHeight()/2 -readyResim.getRegionHeight()/2);

			musicb.setVolume(0.1f);
			musicb.setLooping(false);
			musicb.play();

			if (Gdx.input.justTouched()) {

				gameState = 1;
			}
		}
		else if (gameState==2) {
			//font2.draw(batch,"Game Over", 100,Gdx.graphics.getHeight()/2);
			batch.draw(gameOverResmi,Gdx.graphics.getWidth()/2- gameOverResmi.getRegionWidth()/2,
					                 Gdx.graphics.getHeight()/2 -gameOverResmi.getRegionHeight()/2);
			music.setVolume(1.2f);
			if (flag) {
				music.play();
				flag=false;
			}

			GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Highscore:" + highscore, Color.WHITE, 0, Align.left, false);
			scoreFont.draw( batch,highscoreLayout,Gdx.graphics.getWidth()/20,950);

			GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont,"Try Again");

			float tryAgainX= 1600;
			float tryAgainY=950;

			float touchX = Gdx.input.getX(),touchY = Gdx.graphics.getHeight()- Gdx.input.getY();

			if (touchX >= tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY >= tryAgainY - tryAgainLayout.height && touchY < tryAgainY)
				tryAgainLayout.setText(scoreFont, "Try Again", Color.WHITE, 0, Align.left, false);
			scoreFont.draw(batch, tryAgainLayout, tryAgainX, tryAgainY);
			if (Gdx.input.justTouched()) {
				if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
					this.dispose();
					gameState = 0;
				}
				humanY=Gdx.graphics.getHeight()/3;


				for(int i=0; i<numberofEnemies; i++) {
					enemyOffSet1[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-200);

					enemyX[i] = Gdx.graphics.getWidth()-virus1.getWidth()/2 + i*distance;

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

		humanCircle.set(humanX + Gdx.graphics.getWidth() / 30, humanY + Gdx.graphics.getHeight() / 20,
				        Gdx.graphics.getWidth() / 30);

		for (int i = 0; i<numberofEnemies; i++) {
			if(Intersector.overlaps(humanCircle,enemyCircles1[i]) || Intersector.overlaps(humanCircle,enemyCircles2[i])
					|| Intersector.overlaps(humanCircle,enemyCircles3[i])) {
				gameState=2;
			}
		}
	}
	@Override
	public void dispose () {

	}
}
