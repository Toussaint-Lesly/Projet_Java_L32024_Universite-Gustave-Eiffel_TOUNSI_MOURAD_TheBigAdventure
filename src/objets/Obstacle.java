package objets;

import java.util.Objects;

import objets.primitive.Couple;

public class Obstacle {
  /* CHAMPS */
  private final String name;  // optionnel
  private final String skin;
  private Couple position;
  private final String locked;
  private final String teleport;
  // champs utile pour les objets de type porte
  private boolean open = false;
  
  
	/* CONSTRUCTEUR */
  public Obstacle(String name, String skin, Couple position, String locked, String teleport) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
    Objects.requireNonNull(locked);
    Objects.requireNonNull(teleport);
    this.name = name;
    this.skin = skin;
    this.position = position.getCouple();
    this.locked = locked;
    this.teleport = teleport;
  }
  
  /* FONCTIONS */
  public void tryToOpen(String name, String skin) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(skin);
    String key = skin + "|" + name;
    if (key.equals(this.locked)) {
      this.open = true;
    }
  }
  
  public boolean checkTeleport(Player player) {
    if (this.open && player.getPosition().equals(this.position) && !this.teleport.equals("NoN") ) {
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
  public boolean isOpen() {return this.open;}
  public String getLocked() {return this.locked;}
  public String getTeleport() {return this.teleport;}
	
  
}
