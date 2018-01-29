public class Main {
    



    public static void main ( String [] args ){
	//à régler en fonction de l'ordinateur utilisé
	int vitesse = 20;
	Pong p = new Pong();
	
	while ( true ) { 
	    try {
		Thread.sleep ( vitesse );
	    }		
	    catch ( Exception e ) {		
		System.out.println ( e );
	    }
	    
	    p.maj();
	}
	
    }//main




}//class Main
