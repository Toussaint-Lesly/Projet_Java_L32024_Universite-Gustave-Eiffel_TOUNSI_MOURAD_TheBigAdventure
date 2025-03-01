package objets;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import objets.primitive.Couple;
import objets.primitive.Direction;
import objets.primitive.Zone;

public class Enemy {
  /* CHAMPS */
  private final String name;      //: waldo
  private final String skin;      //: CRAB
  private Couple position;        //: (38, 24)
  private int health;             //: 10
  private Zone zone;              //: (38, 24) (10 x 8)
  private final String behavior;  //shy, stroll, agressive
  private final int damage;       //
  /* autres variable */
  private Direction moveDirection = Direction.NULL;
  private Random randomNumberDirection;
  private float chronometreStrollSpeed;
  
  private boolean isOutOfZone = false;
  private boolean inCollisionWithObstacle = false;
  
  private int maxHealth;
  private int damageCollection = 0;
  private boolean death = false;
  
  /* CONSTRUCTEUR */
  public Enemy(String name, String skin, Couple position, int health, Zone zone, String behavior, int damage) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
    Objects.requireNonNull(zone);
    Objects.requireNonNull(behavior);
    if (health < 0) {
      throw new IllegalArgumentException("health < 0");
    }
    if (damage < 0) {
      throw new IllegalArgumentException("damage < 0");
    }
    this.name = name;
    this.skin = skin;
    this.position = position.getCouple();
    this.health = health;
    this.zone = zone.getZone();
    this.behavior = behavior;
    this.damage = damage;
    /* seconde constructeur */
    this.randomNumberDirection = new Random();
    this.maxHealth = this.health;
  }
  
  /* FONCTIONS */
  // ajouter des damage a l'enemie
  public void addDamage(int damage) {
    this.damageCollection += damage;
  }
  
  /* UPDATE */
  public void update(List<Obstacle> liste_obstacle) {
    this.updateChronometres();
    this.updateComportement();
    this.checkOutOfZone();
    this.resolveOutOfZone();
    this.checkCollisionWithObstacle(liste_obstacle);
    this.resolveCollisionWithObstacle();
    this.updateHealth();
    this.checkDeath();
  }
  
  private void checkDeath() {
    if (this.health == 0) {
      this.death = true;
    }
  }
  
  private void updateHealth() {
    this.health -= this.damageCollection;
    this.damageCollection = 0;
    this.health = Math.max(0, this.health);
  }
  
  private void resolveCollisionWithObstacle() {
    if (this.inCollisionWithObstacle == false) {  // no collision
      return;                                     // exit (nothing to do)
    }
    this.inCollisionWithObstacle = false;
    int x = this.position.getX();
    int y = this.position.getY();
    switch (this.moveDirection) {
      case Direction.UP    : this.position.put(x, y + 1); break;
      case Direction.DOWN  : this.position.put(x, y - 1); break;
      case Direction.LEFT  : this.position.put(x + 1, y); break;
      case Direction.RIGHT : this.position.put(x - 1, y); break;
      default : break;
    }
  }
  
  private void checkCollisionWithObstacle(List<Obstacle> liste_obstacle) {
    for (var obstacle : liste_obstacle) {
      if (this.position.equals(obstacle.getPosition())) {
        this.inCollisionWithObstacle = true;
        return;
      }
    }
  }
  
  private void resolveOutOfZone() {
    if (this.isOutOfZone == false) {
      return;
    }
    int x = this.position.getX();
    int y = this.position.getY();
    int u = this.zone.getZonePosition().getX();
    int v = this.zone.getZonePosition().getY();
    int p = this.zone.getZoneRegion().getX();
    int q = this.zone.getZoneRegion().getY();
    x = Math.max(x, u);
    x = Math.min(x, u + p);
    y = Math.max(y, v);
    y = Math.min(y, v + q);
    this.position.put(x, y);
    this.isOutOfZone = false;
  }
  
  private void checkOutOfZone() {
    int x = this.position.getX();
    int y = this.position.getY();
    int u = this.zone.getZonePosition().getX();
    int v = this.zone.getZonePosition().getY();
    int p = this.zone.getZoneRegion().getX();
    int q = this.zone.getZoneRegion().getY();
    if (x >= u && x <= u + p && y >= v && y <= v + q) {
      this.isOutOfZone = false;
    } else {
      this.isOutOfZone = true;
    }
  }
  
  private void updateChronometres() {
    this.chronometreStrollSpeed += 1.0 / 10.0;
    this.chronometreStrollSpeed = this.chronometreStrollSpeed % 2;
  }
  
  private void updateComportement() {
    switch (this.behavior) {
      case "shy" -> { this.shy(); }
      case "stroll" -> { this.stroll(); }
      default -> { this.agressive(); }
    }
  }
  
  private void shy() {
    this.moveDirection = Direction.NULL;
  }
  
  private void stroll() {
    if (this.chronometreStrollSpeed < 1.0) {
      return;
    }
    this.chronometreStrollSpeed = 0.0f;
    switch (this.randomNumberDirection.nextInt(4)) {
      case 0 : this.moveDirection = Direction.LEFT; break;
      case 1 : this.moveDirection = Direction.RIGHT; break;
      case 2 : this.moveDirection = Direction.UP; break;
      case 3 : this.moveDirection = Direction.DOWN; break;
    }
    this.updateMovement();
  }
  
  private void agressive() {
    
  }
  
  private void updateMovement() {
    int x = this.position.getX();
    int y = this.position.getY();
    switch (this.moveDirection) {
      case Direction.UP    : this.position.put(x, y - 1); break;
      case Direction.DOWN  : this.position.put(x, y + 1); break;
      case Direction.LEFT  : this.position.put(x - 1, y); break;
      case Direction.RIGHT : this.position.put(x + 1, y); break;
      default : break;
    }
  }
  
  /* getteur */
  public String getName() {return this.name;}
  public String getSkin() {return this.skin;}
  public Couple getPosition() {
    var x = this.position.getX();
    var y = this.position.getY();
    return new Couple(x, y);
  }
  public int getHealth() {return this.health;}
  public Zone getZone() {
    var X = this.zone.getZonePosition();
    var Y = this.zone.getZoneRegion();
    return new Zone(X, Y);
  }
  public String getBehavior() {return this.behavior;}
  public int getDamage() {return this.damage;}
  public int getMaxHealth() {return this.maxHealth;}
  public boolean getDeath() {return this.death;}
}
