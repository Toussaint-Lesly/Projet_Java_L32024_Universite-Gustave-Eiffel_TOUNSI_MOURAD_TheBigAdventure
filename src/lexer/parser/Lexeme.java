package lexer.parser;

import java.util.Objects;

public record Lexeme(Token token, String content) {
  public Lexeme {
    Objects.requireNonNull(token);
    Objects.requireNonNull(content);
  }
}