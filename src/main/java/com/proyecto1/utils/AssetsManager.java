package com.proyecto1.utils;

import com.proyecto1.containers.Vector;

/**
 * Singleton que contiene todos los recursos usados por el programa
 * @author sebas
 */
public class AssetsManager {
    private static AssetsManager instance;
    private Vector<MusicAsset> musicAssets;
    private Vector<ImageAsset> imageAssets;

    AssetsManager() {
        this.musicAssets = new Vector<>();
        this.imageAssets = new Vector<>();
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

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     * @return Retorna el recurso agregado
     */
    public MusicAsset addMusic(String path, String name) {
        try {
            MusicAsset newAsset = new MusicAsset(path, name);
            this.musicAssets.pushBack(newAsset);
            return newAsset;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     * @return Retorna el recurso agregado
     */
    public ImageAsset addImage(String path, String name) {
        ImageAsset newAsset = new ImageAsset(path, name);
        if (newAsset.image.getIconWidth() > 0)
            this.imageAssets.pushBack(newAsset);
        else
            return null;
        return newAsset;
    }

    /**
     * @param name Nombre del recurso a buscar
     * @return Retorna el recurso se si encontro, de lo contrario Null
     */
    public MusicAsset getMusic(String name) {
        for (MusicAsset a : this.musicAssets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }

    /**
     * @param name Nombre del recurso a buscar
     * @return Retorna el recurso se si encontro, de lo contrario Null
     */
    public ImageAsset getImage(String name) {
        for (ImageAsset a : this.imageAssets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }
}
