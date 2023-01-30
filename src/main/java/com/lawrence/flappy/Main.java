package com.lawrence.flappy;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double VX = 150.0; // pixels per second
    private static final double JUMP_VY = -350; // pixels per second
    private static final double AY = 1200.0; // pixels per second squared
    private static final double GAP_RANGE = 75;
    
    private static final double CHEAT_DURATION = 2;
    
    private GameState gameState;
    private double vx = VX;
    private double ay = AY;
    private double vyPrev = 0;
    private double vy = 0;
    
    private boolean cheating = false;
    private double cheatingTime = 0;
    private int score = 0;
    private boolean enteredGap = false;
    private Scene scene;
    
    private Timer timer;
    private Pane pane;
    private Sprite bird;
    
    private Sprite splash;
    
    private Sprite ad;
    
    private Sprite gameOver;
    private Sprite[] digits;
    private Sprite[] bases;
    private Sprite[] pipesTop;
    private Sprite[] pipesBottom;
    
    private Sound die;
    private Sound hit;
    private Sound point;
    private Sound swoosh;
    private Sound wing;
    
    @Override
    public void start(Stage stage) {
        // Set scene
        BackgroundImage background =
                new BackgroundImage(new Image("/background-day.png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT
                        ,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT);
        pane = new Pane();
        pane.setBackground(new Background(background));
        pane.setMaxWidth(288);
        pane.setMaxHeight(512);
        scene = new Scene(pane, 288, 512);
        stage.setScene(scene);
        stage.setResizable(false);
        
        splash = new Sprite();
        splash.addFrame(new Image("/message.png"));
        pane.getChildren().add(splash.getImageView());
        
        pipesBottom = new Sprite[3];
        Image pipeImage = new Image("/pipe-green.png");
        for (int i = 0; i < 3; i++) {
            pipesBottom[i] = new Sprite();
            pipesBottom[i].addFrame(pipeImage);
            pane.getChildren().add(pipesBottom[i].getImageView());
        }
    
        pipesTop = new Sprite[3];
        for (int i = 0; i < 3; i++) {
            pipesTop[i] = new Sprite();
            pipesTop[i].addFrame(pipeImage).getImageView().setRotate(180);
            pane.getChildren().add(pipesTop[i].getImageView());
        }
    
        // Initialize sprites
        bird = new Sprite();
        Image mid = new Image("/yellowbird-midflap.png");
        Image up = new Image("/yellowbird-upflap.png");
        Image down = new Image("/yellowbird-downflap.png");
        bird.addFrame(mid)
                .addFrame(up)
                .addFrame(mid)
                .addFrame(down);
        pane.getChildren().add(bird.getImageView());
    
        bases = new Sprite[2];
        Image base = new Image("/base.png");
        for (int i = 0; i < 2; i++) {
            bases[i] = new Sprite();
            bases[i].addFrame(base);
            pane.getChildren().add(bases[i].getImageView());
        }
        
        digits = new Sprite[4];
        Image d0 = new Image("0.png");
        Image d1 = new Image("1.png");
        Image d2 = new Image("2.png");
        Image d3 = new Image("3.png");
        Image d4 = new Image("4.png");
        Image d5 = new Image("5.png");
        Image d6 = new Image("6.png");
        Image d7 = new Image("7.png");
        Image d8 = new Image("8.png");
        Image d9 = new Image("9.png");
        for (int i = 0; i < 4; i++) {
            digits[i] = new Sprite();
            digits[i].addFrame(d0).addFrame(d1).addFrame(d2).addFrame(d3).addFrame(d4)
                    .addFrame(d5).addFrame(d6).addFrame(d7).addFrame(d8).addFrame(d9);
            pane.getChildren().add(digits[i].getImageView());
        }
    
        gameOver = new Sprite();
        gameOver.addFrame(new Image("/gameover.png"));
        pane.getChildren().add(gameOver.getImageView());
        
        ad = new Sprite();
        ad.addFrame(new Image("/ad.png"));
        pane.getChildren().add(ad.getImageView());
        
        // Initialize sounds
        die = new Sound("/die.wav");
        hit = new Sound("/hit.wav");
        point = new Sound("/point.wav");
        swoosh = new Sound("/swoosh.wav");
        wing = new Sound("/wing.wav");
    
        gameState = GameState.INIT_SPLASH;
        
        scene.setOnKeyPressed(new InputHandler());
        timer = new Timer();
        timer.start();
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
    
    private class Timer extends AnimationTimer {
        private long prev = 0;
        private double deltaTs = 0;
        
        @Override
        public void handle(long now) {
            deltaTs = Math.min(((double) (now - prev)) / 1000000000.0, 0.01666666666);
            
            switch (gameState) {
                case INIT_SPLASH:
                    updateInitSplash(deltaTs);
                    break;
                case UPDATE_SPLASH:
                    updateSplash(deltaTs);
                    break;
                case INIT_GAME:
                    updateInitGame(deltaTs);
                    break;
                case UPDATE_GAME:
                    updateGame(deltaTs);
                    break;
                case INIT_COLLIDE:
                    updateInitCollide(deltaTs);
                    break;
                case UPDATE_COLLIDE:
                    updateCollide(deltaTs);
                    break;
                case INIT_GAME_OVER:
                    updateInitGameOver(deltaTs);
                    break;
                case UPDATE_GAME_OVER:
                    updateGameOver(deltaTs);
                    break;
                default:
                    break;
            }
            
            prev = now;
        }
    
        private void updateInitSplash(double deltaTs) {
            vyPrev = 0;
            bird.move(65, 210);
            splash.move(52, 50);
            bases[0].move(0, 400);
            bases[1].move(288, 400);
            gameOver.move(-288, 0);
            ad.move(-288, 0);
            for (int i = 0; i < 3 ; i++) {
                pipesTop[i].move(-288, 0);
                pipesBottom[i].move(-288, 0);
            }
            for (int i = 0; i < 4 ; i++) {
                digits[i].move(-288, 0);
            }
            score = 0;
            enteredGap = false;
            cheating = false;
            cheatingTime = 0;
            bird.getImageView().setOpacity(1.0);
            bird.getImageView().setRotate(0);
            swoosh.play();
            gameState = GameState.UPDATE_SPLASH;
        }
    
        private void updateSplash(double deltaTs) {
            bird.nextFrame(deltaTs, 0.05);
            scrollBases(deltaTs);
        }
    
        private void updateInitGame(double deltaTs) {
    
            splash.move(-288, 0);
    
            double gapDither = 0;
            for (int i = 0; i < 3; i++) {
                gapDither = Math.random() * GAP_RANGE;
                pipesTop[i].move(288+(i*3*52), -175 - gapDither);
                pipesBottom[i].move(288+(i*3*52), 512 - 112 - 145 - gapDither);
                gapDither *= -1;
            }
            
            gameState = GameState.UPDATE_GAME;
        }
        private void updateGame(double deltaTs) {
            if (cheating) {
                cheatingTime += deltaTs;
                bird.getImageView().setOpacity(0.2);
            }
            if (cheatingTime >= CHEAT_DURATION) {
                cheatingTime = 0;
                cheating = false;
                bird.getImageView().setOpacity(1.0);
                ad.move(-288, 0);
            }
            vy = vyPrev + ay * deltaTs;
            vyPrev = vy;
            bird.nextFrame(deltaTs, 0.05);
            bird.move(bird.getImageView().getX(), bird.getImageView().getY() + vy * deltaTs);
            rotateBird();
            boolean aligned = false;
            for (int i = 0; i < 3; i++) {
                if (bird.collides(pipesTop[i])) {
                    if (!cheating) gameState = GameState.INIT_COLLIDE;
                    aligned = true;
                } else if (bird.collides(pipesBottom[i])) {
                    if (!cheating) gameState = GameState.INIT_COLLIDE;
                    aligned = true;
                } else if (bird.verticallyAligned(pipesTop[i])) {
                    aligned = true;
                }
                
            }
            if (aligned && !enteredGap) {
                enteredGap = true;
            } else if (!aligned && enteredGap) {
                enteredGap = false;
                score++;
                point.play();
                if (score > 9999) score = 0;
            }
            for (int i = 0; i < 2; i++) {
                if (bird.collides(bases[i])) {
                    gameState = GameState.INIT_COLLIDE;
                }
            }
            displayScore();
            scroll(deltaTs);
        }
    
        private void updateInitCollide(double deltaTs) {
            vyPrev = 0;
            hit.play();
            gameState = GameState.UPDATE_COLLIDE;
        }
    
        private void updateCollide(double deltaTs) {
            vy = vyPrev + ay*deltaTs;
            vyPrev = vy;
            rotateBird();
            bird.nextFrame(deltaTs, 0.05);
            bird.move(bird.getImageView().getX(),bird.getImageView().getY() + vy*deltaTs);
            for (int i = 0; i < 2; i++) {
                if (bird.collides(bases[i])) {
                    die.play();
                    gameState = GameState.INIT_GAME_OVER;
                }
            }
        }
    
        private void rotateBird() {
            if (vy < 0) {
                bird.getImageView().setRotate(-45);
            } else if (vy > 400) {
                bird.getImageView().setRotate(80);
            } else if (vy > 200) {
                bird.getImageView().setRotate(45);
            } else {
                bird.getImageView().setRotate(0);
            }
        }
    
        private void updateInitGameOver(double deltaTs) {
            gameOver.move(50, 175);
            gameState = GameState.UPDATE_GAME_OVER;
        }
    
        private void updateGameOver(double deltaTs) {
        }
    
        private void scroll(double deltaTs) {
            double gapDither = Math.random() * GAP_RANGE;
            for (int i = 0; i < 3; i ++) {
                pipesTop[i].move(pipesTop[i].getImageView().getX() - vx*deltaTs, pipesTop[i].getImageView().getY());
                pipesBottom[i].move(pipesBottom[i].getImageView().getX() - vx*deltaTs, pipesBottom[i].getImageView().getY());
                if (pipesTop[i].getImageView().getX() <= -52) {
                    pipesTop[i].move(pipesTop[(i+2)%3].getImageView().getX() + 3*52, -175 - gapDither);
                }
                if (pipesBottom[i].getImageView().getX() <= -52) {
                    pipesBottom[i].move(pipesBottom[(i+2)%3].getImageView().getX() + 3*52, 512 - 112 - 145 - gapDither);
                }
                gapDither *= -1;
            }
            scrollBases(deltaTs);
        }
    
        private void scrollBases(double deltaTs) {
            for (int i = 0; i < 2; i++) {
                bases[i].move(bases[i].getImageView().getX() - vx*deltaTs, bases[i].getImageView().getY());
                if (bases[i].getImageView().getX() <= -288) {
                    bases[i].move(288, 400);
                }
            }
        }
    
        private void displayScore() {
            int scr = score;
            int digit;
            double xPos = 169;
            for (int i = 0; i < 4; i++) {
                digit = scr % 10;
                scr = scr / 10;
                digits[i].setCurrentFrame(digit);
                digits[i].move(xPos, 20);
                xPos -= 25;
            }
        }
    }
    
    private class InputHandler implements  EventHandler<KeyEvent> {
    
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.SPACE) {
                switch (gameState) {
                    case UPDATE_SPLASH:
                        gameState = GameState.INIT_GAME;
                        break;
                    case UPDATE_GAME:
                        vyPrev = JUMP_VY;
                        wing.play();
                        break;
                    case UPDATE_GAME_OVER:
                        gameState = GameState.INIT_SPLASH;
                        break;
                    default:
                        break;
                }
            }
    
            if (event.getCode() == KeyCode.ENTER) {
                switch (gameState) {
                    case UPDATE_GAME:
                        cheating = true;
                        ad.move(94, 444);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
}