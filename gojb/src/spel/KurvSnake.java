/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package spel;

import static gojb.GoJbGoodies.f�nsterIcon;
import static gojb.GoJbGoodies.prop;
import static gojb.GoJbGoodies.sparaProp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;
import gojb.GoJbGoodies;

public class KurvSnake {
	private GoJbFrame start = new GoJbFrame("Start",false,2),frame = new GoJbFrame("KurvSnake",false,2),highFrame=new GoJbFrame("Tetris Highscore",false,JFrame.EXIT_ON_CLOSE);
	private JButton local = new JButton("Play two on this computer"),
			online = new JButton("Play online"),
			one = new JButton("Single Player");
	private Timer timer = new Timer(10, e -> update());
	private ArrayList<Pixel> pixels = new ArrayList<>();
	private ArrayList<Pixel> pixels2 = new ArrayList<>();
	private double x,y,x2,y2;
	int riktning,riktning2,l�ngd=0,l�ngd2=0;
	boolean h�ger,v�nster,h�ger2,v�nster2;
	Pixel plupp;
	Random random = new Random();
	private ArrayList<String> highscore=new ArrayList<>();
	private JLabel label=new JLabel(){

		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(61,177,223));
			g2.fillRect(0,0, getWidth(), getHeight());
			g2.setColor(Color.red);
			plupp.draw(g2);
			g2.setColor(Color.black);
			for (Pixel pixel : pixels) {
				pixel.draw(g2);
			}
			//			for (Pixel pixel : pixels2) {
			//				pixel.draw(g2);
			//				System.out.println("erhiogdslkf");
			//			}
		};
	};
	public KurvSnake() {
		for (int i = 1; i < 6; i++) {
			highscore.add(prop.getProperty("KurvSnake"+i,"0"));
		}
		sort();
		frame.add(label);
		frame.setLayout(new GridLayout(1, 0));
		frame.addKeyListener(key);
		frame.setResizable(false);
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentMoved(ComponentEvent e) {
				highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
			}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				highFrame.dispose();
			}
			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}
		});
		start.setSize(240,140);
		start.setLayout(new GridLayout(0,1));
		start.setLocationRelativeTo(null);
		start.add(one);
		start.add(local);
		start.add(online);
		start.setVisible(true);
		highFrame.add(scorepanel);
		highFrame.setSize(230,frame.getHeight());
		highFrame.setIconImage(f�nsterIcon);
		highFrame.setUndecorated(true);
		highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
		one.addActionListener(e -> {
			start.dispose();
			highFrame.setVisible(true);
			frame.setVisible(true);
			restart();
		});
	}
	public static void main(String[] args) {
		GoJbGoodies.main("spel.KurvSnake");
	}

	private JPanel scorepanel = new JPanel(){
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			setBackground(Color.white);
			Graphics2D g2 = (Graphics2D)g;
			int pos = 50;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.red);
			g2.setFont(new Font(null, 0, 25));
			g.drawString("Highscore:",10 , pos);

			for (String string : highscore) {
				pos+=25;
				g.drawString(string,10 , pos);
			}
			g2.setColor(Color.green);
			g2.setFont(GoJbGoodies.typsnitt);
			g2.drawString("Po�ng: "+l�ngd, 10, pos+100);
		}
	};

	private void update(){
		if (h�ger) riktning +=1;
		if (v�nster) riktning -=1;
		x += Math.cos(Math.PI*riktning/100);
		y += Math.sin(Math.PI*riktning/100);
		pixels.add(new Pixel(x,y));

		if (h�ger2) riktning2 +=1;
		if (v�nster2) riktning2 -=1;
		x2 += Math.cos(Math.PI*riktning2/100);
		y2 += Math.sin(Math.PI*riktning2/100);
		pixels2.add(new Pixel(x2,y2));
		frame.repaint();

		//		highFrame.repaint();
		while (pixels.size()-l�ngd*80-300>=0) {
			pixels.remove(0);
		}
		while (pixels2.size()-l�ngd2*80-300>=0) {
			pixels2.remove(0);
		}

		for (int i = 0; i < pixels.size()-10; i++) {
			if (pixels.get(pixels.size()-1).nuddar(pixels.get(i))) {
				System.err.println("Game Over");
				timer.stop();
				Scanner scanner = new Scanner(highscore.get(4));
				int hs = scanner.nextInt();
				scanner.close();
				if (l�ngd > hs) {
					highscore.set(4, l�ngd + " " + JOptionPane.showInputDialog("Skriv ditt namn"));


					sort();
					for (int j = 0; j < highscore.size(); j++) {
						prop.setProperty("KurvSnake"+(j+1), highscore.get(j));
					}
					sparaProp("Highscore i KurvSnake");
				}
				highFrame.repaint();
				break;
			}
		}
		for (int i = 0; i < pixels2.size()-10; i++) {
			if (pixels2.get(pixels2.size()-1).nuddar(pixels2.get(i))) {
				System.err.println("Game Over");
				timer.stop();
				Scanner scanner = new Scanner(highscore.get(4));
				int hs = scanner.nextInt();
				scanner.close();
				if (l�ngd2 > hs) {
					highscore.set(4, l�ngd2 + " " + JOptionPane.showInputDialog("Skriv ditt namn"));


					sort();
					for (int j = 0; j < highscore.size(); j++) {
						prop.setProperty("KurvSnake"+(j+1), highscore.get(j));
					}
					sparaProp("Highscore i KurvSnake");
				}
				highFrame.repaint();
				break;
			}
		}
		if (pixels.get(pixels.size()-1).nuddar(plupp)) {
			l�ngd++;
			plupp();
		}
		if (x<0-pixels.get(0).diameter/2) {
			x=frame.getWidth()-pixels.get(0).diameter/2;
		}
		if (x>frame.getWidth()) {
			x=0-pixels.get(0).diameter/2;
		}
		if (y<0-pixels.get(0).diameter/2) {
			y=frame.getHeight()-pixels.get(0).diameter/2;
		}
		if (y>frame.getHeight()) {
			y=0-pixels.get(0).diameter/2;
		}


		if (pixels2.get(pixels2.size()-1).nuddar(plupp)) {
			l�ngd2++;
			plupp();
		}
		if (x2<0-pixels2.get(0).diameter/2) {
			x2=frame.getWidth()-pixels2.get(0).diameter/2;
		}
		if (x2>frame.getWidth()) {
			x2=0-pixels2.get(0).diameter/2;
		}
		if (y<0-pixels.get(0).diameter/2) {
			y2=frame.getHeight()-pixels2.get(0).diameter/2;
		}
		if (y2>frame.getHeight()) {
			y2=0-pixels2.get(0).diameter/2;
		}
	}
	void plupp(){
		plupp=new Pixel(random.nextInt((frame.getWidth()-5*20)+40), random.nextInt((frame.getHeight()-5*20)+40), 20);
		for (Pixel pixel : pixels) {
			if (plupp.nuddar(pixel)) {
				plupp();
			}
		}
		for (Pixel pixel : pixels2) {
			if (plupp.nuddar(pixel)) {
				plupp();
			}
		}
		highFrame.repaint();
	}
	private void sort() {
		Collections.sort(highscore,new Comparator<String>() {
			public int compare(String o1, String o2) {
				Scanner scanner = new Scanner(o1);
				Scanner scanner2 = new Scanner(o2);
				int a=scanner.nextInt(),b=scanner2.nextInt();
				scanner.close();
				scanner2.close();
				return a > b ? -1 : a == b ? 0 : 1;
			}
		});
	}
	private void restart() {
		x=0;
		y=0;
		x2=10;
		y2=10;
		pixels.clear();
		pixels2.clear();
		l�ngd=0;
		l�ngd2=0;
		riktning=20;
		riktning2=20;
		plupp();
		timer.start();

	}
	KeyListener key= new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				v�nster=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				h�ger=false;
			}
			if (e.getKeyCode()==KeyEvent.VK_W) {
				v�nster2=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_S) {
				h�ger2=false;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				v�nster=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				h�ger=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_R) {
				restart();
			}
		}
	};
	public class Pixel{
		private double x,y;
		private int diameter;
		public Pixel(double x,double y) {
			this(x,y,8);
		}
		public Pixel(double x,double y, int diameter) {
			this.x=x;
			this.y=y;
			this.diameter=diameter;
		}
		void draw(Graphics2D g){
			g.fillOval((int)Math.round(x)-diameter/2, (int)Math.round(y)-diameter/2, diameter,diameter);
		}
		boolean nuddar(Pixel pixel){
			return Math.sqrt(Math.pow(x-pixel.x,2)+Math.pow(y-pixel.y,2))<=diameter/2+pixel.diameter/2;
		}
	}
}
