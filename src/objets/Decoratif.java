package objets;

import java.util.Objects;

import objets.primitive.Couple;

public class Decoratif {
  /* CHAMPS */
  private final String name;  // possible null
  private final String skin;
  private Couple position;
	
	/* CONSTRUCTEUR */
	public Decoratif(String name, String skin, Couple position) {
	  Objects.requireNonNull(name);
	  Objects.requireNonNull(skin);
	  Objects.requireNonNull(position);
	  this.name = name;
	  this.skin = skin;
	  this.position = position.getCouple();
	}
	
	/* getteur */
	public String getName() {return this.name;}
	public String getSkin() {return this.skin;}
	public Couple getPosition() {
	  var x = this.position.getX();
	  var y = this.position.getY();
	  return new Couple(x, y);
	}
	
	
}
