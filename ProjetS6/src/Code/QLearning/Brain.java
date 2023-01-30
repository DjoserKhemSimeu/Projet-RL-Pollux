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
		Qfunction.add(new FCLayer(32,6));
		Qfunction.add(new ActivationLayer(ActivationFunction.RELU,ActivationFunction.RELUDER));
		this.pollux=pollux;
		this.env=env;
		s=new ArrayList<>();
		r=new ArrayList<>();
		a=new ArrayList<>();
		st1=new ArrayList<>();

	}
	public ArrayList<Double> getY(){
		ArrayList<Double> res=new ArrayList<>();
		for(int i=0;i<s.size();i++) {
			double d=0;
			if(a.get(i).equals(Robot.AVANCE)) {
				d=r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,0);
			}else if(a.get(i).equals(Robot.RECULE)) {
				d=r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,1);
			}else if(a.get(i).equals(Robot.TOURNERD)) {
				d=r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,2);
			}else if(a.get(i).equals(Robot.TOURNERG)) {
				d=r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,3);
			}else if(a.get(i).equals(Robot.OUVRIR)) {
				d=r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,4);
			}else if(a.get(i).equals(Robot.FERMER)) {
				d=r.get(i)+ 0.9*Qfunction.predict(st1.get(i)).getValue(0,5);
			}
			
			res.add(d);
		}
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
			ArrayList<Matrice>x=new ArrayList<>();
			x.add(st);
			Matrice res=Qfunction.predict(st);
			System.out.println(res);
			double argmax=res.argmax();
			int i=0;
			while(i<6) {
				if(res.getValue(0,i)==argmax) {
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
		
		for(int epi=0; epi<150; epi++) {
			double reward=0;
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
				Qfunction.fit(s,getY(),10,0.01);
			}
		}
	}

}