package lexer.parser;


public enum Token {
  IDENTIFIER("[A-Za-z]+"),
  NUMBER("[0-9]+"),
  LEFT_PARENS("\\("),
  RIGHT_PARENS("\\)"),
  LEFT_BRACKET("\\["),
  RIGHT_BRACKET("\\]"),
  COMMA(","),
  COLON(":"),
  QUOTE("\"\"\"[^\"]+\"\"\""),
  ARROW("->"),
  ;

  private final String regex;

  Token(String regex) {
    this.regex = regex;
  }
  
  public String regex() {
    return this.regex;
  }
}