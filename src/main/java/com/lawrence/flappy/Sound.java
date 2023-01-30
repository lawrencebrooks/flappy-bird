package com.lawrence.flappy;

import java.io.IOException;

import javafx.scene.media.AudioClip;


public class Sound {
    private AudioClip clip;
    
    Sound(String url) {
        clip = new AudioClip(getClass().getResource(url).toExternalForm());
    }
    
    public void play() {
        clip.play();
    }
    
}
