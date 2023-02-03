package com.proyecto1.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

class GameOfLife extends JPanel {
    int nXCells;
    int nYCells;
    int cellWidth;
    int cellHeight;

    boolean[] world;

    GameOfLife(int scrWidth, int scrHeight) {
		this.setSize(scrWidth, scrHeight);
        this.setPreferredSize(new Dimension(scrWidth, scrHeight));
        this.setMinimumSize(new Dimension(scrWidth, scrHeight));

        this.cellWidth = 10;
        this.cellHeight = 10;
        this.nXCells = scrWidth / this.cellWidth;
        this.nYCells = scrHeight / this.cellHeight;

        this.world = new boolean[this.nXCells * this.nYCells];
        Random random = new Random();
		for (int i = 0; i < this.world.length; i++) {
			this.world[i] = random.nextBoolean();
		}
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCell(e.getX() / cellWidth, e.getY() / cellHeight, true);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setCell(e.getX() / cellWidth, e.getY() / cellHeight, true);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean[] renderedWord = this.world.clone();

        for (int x = 0; x < this.nXCells; x++) {
            for (int y = 0; y < this.nYCells; y++) {
				int _x = x - 1 < 0 ? this.nXCells - 1 : x - 1;
				int x_ = x + 1 > this.nXCells ? 0 : x + 1;

				int _y = y - 1 < 0 ? this.nYCells - 1 : y - 1;
				int y_ = y + 1 > this.nYCells ? 0 : y + 1;

				int neighbors = getCell((_x) % this.nXCells, (_y) % this.nYCells, renderedWord) + getCell(( x) % this.nXCells, (_y) % this.nYCells, renderedWord) +
								getCell((x_) % this.nXCells, (_y) % this.nYCells, renderedWord) + getCell((x_) % this.nXCells, ( y) % this.nYCells, renderedWord) +
								getCell((x_) % this.nXCells, (y_) % this.nYCells, renderedWord) + getCell(( x) % this.nXCells, (y_) % this.nYCells, renderedWord) +
								getCell((_x) % this.nXCells, (y_) % this.nYCells, renderedWord) + getCell((_x) % this.nXCells, ( y) % this.nYCells, renderedWord);
				if (getCell(x, y, this.world) == 1)
					setCell(x, y, neighbors == 2 || neighbors == 3);
				else
					setCell(x, y, neighbors == 3);

				if (this.getCell(x, y, renderedWord) == 1) {
					g.fillRect(this.cellWidth * x, this.cellHeight * y, this.cellWidth, this.cellHeight);
				}
            }
        }
    }

    int getCell(int x, int y, boolean[] a) {
        return a[y * this.nXCells + x] ? 1 : 0; // Odio java
    }

    void setCell(int x, int y, boolean v) {
        this.world[y * this.nXCells + x] = v;
    }
}

public class HelpDialog {
    HelpDialog() {
        int width = 400;
        int height = 400;
        JDialog dialog = new JDialog();
        dialog.setModal(true);
		dialog.setResizable(false);

		GameOfLife game = new GameOfLife(width, height);

		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.repaint();
			}
		};
		Timer timer=new Timer(150,al);//create the timer which calls the actionperformed method for every 1000 millisecond(1 second=1000 millisecond)
		timer.setRepeats(true);
		timer.start();

		dialog.add(game);

		dialog.pack();
		dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
