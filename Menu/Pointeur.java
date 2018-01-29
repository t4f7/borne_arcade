import java.io.IOException;

import MG2D.geometrie.Texture;
import MG2D.geometrie.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Triangle;
import MG2D.Clavier;

public class Pointeur {
	private int value;
	private Texture triangleGauche;
	private Triangle triangleDroite;
	private Texture rectangleCentre;

	public Pointeur(){
		this.triangleGauche = new Texture("star.png", new Point(30, 492), 40,40);
		this.triangleDroite = new Triangle(new Triangle(Couleur .ROUGE, new Point(550, 560), new Point(520, 510), new Point(550, 460), true));
		this.rectangleCentre = new Texture("select.png", new Point(80, 460), 440, 100);
		this.value = Graphique.tableau.length-1;
	}

	public void lancerJeu(Clavier clavier){
		if(clavier.getATape()){

			System.out.println(Graphique.tableau[getValue()].getChemin());
			try {
				Process process = Runtime.getRuntime().exec("./"+Graphique.tableau[getValue()].getNom()+".sh");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        System.out.println("le process sur "+Graphique.tableau[getValue()].getChemin()+" est bien lancer");
	}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Texture getTriangleGauche() {
		return triangleGauche;
	}

	public void setTriangleGauche(Texture triangleGauche) {
		this.triangleGauche = triangleGauche;
	}

	public Triangle getTriangleDroite() {
		return triangleDroite;
	}

	public void setTriangleDroite(Triangle triangleDroite) {
		this.triangleDroite = triangleDroite;
	}

	public Texture getRectangleCentre() {
		return rectangleCentre;
	}

	public void setRectangleCentre(Texture rectangleCentre) {
		this.rectangleCentre = rectangleCentre;
	}

}
