
public class Main {
	public static void main(String[] args){
		Graphique g = new Graphique();
		while(true){
			try{
				Thread.sleep(250);
			}catch(Exception e){};
			g.selectionJeu();
		}
	}
}
