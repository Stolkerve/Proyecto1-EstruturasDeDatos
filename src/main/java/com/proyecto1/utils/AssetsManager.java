package com.proyecto1.utils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AssetsManager {
    private static AssetsManager instance;
    private static Object mutex = new Object();
    private ArrayList<MusicAsset> musicAssets;
    private ArrayList<ImageAsset> imageAssets;

    AssetsManager() {
        this.musicAssets = new ArrayList<>();
        this.imageAssets = new ArrayList<>();
    }

    public static AssetsManager getInstance() {
        if (instance == null) {
            synchronized (AssetsManager.class) {
                if (instance == null)
                    instance = new AssetsManager();
            }
        }
        return instance;
    }

    public MusicAsset addMusic(String path, String name) {
        try {
            var newAsset = new MusicAsset(path, name);
            this.musicAssets.add(newAsset);
            return newAsset;
        } catch (Exception e) {
            return null;
        }
    }

    public ImageAsset addImage(String path, String name) {
        var newAsset = new ImageAsset(path, name);
        if (newAsset.image.getIconWidth() > 0)
            this.imageAssets.add(newAsset);
        else
            return null;
        return newAsset;
    }

    public MusicAsset getMusic(String name) {
        for (var a : this.musicAssets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }

    public ImageAsset getImage(String name) {
        for (var a : this.imageAssets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }
}
