/*
 * Copyright 2010 Martin Ueding <mu@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Point;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Date;

@SuppressWarnings("serial")
public class Digger extends JPanel {
	private static byte[][] feld;
	private static Point spieler;
	private static int i, j, k, l;
	
	private static final int zeit = 60;
	
	private static int punkte = 0;
	
	protected static Date start, ende;
	
	private static final int BREITE = 10;
	
	protected static final byte RAUM = 0;
	protected static final byte ERDE = 1;
	protected static final byte STEIN = 2;
	protected static final byte DIAMAND = 3;
	
	static boolean challenge;
	static boolean leftPic;
	
	public static void main (String[] args) {
		challenge = JOptionPane.showConfirmDialog(null, Spr.get("challengetext"), Spr.get("challengetitel"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
		
		feld = new byte[30][30];
		spieler = new Point(0, 0);
		
		int zz;
		
		for (i = 0; i < feld.length; i++) {
			for (j = 0; j < feld[0].length; j++) {
				zz = (int)(Math.random() * 10);
				
				if (zz < 7)
					feld[i][j] = ERDE;
				else if (zz < 9)
					feld[i][j] = STEIN;
				else
					feld[i][j] = DIAMAND;
					
			}
		}
		
		final JFrame f = new JFrame("Digger");
		f.setSize(400, 420);
		f.add(new Digger());
		
		f.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {}

			public void keyReleased(KeyEvent k) {
				ende = new Date();
				
				if (challenge && ende.getTime() - start.getTime() > 60000) {
					gameover();
				}
				
				
				
				if (k.getKeyCode() == KeyEvent.VK_LEFT) {
					if (spieler.x > 0 && feld[spieler.x-1][spieler.y] != STEIN)
						spieler.x--;
					else if (spieler.x-1 > 0 && feld[spieler.x-1][spieler.y] == STEIN && feld[spieler.x-2][spieler.y] == RAUM) {
						feld[spieler.x-1][spieler.y] = RAUM;
						feld[spieler.x-2][spieler.y] = STEIN;
						spieler.x--;
					}
					leftPic = true;
				}
				else if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (spieler.x < feld.length -1 && feld[spieler.x+1][spieler.y] != STEIN)
						spieler.x++;
					else if (spieler.x < feld.length -2 && feld[spieler.x+1][spieler.y] == STEIN && feld[spieler.x+2][spieler.y] == RAUM) {
						feld[spieler.x+1][spieler.y] = RAUM;
						feld[spieler.x+2][spieler.y] = STEIN;
						spieler.x++;
					}
					leftPic = false;
				}
				
				
				else if (k.getKeyCode() == KeyEvent.VK_UP) {
					if (spieler.y > 0 && feld[spieler.x][spieler.y-1] != STEIN)
						spieler.y--;
				}
				else if (k.getKeyCode() == KeyEvent.VK_DOWN) {
					if (spieler.y < feld[0].length -1 && feld[spieler.x][spieler.y+1] != STEIN)
						spieler.y++;
				}
				
				if (feld[spieler.x][spieler.y] == ERDE)
					feld[spieler.x][spieler.y] = RAUM;
				else if (feld[spieler.x][spieler.y] == DIAMAND) {
					feld[spieler.x][spieler.y] = RAUM;
					punkte++;
				}
				
				if (spieler.y-1 > 0 && feld[spieler.x][spieler.y-1] == DIAMAND) {
					feld[spieler.x][spieler.y-1] = RAUM;
					punkte++;
				}
				
				
				
				for (i = 0; i < feld.length; i++) {
					for (j = feld[0].length-1; j > 0 ; j--) {
						if (feld[i][j] == RAUM && feld[i][j-1] == STEIN) {
							feld[i][j] = STEIN;
							feld[i][j-1] = RAUM;
						}
						else if (feld[i][j] == RAUM && feld[i][j-1] == DIAMAND) {
							feld[i][j] = DIAMAND;
							feld[i][j-1] = RAUM;
						}
					}
				}
				
				f.repaint();
				
				if (feld[spieler.x][spieler.y] == STEIN)
					gameover();
			}
			
			public void keyTyped(KeyEvent arg0) {}
		});
		
		start = new Date();
		
		f.setVisible(true);
	}
	
	Image hintergrund, erde, stein, diamand, playerr, playerl, balken;
	ImageObserver io = this;
	ClassLoader cl;

	public Digger () {
		cl = this.getClass().getClassLoader();

		try {
			hintergrund = javax.imageio.ImageIO.read(cl.getResource("hintergrund.png"));
			erde = javax.imageio.ImageIO.read(cl.getResource("erde.png"));
			stein = javax.imageio.ImageIO.read(cl.getResource("stein.png"));
			diamand = javax.imageio.ImageIO.read(cl.getResource("diamand.png"));
			playerl = javax.imageio.ImageIO.read(cl.getResource("spielerl.png"));
			playerr = javax.imageio.ImageIO.read(cl.getResource("spielerr.png"));
			balken = javax.imageio.ImageIO.read(cl.getResource("balken.png"));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}	
	}
	
	protected void paintComponent (Graphics g) {
		g.drawImage(hintergrund, 0, 0, io);
		
		for (i = spieler.x - BREITE/2, k = 0; i < spieler.x + BREITE/2; i++, k++) {
			for (j = spieler.y - BREITE/2, l = 0; j < spieler.y + BREITE/2; j++, l++) {
				if (i >= 0 && i < feld.length && j >= 0 && j < feld[0].length) {
					if (feld[i][j] == ERDE)
						g.drawImage(erde, k*40, l*40, io);
					else if (feld[i][j] == STEIN)
						g.drawImage(stein, k*40, l*40, io);
					else if (feld[i][j] == DIAMAND)
						g.drawImage(diamand, k*40, l*40, io);
					
					if (i == spieler.x && j == spieler.y) {
						if (leftPic)
							g.drawImage(playerl, k*40, l*40, io);
						else
							g.drawImage(playerr, k*40, l*40, io);
					}
				}
			}
		}
		
		g.drawImage(balken, 0, 0, io);
		
		if (challenge && ende != null) {
			g.drawString(Spr.get("zeit")+": "+Math.round(zeit - (ende.getTime() - start.getTime())/1000), 20, 25);
		}
		
		g.drawString(Spr.get("dia")+": "+punkte, 280, 25);
		
		
	}
	
	private static void gameover () {
		System.exit(0);
	}
}
