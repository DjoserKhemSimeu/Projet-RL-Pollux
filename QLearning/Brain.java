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
	private ArrayList<Integer> r;
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
	public void pickAct(Matrice st, double eps) {
		int rec= 0;
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
			System.out.println(rad);
			
		}else {
			ArrayList<Matrice>x=new ArrayList<>();
			x.add(st);
			Matrice res=Qfunction.predict(x).get(0);
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
			System.out.println(i);
		}
		int idx= (int)(Math.random()*s.size());
		s.add(idx, st);
		a.add(idx,act);
		r.add(idx, rec);
		st1.add(idx,env.getState());
		pollux.state=env.getState();
		System.out.println(env.getState());
		
	}

}
