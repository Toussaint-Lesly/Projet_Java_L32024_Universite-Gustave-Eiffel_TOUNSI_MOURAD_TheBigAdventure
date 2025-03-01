package objets.primitive;

import java.util.Objects;

public class Zone {
  private Couple position;
  private Couple region;
  
  public Zone(Couple A, Couple B) {
    Objects.requireNonNull(A);
    Objects.requireNonNull(B);
    this.position = A;
    this.region = B;
  }
  
  public Zone getZone() {
    var X = new Couple(this.position.getX(), this.position.getY());
    var Y = new Couple(this.region.getX(), this.region.getY());
    return new Zone(X, Y);
  }
  
  public Couple getZonePosition() {
    return this.position;
  }
  
  public Couple getZoneRegion() {
    return this.region;
  }
}
