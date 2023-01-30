package com.lawrence.flappy;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Sprite {
    
    private ImageView imageView;
    private List<Image> frames;
    private int currentFrame;
    private double frameDelay;
    
    Sprite() {
        imageView = new ImageView();
        frames = new ArrayList<>();
        currentFrame = 0;
        frameDelay = 0;
        imageView.setX(-336);
        imageView.setY(0);
    }
    
    public Sprite addFrame(Image frame) {
        frames.add(frame);
        imageView.setImage(frames.get(currentFrame));
        return this;
    }
    
    public void move(double x, double y) {
        imageView.setX(x);
        imageView.setY(y);
    }
    
    public int nextFrame(double deltaT, double delay) {
        frameDelay += deltaT;
        if (frameDelay >= delay) {
            currentFrame++;
            if (currentFrame >= frames.size()) {
                currentFrame = 0;
            }
            imageView.setImage(frames.get(currentFrame));
            frameDelay = 0;
        }
        return currentFrame;
    }
    
    public int setCurrentFrame(int index) {
        if ((index < 0) || (index > frames.size())) {
            return currentFrame;
        }
        currentFrame = index;
        imageView.setImage(frames.get(currentFrame));
        return currentFrame;
    }
    
    public ImageView getImageView() {
        return imageView;
    }
    
    public List<Image> getFrames() {
        return frames;
    }
    
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    public double getFrameDelay() {
        return frameDelay;
    }
    
    public boolean collides(Sprite s) {
        if (getImageView().getX() + getImageView().getImage().getWidth() < s.getImageView().getX()){
            return false;
        }
        if (getImageView().getX() > s.getImageView().getX() + s.getImageView().getImage().getWidth()) {
            return false;
        }
        if (getImageView().getY() + getImageView().getImage().getHeight() < s.getImageView().getY()){
            return false;
        }
        if (getImageView().getY() > s.getImageView().getY() + s.getImageView().getImage().getHeight()) {
            return false;
        }
        return true;
    }
    
    public boolean verticallyAligned(Sprite s) {
        if (getImageView().getX() + getImageView().getImage().getWidth() < s.getImageView().getX()){
            return false;
        }
        if (getImageView().getX() > s.getImageView().getX() + s.getImageView().getImage().getWidth()) {
            return false;
        }
        return true;
    }
}
