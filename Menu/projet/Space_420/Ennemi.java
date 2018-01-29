// importation de la bibliotheque MG2D
import MG2D.*;
import MG2D.geometrie.*;

// definition de la classe Ennemi
class Ennemi {

    // creation des attributs de la classe
    private Texture ennemi;
    private int nbVie;
    private int numSprite;

    // constructeur par défaut
    public Ennemi(int choix) {
	if (choix == 1) {
	    ennemi = new Texture("img/ennemi1.png", new Point(0, 0), 50, 50);
	    numSprite = 1;
	    nbVie = 1;
	}
	if (choix == 2) {
	    ennemi = new Texture("img/ennemi2.png", new Point(0, 0), 50, 50);
	    numSprite = 2;
	    nbVie = 2;
	}
	if (choix == 3) {
	    ennemi = new Texture("img/ennemi3.png", new Point(0, 0), 50, 50);
	    numSprite = 3;
	    nbVie = 3;
	}
	if (choix == 4) {
	    ennemi = new Texture("img/boss.png", new Point(0, 0), 300, 300);
	    numSprite = 4;
	    nbVie = 250;
	}
    }

    // constructeur par paramètres
    public Ennemi(Texture t, int v) {
	ennemi = new Texture(t);
	nbVie = v;
    }

    // constructeur par copie
    public Ennemi(Ennemi e) {
	ennemi = new Texture (e.getTexture());
	nbVie = e.getVie();
    }

    // accesseurs
    public Texture getTexture() {
	return ennemi;
    }

    public int getVie() {
	return nbVie;
    }

    public int getSprite() {
	return numSprite;
    }

    public void setTexture(Texture t) {
	ennemi = t;
    }

    public void setVie(int v) {
	nbVie = v;
    }

    public void setSprite(int s) {
	numSprite = s;
    }

    // méthode toString
    public String toString() {
	return "k";
    }

    // méthode equals
    public boolean equals(Ennemi n) {
	if ((ennemi.equals(n.getTexture())) && (nbVie == n.getVie()) && (numSprite == n.getSprite()))
	    return true;
	else
	    return false;
    }
}
