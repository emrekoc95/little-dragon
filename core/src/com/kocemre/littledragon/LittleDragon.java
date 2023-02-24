package com.kocemre.littledragon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LittleDragon extends ApplicationAdapter {

    SpriteBatch batch;

    Texture background;
    Texture[] birds;
    Texture bird;
    Texture bird2;
    Texture bee1;
    Texture bee2;
    Texture bee3;
    Random random;

    Circle birdCircle;
    Circle[] enemyCircles;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;
    //ShapeRenderer shapeRenderer;

    BitmapFont font;
    BitmapFont font2;


    int flapState = 0;
    Timer timer;

    float score = 0;
    int scoredEnemy = 0;

    float birdX = 0;
    float birdY = 0;
    int numberOfEnemies = 4;
    float[] enemyX = new float[numberOfEnemies];
    float[] enemyOffSet = new float[numberOfEnemies];
    float[] enemyOffSet2 = new float[numberOfEnemies];
    float[] enemyOffSet3 = new float[numberOfEnemies];


    int gameState = 0;

    float velocity = 2;
    float gravity = 0.8f;
    float distance = 0;
    float enemyVelocity = 0;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        bird2 = new Texture("bird2.png");
        birds = new Texture[]{new Texture("bird.png"), new Texture("bird2.png"), new Texture("bird.png")};
        bee1 = new Texture("bee.png");
        bee2 = new Texture("bee.png");
        bee3 = new Texture("bee.png");


        font = new BitmapFont();
        font.setColor(Color.LIGHT_GRAY);
        font.getData().setScale(4);

        font2 = new BitmapFont();
        font2.setColor(Color.BLACK);
        font2.getData().setScale(4);

        birdCircle = new Circle();
        enemyCircles = new Circle[numberOfEnemies];
        enemyCircles2 = new Circle[numberOfEnemies];
        enemyCircles3 = new Circle[numberOfEnemies];

        //shapeRenderer = new ShapeRenderer();

        random = new Random();
        timer = new Timer();


        birdX = Gdx.graphics.getWidth() / 6;
        birdY = Gdx.graphics.getHeight() / 3;

        distance = Gdx.graphics.getWidth() / 2;
        enemyVelocity = Gdx.graphics.getWidth() / 120;


        for (int i = 0; i < numberOfEnemies; i++) {
            enemyX[i] = Gdx.graphics.getWidth() - i * distance;
            enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight() - 2 * birdY);
            enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight() - 2 * birdY);
            enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight() - 2 * birdY);

            enemyCircles[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();

        }


    }

    @Override
    public void render() {

        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        if (gameState == 1) {

            score = score + 0.016f;



            for (int i = 0; i < numberOfEnemies; i++) {




                enemyX[i] = enemyX[i] - enemyVelocity;


                if (enemyX[i] < Gdx.graphics.getWidth() / 200) {
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;
                    enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
                    enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
                    enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

                }/*else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}*/

                batch.draw(bee1, enemyX[i], enemyOffSet[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(bee2, enemyX[i], enemyOffSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(bee3, enemyX[i], enemyOffSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

                enemyCircles[i].set(enemyX[i] + Gdx.graphics.getWidth() / 30, enemyOffSet[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 40);
                enemyCircles2[i].set(enemyX[i] + Gdx.graphics.getWidth() / 30, enemyOffSet2[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 40);
                enemyCircles3[i].set(enemyX[i] + Gdx.graphics.getWidth() / 30, enemyOffSet3[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 40);
            }


            if (birdY > 0 && birdY < Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10) {
                velocity = velocity + gravity;
                birdY = birdY - velocity;
            } else {
                gameState = 2;
            }

            if (Gdx.input.justTouched()) {
                velocity = velocity - 16;
                if (flapState == 0) {
                    flapState = 1;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if (flapState == 1) {
                                flapState = 2;
                            }

                        }
                    }, 500);
                } else {
                    flapState = 1;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if (flapState == 1) {
                                flapState = 0;
                            }

                        }
                    }, 500);
                }


            }


        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            font2.draw(batch,"Game Over! Tap to Play Again! Your score: " + String.valueOf(Math.round(score)),Gdx.graphics.getWidth()/31,Gdx.graphics.getHeight()/2);
            if (Gdx.input.justTouched()) {
                birdX = Gdx.graphics.getWidth() / 6;
                birdY = Gdx.graphics.getHeight() / 3;
                velocity = 0;
                score = 0;
                scoredEnemy = 0;


                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyX[i] = Gdx.graphics.getWidth() - i * distance;
                    enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight() - 2 * birdY);
                    enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight() - 2 * birdY);
                    enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight() - 2 * birdY);

                    enemyCircles[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();

                }
                gameState = 1;
            }
        }
        font.draw(batch, "Score: " + String.valueOf(Math.round(score)), Gdx.graphics.getWidth() / 31, Gdx.graphics.getHeight() / 14);
        batch.draw(birds[flapState], birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

        batch.end();

        birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 40);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


        for (int i = 0; i < numberOfEnemies; i++) {
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/34,enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/34,enemyOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/34,enemyOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40);

            if (Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])) {
                gameState = 2;
            }
        }

        //shapeRenderer.end();
    }

    @Override
    public void dispose() {

    }
}
