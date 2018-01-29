import java.util.ArrayList; //Importation classe ArrayList
import MG2D.*; // Importation MG2D
import MG2D.geometrie.*;

import java.awt.Font;
import java.awt.FontMetrics;


// Musique :  import d'Applet + URL
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;




class Jeu{ // Définition de la classe

	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    
    ///////////////////////////////////// Attributs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    Joueur player1;
    Ennemi army,police,tonneau_ennemi;
    ArrayList<Ennemi> tabEnnemis;
    ArrayList<Texture> tabDecor,tabHerbe;
    Fenetre fen;
 
    Point a; // utilisé pour la gen aléatoire d'ennemis
    Point b; // utilisé pour la gen aléatoire de décore

    Clavier clavier;
    int score;
    int random;
    int random_position;
    int dx,dy; // Utilisés pour la translation
    String chemin;
    int TAILLEX; // Largeur fenetre
    int TAILLEY; // Longueur fenetre
    int semaphoreC,semaphoreS; // pour scénario & commandes

    Ligne trait;   // Délimitations entre voies de circulation
    Rectangle colonne; // voie de circulation
    Rectangle noir;

    Texture gameover;
    Texte aff_gameover,aff_score;
    
    
    //musique
    URL url;
    AudioClip ac;


    //textures accueil
    Texture accueil,miguel,scenario,commandes;

    //textures decor
    Texture herbe,mursac,bcptono,tonottseul,soldat;


    ///////////////////////////////////// Constructeurs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    
    // Constructeur par défaut -- Initialisation Interface graphique & clavier
    public Jeu(){
	
	
	TAILLEX = 1280;
	TAILLEY = 1024;
	tabEnnemis= new ArrayList<Ennemi>();
	tabDecor = new ArrayList<Texture>();
	tabHerbe = new ArrayList<Texture>();
	score = 0;
	semaphoreS = 0;
	semaphoreC = 0;
	aff_score=new Texte(Couleur.BLEU,("Score : "+String.valueOf(score)),(new Font("Calibri", Font.BOLD, 24)),new Point(260,(TAILLEY-(TAILLEY-50))));
	fen = new Fenetre("Initial Drift");
	device.setFullScreenWindow(fen);
	fen.setVisible(true);
	fen.addKeyListener(clavier);
	clavier = fen.getClavier();

	
	//Mise en place de la texture de fond
	for(int g=TAILLEY;g>(-200);g--){

	    herbe = new Texture("decor/dirt.jpg",new Point(200,g),200,200); 
	    fen.ajouter(herbe);
	    noir = new Rectangle(Couleur.NOIR,new Point(0,0),200,1024,true);
	    fen.ajouter(noir);
	    tabHerbe.add(herbe);
	    noir = new Rectangle(Couleur.NOIR,new Point(TAILLEX-170,0),200,1024,true);
	    fen.ajouter(noir);
	    herbe = new Texture("decor/dirt.jpg",new Point(TAILLEX-370,g),200,200); 
	    fen.ajouter(herbe);
	    tabHerbe.add(herbe);
	    g-=199;
	}
	


	//Mise en place score
	fen.ajouter(aff_score);

	//Mise en place des colonnes en fonction de la taille de la fenetre.
	for(int i=0;i<=TAILLEX;i++){
	    if(i>350 && i<TAILLEX-400){
		colonne = new Rectangle(Couleur.GRIS_CLAIR,new Point(i,0),100,1024,true);
		fen.ajouter(colonne);
		
		//Mise en place des délimitations blanches
		
		trait = new Ligne(Couleur.BLANC,new Point(i+2,0), new Point(i+2,TAILLEY));
		fen.ajouter(trait);
		trait = new Ligne(Couleur.BLANC,new Point(i+1,0), new Point(i+1,TAILLEY));
		fen.ajouter(trait);
		trait = new Ligne(Couleur.BLANC,new Point(i,0), new Point(i,TAILLEY));
		fen.ajouter(trait);
		trait = new Ligne(Couleur.BLANC,new Point(i-1,0), new Point(i-1,TAILLEY));
		fen.ajouter(trait);
		
		i=i+100;
	    }
	}


	//Derniere délimitation blanche un peu relou
	trait = new Ligne(Couleur.BLANC,new Point(TAILLEX-320,0), new Point(TAILLEX-320,TAILLEY));
	fen.ajouter(trait);
	
	trait = new Ligne(Couleur.BLANC,new Point(TAILLEX-321,0), new Point(TAILLEX-321,TAILLEY));
	fen.ajouter(trait);
	
	trait = new Ligne(Couleur.BLANC,new Point(TAILLEX-322,0), new Point(TAILLEX-322,TAILLEY));
	fen.ajouter(trait);
	
	trait = new Ligne(Couleur.BLANC,new Point(TAILLEX-323,0), new Point(TAILLEX-323,TAILLEY));
	fen.ajouter(trait);
		
	//Mise en place du joueur
	player1 = new Joueur(new Point((TAILLEX/2),0));	
	player1.add(fen);
	


	//musique de fond ecran d'accueil


	url = Jeu.class.getResource("sons/pegi18.wav");

	ac = Applet.newAudioClip(url);
	ac.play();



	//Ecran de chargement
	miguel = new Texture("decor/miguel.jpg",new Point(0,0),TAILLEX,TAILLEY);
	fen.ajouter(miguel);

	try{
	    Thread.sleep(4000);
	}
	catch(Exception e){}
	ac.stop();
	url = Jeu.class.getResource("sons/Dejavu.wav");

	ac = Applet.newAudioClip(url);
	ac.play();
		
	    
	//Ecran d'accueil
	accueil = new Texture("decor/accueil.png",new Point(0,0),TAILLEX,TAILLEY);
	fen.ajouter(accueil);
    
	    
	while(clavier.getEspace() == false){

	    fen.rafraichir();

	    if(clavier.getA() == true){ // commandes
		semaphoreC++;
		if(semaphoreC<2){ // le semaphore sert à eviter 5 millions de créations de texture si la touche reste enfoncée
		    commandes = new Texture("decor/commandes.png",new Point(0,0),TAILLEX,TAILLEY);
		    fen.ajouter(commandes);
		}
	    }
	    if(clavier.getZ() == true){
		semaphoreS++;
		if(semaphoreS<2){ // Pareil ici
		    scenario = new Texture("decor/scenario.jpg",new Point(0,0),TAILLEX,TAILLEY);
		    fen.ajouter(scenario);
		}
	    }
	    if(clavier.getQ() == true){
		semaphoreS = 0;
		semaphoreC = 0;
		fen.supprimer(commandes);
		fen.supprimer(scenario);	    }
	
	}

	fen.supprimer(miguel); // :'(
	fen.supprimer(accueil);

	ac.stop();
	

	// musique de fond ( Initial D <3 )

	url = Jeu.class.getResource("sons/RunninInThe90.wav");
	ac = Applet.newAudioClip(url);
	ac.play();











    }

    //////////////////////////////// Accesseurs & Mutateurs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 

    //joueur
    public Joueur getJoueur(){
	return player1;
    }
    public void setJoueur(Joueur j){
	player1 = new Joueur(j);
    }


    //tableau dynamique d'ennemis
    public ArrayList<Ennemi> getTabEnnemis(){
	return tabEnnemis;	
    }
    public void setTabEnnemis(ArrayList<Ennemi> e){
	tabEnnemis = e;
    }
    
    //score & gameover
    public int getScore(){
	return score;
    }
    public void setScore(int s){
	score = s;
    }
    public Texture getGameover(){
	return gameover;
    }
    public void setGameover(Texture go){
	gameover = new Texture(go);
    }


    //fenetre
    public Fenetre getFenetre(){
	return fen;
    }
    /*    public void setFenetre(Fenetre f){
	  fen = new Fenetre(f);
	  }
    */    
    //Taille fenetre
    public int getTailleX(){
	return TAILLEX;
    }
    public int getTailleY(){
	return TAILLEY;
    }
    
    public void setTailleX(int tail){
        TAILLEX = tail;
    }

    public void setTailleY(int tail){
	TAILLEY = tail;
    }    


    //affichages
    public Texte getAff_score(){
	return aff_score;
    }
    public void setAff_score(Texte af){
	aff_score = new Texte(af);
    }

    public Texte getAff_gameover(){
	return aff_gameover;
    }

    public void setAff_gameover(Texte af){
	aff_gameover = new Texte(af);
    }

    
    
    ///////////////////////////////////////// Méthodes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 

    /////////////////////////////////////////////////
    public void AvancerUnPasDeTemps(){
	
	try{
	    Thread.sleep(40);
	}
	catch(Exception e){}
		
	//Déplacement Joueur
	if (( clavier.getDroite() == true )&(player1.getTextureJoueur().getB().getX() < TAILLEX-326))
	    {
		dx = (int)(score*0.1+15);
	        player1.getTextureJoueur().translater(dx,0);
	    }
	if (( clavier.getGauche( ) == true )&(player1.getTextureJoueur().getB().getX() > TAILLEX-870 ))
	    {
		dx = -(int)(score*0.1+15);
		player1.getTextureJoueur().translater(dx,0);
	    }
	if (( clavier.getHaut() == true )&(player1.getTextureJoueur().getA().getY() < TAILLEY-120 ))
	    {
		dy = (int)(score*0.1+15);
	        player1.getTextureJoueur().translater(0,dy);
	    }
	if (( clavier.getBas() == true )&(player1.getTextureJoueur().getA().getY() > 0 ))
	    {
		dy = - (int)(score*0.1+15);
	        player1.getTextureJoueur().translater(0,dy);
	    }     
	if(clavier.getA()){
		System.exit(1);
	}  
	//fen.rafraichir();
	


	

      	//Déplacement ennemis en fonction de leur vitesse
	for(int ind=0;ind<(tabEnnemis.size()-1);ind++){
	    tabEnnemis.get(ind).getTextureEnnemi().translater(0,(tabEnnemis.get(ind).getVitesse()*(-1)));
	    


	    // On evite que les ennemis se roulent l'un sur l'autre (celui de derrière prend la vitesse de celui de devant ) 
	    for(int ind2=0;ind2<(tabEnnemis.size()-1);ind2++){
			if(tabEnnemis.get(ind).intersection(tabEnnemis.get(ind2))){
				tabEnnemis.get(ind).setVitesse(tabEnnemis.get(ind2).getVitesse());
			}
	    }
	    
	    
	    //Suppression des ennemis qui sont sortis de la fenetre
	    if(tabEnnemis.get(ind).getTextureEnnemi().getB().getY() <= 0){  // Si l'ennemi sort de l'écran alors
			fen.supprimer(tabEnnemis.get(ind).getTextureEnnemi());    // On le supprime de la fenetre	
			tabEnnemis.remove(ind);                                 // On le supprime de l'array list
			score++;
			aff_score.setTexte(("Score :"+String.valueOf(score)));      //Gére l'affichage du score
	    }

	}

	//Déplacement du décor en fonction du 5+score
	for(int indd=0;indd<(tabDecor.size());indd++){
	    tabDecor.get(indd).translater(0,(-5+score*(-1)));
	    //Suppression décor sorti de la fenetre
	    if(tabDecor.get(indd).getB().getY() <=0){
			fen.supprimer(tabDecor.get(indd));
			tabDecor.remove(indd);
	    }
	   
	}  
	for(int ig=0;ig<(tabHerbe.size());ig++){
	    tabHerbe.get(ig).translater(0,(-5+score*(-1)));
	    if(tabHerbe.get(ig).getB().getY() <1){
		tabHerbe.get(ig).translater(0,TAILLEY+200);
	    }
	    
	}
    }
    



    
    //////////////////////////////////////////////////Génération aléatoire ennemis
    public void GenererEnnemi(){
	

	if(score<5){
	    random = 2;	//only police
	}
	else if(score<15){

	    random = (int)(Math.random()*(3-1))+1; // only police + jeep
	}
	else{
	    random = (int)(Math.random()*(4-1))+1; // police + jeep + tonneau
	}
	
	random_position = (int)(Math.random()*(7-1))+1; // Pour générer sa position dans la voie de circulation
	a = new Point(0,TAILLEY);
	
	switch (random_position){ // position
	    
	case 1:
	    a.setX(350);
	    break;

	case 2:
	    a.setX(451);
	    break;
	    
	case 3:
	    a.setX(552);
	    break;
	    
	case 4:
	    a.setX(653);
	    break;
	    
	case 5:
	    a.setX(754);
	    break;	
	    
	case 6:
		a.setX(855);
		break;
	}

	
	switch(random){ // type d'ennemi
	    
	case 1:
	    army = new Ennemi("img/jeep.png",a);
	    army.setVitesse((int)(10+score*0.1));
	    tabEnnemis.add(army);
	    fen.ajouter(army.getTextureEnnemi());

           
	    
	    break;
	    
	case 2:
	    if(tabEnnemis.size() < 6){ // pour éviter qu'il y ait trop de voitures
		police = new Ennemi("img/police.png",a);
		police.setVitesse((int)(15+score*0.2));
		tabEnnemis.add(police);
		fen.ajouter(police.getTextureEnnemi());
	    }	
	    break;
	    
	case 3:
	    tonneau_ennemi = new Ennemi("img/tonneau_ennemi.png",a);
	    tonneau_ennemi.setVitesse((int)(18+score*0.3));
	    tabEnnemis.add(tonneau_ennemi);
	    fen.ajouter(tonneau_ennemi.getTextureEnnemi());

	    break;
	    
	default:
	    break;
	    
	}//Switch

	
    }//GenererEnnemi()


    /////////////////////////////////////////////////////
    // fin
        
    public void fin(){
	for(int ind=0;ind<(tabEnnemis.size()-1);ind++){  // on check tout l'ArrayList qui contient les ennemis
	   
	    if(player1.intersection(tabEnnemis.get(ind)) == true) { // Si y'a un ennemi qui touche le joueur

		ac.stop();

		url = Jeu.class.getResource("sons/explosion.wav");
		ac = Applet.newAudioClip(url);
	  		
		tabEnnemis.get(ind).getTextureEnnemi().setImg("img/explosion.png");    // on fait pop des EXPLOSIONSSSSSSSSSSSS
		player1.getTextureJoueur().setImg("img/explosion.png");
		fen.rafraichir();
		ac.play();  // AVEC UN BRUIT D'EXPLOSION DIGNE DE ZEUS HIMSELF



		// petite pause de 3 secondes	
		try{
		    
		    Thread.sleep(3000);
		    tabEnnemis.clear();
		}
		catch(Exception e){}


		// si le joueur est nul
		if(score<50){
		    gameover = new Texture(Couleur.NOIR,"img/snifsnof.jpeg",new Point(0,0),TAILLEX,TAILLEY);
		    aff_score.setA(new Point((TAILLEX/2),(TAILLEY-150)));
		    aff_score.setTexte("Score (trés nul) total :  "+String.valueOf(score));

		    url = Jeu.class.getResource("sons/scorenul.wav");
		    ac = Applet.newAudioClip(url);
		}
		// sinon, s'il a un semblant de skill
		else{
		    gameover = new Texture(Couleur.NOIR,"img/scorebien.jpg",new Point(0,0),TAILLEX,TAILLEY);
		    aff_score.setA(new Point((TAILLEX/2),(TAILLEY-150)));
		    aff_score.setTexte("Score (impressionnant) total :  "+String.valueOf(score));
					
		    url = Jeu.class.getResource("sons/momgetthecamera.wav");
		    ac = Applet.newAudioClip(url);

		}
		
		ac.play();
		aff_gameover = new Texte(Couleur.ROUGE,("GAME OVER"),(new Font("Calibri", Font.BOLD, 50)),new Point((TAILLEX/2),(TAILLEY-100)));

		fen.ajouter(gameover);
		fen.ajouter(aff_score);
		fen.ajouter(aff_gameover);
		fen.rafraichir();

		//critère de fin du bled ( 2h46 de pause), t'as le temps de mater un ptit film OKLM
		try{
		    
		    Thread.sleep(6000);
		    tabEnnemis.clear();
		    System.exit(1);
		}
		catch(Exception e){}

	    }	    
	}
    }
    ////////////////////////////////////////////////////////////////// Génération aléatoire décor 
    public void GenererDecor(){


	random = (int)(Math.random()*(3-1))+1; // gauche ou droite
	
	b = new Point(0,TAILLEY);
	switch(random){
	    
	case 1:
	    b.setX(225); // GAUCHE
	    break;
	    
	case 2:
	    b.setX(TAILLEX-325);   // DROITE
	    
	    break;
	}//fin de switch

	random = (int)(Math.random()*(5-1))+1; // 4 possibilités de décor 

	switch(random){

	case 1: // 1 - Les tonneaux
	    bcptono = new Texture("decor/bcptono.png",new Point(b));
	    tabDecor.add(bcptono);
	    fen.ajouter(bcptono);
	    break;
	    
	case 2: // 2 - Le tonneau tout seul
	    tonottseul = new Texture("decor/Tonneau.png",new Point(b)); 
	    tabDecor.add(tonottseul);
	    fen.ajouter(tonottseul);
	    break;
	    
	case 3: // 3 - Le soldat
	    soldat = new Texture("decor/soldat.png",new Point(b));	    
	    tabDecor.add(soldat);
	    fen.ajouter(soldat);
	    break;
	    
	case 4:  // 4 - Le mur de sacs de sable
	    mursac = new Texture("decor/mursac.png",new Point(b));
	    tabDecor.add(mursac);
	    fen.ajouter(mursac);
	    break;
	    
	    
	}//fin de switch
	//fen.rafraichir();	


	
    }//genererdecor
    
}//fin de classe





    





