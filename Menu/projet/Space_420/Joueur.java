// importation de la bibliotheque MG2D
import MG2D.*;
import MG2D.geometrie.*;

// definition de la classe Joueur
class Joueur {

    // creation des attributs de la classe
    private Texture joueur;
    private int nbVie;

    // constructeur par défaut
    public Joueur() {
	joueur = new Texture("img/joueur1.png", new Point(275, 20), 50, 50);
	nbVie = 8;
    }

    // constructeur par paramètres
    public Joueur(Texture t, int v) {
	joueur = new Texture(t);
	nbVie = v;
    }

    // constructeur par copie
    public Joueur(Joueur j) {
	joueur = new Texture(j.getTexture());
	nbVie = j.getVie();
    }

    // accesseurs
    public Texture getTexture() {
	return joueur;
    }

    public int getVie() {
	return nbVie;
    }

    public void setTexture(Texture t) {
	joueur = t;
    }

    public void setVie(int v) {
	nbVie = v;
    }

    // méthode intersection
    public boolean intersection(Ennemi e) {
	
	boolean collision = true;

	if (e.getTexture().getA().getX() > joueur.getB().getX() ||
	    e.getTexture().getA().getY() > joueur.getB().getY() ||
	    e.getTexture().getB().getX() > joueur.getA().getX() ||
	    e.getTexture().getB().getY() > joueur.getA().getY())
	    collision = false;

	return collision;
    }

    // méthode toString
    public String toString() {
	return "k";
    }

    // méthode equals
    public boolean equals(Joueur j) {
	if ((joueur.equals(j.getTexture())) && (nbVie == j.getVie()))
	    return true;
	else
	    return false;
    }
}
