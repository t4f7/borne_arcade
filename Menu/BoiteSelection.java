import java.awt.Font;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import MG2D.geometrie.Rectangle;
import MG2D.Clavier;
import MG2D.audio.*;
import java.io.File;

public class BoiteSelection extends Boite{
	Pointeur pointeur;
	Font font;

	public BoiteSelection(Rectangle rectangle, Pointeur pointeur) {
		super(rectangle);
		this.pointeur = pointeur;
	}

	public void selection(Clavier clavier){
		Bruitage selection = new Bruitage("sound/bip.mp3");
		font = null;
		try{
			File in = new File("font2.ttf");
			font = font.createFont(Font.TRUETYPE_FONT, in);
			font = font.deriveFont(26.0f);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(clavier.getHautTape() && pointeur.getValue() < Graphique.tableau.length - 1){
			selection.lecture();
			for(int i = 0 ; i < Graphique.tableau.length ; i++){
				Graphique.tableau[i].getTexte().translater(0, -98);
				Graphique.tableau[i].getTexture().translater(0, -98);
				Graphique.tableau[i].getTexte().setPolice(font);
			}
			pointeur.setValue(pointeur.getValue() + 1);
			
		}
		if(clavier.getBasTape() && pointeur.getValue() > 0){
			selection.lecture();
			for(int i = 0 ; i < Graphique.tableau.length ; i++){
				Graphique.tableau[i].getTexte().translater(0, 98);
				Graphique.tableau[i].getTexture().translater(0, 98);
				Graphique.tableau[i].getTexte().setPolice(font);
			}
			pointeur.setValue(pointeur.getValue() - 1);
			
		}
		if(clavier.getDTape()){
			System.exit(0);
		}
	}

	public Pointeur getPointeur() {
		return pointeur;
	}

	public void setPointeur(Pointeur pointeur) {
		this.pointeur = pointeur;
	}

}
