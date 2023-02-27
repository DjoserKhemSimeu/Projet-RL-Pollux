package Code.QLearning;

import java.util.ArrayList;

import Code.Agent.Robot;
import Code.Environement.EAV;
import Code.NeuralNetwork.ActivationLayer;
import Code.NeuralNetwork.FCLayer;
import Code.NeuralNetwork.Network;
import Code.Util.ActivationFunction;
import Code.Util.Matrice;

public class Brain {
	private Robot pollux;
	private Network Qfunction;
	private EAV env;
	private ArrayList<Matrice> s;
	private ArrayList<Double> r;
	private ArrayList<String> a;
	private ArrayList<Matrice> st1;

	public Brain(Robot pollux,EAV env) { 
		Qfunction= new Network();
		Qfunction.add(new FCLayer(7,32));
		Qfunction.add(new ActivationLayer(ActivationFunction.RELU,ActivationFunction.RELUDER));
		Qfunction.add(new FCLayer(32,32));
		Qfunction.add(new ActivationLayer(ActivationFunction.RELU,ActivationFunction.RELUDER));
		Qfunction.add(new FCLayer(32,6));

		//Qfunction.add(new ActivationLayer(ActivationFunction.NORM,ActivationFunction.NORM));
		
		this.pollux=pollux;
		this.env=env;
		s=new ArrayList<>();
		r=new ArrayList<>();
		a=new ArrayList<>();
		st1=new ArrayList<>();

	}
	public ArrayList<Matrice> getY(){
		ArrayList<Matrice> res=new ArrayList<>();
		for(int i=0;i<s.size();i++) {
			Matrice d=new Matrice();
		//	if(a.get(i).equals(Robot.AVANCE)) {
				
				d=Qfunction.predict(st1.get(i)).multiplyByK(0.9).sumK(r.get(i));
				/*(0,0,r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,0));
			}else if(a.get(i).equals(Robot.RECULE)) {
				d.setValue(1,0,r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(1,0));
			}else if(a.get(i).equals(Robot.TOURNERD)) {
				d.setValue(2,0,r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(2,0));
			}else if(a.get(i).equals(Robot.TOURNERG)) {
				d.setValue(3,0,r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(3,0));
			}else if(a.get(i).equals(Robot.OUVRIR)) {
				d.setValue(4,0,r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(4,0));
			}else if(a.get(i).equals(Robot.FERMER)) {
				d.setValue(5,0,r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(5,0));
			}
			*/
			res.add(d);
			
		}
		System.out.println(res);
		System.out.println(r);

		return res;
	}
	public void pickAct(Matrice st, double eps) {
		double rec= 0;
		String act= "";
		if(Math.random()<eps) {
			int rad= (int)(Math.random()*6);
		
			if(rad==0) {
				rec=pollux.avance();
				act=Robot.AVANCE;
			} else if(rad==1) {
				rec=pollux.recule();
				act=Robot.RECULE;
			} else if(rad==2) {
				rec=pollux.tournerDroite();
				act=Robot.TOURNERD;
			} else if(rad==3) {
				rec=pollux.tournerGauche();
				act=Robot.TOURNERG;
			} else if(rad==4) {
				rec=pollux.ouvrir();
				act=Robot.OUVRIR;
			} else if(rad==5) {
				rec=pollux.fermer();
				act=Robot.FERMER;
			} 
			
			
		}else {
			
			Matrice res=Qfunction.predict(st);
	
			double argmax=res.argmax();
			int i=0;
			while(i<6) {
				if(res.getValue(i,0)==argmax) {
					break;
				}
				i++;
			}
			if(i==0) {
				rec=pollux.avance();
				act=Robot.AVANCE;
			} else if(i==1) {
				rec=pollux.recule();
				act=Robot.RECULE;
			} else if(i==2) {
				rec=pollux.tournerDroite();
				act=Robot.TOURNERD;
			} else if(i==3) {
				rec=pollux.tournerGauche();
				act=Robot.TOURNERG;
			} else if(i==4) {
				rec=pollux.ouvrir();
				act=Robot.OUVRIR;
			} else if(i==5) {
				rec=pollux.fermer();
				act=Robot.FERMER;
			} 
			
			
		}
		int idx= (int)(Math.random()*s.size());
		s.add(idx, st);
		a.add(idx,act);
		r.add(idx, rec);
		st1.add(idx,env.getState());
		pollux.state=env.getState();
		
		
	}
	
	
	public void train() {
		double eps=1.0;
		
		for(int epi=0; epi<300; epi++) {
	
			int step=0;
			while (step<400) {
				pickAct(env.getState(),eps);
				if(s.size()>10000) {
					s.remove(0);
					a.remove(0);
					r.remove(0);
					
					st1.remove(0);
				}
				step++;
				
			}
			eps=Math.max(0.1,eps*0.99);
			
			if(epi%5==0) {
				System.out.println(epi); 
				Qfunction.fit(s,getY(),30,0.01);
			}
		}
		//System.out.println(getY());
	}

}