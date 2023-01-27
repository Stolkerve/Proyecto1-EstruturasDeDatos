package com.proyecto1.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class MusicAsset {
    String name;
    Clip clip;
    AudioInputStream sound;

    MusicAsset(String path, String name) {
        try {
            this.name = name;
            var file = new File(path);
            this.sound = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {

        }
    }

    public void play(boolean loop) {
        this.clip.start();
        if (loop)
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        try {
            this.sound.close();
            this.clip.close();
            this.clip.stop();
        } catch (Exception e) {
        }
    }


    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
