package Code.Environement;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Code.Agent.Robot;
import Code.QLearning.Brain;
import Code.Util.Matrice;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIEspace {

	private JFrame frame;
	public EAV env;
	public Robot pollux;
	private JTextField textf;
	private Brain Q;
	double compt=0;
	private JTextField areLink;
	private JTextField camp;
	private JTextField pince;
	private JTextField angle;
	private JTextField touch;
	private JTextField color;
	private JTextField dist;
	

	/**
	 * Launch the application.
	 */
	
	
	public void state() {
		Matrice state= env.getState();
		dist.setText(""+state.getValue(0,0));
		color.setText(""+state.getValue(0,1));
		touch.setText(""+state.getValue(0,2));
		angle.setText(""+state.getValue(0,3));
		pince.setText(""+state.getValue(0,4));
		camp.setText(""+state.getValue(0,5));
		areLink.setText(""+state.getValue(0,6));
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIEspace window = new GUIEspace();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.	for(int i=0; i<3; i++) {
				Q.pickAct(new Matrice(),1);
				state();
			}
	 */
	public GUIEspace() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	
		pollux=new Robot(30,100,20,30,0);
		env=new EAV(pollux);
		Q= new Brain (pollux,env);
		pollux.addEnv(env);
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		Terrain t=new Terrain(env);
		env.addTerrain(t);
		frame.getContentPane().add(t,BorderLayout.CENTER);
		 JPanel panel1 = new JPanel();
		 frame.getContentPane().add(panel1, BorderLayout.SOUTH);
		 panel1.setLayout(new BorderLayout ());
		 JPanel panel = new JPanel();
		 panel1.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new GridLayout());
			textf= new JTextField();
			
			 JPanel panelS = new JPanel();
			 panel1.add(panelS, BorderLayout.CENTER);
				panelS.setLayout(new GridLayout());
				 dist= new  JTextField("dist");
				 panelS.add(dist);
				 color= new  JTextField("color");
				 panelS.add(color);
				 touch= new  JTextField("touch");
				 panelS.add(touch);
				 angle= new  JTextField("angle");
				 panelS.add(angle);
				 pince= new  JTextField("pince");
				 panelS.add(pince);
				 camp= new  JTextField("camp");
				 panelS.add(camp);
				 areLink= new  JTextField("areLink");
				 panelS.add(areLink);
			
			
		
			
		
		JButton btnDecalage = new JButton("Forward");
		btnDecalage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compt+=pollux.avance();
				textf.setText(""+compt);
				System.out.println(compt);
				 t.repaint();
				 state();
			}
		});
		panel.add(btnDecalage);
		
		JButton btn = new JButton("Turn");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compt+=pollux.tournerGauche();
				textf.setText(""+compt);
				System.out.println(compt);
				 t.repaint();
				 state();
			}
		});
		panel.add(btn);
		JButton btnO = new JButton("Open");
		btnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compt+=pollux.ouvrir();
				textf.setText(""+compt);
				System.out.println(compt);
				System.out.println(pollux.getX());
				 t.repaint();
				 state();
			}
		});
		panel.add(btnO);
	
		
		JButton btnF = new JButton("Close");
		btnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compt+=pollux.fermer();
				textf.setText(""+compt);
				System.out.println(compt);
				 t.repaint();
				 state();
			}
		});
		panel.add(btnF);
		
		JButton btn1 = new JButton("Pred");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<100; i++) {
					Q.pickAct(env.getState(),0.5);
					for(int c=0; c <100000;c++) {
						
					}
					state();
				}
				
			}
		});
		panel.add(btn1);
		JButton btnt = new JButton("Train");
		btnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Q.train();
			}
		});
		panel.add(btnt);
		
		
		
	
	
		
	
	
	}

}
