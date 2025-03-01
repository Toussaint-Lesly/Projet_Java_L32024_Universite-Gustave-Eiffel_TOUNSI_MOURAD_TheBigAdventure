package lexer.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lexemes {
  /* CHAMPS */
  // la liste de tous les lexemes lu dans le fichier .map
	private final ArrayList<Lexeme> liste;
	// in dique la position du curseur de lecture
	private int pointeur;
	
	
	/* CONSTRUCTEUR */
	public Lexemes() {
		this.liste = new ArrayList<Lexeme>();
		this.pointeur = 0;
	}
	
	/* FONCTIONS */
	/* lire le fichier et mettre les lexemes dans une liste */
	public void getLexemesFrom(String file_path) throws IOException {
		Objects.requireNonNull(file_path, "file_path est null");
		
		var path = Path.of(file_path);
		var text = Files.readString(path);
		var lexer = new Lexer(text);
		Lexeme lexeme;
		
		while((lexeme = lexer.nextResult()) != null) {
      this.liste.add(lexeme);
		}
	}
	
	/* retourner une liste de n lexemes suivants */
	public List<Lexeme> getNext(int n) {
	  var lst = new ArrayList<Lexeme>();
	  
	  for (int i = 0; i < n; i++) {
	    if (this.pointeur + i >= this.liste.size()) {
	      break;
	    }
	    lst.add(this.liste.get(this.pointeur + i));
	  }
	  this.pointeur = Math.min(this.pointeur + n, this.liste.size() - 1);
	  return List.copyOf(lst);
	}
	
	/* decremonter le pointer n fois */
	public void unget(int n) {
	  this.pointeur = Math.max(this.pointeur - n, 0);
	}
	
	/* getteur */
	public List<Lexeme> getListeLexeme() {return List.copyOf(this.liste);}
	public int getPointeur() {return this.pointeur;}
	
	
	/* verifier si on est a la fin de la liste des lexemes */
	/*public boolean eof() {
	  if (this.pointeur == this.liste.size()) {
	    return true;
	  }
	  return false;
	}*/
	
	
	
}
