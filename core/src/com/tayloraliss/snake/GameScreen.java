package com.tayloraliss.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;

    private Texture snakeHead;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;
    private static final int SNAKE_MOVEMENT = 32;
    private int snakeX = 0, snakeY = 0;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private int snakeDirection = UP;

    private Texture apple;
    private boolean appleAvailable = false;
    private int appleX, appleY;

    @Override
    public void show() {
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
        apple = new Texture(Gdx.files.internal("apple.png"));
        apple = new Texture(Gdx.files.internal("apple.png"));
    }

    @Override
    public void render(float delta){
        queryInput();
        timer -= delta;
        if (timer <= 0){
            timer = MOVE_TIME;
            moveSnake();
            checkForOutOfBounds();
        }
        checkAppleCollision();
        checkAndPlaceApple();
        clearScreen();
        draw();
    }

    private void checkForOutOfBounds() {
        if (snakeX >= Gdx.graphics.getWidth()){
            snakeX = 0;
        }
        if (snakeX < 0) {
            snakeX = Gdx.graphics.getWidth() - SNAKE_MOVEMENT;
        }
        if (snakeY >= Gdx.graphics.getHeight()){
            snakeY = 0;
        }
        if (snakeY < 0) {
            snakeY = Gdx.graphics.getHeight() - SNAKE_MOVEMENT;
        }
    }

    private void moveSnake(){
        switch (snakeDirection){
            case RIGHT: {
                snakeX += SNAKE_MOVEMENT;
                return;
            }
            case LEFT: {
                snakeX -= SNAKE_MOVEMENT;
                return;
            }
            case UP: {
                snakeY += SNAKE_MOVEMENT;
                return;
            }
            case DOWN: {
                snakeY -= SNAKE_MOVEMENT;
                return;
            }
        }
    }

    private void queryInput() {
        boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);

        if (aPressed) snakeDirection = LEFT;
        if (dPressed) snakeDirection = RIGHT;
        if (wPressed) snakeDirection = UP;
        if (sPressed) snakeDirection = DOWN;
    }

    private void checkAndPlaceApple() {
        if (!appleAvailable) {
            do {
                appleX = MathUtils.random(Gdx.graphics.getWidth() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                appleY = MathUtils.random(Gdx.graphics.getHeight() / SNAKE_MOVEMENT -1) * SNAKE_MOVEMENT;
                appleAvailable = true;
            } while (appleX == snakeX && appleY == snakeY);
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        batch.begin();
        batch.draw(snakeHead, snakeX, snakeY);
        if (appleAvailable) {
            batch.draw(apple, appleX, appleY);
        }
        batch.end();
    }

    private void checkAppleCollision() {
        if (appleAvailable && appleX == snakeX && appleY == snakeY) {
            appleAvailable = false;
        }
    }

}
