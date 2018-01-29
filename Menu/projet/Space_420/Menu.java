// importation des bibliotheque MG2D
import MG2D.*;
import MG2D.geometrie.*;
import MG2D.audio.*;
import java.awt.Font;
import java.awt.FontMetrics;

// définition e la classe Menu
class Menu {

    // ******************* //
    // création des attributs

    // fenetre et clavier pour affichage et entrées
    private Fenetre fenetre;
    private Clavier clavier;

    // création de l'interface graphique
    private Texture background;
    private Texte pressEspace;
    private Texte titre;

    // sons du menu
    private Bruitage musiqueMenu;
    private int testMusique;

    // ****************** //
    // constructeur du menu
    public Menu() {

	// creation de la fenetre
	fenetre = new Fenetre("Space 420", 600, 600);
	//fenetre.setAffichageNbPrimitives(true);

	// creation de l'interface graphique
	background = new Texture("img/backgroundMenu.png", new Point(0, 0), 600, 600);
	titre = new Texte(Couleur.BLANC, new String("SPACE 420"), new Font("Impact", Font.TYPE1_FONT, 75), new Point(300, 500));
	pressEspace = new Texte(Couleur.BLANC, new String("APPUYEZ SUR ESPACE"), new Font("Impact", Font.TYPE1_FONT, 25), new Point(300, 150));

	// sons du menu
	musiqueMenu = new Bruitage("music/menu.mp3");
	//musiqueMenu.arret();

	// ajout des éléments graphique à la fenetre
	fenetre.ajouter(background);
	fenetre.ajouter(titre);
	fenetre.ajouter(pressEspace);

	// initialisation des attributs
	testMusique = 1;

	clavier = fenetre.getClavier();
	fenetre.rafraichir();
    }

    // ***************** //
    // méthode du menu
    public boolean sortieMenu() {

	// Musique
	if (testMusique == 1) {
	    musiqueMenu.lecture();
	    testMusique = 0;
	}

	if (clavier.getEspaceEnfoncee()) {
	    musiqueMenu.arret();
	    fenetre.effacer();
	    fenetre.rafraichir();
	    fenetre.dispose();
	    return true;
	}

	fenetre.rafraichir();

	return false;
    }
}
