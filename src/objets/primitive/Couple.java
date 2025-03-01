package objets.primitive;

public class Couple {
  private int x;
  private int y;
  
  public Couple(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void put(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public Couple getCouple() {
    return new Couple(this.x, this.y);
  }
  public int getX() { return this.x;}
  public int getY() { return this.y;}
  
  @Override
  public boolean equals(Object o) {
    return o instanceof Couple p
        && p.x == this.x
        && p.y == this.y;
  }
}
