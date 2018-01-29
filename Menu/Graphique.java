import java.awt.Font;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;


import MG2D.geometrie.*;
import MG2D.Clavier;
import MG2D.Fenetre;

public class Graphique {

	private final Fenetre f;
	private int TAILLEX;
	private int TAILLEY;
	private int i;
	private Clavier clavier;
	private BoiteSelection bs;
	private BoiteImage bi;
	private BoiteDescription bd;
	public static Bouton[] tableau;
	private Pointeur pointeur;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	Font font;
	Font fontSelect;



	public Graphique(){

		TAILLEX = 1280;
		TAILLEY = 1024;

		font = null;
		try{
			File in = new File("font2.ttf");
			font = font.createFont(Font.TRUETYPE_FONT, in);
			font = font.deriveFont(32.0f);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		f = new Fenetre("_Menu Borne D'arcade_");
		device.setFullScreenWindow(f);
	    f.setVisible(true);
		clavier = f.getClavier();
		tableau = new Bouton[5];
		Bouton.remplirBouton();
		pointeur = new Pointeur();
		bs = new BoiteSelection(new Rectangle(Couleur .GRIS_CLAIR, new Point(0, 0), new Point(640, TAILLEY), true), pointeur);
		f.ajouter(bs.getRectangle());
		System.out.println(tableau[pointeur.getValue()].getChemin());
		bi = new BoiteImage(new Rectangle(Couleur .GRIS_FONCE, new Point(640, 512), new Point(TAILLEX, TAILLEY), true), new String(tableau[pointeur.getValue()].getChemin()));
		f.ajouter(bi.getRectangle());
		bd = new BoiteDescription(new Rectangle(Couleur .GRIS, new Point(640, 0), new Point(TAILLEX, 512), true));
		bd.lireFichier(tableau[pointeur.getValue()].getChemin());
		//f.ajouter(bd.getRectangle());

		Texture fond = new Texture("fond.png", new Point(0, 0), TAILLEX, TAILLEY);
		f.ajouter(fond);
		//ajout apres fond car bug graphique sinon
		f.ajouter(bi.getImage());
		f.ajouter(bd.getMessage());
		f.ajouter(pointeur.getTriangleGauche());
		//f.ajouter(pointeur.getTriangleDroite());
		for(int i = 0 ; i < tableau.length ; i++){
	    f.ajouter(tableau[i].getTexture());
		}
		f.ajouter(pointeur.getRectangleCentre());
		for(int i = 0 ; i < tableau.length ; i++){
			f.ajouter(tableau[i].getTexte());
			tableau[i].getTexte().setPolice(font);
		}

	}

	public void selectionJeu(){
		
		bs.selection(clavier);

		bi.setImage(tableau[pointeur.getValue()].getChemin());

		fontSelect = null;
		try{
			File in = new File("font2.ttf");
			fontSelect = fontSelect.createFont(Font.TRUETYPE_FONT, in);
			fontSelect = fontSelect.deriveFont(48.0f);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if(!tableau[pointeur.getValue()].getTexte().getPolice().equals(fontSelect)){
			tableau[pointeur.getValue()].getTexte().setPolice(fontSelect);
		}
		
		
		
		//clignotement texte selectionne
		
		
			
		i=0;
		while(i<1){
			try {
				Thread.sleep(100);
				f.supprimer(tableau[pointeur.getValue()].getTexte());

				Thread.sleep(250);
				f.ajouter(tableau[pointeur.getValue()].getTexte());

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			i++;
		}
		

	//tpsTexte est initialisé a system.currentmillis au depart

		tableau[pointeur.getValue()].getTexte().setPolice(font);

		bd.setMessage(tableau[pointeur.getValue()].getNom());

		pointeur.lancerJeu(clavier);

		f.rafraichir();
	}
}
