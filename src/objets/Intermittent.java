package objets;

import java.util.Objects;

import objets.primitive.Couple;

public class Intermittent {
  /* CHAMPS */
  private final String skin;
  private final Couple position;
  // chronometre d'apparition
  private double chronometre;
  private boolean showSkin = false;
  
  /* CONSTRUCTEUR */
  public Intermittent(String skin, Couple position) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
    this.skin = skin;
    this.position = position.getCouple();
    this.chronometre = 0.0;
  }
  
  /* UPDATE */
  public void update() {
    this.updateChronometre();
    this.updateShowSkin();
  }
  
  private void updateChronometre() {
    if (this.chronometre > 0.5) {
      this.chronometre = 0.0;
    }
    this.chronometre += 1.0 / 60.0;
  }
  
  private void updateShowSkin() {
    if (this.chronometre > 0.5) {
      this.showSkin = !this.showSkin;
    }
  }
  
  /* getteur */
  public String getSkin() {return this.skin;}
  public Couple getPosition() {
    var x = this.position.getX();
    var y = this.position.getY();
    return new Couple(x, y);
  }
  public boolean getShowSkin() {return this.showSkin;}
}
