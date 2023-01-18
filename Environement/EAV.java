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



	// Constructeur qui vas initialiser le robot et les attributs d'
	// instance via la methode initialize
	public EAV(Robot pollux) {
		this.pollux=pollux;
		pollux.addEnv(this);
		palets=new ArrayList<>();
		lignes= new ArrayList<>();
		initialize();
	}


	/*
	 * Methode initialize qui initialize la liste de palet avec lec position sur
	 * sur le terrain et les lignes de couleurs
	 */
	private void initialize() {

		// initialisation des palet A l'aide du Constructeur:
		//Palet(double x, double y)
		palets.add(new Palet(90,50,10,10,this));
		palets.add(new Palet(90,100,10,10,this));
		palets.add(new Palet(90,150,10,10,this));
		palets.add(new Palet(150,50,10,10,this));
		palets.add(new Palet(150,100,10,10,this));
		palets.add(new Palet(150,150,10,10,this));
		palets.add(new Palet(210,50,10,10,this));
		palets.add(new Palet(210,100,10,10,this));
		palets.add(new Palet(210,150,10,10,this));



		//initialisation des lignes a l'aide du constructeur de la class LIGNE:
		// Lignes(double val, Color color, boolean ori)
		lignes.add(new Ligne(90,Color.GREEN,Ligne.VERTICAL));
		lignes.add(new Ligne(50,Color.RED,Ligne.HORIZONTAL));
		lignes.add(new Ligne(210,Color.BLUE,Ligne.VERTICAL));
		lignes.add(new Ligne(150,Color.YELLOW,Ligne.HORIZONTAL));
		lignes.add(new Ligne(100,Color.BLACK,Ligne.HORIZONTAL));
		lignes.add(new Ligne(150,Color.BLACK,Ligne.VERTICAL));
		lignes.add(new Ligne(30,Color.WHITE,Ligne.VERTICAL));
		lignes.add(new Ligne(270,Color.WHITE,Ligne.VERTICAL));


	}

	public void addTerrain(Terrain t) {
		zone=t;
	}


	public ArrayList<Palet> getPalets() {
		return palets;
	}
	public Matrice getState() {
		Matrice res=new Matrice(7,1);
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
		res.setValue(1,0,-1);
		for(Ligne l: lignes) {
			if(pollux.superpose(l)) {
				if(l.getColor().equals(Color.WHITE)) {
					res.setValue(1,0,0);
				}else if(l.getColor().equals(Color.BLACK)) {
					res.setValue(1,0,1);
				}else if(l.getColor().equals(Color.BLUE)) {
					res.setValue(1,0,2);
				}else if(l.getColor().equals(Color.RED)) {
					res.setValue(1,0,3);
				}else if(l.getColor().equals(Color.YELLOW)) {
					res.setValue(1,0,4);
				}else if(l.getColor().equals(Color.GREEN)) {
					res.setValue(1,0,5);
				}
				
				
				
				
			}
		}
		res.setValue(2,0,0);
		res.setValue(3,0,pollux.getAngle());
		if(pollux.isPince()) {
			res.setValue(4,0,1);
		}else {
			res.setValue(4,0,0);
		}
		res.setValue(5,0,1);
		if(pollux.areLink) {
			res.setValue(6,0,1);
		}else {
			res.setValue(6,0,0);
		}
		return res;
	}


	public Robot getPollux() {
		return pollux;
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
	public int doAction(String action) {
		if(action==Robot.AVANCE) {
			if(pollux.getAngle()==0) {
				return ifPossible(pollux,(int) pollux.getX()+10,(int)pollux.getY());
			}else if(pollux.getAngle()==90) {
				return ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()+10);
			}else if (pollux.getAngle()==180) {
				return ifPossible(pollux,(int) pollux.getX()-10,(int)pollux.getY());
			}else if(pollux.getAngle()==270) {
				return ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()-10);
			}else {
				return 0;
			}
		}if(action==Robot.RECULE) {
			if(pollux.getAngle()==0) {
				return ifPossible(pollux,(int) pollux.getX()-10,(int)pollux.getY());
			}else if(pollux.getAngle()==90) {
				return ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()-10);
			}else if (pollux.getAngle()==180) {
				return ifPossible(pollux,(int) pollux.getX()+10,(int)pollux.getY());
			}else if(pollux.getAngle()==270) {
				return ifPossible(pollux,(int) pollux.getX(),(int)pollux.getY()+10);
			}else {
				return 0;
			}
		}if(action==Robot.TOURNERD) {
			pollux.addAngle(90);
			pollux.getZ().maj();
			return 0;
		}if (action==Robot.TOURNERG) {
			pollux.addAngle(-90);
			pollux.getZ().maj();
			return 0;
		}if(action==Robot.OUVRIR) {
			pollux.ouvrir();
			return 0;
		}if (action==Robot.FERMER) {
			pollux.fermer();
			//a definir pour la fonction de recompense
			return 0;
		}
		return 0;


	}




}
