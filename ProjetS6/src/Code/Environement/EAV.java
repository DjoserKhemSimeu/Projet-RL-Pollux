package Code.Environement;

import java.awt.Color;
import java.util.ArrayList;
/*
 * La classe EAV représent l'environement d'apprentissage virtuel qui est l'espace 
 * de jeu du robot
 */

import Code.Agent.Robot;
import Code.Util.Matrice;

public class EAV {

	// Liste des palets
	private ArrayList<Palet> palets ;

	//Le robot qui interagit
	private Robot pollux;

	//Liste des lignes du terrain
	private ArrayList <Ligne> lignes;

	private Terrain zone;

	private double ix, iy;
	boolean b=false;
	private static final double lvl1=-2, lvl2=-1, lvl3=-0.5, lvl4=0.5, lvl5=1, lvl6=2;



	// Constructeur qui vas initialiser le robot et les attributs d'
	// instance via la methode initialize
	public EAV(Robot pollux) {
		this.pollux=pollux;
		pollux.addEnv(this);
		palets=new ArrayList<>();
		lignes= new ArrayList<>();
		ix=pollux.getX();
		iy=pollux.getY();
		initialize();
	}


	/*
	 * Methode initialize qui initialize la liste de palet avec lec position sur
	 * sur le terrain et les lignes de couleurs
	 */
	private void initialize() {
		ArrayList<Palet>copyP=new ArrayList<>();
		ArrayList<Ligne>copyL=new ArrayList<>();

		// initialisation des palet A l'aide du Constructeur:
		//Palet(double x, double y)
		copyP.add(new Palet(90,50,10,10,this));
		copyP.add(new Palet(90,100,10,10,this));
		copyP.add(new Palet(90,150,10,10,this));
		copyP.add(new Palet(150,50,10,10,this));
		copyP.add(new Palet(150,100,10,10,this));
		copyP.add(new Palet(150,150,10,10,this));
		copyP.add(new Palet(210,50,10,10,this));
		copyP.add(new Palet(210,100,10,10,this));
		copyP.add(new Palet(210,150,10,10,this));



		//initialisation des lignes a l'aide du constructeur de la class LIGNE:
		// Lignes(double val, Color color, boolean ori)
		copyL.add(new Ligne(90,Color.GREEN,Ligne.VERTICAL));
		copyL.add(new Ligne(50,Color.RED,Ligne.HORIZONTAL));
		copyL.add(new Ligne(210,Color.BLUE,Ligne.VERTICAL));
		copyL.add(new Ligne(150,Color.YELLOW,Ligne.HORIZONTAL));
		copyL.add(new Ligne(100,Color.BLACK,Ligne.HORIZONTAL));
		copyL.add(new Ligne(150,Color.BLACK,Ligne.VERTICAL));
		copyL.add(new Ligne(30,Color.WHITE,Ligne.VERTICAL));
		copyL.add(new Ligne(270,Color.WHITE,Ligne.VERTICAL));
		palets=copyP;
		lignes=copyL;
		


	}

	public Terrain getZone() {
		return zone;
	}


	public void addTerrain(Terrain t) {
		zone=t;
	}


	public ArrayList<Palet> getPalets() {
		return palets;
	}
	public Matrice getState() {
		Matrice res=new Matrice(1,7);
		if(pollux.getAngle()==0) {
			res.setValue(0,0,300-pollux.getX());
		}
		if(pollux.getAngle()==180) {
			res.setValue(0,0,pollux.getX());
		}
		if(pollux.getAngle()==90) {
			res.setValue(0,0,200-pollux.getY());
		}
		if(pollux.getAngle()==270) {
			res.setValue(0,0,pollux.getY());
		}
		res.setValue(0,1,-1);
		for(Ligne l: lignes) {
			if(pollux.superpose(l)) {
				if(l.getColor().equals(Color.WHITE)) {
					res.setValue(0,1,0);
				}else if(l.getColor().equals(Color.BLACK)) {
					res.setValue(0,1,1);
				}else if(l.getColor().equals(Color.BLUE)) {
					res.setValue(0,1,2);
				}else if(l.getColor().equals(Color.RED)) {
					res.setValue(0,1,3);
				}else if(l.getColor().equals(Color.YELLOW)) {
					res.setValue(0,1,4);
				}else if(l.getColor().equals(Color.GREEN)) {
					res.setValue(0,1,5);
				}




			}
		}
		res.setValue(0,2,0);
		res.setValue(0,3,pollux.getAngle());
		if(pollux.isPince()) {
			res.setValue(0,4,1);
		}else {
			res.setValue(0,4,0);
		}
		res.setValue(0,5,1);
		if(pollux.areLink) {
			res.setValue(0,6,1);
		}else {
			res.setValue(0,6,0);
		}
		return res;
	}


	public Robot getPollux() {
		return pollux;
	}
	public void restart() {
		pollux.setX((int)ix);
		pollux.setY((int)iy);
		initialize();
		pollux.restart();
	}

	public int ifPossible(Corp c,int x, int y) {
		
		if (x<290 && x>10 &&y<190 && y>10) {
			if(c instanceof Robot && ((Robot)c).areLink) {
				Palet p= ((Robot)c).link;
				p.setX(x);
				p.setY(y);
			}
			c.setX(x);
			c.setY(y);
			c.getZ().initialize();
			if(rectifColi()&&pollux.areLink) {
				return-1;
			}
			return 0;
		}else {
			restart();
			return -2;

		}
	}



	public boolean rectifColi() {
		boolean b=false;
		for(Palet p: palets) {
			if(pollux.rectifCollision(p)) {
				b=true;
				for(Palet o: palets) {
					p.rectifCollision(o);
				}
			}

		}
		return b;

	}
	public Palet paletPP() {
		double minValue=Double.MAX_VALUE;
		Palet res=null;
		for(Palet p: palets) {
			double x1=pollux.getX();
			double x2=p.getX();
			double y1=pollux.getY();
			double y2=p.getY();
			double val= Math.sqrt(Math.pow(Math.abs(x1-x2),2)+Math.pow(Math.abs(y1-y2),2));
			if( val< minValue) {
				minValue=val;
				res=p;
			}
		}
		return res;
	}
	public double calculRec(String act) {
		if(act==Robot.AVANCE) {
			if(pollux.areLink) {
				if(pollux.getAngle()==0) {
					return lvl5;
				}else if(pollux.getAngle()==90) {
					return lvl1;
				}else if (pollux.getAngle()==180) {
					return lvl1;
				}else if(pollux.getAngle()==270) {
					return lvl1;
				}
			}else {
				Palet p=paletPP();
				double x1=pollux.getX();
				double x2=p.getX();
				double y1=pollux.getY();
				double y2=p.getY();
				if(pollux.getAngle()==0) {
					if(Math.abs(y1-y2)<Math.abs(x1-x2) && x1<x2) {
						return lvl5;
					}else {
						return lvl1;
					}
				}else if(pollux.getAngle()==90) {
					if(Math.abs(y1-y2)>Math.abs(x1-x2) && y1<y2) {
						return lvl5;
					}else {
						return lvl1;
					}
				}else if (pollux.getAngle()==180) {
					if(Math.abs(y1-y2)<Math.abs(x1-x2) && x1>x2) {
						return lvl5;
					}else {
						return lvl1;
					}
				}else if(pollux.getAngle()==270) {
					if(Math.abs(y1-y2)>Math.abs(x1-x2) && y1>y2) {
						return lvl5;
					}else {
						return lvl1;
					}
				}

			}
		}
		else if (act==Robot.RECULE) {
			if(pollux.areLink) {
				if(pollux.getAngle()==0) {
					return lvl1;
				}else if(pollux.getAngle()==90) {
					return lvl2;
				}else if (pollux.getAngle()==180) {
					return 0;
				}else if(pollux.getAngle()==270) {
					return lvl2;
				}
			}else {
				return lvl3;
			}
		}else if (act==Robot.TOURNERD) {
			if(pollux.areLink) {
				if(pollux.getAngle()==0) {
					return lvl6;
				}else if(pollux.getAngle()==90) {
					return lvl1;
				}else if (pollux.getAngle()==180) {
					return lvl1;
				}else if(pollux.getAngle()==270) {
					return lvl4;
				}
			}else {
				Palet p=paletPP();
				double x1=pollux.getX();
				double x2=p.getX();
				double y1=pollux.getY();
				double y2=p.getY();
				if(pollux.getAngle()==0) {
					if(Math.abs(y1-y2)<Math.abs(x1-x2) && x1<x2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}else if(pollux.getAngle()==90) {
					if(Math.abs(y1-y2)>Math.abs(x1-x2) && y1<y2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}else if (pollux.getAngle()==180) {
					if(Math.abs(y1-y2)<Math.abs(x1-x2) && x1>x2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}else if(pollux.getAngle()==270) {
					if(Math.abs(y1-y2)>Math.abs(x1-x2) && y1>y2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}
			}
		}else if (act==Robot.TOURNERG) {
			if(pollux.areLink) {
				if(pollux.getAngle()==0) {
					return lvl6;
				}else if(pollux.getAngle()==90) {
					return lvl4;
				}else if (pollux.getAngle()==180) {
					return lvl1;
				}else if(pollux.getAngle()==270) {
					return lvl1;
				}
			}else {
				Palet p=paletPP();
				double x1=pollux.getX();
				double x2=p.getX();
				double y1=pollux.getY();
				double y2=p.getY();
				if(pollux.getAngle()==0) {
					if(Math.abs(y1-y2)<Math.abs(x1-x2) && x1<x2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}else if(pollux.getAngle()==90) {
					if(Math.abs(y1-y2)>Math.abs(x1-x2) && y1<y2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}else if (pollux.getAngle()==180) {
					if(Math.abs(y1-y2)<Math.abs(x1-x2) && x1>x2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}else if(pollux.getAngle()==270) {
					if(Math.abs(y1-y2)>Math.abs(x1-x2) && y1>y2) {
						return lvl6;
					}else {
						return lvl2;
					}
				}
			}
		}else if (act==Robot.OUVRIR) {
			if(pollux.areLink) {
				if(pollux.getX()>=200) {
					return lvl6;
				}else {
					return lvl1;
				}
			}else {
				return lvl1;
			}
		}
		else if (act==Robot.FERMER) {
			if(pollux.areLink) {

				return lvl1;

			}else {
				Palet p=paletPP();
				double x1=pollux.getX();
				double x2=p.getX();
				double y1=pollux.getY();
				double y2=p.getY();
				if(pollux.getAngle()==180) {
					if(Math.abs(y1-y2)<10 
							&& x1-x2>0 && x1-x2<50) {
						return lvl6;
					}else {
						return lvl1;
					}
				}else if(pollux.getAngle()==90) {
					if(Math.abs(x1-x2)<10 
							&& y1-y2<0 && y1-y2>-50) {
						return lvl6;
					}else {
						return lvl1;
					}
				}else if (pollux.getAngle()==0) {
					if(Math.abs(y1-y2)<10 
							&& x1-x2>0 && x1-x2>-50) {
						return lvl6;
					}else {
						return lvl1;
					}
				}else if(pollux.getAngle()==270) {
					if(Math.abs(x1-x2)<10 
							&& y1-y2>0 && y1-y2<50) {
						return lvl6;
					}else {
						return lvl1;
					}
				}
			}
		}

		return 0;

	}

	public double doAction(String action) {
		if(action==Robot.AVANCE) {
			if(pollux.getAngle()==0) {
				ifPossible(pollux,(int) pollux.getX()+10,(int)pollux.getY());

			}else if(pollux.getAngle()==90) {
				ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()+10);

			}else if (pollux.getAngle()==180) {
				ifPossible(pollux,(int) pollux.getX()-10,(int)pollux.getY());

			}else if(pollux.getAngle()==270) {
				ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()-10);

			}
		}if(action==Robot.RECULE) {
			if(pollux.getAngle()==0) {
				ifPossible(pollux,(int) pollux.getX()-10,(int)pollux.getY());

			}else if(pollux.getAngle()==90) {
				ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()-10);

			}else if (pollux.getAngle()==180) {
				ifPossible(pollux,(int) pollux.getX()+10,(int)pollux.getY());

			}else if(pollux.getAngle()==270) {
				ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()+10);

			}
		}if(action==Robot.TOURNERD) {
			pollux.addAngle(90);
			pollux.getZ().maj();

		}if (action==Robot.TOURNERG) {
			pollux.addAngle(-90);
			pollux.getZ().maj();

		}if(action==Robot.OUVRIR) {
			pollux.ouvrir();
		}if (action==Robot.FERMER) {
			pollux.fermer();
			//a definir pour la fonction de recompense


		}
		return calculRec(action);


	}




}
