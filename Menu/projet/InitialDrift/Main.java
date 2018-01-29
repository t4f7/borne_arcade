import MG2D.*;
import MG2D.geometrie.*;



class Main{


    //Attributs

		
    public static void main(String[] args){

	//attributs
	int i42 = 0;
	Jeu j = new Jeu();
	Clavier cla = new Clavier();
	    
	j.getFenetre().addKeyListener(cla);
	cla = j.getFenetre().getClavier();


	// Jeu 
	    
	while(true){
		
	    j.AvancerUnPasDeTemps();
	    j.getFenetre().rafraichir();
	    if(i42==15){
			j.GenererDecor();
			j.GenererEnnemi();
			i42=0;
	    }
	    i42++;
	    j.fin();
	}
	
    }			
}
