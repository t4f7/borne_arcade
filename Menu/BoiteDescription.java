import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import MG2D.geometrie.Texture;	
import MG2D.geometrie.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texte;

public class BoiteDescription extends Boite{

	private Texte[] message;
	private boolean stop;
	private int nombreLigne;
	private Texture joystick;
	private Texture[] bouton;
	private Texte tJoystick;
	private Texte[] tBouton;
	private String[] texteBouton;
	
	BoiteDescription(Rectangle rectangle) {
		super(rectangle);
		bouton = new Texture[6];
		tBouton = new Texte[6];
		texteBouton = new String[7];
		
		//declaration des texture bouton + joystick
		this.joystick = new Texture("joystick.png", new Point(740, 120), 40,40);
		for(int i = 0 ; i < 3 ; i++){
			this.bouton[i] = new Texture("ibouton.png", new Point(890+130*i, 150), 40, 40);
		}
		for(int i = 3 ; i < 6 ; i++){
			this.bouton[i] = new Texture("ibouton.png", new Point(890+130*(i-3), 70), 40, 40);
		}
		//declaration des textes bouton + joystick
		this.tJoystick = new Texte(Couleur .NOIR, "...", new Font("Calibri", Font.TYPE1_FONT, 15), new Point(760, 100));
		for(int i = 0 ; i < 3 ; i++){
			this.tBouton[i] = new Texte(Couleur .NOIR, "...", new Font("Calibri", Font.TYPE1_FONT, 15), new Point(910+130*i, 140));
		}
		for(int i = 3 ; i < 6 ; i++){
			this.tBouton[i] = new Texte(Couleur .NOIR, "...", new Font("Calibri", Font.TYPE1_FONT, 15), new Point(910+130*(i-3), 60));
		}
		stop = false;
		message = new Texte[10];
		for(int i = 0 ; i < message.length ; i++){
			message[i] = new Texte(Couleur .NOIR, "", new Font("Calibri", Font.TYPE1_FONT, 20), new Point(950, 500));
			message[i].translater(0, -i*30);

		}
		nombreLigne = 0;
	}
	
	public void lireFichier(String path){
		System.out.println(path);
		String fichier =path+"/description.txt";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while (/*(ligne=br.readLine())!=null &&*/stop == false){
				ligne=br.readLine();
				System.out.println(ligne);
				if(ligne != null){
					//changer message
					
					message[nombreLigne].setTexte(ligne);
					setMessage(ligne, nombreLigne);
				}else{
					//changer message
					
					message[nombreLigne].setTexte("");
					setMessage("", nombreLigne);
				}
				nombreLigne++;
				if(nombreLigne >= 10){
					stop = true;
					nombreLigne = 0;
				}
			}
			stop = false;
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void lireBouton(String path){
		System.out.println(path);
		String fichier =path+"/bouton.txt";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			ligne = br.readLine();
			if(ligne == null){
				System.out.println("le fichier bouton est surment vide!");
			}else{
				texteBouton = ligne.split(":");
				//changer le texte des boutons
				settJoystick(texteBouton[0]);
				for(int i = 0 ; i < 7 ; i++){
					settBouton(texteBouton[i+1], i);
				}				
			}
		}catch(Exception e){System.out.println(e.toString());};
		
	}
	
	public Texte[] getMessage(){
		return message;
	}
	
	public void setMessage(String message, int a) {
		this.message[a].setTexte(message);	
	}
	
		public Texture[] getBouton(){
			return this.bouton;
	}
	
	public Texture getJoystick(){
		return this.joystick;
	}
	
	public Texte[] gettBouton(){
		return this.tBouton;
	}
	
	public Texte gettJoystick(){
		return this.tJoystick;
	}
	
	public void settJoystick(String s){
		this.tJoystick.setTexte(s);		
	}
	
	public void settBouton(String s, int a){
		this.tBouton[a].setTexte(s);		
	}
	
	/*public Texte getMessage() {
		return message;
	}
	*/
	

}
