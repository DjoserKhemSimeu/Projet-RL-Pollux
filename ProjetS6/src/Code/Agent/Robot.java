package Code.Agent;

import java.awt.Color;

import Code.Environement.Corp;
import Code.Environement.EAV;
import Code.Environement.Ligne;
import Code.Environement.Palet;
import Code.Util.Matrice;

/*
 * Classe robot qui definie le fonctionnement du robot
 */
public class Robot extends Corp {
	
	//Attribut d'instance
	
	// distance captée par le robot
	private double distance;
	
	// couleur captée par le robot
	private double color;
	
	//toucher capté par le robot
	private double touch;
	
	//angle du robot sur le terrain
	private int angle;
	
	private boolean pince;
	
	//camps du robot
	private boolean camp=true;
	
	public boolean areLink;
	
	public Palet link;
	
	public Matrice state;
	
	
	
	
	public static final String AVANCE="avance", RECULE="recule", TOURNERD="tournerd"
			, TOURNERG="tournerg",OUVRIR="ouvrir",FERMER="fermer";
	

	
	//Getters
	public double getDistance() {
		return distance;
	}




	public double getColor() {
		return color;
	}

	public void restart() {
		areLink=false;
		link=null;
		angle=0;
		pince=true;
	}



	public double getTouch() {
		return touch;
	}
	public int getAngle() {
		return angle;
	}
	
	
	// Panel d'action possible du robot
	public double avance() {
		return terrain.doAction(AVANCE);
	}
	public double recule() {
		return terrain.doAction(RECULE);
	}
	public double tournerDroite() {
		return terrain.doAction(TOURNERD);
	}
	public double tournerGauche() {
		return terrain.doAction(TOURNERG);
		
	}
	public double ouvrir() {
		double res=terrain.calculRec(OUVRIR);
		if(pince==false) {
			pince=true;
		}
		if (areLink) {
			areLink=false;
			if(angle==0) {
				link.setX((int)getX()+10);
			}else if(angle==180) {
				link.setX((int)getX()-10);
				
			}else if(angle==270) {
				link.setY((int)getY()-15);
				link.setX((int)getX()-5);
			}else if(angle==90) {
				link.setY((int)getY()+15);
				link.setX((int)getX()-5);
			}
			
			link=null;
			if(color==0 && angle==0) {
				
			}
		}
		terrain.getZone().repaint();
		return res;
		
		
	}
	public double fermer() {
		double res= terrain.calculRec(FERMER);
		if(pince) {
			pince=false;
		}
		for(Palet p: terrain.getPalets()) {
			if(angle==180) {
				if(Math.abs((int)getY()-p.getY())<10 
						&& getX()-p.getX()>0 && getX()-p.getX()<25) {
					areLink=true;
					link=p;
				}
			}else if(angle==0) {
				if(Math.abs((int)getY()-p.getY())<10 
						&& getX()-p.getX()<0 && getX()-p.getX()>-25) {
					areLink=true;
					link=p;
				
				}
			}else if(angle==270) {
				if(Math.abs((int)getX()-p.getX())<10 
						&& getY()-p.getY()>0 && getY()-p.getY()<25) {
					areLink=true;
					link=p;
					
				}
			}else if(angle==90) {
				if(Math.abs((int)getX()-p.getX())<10 
						&& getY()-p.getY()<0 && getY()-p.getY()>-25) {
					areLink=true;
					link=p;
					
				}
			}
			
				
			
		}
		terrain.getZone().repaint();
		return res;
	}
	
	public void addAngle (int deg) {
		angle=angle+deg;
		if(angle<0) {
			angle=360-(Math.abs(angle)%360);
		}else {
			angle=angle%360;
		}
	}
	
	public boolean rectifCollision(Corp c) {
		int res=getZ().superpose(c.getZ());
		if (res!=0) {
			if(angle==0&& getX()<280) {
				terrain.ifPossible(c,(int)c.getX()+5,(int)c.getY());
			}
			else if(angle==180&& getX()>20) {
				terrain.ifPossible(c,(int)c.getX()-5,(int)c.getY());
			}
			else if(angle==90&& getY()<180) {
				terrain.ifPossible(c,(int)c.getX(),(int)c.getY()+5);
			}
			else if(angle==270&& getY()>20) {
				terrain.ifPossible(c,(int)c.getX(),(int)c.getY()-5);
			}else {
				terrain.restart();
				return false;
			}
			return true;
			
		}
		return false;
	}





	//Constructeur de la classe qui appel le constructeur de la super classe
	public Robot(double x, double y, int l, int h, int angle) {
	super(x,y,l,h);
	pince=true;
	this.angle=angle;
	
	}
	
	public boolean superpose(Ligne l) {
		if(l.isOrientation()==Ligne.VERTICAL) {
			return (Math.abs(l.getVal()-getX())<10);
		}else {
			return (Math.abs(l.getVal()-getY())<10);
		}
	}




	public boolean isPince() {
		return pince;
	}

}