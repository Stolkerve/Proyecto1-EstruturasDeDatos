package com.proyecto1.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class MusicAsset {
    private Clip clip;
    private AudioInputStream sound;
    String name;

    MusicAsset(String path, String name) throws Exception {
        this.name = name;
        var file = new File(path);
        this.sound = AudioSystem.getAudioInputStream(file);
        this.clip = AudioSystem.getClip();
        clip.open(sound);
    }

    public void play(boolean loop) {
        this.clip.start();
        if (loop)
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() throws Exception {
        this.sound.close();
        this.clip.close();
        this.clip.stop();
    }

    public void setVolume(float volume) {
        if (volume < 0.f || volume > 1.0f) return;
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
