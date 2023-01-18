package Code.Environement;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Terrain extends JPanel{
	public EAV env;
	public LinkedList<Integer> memoX;
	public LinkedList<Integer> memoY;
	public LinkedList<Integer>memoA;
	public static final int MOD=2;

	public Terrain(EAV env) {
		// TODO Auto-generated constructor stub
		this.env=env;
		memoX=new LinkedList<>();
		memoY=new LinkedList<>();
		memoA=new LinkedList<>();

	}
	public void paint(Graphics g) {

		setForeground(Color.RED);
		BasicStroke line = new BasicStroke(3.0f);
		((Graphics2D) g).setStroke(line);

		if(!memoX.isEmpty()) {
			g.setColor(getBackground());
			if(memoA.getFirst()==0 ||memoA.getFirst()==180) {
				g.fillRect(0,0,4500*MOD,3500*MOD);
			}else {
				g.fillRect(memoX.getFirst()-20,memoY.getFirst()-20,350*MOD,450*MOD);
			}
		}

		g.setColor(Color.GREEN);
		g.drawLine(90*MOD, 0*MOD, 90*MOD, 200*MOD);

		g.setColor(Color.BLUE);
		g.drawLine(210*MOD, 0*MOD, 210*MOD, 200*MOD);


		g.setColor(Color.YELLOW);
		g.drawLine(30*MOD, 50*MOD, 270*MOD, 50*MOD);
		g.setColor(Color.BLACK);
		g.drawLine(30*MOD, 100*MOD, 270*MOD, 100*MOD);
		g.setColor(Color.RED);
		g.drawLine(30*MOD, 150*MOD, 270*MOD, 150*MOD);
		g.setColor(Color.BLACK);

		g.setColor(Color.BLACK);
		g.drawLine(150*MOD, 0*MOD, 150*MOD, 200*MOD);

		g.setColor(Color.WHITE);
		g.drawLine(30*MOD, 0*MOD, 30*MOD, 200*MOD);
		g.drawLine(270*MOD, 0*MOD, 270*MOD, 200*MOD);


		g.setColor(Color.BLACK);
		g.drawRect(0*MOD,0*MOD,300*MOD,200*MOD);

		for(Palet p: env.getPalets()) {
			if(p!= env.getPollux().link) {
				g.fillOval((int)p.getX()*MOD-7,(int)p.getY()*MOD-7,15,15);
			}
		}

		int x=(int)env.getPollux().getX()*MOD-15;
		int y=(int)env.getPollux().getY()*MOD-15;
		g.setColor(Color.BLACK);
		if(env.getPollux().getAngle()==0 ||env.getPollux().getAngle()==180) {
			//g.drawImage(myPicture,0,0,null);
			g.fillRect((int)env.getPollux().getX()*MOD-15,(int)env.getPollux().getY()*MOD-8,12*MOD,8*MOD);

		}else {
			//g.drawImage(myPicture,0,0,null);
			g.fillRect((int)env.getPollux().getX()*MOD-15,(int)env.getPollux().getY()*MOD-8,8*MOD,12*MOD);

		}
		if(!env.getPollux().isPince()) {	
			if(env.getPollux().getAngle()==0) {
				if(env.getPollux().areLink) {
					g.fillOval(x+22,y+5,20,20);
				}else {
					g.drawOval(x+22,y+5,20,20);
				}
			}if(env.getPollux().getAngle()==180) {
				if(env.getPollux().areLink) {
					g.fillOval(x-17,y+5,20,20);
				}else {
					g.drawOval(x-17,y+5,20,20);
				}
			}
			if(env.getPollux().getAngle()==90) {
				if(env.getPollux().areLink) {
					g.fillOval(x-3,y+25,20,20);
				}else {
					g.drawOval(x-3,y+25,20,20);
				}
			}if(env.getPollux().getAngle()==270) {
				if(env.getPollux().areLink) {
					g.fillOval(x-3,y-12,20,20);
				}else {
					g.drawOval(x-3,y-12,20,20);
				}
			}
		}	else {
			if(env.getPollux().getAngle()==0) {
				g.drawLine(x+24,y+6,x+30,y);
				g.drawLine(x+24,y+22,x+30,y+28);
			}if(env.getPollux().getAngle()==180) {
				g.drawLine(x,y+6,x-6,y);
				g.drawLine(x,y+22,x-6,y+28);
			}
			if(env.getPollux().getAngle()==90) {
				g.drawLine(x,y+30,x-6,y+36);
				g.drawLine(x+16,y+30,x+22,y+36);
			}if(env.getPollux().getAngle()==270) {
				g.drawLine(x,y+6,x-6,y);
				g.drawLine(x+16,y+6,x+22,y);
			}
		}

		/*int[]polyX=new int[] {x+40,x+32,x+24,x,x,x+24,x+32,x+40,x+38,x+32,x+24,x+24,x+32,x+38};
		int[]polyY=new int[] {y+6,y-2,y,y,y+16,y+16,y+18,y+10,y+10,y+16,y+14,y+2,y+6};
		g.fillPolygon(polyX,polyY,polyX.length);*/

		memoX.addFirst((int)env.getPollux().getX()*MOD-15);
		memoY.addFirst((int)env.getPollux().getY()*MOD-8);
		memoA.addFirst(env.getPollux().getAngle());
	}


}
