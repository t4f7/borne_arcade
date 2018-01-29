// importation de la bibliotheque MG2D
import MG2D.*;
import MG2D.geometrie.*;

// definition de la classe Laser
class Laser {

    // creation des attributs de la classe
    private Rectangle laser;

    // constructeur par défaut
    public Laser(Point a, String couleur) {

	if (couleur.equals("VERT"))
	    laser = new Rectangle(Couleur.VERT, a, new Point((a.getX() + 3), (a.getY() + 6)), true);

	if (couleur.equals("ROSE"))
	    laser = new Rectangle(Couleur.ROSE, a, new Point((a.getX() + 3), (a.getY() + 6)), true);
    }

    // constructeur par paramètres
    public Laser(Rectangle r) {
	laser = new Rectangle(r);
    }

    // constructeur par copie
    public Laser(Laser l) {
	laser = new Rectangle(l.getRectangle());
    }

    // accesseurs
    public Rectangle getRectangle() {
	return laser;
    }

    public void setRectangle(Rectangle r) {
	laser = r;
    }

    // méthode toString
    public String toString() {
	return "k";
    }

    // méthode equals
    public boolean equals(Laser l) {
	if ((laser.equals(l.getRectangle())))
	    return true;
	else
	    return false;
    }
}
