package menu.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.parser.DataCarte;
import lexer.parser.GestionErreurs;
import lexer.parser.Lexemes;
import lexer.parser.Parser;
import objets.Carte;


public class Main {
	public static void main(String[] args) throws IOException {
		
		/* initialisation */
		List<Carte> liste_carte = new ArrayList<Carte>();
		List<Lexemes> liste_lexemes = new ArrayList<Lexemes>();
		List<DataCarte> liste_carteData = new ArrayList<DataCarte>();
		List<String> liste_path = List.<String>of("./maps/demo.map", "./maps/adventure.map");
		List<String> liste_carteName = List.<String>of("demo", "adventure");
		
		// charger les image Map<String, Image>
		// associe chaque skin une image
		var mapSkinToImage = LoadImage.loadImages("./data/images/png");
		
		// creer la liste des carte
		for (var path : liste_path) {
		  liste_lexemes.add(new Lexemes());
		  liste_lexemes.getLast().getLexemesFrom(path);
		}
		
		for (var lexemes : liste_lexemes) {
      liste_carteData.add (Parser.parse(lexemes));
      liste_carteData.getLast().addDefaultValue();
    }
    
    for (int i = 0; i < liste_carteData.size(); i++) {
      var carteData = liste_carteData.get(i);
      var carteName = liste_carteName.get(i);
      liste_carte.add(new Carte(carteData, carteName));
    }
    
    //Gestion des erreurs
    GestionErreurs.checkErrors(liste_carteData, liste_lexemes);
    
    // lancer le Jeux
    Game.game(liste_carte, mapSkinToImage);
	}
}
