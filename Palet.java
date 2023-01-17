package Code.Environement;

/*
 * Deffinition de la classe Palet qui extends Corp cette class représente
 * les palets sur le terrain.
 */
public class Palet extends Corp{
	// Constructeur qui fait appel a celui de la super classe
	public Palet(double x, double y, int l, int h,EAV env) {
		super(x,y,l,h);
		terrain=env;
	}


	public boolean rectifCollision(Corp c) {
		int res=getZ().superpose(c.getZ());
		if (res!=0) {
			if(terrain.getPollux().getAngle()==0) {
				terrain.ifPossible(c,(int)c.getX()+2,(int)c.getY());
			}
			if(terrain.getPollux().getAngle()==180) {
				terrain.ifPossible(c,(int)c.getX()-2,(int)c.getY());
			}
			if(terrain.getPollux().getAngle()==90) {
				terrain.ifPossible(c,(int)c.getX(),(int)c.getY()+2);
			}
			if(terrain.getPollux().getAngle()==270) {
				terrain.ifPossible(c,(int)c.getX(),(int)c.getY()-2);
			}
			return true;

		}
		
		return false;
	}


}


