package Code.Util;

/** DÃ©finition de la classe Matrice (Matrix) dans lequel nous trouvons beaucoup
 * de mÃ©thodes, d'opÃ©rations sur les matrices
 * 
 * Remarque : 
 * Cette classe est seulement un outils dans le but d'effectuer des calculs sur
 * les graphes. Ceci n'est pas une classe complÃ¨te pour la manipulation de  
 * matrice.
 * 
 * @author ASLAN Hikmet
 * @version 1.1
 */

public class Matrice
{
	private double[][] coeff = null;
	private int diametre = 0;
	private int distance = 0;
	
	//----------------------------------------------//
	//					   CONSTRUCTOR							//
	//----------------------------------------------//
	/** Constructeur Matrice
	  * @param
	  * int i - ligne
	  * int j - colonne 
	  */
	public Matrice(int i, int j)
	{
		this.setLength(i,j);
	}
	
	public Matrice()
	{
		this(0,0);
	}

	public Matrice(double[][] mat)
	{
		this.coeff = mat;
	}

	//----------------------------------------------//
	//					  		 SETTER					   	//
	//----------------------------------------------//	
	// dÃ©finit une matrice de type double[][]
	public void setMatrice(double[][] mat)
	{
		this.coeff = mat;
	}
	
	// dÃ©finit une valeur Ã  la position i et j
	// i - ligne
	// j - col
	public void setValue(int i, int j, double value)
	{
		this.coeff[i][j] = value;
	}
	
	
	// on dÃ©finit la taille de la mtrice
	public void setLength(int i, int j)
	{
		this.coeff = new double[i][j];
	}
	
	
	//----------------------------------------------//
	//					  		 GETTER					   	//
	//----------------------------------------------//	
	// retourne la matrice sous forme du type double[][]
	public double[][] getMatrice()
	{
		return this.coeff;
	}
	
	// retourne le nombre de ligne
	public int getRows()
	{
		return this.coeff.length;
	}
	
	// retourne le nombre de colonne
	public int getColumns()
	{
		return this.coeff[0].length;
	}
	public double mean() {
		double res=0;
		for (int i=0;i<getRows();i++) {
			for(int j=0;j<getColumns();j++) {
				res+=getValue(i,j);
			}
		}
		return res/(getRows()*getColumns());
	}
	
	// retourne la valeur Ã  la position i et j
	public double getValue(int i, int j)
	{
		return this.coeff[i][j];
	}
	
	public int[] getValue(double d) {
		for (int i=0; i<this.getRows(); i++) {
			for (int j=0; j<this.getColumns(); j++) {
				if(d==getValue(i,j)) {
					return new int[] {i,j};
				}
		}
	}
		return new int[] {};
	}
	
	// retourne le dÃ©terminant d'une matrice
	public double getDeterminant()
	{
		Matrice a = null;
		double value = 0;
	
		if (this.getRows() < 3 && this.getColumns() < 3)
			return (this.getValue(0,0)*this.getValue(1,1) - this.getValue(1,0)*this.getValue(0,1));
		
		
		for (int j=0; j<this.getColumns(); j++)
		{
				a = this.getNewMatrice(0,j);
				value += (int)Math.pow(-1,j)*(this.getValue(0,j)*a.getDeterminant());
		}
		
		return value;
	}
	
	// retourne la matrice inverse de la matrice this
	public Matrice getMatriceInverse()
	{
		Matrice a = new Matrice(this.getRows(), this.getColumns());
		Matrice tmp = null;
		double det = this.getDeterminant();
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				tmp = this.getNewMatrice(i,j);
				a.setValue(i,j,(int)Math.pow(-1,i+j)*(tmp.getDeterminant()/det));
			}
			
		// on transpose la matrice les coeffcients seront positionnÃ© de faÃ§on incorrect
		return a.getMatriceTranspose();
	}
	
	
	/* Retourne une nouvelle matrice mais en supprimant 
	 * la ligne row et la colonne columns
	 */
	private Matrice getNewMatrice(int row, int columns)
	{
		Matrice a = new Matrice(this.getRows()-1, this.getColumns()-1);
		int k = -1, m = 0;
		
		for (int i=0; i<this.getRows(); i++)
		{
			
			k++;
			
			if (i == row) 
			{	
				k--;
			 	continue;
			}

			m = -1;
			
			for (int j=0; j<this.getColumns(); j++)
			{
			
				m++;
							
				if (j==columns) 
				{
					m--;
					continue;					
				}
				
				a.setValue(k,m,this.getValue(i,j));
			}		

		}
		
		return a;
	}
	
	/**
	  * Retourne le nombre de combinaison a partir d'une matrice
	  * dÃ©finit a partir d'un graphe.
	  * @param 
	  * 	sA - sommet A
	  *	sB - sommet B
	  *	nb - Nombre de arrete (ou nombre de caractere du combinaison)
	  * @return
	  * 	double - nombre de combinaison possible entre 2 sommets
	  */
	public double getGrapheCombiCount(int sA, int sB, int nb)
	{
		if (sB > this.getRows() || sA > this.getColumns())
			return -1;
	
		Matrice a = this.matricePow(nb);
		
		return a.getValue(sB-1,sA-1);
	}
	
	
	// retourne la matrice I en fonction de la mtrice this
	public Matrice getMatriceIdentity()
	{
		Matrice a = new Matrice(this.getRows(),this.getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			a.setValue(i,i,1);
		
		return a;		
	}
	
	// transpose la matrice 
	public Matrice getMatriceTranspose()
	{
		Matrice a = new Matrice(this.getColumns(), this.getRows());
		double tmp = 0;
		
		for (int i=0; i<a.getRows(); i++)
			for (int j=0; j<a.getColumns(); j++)
			{
				tmp = this.getValue(j,i);
				a.setValue(i,j,tmp);
			}
		
		
		return a;
	}
	
	// retourne la valeur de la trice de la matrice
	public double getTraceMatrice()
	{
		double value = 0;
		
		for (int i=0; i<this.getRows(); i++)
			value += this.getValue(i,i);
		
		return value;
	}
	
	
	/** Retourne la distance (nombre d'arrete) entre 
	  * entre deux sommets sA et sB, tel que sA <= sB
	  * si sA > sB, mÃ©thode renvoi -1 pour erreur
	  */
	public double getDistanceGraphe(int sA, int sB)
	{
		double value = 0;
		
		if (sA > sB) 
			return -1;
		
		if (this.getValue(sB-1,sA-1) != 0)
			return (this.getValue(sB-1,sA-1));
		
		for (int i=sA; i<sB; i++)
			value += this.getValue(i-1, (i+1)-1);

		return value;
	}
	
	// GRAPHE
	// retourne la matrice de distance 
	public Matrice getMatriceDistanceGraphe()
	{
		Matrice a = new Matrice(this.getRows(),this.getColumns());
		int n=1;
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				 if (a.getValue(i,j) == 0)
						a = this.matricePow(n++);

			}

		// n-1 correspond a la distance 
		this.distance = n-1;
	
		return a;
	}
	
	// GRAPHE
	// retourne la distance de la matrice
	public int getDistance()
	{
		this.getMatriceDistanceGraphe();
		return this.distance;
	}
	
	
	// retourne la matrice compagnon en fonction de la matrice this
	public Matrice getMatriceCompagnon()
	{
		Matrice a = new Matrice(this.getRows(),this.getColumns());
		
		for (int i=0; i<a.getRows()-1; i++)
			a.setValue(i+1,i,1);
		
			a.setValue(this.getRows()-1,this.getColumns()-1,-1);				
					
		return a;
	}
	
	
	// GRAPHE
	// retourne la matrice diam
	public Matrice getMatriceDiametre()
	{
		int n=1;
		Matrice ai = this.sumMatrice(this.getMatriceIdentity());

		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				 if (ai.getValue(i,j) == 0)
						ai = this.matricePow(n++);

			}
			
		this.diametre = n-1;
		
		return ai;
	}
	
	// GRAPHE
	// retourne la valeur du diametre de la matrice (ou graphe :))
	public int getDiametre()
	{
		this.getMatriceDiametre();
		return this.diametre;
	}
	
	// GRAPHE
	// retourne les deux sommets les plus Ã©loignÃ©s
	public int[] getSommetPLusDistant()
	{
		int[] sommets = new int[2];
		Matrice m = this.matricePow(this.getDiametre()-1);
		byte n = 0;
		
		for (int i=0; i<m.getRows(); i++)
			for (int j=0; j<m.getColumns(); j++)
				if (m.getValue(i,j) == 0)
				{
					sommets[n++] = i+1;
				}
		
		return sommets;
	}
	
	//----------------------------------------------//
	//  		  			 OTHERS METHODS				   //
	//----------------------------------------------//	
	// multiplication
	public Matrice multiply(final Matrice matrice)
	{
		if(this.getColumns()==matrice.getRows()) {
		Matrice a = new Matrice(this.getRows(), matrice.getColumns());
		int k,i,j,m;
		double value = 0;
				
		for (k=0; k<matrice.getColumns(); k++)
		{
						
			for (i=0; i<this.getRows(); i++)
			{
			
				for (j=0; j<this.getColumns(); j++)
					value += this.getValue(i,j)*matrice.getValue(j,k);

				a.setValue(i,k,value);
				value = 0;
			}
		}
		
		return a;
		}else {
			throw new IllegalArgumentException();
		}
	}
	public Matrice sumK (double k)
	{
		Matrice a = new Matrice(this.getRows(), this.getColumns());
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)+k);
			
		return a;
	}
	// addition
	public Matrice sumMatrice(final Matrice matrice)
	{
		Matrice a = new Matrice(this.getRows(), this.getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)+matrice.getValue(i,j));
			
		return a;
	}
	
	// puissance -> M^n
	public Matrice matricePow(int n)
	{
		Matrice a = this;
		Matrice b = a;
		
		for (int i=0; i<n-1; i++)
			b = a.multiply(b);
			
		return b;
	}
	
	// soustraction
	public Matrice soustraction(final Matrice matrice)
	{
		Matrice a = new Matrice(this.getRows(), this.getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)-matrice.getValue(i,j));
			
		return a;	
	}
	
	// multiplication d'une matrice par une constante k
	public Matrice multiplyByK(double k)
	{
		Matrice a = this;
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)*k);
		
		return a;
	}
	
	
	// division d'une matrice par une constante k
	public Matrice divByK(double k)
	{
		Matrice a = new Matrice (getRows(),getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)/k);
		
		return a;
	}
	
	// le fameux toString() :)
	public String toString()
	{
		String out = "";
	
		for (int i=0; i<this.getRows(); i++)
		{
			for (int j=0; j<this.getColumns(); j++)
				out +=this.coeff[i][j]+"\t ";
				
				out+="\n";
		}
				
		return out;
	}
	
	
	// definit si deux matrices sont Ã©quivalentes
	public boolean equals(Matrice matrice)
	{
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j) != matrice.getValue(i,j))
						return false;
		
		return true;
	} 
	
	//----------------------------------------------//
	//	   		  		 METHODS IS...					   //
	//----------------------------------------------//	
	// dÃ©termine si la matrice est symetrique
	public boolean isSymetric()
	{
		if (this.getRows() == this.getColumns())
			return false;
			
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j) != this.getValue(j,i))
						return false;
						
		return true;				
	}
	
	// dÃ©termine si la matrice est triangulaire
	public boolean isTriangularMatrix()
	{
		for(int i=0; i<this.getRows(); i++)
			for (int j=1; j<this.getColumns(); j++)
				if (this.getValue(i,j) != 0)
						return false;		
		
		return true;
	}
	
	// dÃ©termine si la matrice est une matrice unitÃ©
	public boolean isUnitMatrix()
	{		
		return (this.equals(this.getMatriceIdentity()));
	}
	
	// dÃ©termine si la matrice est inversible
	public boolean isInversible()
	{
		return (this.getDeterminant() != 0);
	}
	
	// determine si la mtrice contient au moins une valeur 0
	public boolean isZero()
	{
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j) == 0)
						return true;			
						
		return false;
	}
	public double argmax() {
		double maxValue=Double.MIN_VALUE;
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j)>maxValue)
						maxValue=this.getValue(i,j);	
		return maxValue;
						
	}
	public Matrice multSca(Matrice b) {
		if(b.getRows()==getRows() && b.getColumns()==getColumns()) {
			Matrice res=new Matrice(getRows(),getColumns());
			for(int i=0;i<getRows();i++) {
				for(int j=0;j<getColumns();j++) {
					res.setValue(i,j,b.getValue(i,j)*getValue(i,j));
				}
			}
			return res;
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	public static void main (String[]args) {
		Matrice m= new Matrice (6,1);
		for(int i=0;i<1;i++) {
			for(int j=0;j<6;j++) {
				//Warning 0.5 peut donner des valeur négative peut pas fonct avec relu
				m.setValue(j,i,Math.random()-0.5);
				
			}
			}
	
		Matrice p= new Matrice (1,7);
		for(int i1=0;i1<7;i1++) {
			for(int j1=0;j1<1;j1++) {
				//Warning 0.5 peut donner des valeur négative peut pas fonct avec relu
				p.setValue(j1,i1,Math.random()-0.5);
				
			}
		}
		Matrice masque= new Matrice(new double[][] {{0,0,0,0,0,0}});
		System.out.println(masque);
		Matrice output= new Matrice(new double[][] {{5,0,0,0,0,0}});
		System.out.println(output);
		
		masque.setValue(output.getValue(output.argmax())[0],output.getValue(output.argmax())[1],1.0);
		System.out.println(masque);
		
	}

}