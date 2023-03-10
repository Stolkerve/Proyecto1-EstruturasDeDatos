package com.proyecto1.gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

/**
 * @author sebas
 */
public class MenuComponent extends JPanel {
    MainPanel mainMenuPanel;

    /**
     * Constructor que crea un topbar en el menu
     * @param mainMenuPanel Panel principal
     * @param title titulo del menu
     */
    protected MenuComponent(MainPanel mainMenuPanel, String title) {
        this.mainMenuPanel = mainMenuPanel;
        this.initSize();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ImageAsset leftArrow = AssetsManager.getInstance().getImage("left-arrow");
        JButton backToMenuBtn = new JButton(leftArrow.image);
        backToMenuBtn.addActionListener(e -> this.backToMainMenu());
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BoxLayout(topPanel, BoxLayout.X_AXIS) );
        topPanel.add(backToMenuBtn);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(new JLabel(title));
        topPanel.add(Box.createHorizontalGlue());

        this.add(topPanel);

        this.initMenuComponents();
    }

    /**
     * Constructor basico
     * @param mainMenuPanel Panel principal
     */
    protected MenuComponent(MainPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
        this.initSize();
    }

    /**
     * Interfaz para inicar los componentes del menu
     */
    protected void initMenuComponents() {}

    /**
     * Volver al menu principal
     */
    protected void backToMainMenu() {
        this.mainMenuPanel.removeAll();
        this.mainMenuPanel.initComponents();
        this.mainMenuPanel.repaint();
        this.mainMenuPanel.validate();
    }

    /**
     * Forzar las dimenciones del menu
     */
    private void initSize() {
        Dimension d = this.mainMenuPanel.mainFrame.getSize();
        this.setPreferredSize(new Dimension(d.width, d.height));
        this.setSize(new Dimension(d.width, d.height));
        this.setMaximumSize(new Dimension(d.width, d.height));
        this.setMinimumSize(new Dimension(d.width, d.height));
    }
}
