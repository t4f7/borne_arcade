// importation des bibliotheque MG2D
import MG2D.*;
import MG2D.geometrie.*;
import MG2D.audio.*;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;

// définition de la classe Jeu
class Jeu {

    // ******************* //
    // création des attributs

    // pour joueur
    private Joueur joueur;
    private int translationJoueur;
    private int score;

    // pour ennemis
    private Ennemi ennemi;
    private ArrayList <Ennemi> tabEnnemis;
    private ArrayList <Cercle> tabBoss;
    private ArrayList <Laser> tabLasersEnnemi;
    private int translationEnnemi;
    private int indexEnnemi;
    private int moduloTirsEnnemi;
    private int moduloBoss;
    private int moduloBouclier;
    private int compteurBouclier;
    private int ennemisTues;

    // pour lasers
    private Laser laser;
    private ArrayList <Laser> tabLasers;
    private int translationLaser;
    private int moduloLasers;
    private int indexLaser;
    private boolean intersection;
    private int zoneJoueur;
    private boolean bonusActif;
    private int compteurBonus;

    // fenetre et clavier pour affichage et entrées
    private Fenetre fenetre;
    private Clavier clavier;

    // primitives de gestion des évènements
    private int groupeAleatoire;
    private int tirAleatoire;
    private int compteurJeu;
    private int moduloEnnemis;
    private boolean boss;
    private boolean bossTest;
    private boolean bossMort;
    private boolean testBouclier;
    private boolean choixFin;
    
    // pour création et gestion de l'interface graphique
    private Texture background1;
    private Texture background2;
    private int translationBackground;
    private Texture coeurVie;
    private Texture explosion;
    private Texture coeur;
    private Texture bonus;
    private Cercle cercle;
    private Cercle bouclier;
    private ArrayList <Texture> tabExplosions;
    private ArrayList <Texture> tabCoeursVie;
    private ArrayList <Texture> tabBonus;
    private int coeurAleatoire;
    private int moduloCoeursVie;
    private int translationCoeur;
    private int moduloBonus;
    private int translationBonus;
    private int moduloExplosions;
    private Rectangle fondNoir;
    private Texte texteScore;
    private Texte texteGagne;
    private Texte textePerdu;
    private Texte texteRejouer;
    private Texte texteScoreFin;
    private Texte texteNbVie;
    private Rectangle fondRouge;
    private Rectangle fondRouge2;
    private Rectangle fondRouge3;

    // sons du jeu
    private Bruitage musiqueJeu;
    private Bruitage musiqueBoss;
    private Bruitage musiqueDefaite;
    private Bruitage musiqueVictoire;
    private Bruitage sonTir;
    private Bruitage sonBonus;
    private Bruitage sonExplosion;
    private int testMusique;
    private int musiqueJouee;

    // **************** //
    // constructeur du jeu
    public Jeu() {

	// creation de le fenetre
	fenetre = new Fenetre("Space 420", 600, 600);
	//fenetre.setAffichageNbPrimitives(true);
	//fenetre.setAffichageFPS(true);

	// creation du joueur
	joueur = new Joueur();

	// creation de l'interface graphique
	background1 = new Texture("img/background.png", new Point(0, 0), 600, 600);
	background2 = new Texture("img/background.png", new Point(0, 600), 600, 600);
	coeurVie = new Texture("img/coeur.png", new Point(525, 540), 50, 50);
	texteScore = new Texte(Couleur.BLANC, new String("Score : " + String.valueOf(score)), new Font("Calibri", Font.TYPE1_FONT, 20), new Point(75, 570));
	texteNbVie = new Texte(Couleur.NOIR, new String(String.valueOf(joueur.getVie())), new Font("Calibri", Font.TYPE1_FONT, 20), new Point(550, 565));
	fondRouge = new Rectangle(Couleur.ROUGE, new Point(0, 0), 600, 600, true);
	bouclier = new Cercle(Couleur.BLEU, new Point (300, 550), 210);

	// sons du jeu
	//musiqueJeu = new Son("music/game.mp3");
	//musiqueBoss = new Son("music/boss.mp3");

	// ajout des éléments graphique à la fenetre
	fenetre.ajouter(background1);
	fenetre.ajouter(background2);
	fenetre.ajouter(coeurVie);
	fenetre.ajouter(texteScore);
	fenetre.ajouter(texteNbVie);
			       
	// initialisation des attributs
	translationBackground = -1;
	translationJoueur = 3;
	translationEnnemi = 2;
	translationCoeur = 3;
	translationBonus = 4;
	testMusique = 1;

	tabEnnemis = new ArrayList <Ennemi> ();
	tabLasers = new ArrayList <Laser> ();
	tabLasersEnnemi = new ArrayList <Laser> ();
	tabExplosions = new ArrayList <Texture> ();
	tabCoeursVie = new ArrayList <Texture> ();
	tabBonus = new ArrayList <Texture> ();
	tabBoss = new ArrayList <Cercle> ();

	moduloEnnemis = 200;
	moduloExplosions = 15;
	moduloLasers = 10;
	moduloTirsEnnemi = 150;
	moduloCoeursVie = 1000;
	moduloBonus = 1200;
	moduloBoss = 250;
	moduloBouclier = 500;
	boss = false;
	bossTest = false;
	testBouclier = false;
	choixFin = false;
	bonusActif = false;

	compteurJeu = 0;
	ennemisTues = 0;
	
	clavier = fenetre.getClavier();
	fenetre.ajouter(joueur.getTexture());
	fenetre.rafraichir();
    }

    // *********************************** //
    // méthode pour gérer la mécanique du jeu
    public boolean avancerUnPasDeTemps() {

	compteurJeu += 1;

	fenetre.supprimer(fondRouge);
	
	// test sur le score
	if (ennemisTues >= 50 && bossTest == false) {
 
	    boss = true;
	    testMusique = 2;
	}

	// gestion de la musique de fond
	if (testMusique == 1) {
	    musiqueJeu = new Bruitage("music/game.mp3");
	    musiqueJeu.lecture();
	    testMusique = -1;
	    musiqueJouee = 1;
	}
	if (testMusique == 2) {
	    musiqueJeu.arret();
	    musiqueBoss = new Bruitage("music/boss.mp3");
	    musiqueBoss.lecture();
	    testMusique = -1;
	    musiqueJouee = 2;
	}

	// gestion du bouclier boss
	if (testBouclier == true)
	    compteurBouclier += 1;

	// gestion de la suppression des sprites d'explosion
	if ((compteurJeu % moduloExplosions) == 0) {

	    for (int i = tabExplosions.size() - 1; i >= 0; i--) {
		
		fenetre.supprimer(tabExplosions.get(i));
		tabExplosions.remove(i);
	    }
	}

      	// **************************************************************** COEUR COEUR COEUR COEUR COEUR COEUR
	// gestion de l'apparition aléatoire de coeurs de vie
	if ((compteurJeu % moduloCoeursVie) == 0) {

	    coeurAleatoire = (int)(Math.random() * 574);

	    coeur = new Texture("img/coeur.png", new Point(0, 650), 25, 25);
	    coeur.setA(new Point(coeurAleatoire, 650));

	    fenetre.ajouter(coeur);
	    tabCoeursVie.add(coeur);
	}

	// boucle pour parcourir l'arraylist de coeurs
	// translation des coeurs
	for (int i = tabCoeursVie.size() - 1; i >= 0; i--)
	    tabCoeursVie.get(i).translater(0, -translationCoeur);

	// boucle pour parcourir l'arraylist de coeurs
	// test pour supprimer les coeurs qui sortent de l'écran
	for (int i = tabCoeursVie.size() - 1; i >= 0; i--) {

	    if (tabCoeursVie.get(i).getA().getY() < -50) {

		fenetre.supprimer(tabCoeursVie.get(i));
		tabCoeursVie.remove(i);
	    }
	}

	// boucle pour parcourir l'arraylist de coeurs
	// test collision coeur/joueur
	for (int i = tabCoeursVie.size() - 1; i >= 0; i--) {

	    if ((tabCoeursVie.get(i).intersection(joueur.getTexture()) == true)) {

		// le joueur gagne une vie
		joueur.setVie(joueur.getVie() + 1);
		texteNbVie.setTexte(String.valueOf(joueur.getVie()));

		// on supprime le coeur de l'écran et de l'arraylist
		fenetre.supprimer(tabCoeursVie.get(i));
		tabCoeursVie.remove(i);
	    }
	}

	// **************************************************************** BONUS BONUS BONUS BONUS BONUS BONUS
	// gestion de l'apparition aléatoire de bonus
	if ((compteurJeu % moduloBonus) == 0) {

	    coeurAleatoire = (int)(Math.random() * 574);

	    bonus = new Texture("img/bonus.png", new Point(0, 650), 25, 25);
	    bonus.setA(new Point(coeurAleatoire, 650));

	    fenetre.ajouter(bonus);
	    tabBonus.add(bonus);
	}

	// boucle pour parcourir l'arraylist de bonus
	// translation des bonus
	for (int i = tabBonus.size() - 1; i >= 0; i--)
	    tabBonus.get(i).translater(0, -translationBonus);

	// boucle pour parcourir l'arraylist de bonus
	// test pour supprimer les bonus qui sortent de l'écran
	for (int i = tabBonus.size() - 1; i >= 0; i--) {

	    if (tabBonus.get(i).getA().getY() < -50) {

		fenetre.supprimer(tabBonus.get(i));
		tabBonus.remove(i);
	    }
	}

	// boucle pour parcourir l'arraylist de bonus
	// test collision bonus/joueur
	for (int i = tabBonus.size() - 1; i >= 0; i--) {

	    if ((tabBonus.get(i).intersection(joueur.getTexture()) == true)) {

		// son de bonus
		sonBonus = new Bruitage("music/powerup.mp3");
		sonBonus.lecture();

		// on supprime le bonus de l'écran et de l'arraylist
		fenetre.supprimer(tabBonus.get(i));
		tabBonus.remove(i);

		bonusActif = true;
		compteurBonus = 0;
		score += 50;
	    }
	}

	if (compteurBonus >= 50)
	    bonusActif = false;

	// **************************************************************** ENNEMI ENNEMI ENNEMI ENNEMI ENNEMI ENNEMI
	// Des ennemis doivent-ils apparaitre ?
	if ((compteurJeu % moduloEnnemis) == 0) {

	    groupeAleatoire = (int)(Math.random() * 3) + 1;

	    // Quel groupe d'ennemi doit apparaitre ?
	    if (groupeAleatoire == 1) {

		// diagonale gauche - droite
		for (int i = 1; i < 7; i++) {
		    
		    ennemi = new Ennemi(1);
		    ennemi.getTexture().setA(new Point(-150 + (i*100), 600 + (i*50)));

		    fenetre.ajouter(ennemi.getTexture());
		    tabEnnemis.add(ennemi);
		}
	    }
	    if (groupeAleatoire == 2) {

		// ligne horizontale
		for (int i = 1; i < 7; i++) {

		    ennemi = new Ennemi(2);
		    ennemi.getTexture().setA(new Point(-75 + (i*100), 650));

		    fenetre.ajouter(ennemi.getTexture());
		    tabEnnemis.add(ennemi);
		}	
	    }
	    if (groupeAleatoire == 3) {

		// diagonale droite - gauche
		for (int i = 1; i < 7; i++) {

		    ennemi = new Ennemi(3);
		    ennemi.getTexture().setA(new Point(675 - (i*100), 600 + (i*50)));

		    fenetre.ajouter(ennemi.getTexture());
		    tabEnnemis.add(ennemi);
		}
	    }
	}

	// apparition du boss de fin
	if (boss == true && bossTest == false) {

	    ennemi = new Ennemi(4);
	    ennemi.getTexture().setA(new Point(150, 600));

	    fenetre.ajouter(ennemi.getTexture());
	    tabEnnemis.add(ennemi);

	    bossTest = true;
	}

	// boucle pour parcourir l'arraylist d'ennemis
	// détection des collisions avec le joueur
	for (int i = tabEnnemis.size() - 1; i >= 0; i--) {

	    // collision ennemi / joueur
	    if ((tabEnnemis.get(i).getTexture().intersection(joueur.getTexture()) == true) && (tabEnnemis.get(i).getSprite() != 4)) {

		// ajout d'un sprite explosion
		explosion = new Texture("img/explosion.png", new Point(tabEnnemis.get(i).getTexture().getA().getX(), tabEnnemis.get(i).getTexture().getA().getY()), 50, 50);
		tabExplosions.add(explosion);
		fenetre.ajouter(explosion);

		// on supprime l'ennemi de l'écran et de l'ArrayList
		fenetre.supprimer(tabEnnemis.get(i).getTexture());
		tabEnnemis.remove(i);

		// on joue un son d'explosion
		sonExplosion = new Bruitage("music/explosion.mp3");
		sonExplosion.lecture();

		// le joueur perd une vie
		joueur.setVie(joueur.getVie() - 1);
		texteNbVie.setTexte(String.valueOf(joueur.getVie()));

		fenetre.ajouter(fondRouge);
	    }

	    // collision boss / joueur
	    if ((boss == true) && (tabEnnemis.get(i).getTexture().intersection(joueur.getTexture()) == true) && (tabEnnemis.get(i).getSprite() == 4)) {
		// le joueur perd plein de vie
		joueur.setVie(joueur.getVie() - 5);
		texteNbVie.setTexte(String.valueOf(joueur.getVie()));
	    }
	}

	// boucle pour parcourir l'arraylist d'ennemis
	// test pour supprimer les ennemis en dehors de l'écran
	for (int i = tabEnnemis.size() - 1; i >= 0; i--) {

	    if (tabEnnemis.get(i).getTexture().getA().getY() < -50) {

		fenetre.supprimer(tabEnnemis.get(i).getTexture());
		tabEnnemis.remove(i);
		score += 1;

		texteScore.setTexte(("Score : " + String.valueOf(score)));
	    }
	}

	// boucle pour parcourir l'arraylist d'ennemis
	// translation des ennemis
	for (int i = tabEnnemis.size() - 1; i >= 0; i--) {

	    if (tabEnnemis.get(i).getSprite() == 1)
		tabEnnemis.get(i).getTexture().translater(1, -translationEnnemi);
	    if (tabEnnemis.get(i).getSprite() == 2)
		tabEnnemis.get(i).getTexture().translater(0, -translationEnnemi);
	    if (tabEnnemis.get(i).getSprite() == 3)
		tabEnnemis.get(i).getTexture().translater(-1, -translationEnnemi);
	    if (tabEnnemis.get(i).getSprite() == 4 && tabEnnemis.get(i).getTexture().getA().getY() > 400)
		tabEnnemis.get(i).getTexture().translater(0, -translationEnnemi);
	}

	// boucle pour parcourir l'arraylist d'ennemis
	// tir des ennemis
	if ((compteurJeu % moduloTirsEnnemi) == 0) {
	    
	    for (int i = tabEnnemis.size() - 1; i >= 0; i--) {

		tirAleatoire = (int)(Math.random() * 5) + 1;

		if (tirAleatoire == 3) {

		    laser = new Laser(new Point(((tabEnnemis.get(i).getTexture().getA().getX() + tabEnnemis.get(i).getTexture().getB().getX()) / 2), tabEnnemis.get(i).getTexture().getB().getY()), "VERT");
		    tabLasersEnnemi.add(laser);
		    fenetre.ajouter(laser.getRectangle());
		}	    
	    }
	}

	// **************************************************************** BOSS BOSS BOSS BOSS BOSS BOSS
	// tir du boss
	if (boss == true && (compteurJeu % moduloBoss) == 0) {
	    
	    cercle = new Cercle(Couleur.VERT, new Point(300, 480), 15, true);
	    tabBoss.add(cercle);
	    fenetre.ajouter(cercle);

	    if (joueur.getTexture().getB().getX() <= 215)
		zoneJoueur = 1;
	    if ((joueur.getTexture().getA().getX() >= 215) && (joueur.getTexture().getB().getX() <= 385))
		zoneJoueur = 2;
	    if (joueur.getTexture().getA().getX() >= 385)
		zoneJoueur = 3;
	}

	// bouclier du boss
	if (boss == true && (compteurJeu % moduloBouclier) == 0) {
	    
	    fenetre.ajouter(bouclier);
	    compteurBouclier = 0;
	    testBouclier = true;
	}

	// arrêt bouclier du boss
	if (boss == true && compteurBouclier == 200) {

	    fenetre.supprimer(bouclier);
	    testBouclier = false;
	}

	// test collision bouclier / joueur
	if ((boss == true) && (joueur.getTexture().intersection(bouclier) == true)) {

	    // on joue un son d'explosion
	    sonExplosion = new Bruitage("music/explosion.mp3");
	    sonExplosion.lecture();

	    // le joueur perd plein de vie
	    joueur.setVie(joueur.getVie() - 2);
	    texteNbVie.setTexte(String.valueOf(joueur.getVie()));
	}

	// boucle pour parcourir l'arraylist de tirs du boss
	// translation des tirs du boss
	for (int i = tabBoss.size() - 1; i >= 0; i--) {

	    if (zoneJoueur == 1)
		tabBoss.get(i).translater(-1, -2);
	    if (zoneJoueur == 2)
		tabBoss.get(i).translater(0, -2);
	    if (zoneJoueur == 3)
		tabBoss.get(i).translater(1, -2);

	    if (compteurJeu % (moduloBoss / 75) == 0)
		tabBoss.get(i).setDiametre(tabBoss.get(i).getDiametre() + 2);
	}

	// boucle pour parcourir l'arraylist de tirs du boss
	// test de collision entre joueur et tirs du boss
	for (int i = tabBoss.size() - 1; i >= 0; i--) {

	    if (tabBoss.get(i).intersection(joueur.getTexture())) {

		// le joueur perd une vie
		joueur.setVie(joueur.getVie() - 3);
		texteNbVie.setTexte(String.valueOf(joueur.getVie()));

		// on supprime le tir boss de l'écran et de l'arraylist
		fenetre.supprimer(tabBoss.get(i));
		tabBoss.remove(i);

		fenetre.ajouter(fondRouge);
	    }
	}

	// boucle pour parcourir l'arraylist de tirs du boss
	// test pour supprimer les tirs en dehors de l'écran
        for (int i = tabBoss.size() - 1; i >= 0; i--) {

	    if ((tabBoss.get(i).getO().getY() + tabBoss.get(i).getRayon()) < 0) {

		// on supprime le tir du boss
		fenetre.supprimer(tabBoss.get(i));
		tabBoss.remove(i);
	    }									  
	}

	// boucles pour parcourir arraylist ennemis + arraylist lasers
	// test de collision entre ennemi et laser
	indexEnnemi = -1;
	indexLaser = -1;
	intersection = false;
	
	for (int i = tabEnnemis.size() - 1; i >= 0; i--) {

	    for (int j = tabLasers.size() - 1; j >= 0; j--) {

		if ((tabLasers.get(j).getRectangle().intersection(tabEnnemis.get(i).getTexture()) == true)) {

		    indexEnnemi = i;
		    indexLaser = j;
		    intersection = true;
		}
	    }
	}

	// s'il y a intersection entre ennemi et laser
	if (intersection == true) {

	    // l'ennemi perd 1 point de vie
	    tabEnnemis.get(indexEnnemi).setVie(tabEnnemis.get(indexEnnemi).getVie() - 1);

	    // si sa vie est à 0
	    if (tabEnnemis.get(indexEnnemi).getVie() == 0) {

		// ajout d'un sprite explosion
		if (tabEnnemis.get(indexEnnemi).getSprite() == 4) {
		    
		    explosion = new Texture("img/explosion.png", new Point(tabEnnemis.get(indexEnnemi).getTexture().getA().getX(), tabEnnemis.get(indexEnnemi).getTexture().getA().getY()), 300, 300);
		    bossMort = true;
		}

		else {
		    explosion = new Texture("img/explosion.png", new Point(tabEnnemis.get(indexEnnemi).getTexture().getA().getX(), tabEnnemis.get(indexEnnemi).getTexture().getA().getY()), 50, 50);
		
		    tabExplosions.add(explosion);
		    fenetre.ajouter(explosion);

		    // on supprime l'ennemi de l'écran et de l'ArrayList
		    fenetre.supprimer(tabEnnemis.get(indexEnnemi).getTexture());
		    tabEnnemis.remove(indexEnnemi);

		    // on joue un son d'explosion
		    sonExplosion = new Bruitage("music/explosion.mp3");
		    sonExplosion.lecture();

		    // le joueur gagne des points
		    ennemisTues += 1;
		    score += 3;
		    texteScore.setTexte(("Score : " + String.valueOf(score)));
		}
	    }
	    
	    // on supprime le laser de l'écran et de l'arraylist
	    fenetre.supprimer(tabLasers.get(indexLaser).getRectangle());
	    tabLasers.remove(indexLaser);   
	}
	// fin instructions après test collision ennemi/laser

	// **************************************************************** LASER LASER LASER LASER LASER LASER
	// boucle pour parcourir l'arraylist de lasers ennemi
	// test de collision entre joueur/lasers ennemi
	for (int i = tabLasersEnnemi.size() - 1; i >= 0; i--) {

	    if (tabLasersEnnemi.get(i).getRectangle().intersection(joueur.getTexture())) {

		// le joueur perd une vie
		joueur.setVie(joueur.getVie() - 1);
		texteNbVie.setTexte(String.valueOf(joueur.getVie()));

		// on supprime le laser ennemi de l'écran et de l'arraylist
		fenetre.supprimer(tabLasersEnnemi.get(i).getRectangle());
		tabLasersEnnemi.remove(i);

		fenetre.ajouter(fondRouge);
	    }
	}

	// boucle pour parcourir l'arraylist de lasers
	// test pour supprimer les lasers en dehors de l'écran
	for (int i = tabLasers.size() - 1; i >= 0; i--) {

	    if (tabLasers.get(i).getRectangle().getA().getY() > 600) {

		fenetre.supprimer(tabLasers.get(i).getRectangle());
		tabLasers.remove(i);
	    }
	}

	// boucle pour parcourir l'arraylist de lasers ennemi
	// test pour supprimer les lasers ennemis en dehors de l'acran
	for (int i = tabLasersEnnemi.size() - 1; i >= 0; i--) {

	    if (tabLasersEnnemi.get(i).getRectangle().getB().getY() < 0) {

		fenetre.supprimer(tabLasersEnnemi.get(i).getRectangle());
		tabLasersEnnemi.remove(i);
	    }
	}

	// boucle pour parcourir l'arraylist de lasers
	// translation des lasers
	for (int i = tabLasers.size() - 1; i >= 0; i--)
	    tabLasers.get(i).getRectangle().translater(0, 3);

	// boucle pour parcourir l'arraylist de lasers ennemi
	// translation des lasers ennemi
	for (int i = tabLasersEnnemi.size() - 1; i >= 0; i--)
	    tabLasersEnnemi.get(i).getRectangle().translater(0, -3);

	// boucle pour parcourir l'arraylist de lasers joueur
	// test intersection avec bouclier du boss
	if (testBouclier == true) {
	    
	    for (int i = tabLasers.size() - 1; i >= 0; i--) {

		if (tabLasers.get(i).getRectangle().intersection(bouclier)) {

		    fenetre.supprimer(tabLasers.get(i).getRectangle());
		    tabLasers.remove(i);
		}
	    }
	}
	    
	// translation perpétuelle de l'image de fond
	background1.translater(0, translationBackground);
	background2.translater(0, translationBackground);

	if (background1.getB().getY() <= 0)
	    background1.translater(0, 1200);
	if (background2.getB().getY() <= 0)
	    background2.translater(0, 1200);

	// **************************************************************** CLAVIER CLAVIER CLAVIER CLAVIER CLAVIER CLAVIER
	// tests entrées clavier // déplacement du joueur
	// changement du sprite avec le déplacement
	if (clavier.getGaucheEnfoncee() && joueur.getTexture().getA().getX() > 0) {
	    joueur.getTexture().translater(-translationJoueur - 1, 0);
	    joueur.getTexture().setImg("img/joueur1_g.png");
	    joueur.getTexture().setLargeur(50);
	    joueur.getTexture().setHauteur(50);
	}
	if (clavier.getDroiteEnfoncee() && joueur.getTexture().getB().getX() < 600) {
	    joueur.getTexture().translater(translationJoueur + 1, 0);
	    joueur.getTexture().setImg("img/joueur1_d.png");
	    joueur.getTexture().setLargeur(50);
	    joueur.getTexture().setHauteur(50);
	}
	if (clavier.getBasEnfoncee() && joueur.getTexture().getA().getY() > 0) {
	    joueur.getTexture().translater(0, -translationJoueur);
	    joueur.getTexture().setImg("img/joueur1.png");
	    joueur.getTexture().setLargeur(50);
	    joueur.getTexture().setHauteur(50);
	}
	if (clavier.getHautEnfoncee() && joueur.getTexture().getB().getY() < 600) {
	    joueur.getTexture().translater(0, translationJoueur);
	    joueur.getTexture().setImg("img/joueur1.png");
	    joueur.getTexture().setLargeur(50);
	    joueur.getTexture().setHauteur(50);
	}

	// tests entrées clavier // tir du vaisseau joueur
	if (clavier.getEspaceEnfoncee() && ((compteurJeu % moduloLasers) == 0)) {

	    // on joue un son de tir de laser pew pew pew pew pew pew pew pew pew pew
	    sonTir = new Bruitage("music/pew.mp3");
	    sonTir.lecture();
	    
	    // ajout d'un laser à l'écran et dans l'ArrayList
	    laser = new Laser(new Point((((joueur.getTexture().getA().getX() + joueur.getTexture().getB().getX()) / 2) - 2), joueur.getTexture().getB().getY()), "ROSE");
	    tabLasers.add(laser);
	    fenetre.ajouter(laser.getRectangle());

	    if ((bonusActif == true) && (compteurBonus <= 50)) {

		laser = new Laser(new Point((joueur.getTexture().getA().getX()), joueur.getTexture().getA().getY() + 20), "ROSE");
		tabLasers.add(laser);
		fenetre.ajouter(laser.getRectangle());

		laser = new Laser(new Point((joueur.getTexture().getB().getX()) - 4, joueur.getTexture().getB().getY() - 30), "ROSE");
		tabLasers.add(laser);
		fenetre.ajouter(laser.getRectangle());

		compteurBonus += 1;
	    }
	}

	// **************************************************************** VICTOIRE DEFAITE VICTOIRE DEFAITE VICTOIRE DEFAITE
	// si le joueur n'a plus de vie
	if (joueur.getVie() <= 0) {

	    fenetre.supprimer(fondRouge);
	    
	    // on arrête la musique en cours
	    if (musiqueJouee == 1)
		musiqueJeu.arret();
	    if (musiqueJouee == 2)
		musiqueBoss.arret();

	    musiqueDefaite = new Bruitage("music/defaite.mp3");

	    musiqueDefaite.lecture();

	    // on créé les textes
	    fondNoir = new Rectangle(Couleur.BLANC, new Point(100, 100), 400, 400, true);
	    textePerdu = new Texte(Couleur.NOIR, new String("Défaite"), new Font("Calibri", Font.TYPE1_FONT, 45), new Point(300, 450));
	    texteRejouer = new Texte(Couleur.NOIR, new String("Z pour rejouer"), new Font("Calibri", Font.TYPE1_FONT, 20), new Point(300,270));
	    texteScoreFin = new Texte(Couleur.NOIR, new String("Score final : " + String.valueOf(score)), new Font("Calibri", Font.TYPE1_FONT, 30), new Point(300, 385));

	    // on ajoute les textes à l'écran
	    fenetre.ajouter(fondNoir);
	    fenetre.ajouter(textePerdu);
	    fenetre.ajouter(texteScoreFin);
	    fenetre.ajouter(texteRejouer);

	    // le jeu s'arrête et attend un choix
	    while (true) {

		if (clavier.getZEnfoncee()) {

		    // on efface et on ferme la fenetre
		    fenetre.effacer();
		    fenetre.rafraichir();
		    fenetre.dispose();
		    musiqueDefaite.arret();

		    // on retourne true arret du jeu
		    return true;
		}

		fenetre.rafraichir();
	    }
	}

	// Si le boss est mort
	if (bossMort == true) {

	     // on arrête la musique en cours
	    if (musiqueJouee == 1)
		musiqueJeu.arret();
	    if (musiqueJouee == 2)
		musiqueBoss.arret();

	    musiqueVictoire = new Bruitage("music/victoire.mp3");

	    musiqueVictoire.lecture();

	    // on créé les textes
	    fondNoir = new Rectangle(Couleur.BLANC, new Point(100, 100), 400, 400, true);
	    texteGagne = new Texte(Couleur.NOIR, new String("Victoire"), new Font("Calibri", Font.TYPE1_FONT, 45), new Point(300, 450));
	    texteRejouer = new Texte(Couleur.NOIR, new String("Z pour rejouer"), new Font("Calibri", Font.TYPE1_FONT, 20), new Point(300,270));
	    texteScoreFin = new Texte(Couleur.NOIR, new String("Score final : " + String.valueOf(score)), new Font("Calibri", Font.TYPE1_FONT, 30), new Point(300, 385));

	    // on ajoute les textes à l'écran
	    fenetre.ajouter(fondNoir);
	    fenetre.ajouter(texteGagne);
	    fenetre.ajouter(texteScoreFin);
	    fenetre.ajouter(texteRejouer);

	    // le jeu s'arrête et attend un choix
	    while (true) {

		if (clavier.getZEnfoncee()) {

		    // on efface et on ferme la fenetre
		    fenetre.effacer();
		    fenetre.rafraichir();
		    fenetre.dispose();
		    musiqueVictoire.arret();

		    // on retourne true arret du jeu
		    return true;
		}

		fenetre.rafraichir();
	    }
	}
	  
	// on rafraichit la fenetre car elle a chaud
	fenetre.rafraichir();

	// on retourne 0 le jeu continu
	return false;
    }
}
