package Code.Environement;

/*
 * Classe Corp qui représente un corp sur l'EAV
 */

public abstract class Corp {
	// position x y
	double x,y;
	
	public EAV terrain;
	
	// perimetre
	public Zone z;
	
	// Constructeur de la cl&asse qui initialise les attributs
	public Corp(double x, double y, int l, int h) {
		this.x=x;
		this.y=y;
		z=new Zone (this, l, h);
		
	}
	// Getters
	public double getX () {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int x) {
		this.y=x;
	}
	public Zone getZ() {
		return z;
	}
	public void addEnv(EAV t) {
		terrain=t;
	}
	
	public abstract boolean rectifCollision(Corp c);
	
	


}