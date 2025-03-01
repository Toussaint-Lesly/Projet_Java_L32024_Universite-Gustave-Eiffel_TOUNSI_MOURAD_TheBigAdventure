package objets;

import java.util.Objects;

import objets.primitive.Flow;
import objets.primitive.Zone;

public class Biome {
  /* CHAMPS */
  private final String skin;
  private Zone zone;
  private final Flow flow;
  
  /* CONSTRUCTEUR */
  public Biome(String skin, Zone zone, Flow flow) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(zone);
    this.skin = skin;
    this.zone = zone.getZone();
    this.flow = flow;
  }
  
  /* getters */
  public String getSkin() {return this.skin;}
  public Zone getZone() {
    var X = this.zone.getZonePosition();
    var Y = this.zone.getZoneRegion();
    return new Zone(X, Y);
  }
  public Flow getFlow() {return this.flow;}
}
