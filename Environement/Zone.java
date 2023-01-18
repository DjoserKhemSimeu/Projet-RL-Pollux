package Code.Environement;

import java.util.ArrayList;

import Code.Agent.Robot;

public class Zone {
	Corp c;
	int l;
	int h;
	ArrayList<Position> zone;

	public Zone(Corp c,int l,int h) {
		this.c=c;
		this.l=l;
		this.h=h;
		zone= new ArrayList<>();
		initialize();
	
	}
	public void initialize() {
		zone.clear();
		int rayonH=(int) c.getY()-h/2;
		int rayonL=(int) c.getX()-l/2;
		for(int i=rayonH;i<c.getY()+h/2;i++) {
			for(int j=rayonL;j<c.getX()+l/2;j++) {
				zone.add(new Position(j,i));
			}
		}
	}
	
	public void maj() {
		int trans=l;
		l=h;
		h=trans;
		initialize();
	}
	
	public int superpose(Zone z) {
		ArrayList<Position>copy=new ArrayList<>();
		int i=0;
		for(Position p: zone) {
			for(Position p1: z.zone) {
				if(p1.equals(p)) {
					i++;
				}
			}
		}
		return i;
	}
	
	

}
