package spel;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;

public class Studs implements KeyListener{

	GoJbFrame frame = new GoJbFrame("Studs", true, 3);

	int xBlock=150, yBoll=50, xBoll = 245;

	boolean h�ger,v�nster;

	Timer timer, faller;

	JLabel label = new JLabel(){
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.blue);
			g.fillRoundRect(xBlock, 400, 200, 20,10,10);

			g.setColor(Color.red);
			g.fillOval(xBoll, yBoll, 10, 10);
			g.drawRect(500, 1, 1, 500);
		}
	};

	public static void main(String[] args) {
		new Studs();
	}
	public Studs() {
		// FIXME Auto-generated constructor stub

		frame.add(label);
		frame.addKeyListener(this);

		faller = new Timer(10, e->{
			yBoll=yBoll+2;
			if(yBoll+10==400){
				faller.stop();
				if(250-xBlock>-1&&250-xBlock<201){
					System.out.println("TR�FF "+(150-xBlock));
					int xVar=150-xBlock;
					double koficient=1;
					if(xVar!=0){ koficient = .001*xVar;}
					System.err.println(koficient + " : "+xBoll + " : " + yBoll + " : "+ xVar);
					
					
					if(koficient<0){
						xBoll=xBoll-1;
						yBoll=yBoll+(int)((xBoll)*koficient);
					}
					else if(koficient>0){
						xBoll=xBoll+1;
						yBoll=yBoll+(int)((xBoll)*(-1)*koficient);
						
					}
//					yBoll=yBoll+(int)((xBoll)*koficient);
					
					System.err.println(koficient + " : "+xBoll + " : " + yBoll);			
					
					for(int i = 0;i<10;i++){
						if(koficient<0){
							xBoll=xBoll-1;
							yBoll=yBoll+(int)((xBoll)*koficient);
						}
						else if(koficient>0){
							xBoll=xBoll+1;
							yBoll=yBoll+(int)((xBoll)*(-1)*koficient);
							
						}
						frame.revalidate();
						frame.repaint();
						System.out.println("asadssad");
						
					}
//					new Studs();
				}
				else{
					System.err.println("MISS");

				}
			}
			frame.repaint();
			frame.revalidate();
		});
		faller.start();
		timer = new Timer(10, e->{
			int i=0;
			if (h�ger) {
				i+=1;
			}
			if (v�nster) {
				i-=1;
			}
			if(xBlock+(3*i)>=0&&(xBlock+(i)+200)<482){
				xBlock=xBlock+(3*i);
				frame.revalidate();
				frame.repaint();

			}


		});
		timer.start();
		frame.repaint();
		frame.revalidate();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// FIXME Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// FIXME Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(h�ger)h�ger=false;
			v�nster=true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(v�nster)v�nster=false;
			h�ger=true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			v�nster=false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			h�ger=false;
		}

	}
}
