package objets;

import java.util.Objects;

import objets.primitive.Couple;
import objets.primitive.Skins;

public class Item {
  /* CHAMPS */
  private final String name;    // possible null
  private final String skin;
  private Couple position;
  private final int damage;
  
  /* CONSTRUCTEUR */
  public Item(String name, String skin, Couple position, int damage) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
    if (damage < 0) {
      throw new IllegalArgumentException("damage < 0");
    }
    this.name = name;
    this.skin = skin;
    this.position = position.getCouple();
    this.damage = damage;
  }
  
  /* FONCTIONS */
  public void setPosition(int x, int y) {
    this.position.put(x, y);
  }
  
  public boolean isNutritif() {
    if (Skins.nutritifs.contains(this.skin)) {
      return true;
    }
    return false;
  }
  
  /* getters */
  public String getName() {return this.name;}
  public String getSkin() {return this.skin;}
  public Couple getPosition() {
    var x = this.position.getX();
    var y = this.position.getY();
    return new Couple(x, y);
  }
  public int getDamage() {return this.damage;}
}
