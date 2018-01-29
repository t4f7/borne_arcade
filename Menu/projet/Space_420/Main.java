// définition de la classe Main
class Main {

    // Main
    public static void main(String[] args) {

	// boucle pour pouvoir relancer le jeu
	while (true) {
	
	    // instanciation du menu
	    Menu menu = new Menu();
	    boolean finMenu = false;

	    // boucle du menu
	    while(finMenu == false) {

		finMenu = menu.sortieMenu();
	    }
	
	    // instanciation du jeu
	    Jeu jeu = new Jeu();
	    boolean finJeu = false;

	    // boucle de jeu
	    while(finJeu == false) {

		// limitation des FPS
		try {
		    Thread.sleep(10); // pause en ms
		}
		catch (Exception e) {
		}

		// On avance d'un pas dans le jeu
		finJeu = jeu.avancerUnPasDeTemps();
	    }
	}
    }
}
