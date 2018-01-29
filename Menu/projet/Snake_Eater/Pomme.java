import MG2D.geometrie.Carre;
import MG2D.geometrie.Point;
import MG2D.geometrie.Couleur;


// crée une pomme
public class Pomme {
	
// Attributs //
	
	private Carre c;
	private boolean etat;
	
// Constructeur //
	
	public Pomme ( Point a ) {
		
		c = new Carre ( Couleur.VERT, a, 10, true );
		etat = false;
	}

// Accesseurs //
	
	// Getter //
	
	public Carre getC () {
		
		return c;
	}
	
	public boolean getEtat () {
		
		return etat;
	}
	
	// Setter //
	
	public void setEtat ( boolean etat ) {
		
		this.etat = etat;
	}
}
