package com.proyecto1.utils;

import java.util.ArrayList;

public class MusicAssets {
    private static MusicAssets instance;
    private ArrayList<MusicAsset> assets;
    private MusicAssets() {
        this.assets = new ArrayList<>();
    }

    public static MusicAssets getInstance() {
        if (instance == null) {
            instance = new MusicAssets();
        }
        return instance;
    }

    public MusicAsset add(String path, String name) {
        var asset = new MusicAsset(path, name);
        this.assets.add(asset);
        return asset;
    }

    public MusicAsset get(String name) {
        for (var a : this.assets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }
}
