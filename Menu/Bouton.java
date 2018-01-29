import java.awt.Font;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;



import MG2D.geometrie.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;
import MG2D.geometrie.Texte;

public class Bouton {
	private Texte texte;
	private String chemin;
	private String nom;
	private Texture texture;
	private int numeroDeJeu;


	public Bouton(){
		this.texte = null;
		this.texture = null;
		this.chemin = null;
		this.nom = null;
	}

	public Bouton(Texte texte, Texture texture, String chemin, String nom){
		this.texte = texte;
		this.texture = texture;
		this.chemin = chemin;
		this.nom = nom;
	}

	public static void remplirBouton(){
		for(int i = 0 ; i < Graphique.tableau.length ; i++){
			Graphique.tableau[i] = new Bouton();
		}

		Path yourPath = FileSystems.getDefault().getPath("projet/");

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(yourPath)) {
			int i = Graphique.tableau.length - 1;
		  for (Path path : directoryStream) {
		    Graphique.tableau[i].setTexte(new Texte(Couleur .NOIR, path.getFileName().toString(), new Font("Calibri", Font.TYPE1_FONT, 30), new Point(310, 98 * (i+1)+20)));
		    Graphique.tableau[i].setTexture(new Texture("bouton.png", new Point(100, 98 * (i+1)-12), 400, 65));
		    Graphique.tableau[i].setChemin("projet/"+path.getFileName().toString());
				Graphique.tableau[i].setNom(path.getFileName().toString());
		    Graphique.tableau[i].setNumeroDeJeu(i);
		    i--;
		  }
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Texte getTexte() {
		return texte;
	}

	public void setTexte(Texte texte) {
		this.texte = texte;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getNumeroDeJeu() {
		return numeroDeJeu;
	}

	public void setNumeroDeJeu(int numeroDeJeu) {
		this.numeroDeJeu = numeroDeJeu;
	}
}
