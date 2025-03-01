package lexer.parser;


import java.util.List;
import java.util.stream.Collectors;


public class Parser {
	
	
  public static DataCarte parse(Lexemes lexemes) {
    var carteData = new DataCarte();
    MAP(lexemes, carteData);
    return carteData;
  }
  ///////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////
  private static void MAP(Lexemes lexemes, DataCarte carteData) {
    MAP1(lexemes, carteData);
    MAP2(lexemes, carteData);
    MAP1(lexemes, carteData);
  }
  
  ///////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////
  private static void MAP1(Lexemes lexemes, DataCarte carteData) {
    var lexs = lexemes.getNext(3);//asert()
    String slex = convertContentToString(lexs);
    if (slex.equals("[element]")) {
      ELEMENT(lexemes, carteData);
      return;
    }
    lexemes.unget(lexs.size());
  }
  
  private static void MAP2(Lexemes lexemes, DataCarte carteData) {
    var lexs = lexemes.getNext(3);
    String slex = convertContentToString(lexs);
    if (slex.equals("[grid]")) {
      GRID(lexemes, carteData);
      return;
    }
    lexemes.unget(lexs.size());
  }
  
  ///////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////
  private static void GRID(Lexemes lexemes, DataCarte carteData) {  
    for (int i = 0; i < 3; i++) {
      var lexs = lexemes.getNext(2);
      String slex = convertContentToString(lexs);
      
      switch (slex) {
        case "size:"      -> {V1(lexemes, carteData);}
        case "encodings:" -> {V2(lexemes, carteData);}
        case "data:"      -> {V3(lexemes, carteData);}
        default -> {lexemes.unget(lexs.size()); return;}
      }
    }
  }
  
  private static void V1(Lexemes lexemes, DataCarte carteData) {
    var lexs = lexemes.getNext(5);
    if (lexs.size() < 5) {
      lexemes.unget(lexs.size() - 1);
      return;
    }
    var l0 = lexs.get(0);
    var l1 = lexs.get(1);
    var l2 = lexs.get(2);
    var l3 = lexs.get(3);
    var l4 = lexs.get(4);
    if (l0.token() != Token.LEFT_PARENS || l2.token() != Token.IDENTIFIER || l4.token() != Token.RIGHT_PARENS ) {
      lexemes.unget(lexs.size());
      return;
    }
    if (l1.token() == Token.NUMBER && l3.token() == Token.NUMBER) {
      carteData.addSize(l1.content(), l3.content());
      return;
    }
    lexemes.unget(lexs.size());
  }
  
  private static void V2(Lexemes lexemes, DataCarte carteData) {
    var lexs = lexemes.getNext(4);
    if (lexs.size() < 4) {
      lexemes.unget(lexs.size() - 1);
      return;
    }
    var l = new Lexeme[] {lexs.get(0), lexs.get(1), lexs.get(2), lexs.get(3)};
    if (l[1].token() != Token.LEFT_PARENS || l[3].token() != Token.RIGHT_PARENS) {
      lexemes.unget(lexs.size());
      return;
    }
    if (l[0].token() == Token.IDENTIFIER && l[2].token() == Token.IDENTIFIER) {
      carteData.addEncodings(l[0].content(), l[2].content());
      V2(lexemes, carteData);
      return;
    }
    lexemes.unget(lexs.size());
  }
  
  private static void V3(Lexemes lexemes, DataCarte carteData) {
    var lexs = lexemes.getNext(1);
    var l = lexs.get(0);

    if (lexs.size() < 1) {
      lexemes.unget(lexs.size() - 1);
      return;
    }
    
    if (l.token() == Token.QUOTE) {
      carteData.addData(l.content());
      return;
    }
    lexemes.unget(lexs.size());
  }
  ///////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////
  
  private static void ELEMENT(Lexemes lexemes, DataCarte carteData) {
    
    carteData.addElement();
    SUB_ELEMENT(lexemes, carteData);
    
    var lexs = lexemes.getNext(3);
    var slex = convertContentToString(lexs);
    
    if (slex.equals("[element]")) {
      ELEMENT(lexemes, carteData);
      return;
    }
    lexemes.unget(lexs.size());
  }
  
  private static void SUB_ELEMENT(Lexemes lexemes, DataCarte carteData) {
    var lexs = lexemes.getNext(2);
    
    if (lexs.size() < 2) {
      lexemes.unget(lexs.size() - 1);
      return;
    }
    
    var l0 = lexs.get(0);
    var l1 = lexs.get(1);
    
    if (l0.token() == Token.IDENTIFIER && l1.token() == Token.COLON) {
      String attribut = l0.content();
      carteData.addAttribut(attribut, VALEUR(lexemes, attribut));
      SUB_ELEMENT(lexemes, carteData);
      
      return;
    }
    lexemes.unget(lexs.size());
  }
  
  private static String VALEUR(Lexemes lexemes, String attribut) {
    switch(attribut) {
    case "position" -> {return COUPLE(lexemes);}
    case "zone"     -> {return ZONE(lexemes);}
    case "text"     -> {return TEXT(lexemes);}
    case "locked"   -> {return LOCKED(lexemes);} /* pas encore fini*/
    case "steal"    -> {return STEAL(lexemes);} /* pas encore fini*/
    case "trade"    -> {return TRADE(lexemes);} /* pas encore fini*/
    default         -> {return ID(lexemes);}
    }
  }
  
  private static String TRADE(Lexemes lexemes) {
	    var lexs = lexemes.getNext(5);
	    if (lexs.size() == 4 && lexs.get(1).content().equals("->")) {
	      return lexs.get(0).content() + "->" + lexs.get(2).content() + "::" + lexs.get(3).content(); 
	    }
	    if (lexs.size() == 3 && lexs.get(1).content().equals("->")) {
		      return lexs.get(0).content() + "->" + lexs.get(2).content(); 
	    }
	    if (lexs.size() < 3) {
	      lexemes.unget(lexs.size() - 1);
	      return "";
	    }
	    var l = new Lexeme[] {lexs.get(0), lexs.get(1), lexs.get(2), lexs.get(3), lexs.get(4)};
	    
	    if (l[0].token() == Token.IDENTIFIER && l[2].token() == Token.IDENTIFIER && l[3].token() == Token.IDENTIFIER ) {
	      if (l[1].token() == Token.ARROW && l[4].token() != Token.COMMA) {
	        lexemes.unget(1);
	        return l[0].content() + "->" + l[2].content() + "::" + l[3].content(); 
	      }
	      if (l[1].token() == Token.ARROW && l[4].token() == Token.COMMA) {
	        return l[0].content() + "->" + l[2].content() + "::" + l[3].content() + "|" + TRADE(lexemes); 
	      }
	    }
	    if (l[0].token() == Token.IDENTIFIER && l[2].token() == Token.IDENTIFIER ) {
	    	if (l[1].token() == Token.ARROW && l[3].token() != Token.COMMA) {
		        lexemes.unget(2);
		        return l[0].content() + "->" + l[2].content(); 
		      }
		      if (l[1].token() == Token.ARROW && l[3].token() == Token.COMMA) {
		    	lexemes.unget(1);
		        return l[0].content() + "->" + l[2].content() + "|" + TRADE(lexemes); 
		      }
	    }
	    return "";
	  }
  
  private static String STEAL(Lexemes lexemes) {
    var lexs = lexemes.getNext(2);
    if (lexs.size() == 1) {
      return lexs.get(0).content();
    }
    if (lexs.size() != 2) {
      return "";
    }
    var l1 = lexs.get(0);
    var l2 = lexs.get(1);
    if (l1.token() == Token.IDENTIFIER && l2.token() == Token.COMMA) {
      var s = l1.content() + "|" + STEAL(lexemes);
      return s;
    }
    if (l1.token() == Token.IDENTIFIER && l2.token() != Token.COMMA) {
      lexemes.unget(1);
      return l1.content();
    }
    return "";
  }
  
  private static String LOCKED(Lexemes lexemes) {
    var lexs = lexemes.getNext(2);
    if (lexs.size() != 2) {
      return "";
    }
    var l1 = lexs.get(0);
    var l2 = lexs.get(1);
    if (l1.token() == Token.IDENTIFIER && l2.token() == Token.IDENTIFIER) {
      return l1.content() + "|" + l2.content();
    }
    return "";
  }
  
  private static String TEXT(Lexemes lexemes) {
    var lexs = lexemes.getNext(1);
    var l = lexs.get(0);
    if (lexs.size() != 0 && l.token() == Token.QUOTE) {
      return lexs.getFirst().content();
    }
    return "";
  }
  
  private static String ID(Lexemes lexemes) {
    var lexs = lexemes.getNext(1);
    var l = lexs.getFirst();
    if (lexs.size() != 0) {
      if (l.token() == Token.IDENTIFIER || l.token() == Token.NUMBER) {
        return lexs.getFirst().content();
      }
    }
    return "";
  }
  
  private static String COUPLE(Lexemes lexemes) {
    var lexs = lexemes.getNext(5);
    
    if (lexs.size() < 5) {
      return "";
    }
    
    var l = new Lexeme[] {lexs.get(0), lexs.get(1), lexs.get(2), lexs.get(3), lexs.get(4)};
    
    if (l[0].token() != Token.LEFT_PARENS || l[2].token() != Token.COMMA || l[4].token() != Token.RIGHT_PARENS ) {
      return "";
    }
    
    if (l[1].token() == Token.NUMBER && l[3].token() == Token.NUMBER) {
      return l[1].content() + "|" + l[3].content();
    }
    return "";
  }
  
  private static String ZONE(Lexemes lexemes) {
    var lexs = lexemes.getNext(10);
    if (lexs.size() < 10) {
      return "";
    }
    String[] s = new String[10];
    Token[] t = new Token[10];
    for (int i = 0; i < 10; i++) {
      s[i] = lexs.get(i).content();
      t[i] = lexs.get(i).token();
    }
    if (t[0] != Token.LEFT_PARENS || t[2] != Token.COMMA || t[4] != Token.RIGHT_PARENS ) {
      return "";
    }
    if (t[5] != Token.LEFT_PARENS || t[7] != Token.IDENTIFIER || t[9] != Token.RIGHT_PARENS ) {
      return "";
    }
    if (t[1] == Token.NUMBER && t[3] == Token.NUMBER && t[6] == Token.NUMBER && t[8] == Token.NUMBER) {
      return s[1] + "|" + s[3] + "|" + s[6] + "|" + s[8];
    }
    return "";
  }
  
  ///////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////
  private static String convertContentToString(List<Lexeme> liste) {
    return liste.stream()
                .map(e -> e.content())
                .collect(Collectors.joining(""));
  }
}
