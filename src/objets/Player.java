package objets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import objets.primitive.Couple;
import objets.primitive.Direction;
  
public class Player {
  /* CHAMPS */
  private final String name;
  private final String skin;
  private Couple position; 
  private int health;
  private List<String> trade;
  // autre champs
  private Direction moveDirection = Direction.NULL;
  private Direction direction = Direction.LEFT;
  private double chronometre = 0.0;
  // collision
  private boolean inCollisionWithEnemy = false;
  private boolean inCollisionWithObstacle = false;
  private boolean inCollisionWithPorte = false;
  private boolean inCollision = false;
  // maximum health
  private int maxHealth;
  private int damageCollection;
  private int winHealth = 10;
  private boolean death = false;
  // quand on clic sur ESPACE on fait une action
  private boolean action = false;
  private Couple positionHand;
  // gestion de l'inventaire du joueur
  private ArrayList<Item> inventaire;
  private boolean showInvetaire = false;
  private int selecteur = 0;
  private Item selectedItem = null;
  // jeter un element
  private boolean dropItem = false;
  // afficher la liste d'echange
  private boolean showTrade = false;
  
  /* CONSTRUCTEUR */
  public Player(String name, String skin, Couple position, int health, String[] trade) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
    if (health < 0) {
      throw new IllegalArgumentException("health < 0");
    }
    
    this.name = name;
    this.skin = skin;
    this.position = position.getCouple();
    this.health = health;
    this.trade = List.<String>of(trade);
    // autres champs
    this.maxHealth = this.health;
    this.damageCollection = 0;
    this.inventaire = new ArrayList<Item>();
    this.positionHand = new Couple(0, 0);
  }
  
  /* UPDATE */
  public void update(Event event, Carte carte) {
    if (event != null) {
      this.updateMoveDirection(event);
      this.updateAction(event);
      this.updateShowInventaire(event);
      this.updateSelecteur(event);
      this.updateDrop(event);
    }
    if (!this.showInvetaire && !this.showTrade) {
      this.updateDirection();
      this.updatePosition();
      this.updatePositionHand();
      this.dropItem(carte);
    }
    this.checkCollision(carte);
    this.resolveCollision();
    this.updateChronometres();
    this.updateHealth();
    this.makeAction(carte);
  }
  
  private void dropItem(Carte carte) {
    if (!this.dropItem || this.selectedItem == null) {
      return;
    }
    this.dropItem = false;
    int x = this.positionHand.getX();
    int y = this.positionHand.getY();
    this.selectedItem.setPosition(x, y);
    carte.getListeItem().add(this.selectedItem);
    this.selectedItem = null;
  }
  
  private void updateDrop(Event event) {
    var action = event.getAction();
    var key = event.getKey();
    if (action == Action.KEY_PRESSED) {
      if (key == KeyboardKey.D) {
        this.dropItem = true;
      }
    }
  }
  
  private void updateSelecteur(Event event) {
    if (!this.showInvetaire) {
      return;
    }
    var action = event.getAction();
    var key = event.getKey();
    if (action == Action.KEY_PRESSED) {
      switch (key) {
        case KeyboardKey.UP    : this.selecteur -= 5; break;
        case KeyboardKey.DOWN  : this.selecteur += 5; break;
        case KeyboardKey.LEFT  : this.selecteur -= 1; break;
        case KeyboardKey.RIGHT : this.selecteur += 1; break;
        default : break;
      }
    }
    this.selecteur = Math.max(0, this.selecteur);
    this.selecteur = Math.min(this.selecteur, this.inventaire.size() - 1);
  }
  
  private void updateShowInventaire(Event event) {
    var action = event.getAction();
    var key = event.getKey();
    if (action == Action.KEY_PRESSED) {
      if (key == KeyboardKey.I) {
        this.showInvetaire = !this.showInvetaire;
        this.selecteur = 0;
      }
    }
  }
  
  private void makeAction(Carte carte) {
    if (this.action == false) {
      return;
    }
    this.action = false;
    this.putItemInInventaire(carte);
    this.selectItem();
    this.tryToHit(carte);
    this.tryToOpen(carte);
    this.tryToEat();
    this.tryMakeTrade();
  }
  
  private void tryMakeTrade() {
	  //this.showTrade = !this.showTrade;
	  //////////////////////////////////////
	  ///////////////////////////////////////
  }
  
  private void tryToEat() {
    if (this.selectedItem == null) {
      return;
    }
    if (this.selectedItem.isNutritif()) {
      this.health += this.winHealth;
      this.selectedItem = null;
    }
  }
  
  private void tryToOpen(Carte carte) {
    if (this.selectedItem == null) {
      return;
    }
    int x = this.positionHand.getX();
    int y = this.positionHand.getY();
    var porte = carte.getPorteInPosition(x, y);
    if (porte == null) {
      return;
    }
    String name = this.selectedItem.getName();
    String skin = this.selectedItem.getSkin();
    porte.tryToOpen(name, skin);
  }
  
  private void tryToHit(Carte carte) {
    if (this.selectedItem == null) {
      return;
    }
    if (!this.selectedItem.getSkin().equals("SWORD") && !this.selectedItem.getSkin().equals("STICK")) {
      return;
    }
    int x = this.positionHand.getX();
    int y = this.positionHand.getY();
    var enemy = carte.getEnemyInPosition(x, y);
    if (enemy == null) {
      return;
    }
    enemy.addDamage(this.selectedItem.getDamage());
  }
  
  private void selectItem() {
    if (!this.showInvetaire || this.selecteur < 0) {
      return;
    }
    if (this.selectedItem != null) {
      this.inventaire.add(this.selectedItem);
    }
    this.selectedItem = this.inventaire.get(this.selecteur);
    this.inventaire.remove(this.selecteur);
  }
  
  private void putItemInInventaire(Carte carte) {
    if (this.showInvetaire || this.selectedItem != null) {
      return;
    }
    int x = this.positionHand.getX();
    int y = this.positionHand.getY();
    Item item = carte.getItemInPosition(x, y);
    if (item != null) {
      this.inventaire.add(item);
    }
  }
  
  private void updateAction(Event event) {
    var action = event.getAction();
    var key = event.getKey();
    if (action == Action.KEY_PRESSED && this.action == false) {
      if (key == KeyboardKey.SPACE) {
        this.action = true;
      }
    }
  }
  
  private void updatePositionHand() {
    int i = 0;
    int j = 0;
    int x = this.position.getX();
    int y = this.position.getY();
    switch (this.direction) {
      case Direction.UP    : j = -1; break;
      case Direction.DOWN  : j = 1; break;
      case Direction.LEFT  : i = -1; break;
      case Direction.RIGHT : i = 1; break;
      default : break;
    }
    this.positionHand.put(x + i, y + j);
  }
  
  private void updateHealth() {
    this.health -= this.damageCollection;
    this.health = Math.max(0, this.health);
    this.damageCollection = 0;
    this.maxHealth = Math.max(this.health, this.maxHealth);
    if (this.health == 0) {
      this.death = true;
    }
  }
  
  private void resolveCollision() {
    if (this.inCollision == false) {  // no collision
      return;                         // exit (nothing to do)
    }
    int x = this.position.getX();
    int y = this.position.getY();
    switch (this.direction) {
      case Direction.UP    : this.position.put(x, y + 1); break;
      case Direction.DOWN  : this.position.put(x, y - 1); break;
      case Direction.LEFT  : this.position.put(x + 1, y); break;
      case Direction.RIGHT : this.position.put(x - 1, y); break;
      default : break;
    }
  }
  
  private void checkCollision(Carte carte) {
    this.checkCollisionWithObstacle(carte.getListeObstacle());
    this.checkCollisionWithEnemy(carte.getListeEnemy());
    this.checkCollisionWithPorte(carte.getListePorte());
    if (this.inCollisionWithEnemy || this.inCollisionWithObstacle || this.inCollisionWithPorte) {
      this.inCollision = true;
    } else {
      this.inCollision = false;
    }
  }
  
  private void checkCollisionWithEnemy(List<Enemy> liste) {
    for (var enemy : liste) {
      if (enemy.getPosition().equals(this.position)) {
        this.inCollisionWithEnemy = true;
        this.damageCollection += enemy.getDamage();
        return;
      }
    }
    this.inCollisionWithEnemy = false;
  }
  
  private void checkCollisionWithObstacle(List<Obstacle> liste) {
    for (var obs : liste) {
      if (obs.getPosition().equals(this.position)) {
        this.inCollisionWithObstacle = true;
        return;
      }
    }
    this.inCollisionWithObstacle = false;
  }
  
  private void checkCollisionWithPorte(List<Obstacle> liste) {
    for (var porte : liste) {
      if (porte.getPosition().equals(this.position) && !porte.isOpen()) {
        this.inCollisionWithPorte = true;
        return;
      }
    }
    this.inCollisionWithPorte = false;
  }
  
  private void updateChronometres() {
    if (this.moveDirection != Direction.NULL) {
      this.chronometre += 1.0/3.0;
    } else {
      this.chronometre = 0.0;
    }
  }
  
  private void updateDirection() {
    if (this.moveDirection != Direction.NULL) {
      this.direction = this.moveDirection;
    }
  }
  
  private void updatePosition() {
    if (this.chronometre < 1.0) {
      return;
    }
    this.chronometre = 0;
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
  
  private void updateMoveDirection(Event event) {
    var action = event.getAction();
    var key = event.getKey();
    if (action == Action.KEY_PRESSED && this.moveDirection == Direction.NULL) {
      switch (key) {
        case KeyboardKey.UP    : this.moveDirection = Direction.UP   ; break;
        case KeyboardKey.DOWN  : this.moveDirection = Direction.DOWN ; break;
        case KeyboardKey.LEFT  : this.moveDirection = Direction.LEFT ; break;
        case KeyboardKey.RIGHT : this.moveDirection = Direction.RIGHT; break;
        default : this.moveDirection = Direction.NULL; 
      }
    }
    if (action == Action.KEY_RELEASED) {
      if (key == KeyboardKey.UP || key == KeyboardKey.DOWN || key == KeyboardKey.LEFT ||key == KeyboardKey.RIGHT) {
        this.moveDirection = Direction.NULL;
      }
    }
  }
  
  /* FONCTIONS */
  public void putPosition(Couple psition) {
    this.position = position.getCouple();
  }
  
  
  
  /* getters */
  public String getName() {return this.name;}
  public String getSkin() {return this.skin;}
  public Couple getPosition() {
    var x = this.position.getX();
    var y = this.position.getY();
    return new Couple(x, y);
  }
  public int getHealth() {return this.health;}
  public boolean getDeath() {return this.death;}
  public Direction getDirection() {return this.direction;}
  public int getMaxHealth() {return this.maxHealth;}
  public List<Item> getInventaire() {return this.inventaire;}
  public boolean getShowInventaire() {return this.showInvetaire;}
  public int getSelecteur() {return this.selecteur;}
  public Couple getPositionHand() {
    var x = this.positionHand.getX();
    var y = this.positionHand.getY();
    return new Couple(x, y);
  }
  public Item getSelectedItem() {return this.selectedItem;}
  public List<String> getListeTrade() {return this.trade;}
}
