package Code.Environement;

import java.awt.Color;
/*
 * Classe Ligne qui représente les lignes sur le terrain
 */
public class Ligne {
	// Couleur de la ligne
	private Color color;
	
	// abcsisse ou ordonne de la ligne
	private double val;
	
	// orentation horizontal ou vetical
	private boolean orientation;
	
	// Constantes booleen de l'orientation
	public static final boolean VERTICAL=true, HORIZONTAL =false;

	
	
	//Constructeur
	public Ligne(double val, Color color, boolean ori) {
		this.val=val;
		this.color=color;
		orientation=ori;
	}

	
	//Getters
	public Color getColor() {
		return color;
	}

	public double getVal() {
		return val;
	}

	public boolean isOrientation() {
		return orientation;
	}

}
