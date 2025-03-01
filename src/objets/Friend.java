package objets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import objets.primitive.Couple;
import objets.primitive.Skins;

public class Friend {
  /* CHAMPS */
  private final String name;  // possible null
  private final String skin;
  private Couple position;
  private final String text;
  
  // autres champs
  // indique si il peut parler
  private boolean inStateOfTalk = false;
  // l'inventaire de l'amis
  private final ArrayList<Item> inventaire;
  private boolean showInvetaire = false;
  
  /* CONSTRUCTEUR */
  public Friend(String name, String skin, Couple position, String text) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
    Objects.requireNonNull(text);
    
    this.name = name;
    this.skin = skin;
    this.position = position.getCouple();
    this.text = text.replace("\"", "");
    this.inventaire = new ArrayList<Item>();
    this.addItemToInvetory();
  }
  
  // add random item to the inventory of Friend
  private void addItemToInvetory() {
	  var random = new Random();
	  int min = 3;
	  int max = 10;
	  int randomInteger = random.nextInt((max - min) + 1) + min;
	  for (int i = 0; i < randomInteger; i++) {
		  var randomSkin = Skins.items.get(random.nextInt(Skins.items.size()));
		  var randomDamage = random.nextInt(20 - 10) + 10;
		  this.inventaire.add(new Item("NoN", randomSkin, new Couple(0, 0), randomDamage));
	  }
  }
  
  /* UPDATE */
  public void update(Player player) {
    this.checkIsInStateOfTalk(player);
    this.checkIsInStateToShowInventory(player);
  }
  
  private void checkIsInStateOfTalk(Player player) {
    if (player.getPositionHand().equals(this.position)) {
      this.inStateOfTalk = true;
    } else {
      this.inStateOfTalk = false;
    }
  }
  
  private void checkIsInStateToShowInventory(Player player) {
	if (player.getPositionHand().equals(this.position)) {
	  this.showInvetaire = true;
	} else {
	  this.showInvetaire = false;
	}
  }
  
  /* FONCTIONS */
  
  
  /* getteur */
  public String getName() {return this.name;}
  public String getSkin() {return this.skin;}
  public Couple getPosition() {
    var x = this.position.getX();
    var y = this.position.getY();
    return new Couple(x, y);
  }
  public String getText() {return this.text;}
  public boolean getInStateOfTalk() {return this.inStateOfTalk;}
  public List<Item> getInventaire() {return this.inventaire;}
  public boolean getShowInventaire() {return this.showInvetaire;}
  
}
