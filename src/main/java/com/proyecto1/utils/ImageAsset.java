package com.proyecto1.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageAsset {
    String name;
    public ImageIcon image;

    ImageAsset(String path, String name) {
        this.name = name;
        this.image = new ImageIcon(path);
    }
}
