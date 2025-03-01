package lexer.parser;

import java.util.List;

public class GestionErreurs {
  public static void checkErrors(List<DataCarte> liste_carteData, List<Lexemes> liste_lexemes) {
    errorParser(liste_lexemes);
    /* 
     * il manque les de verifier les la partie grid
     * - size par rapport a data
     * - l'encodings (si ya plusieur lettre qui represente le meme skin)
     * - ...etc 
     * */
  }
  
  private static void errorParser(List<Lexemes> liste_lexemes) {
    for (var lexemes : liste_lexemes) {
      int P1 = lexemes.getPointeur();
      int P2 = lexemes.getListeLexeme().size();
      if (P2 - P1 - 2!= 0) {
        throw new IllegalStateException("ERROR PARSER : error in file(.map)");
      }
    }
  }
}
