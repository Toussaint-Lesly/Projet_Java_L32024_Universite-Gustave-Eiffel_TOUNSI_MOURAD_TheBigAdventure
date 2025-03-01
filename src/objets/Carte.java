package objets;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lexer.parser.DataCarte;
//import lexer.parser.data.DataElement;
//import lexer.parser.data.DataGrille;
import objets.primitive.Couple;
import objets.primitive.Flow;
import objets.primitive.Skins;
import objets.primitive.Zone;

public class Carte {
  /* CHAMPS */
  private final Couple size;
  private final HashMap<String, String> encodings;
  private final String[][] grille;
  
  private final ArrayList<Decoratif> liste_decore;
  private final ArrayList<Obstacle> liste_obstacle;
  private final ArrayList<Obstacle> liste_porte; 		// lister les porte existant de la carte
  private final ArrayList<Item> liste_item;
  private final ArrayList<Enemy> liste_enemy;
  private final ArrayList<Friend> liste_friend;
  private final ArrayList<Intermittent> liste_intermittent;
  private final ArrayList<Biome> liste_biome;
  private Player player;
  // autres champs
  // pour la teleportation
  private final String carteName;
  private final Player LocalPlayer;
  private boolean switchToAnotherMap = false;
  private String nextMap = null;
  
  /* CONSTRUCTEUR */
  public Carte(DataCarte carteData, String carteName) {
    Objects.requireNonNull(carteData);
    this.size = new Couple(0, 0);                                 
    constructSize(carteData);
    this.encodings = new HashMap<String, String>();               
    this.grille = new String[this.size.getY()][this.size.getX()]; 
    
    liste_decore = new ArrayList<Decoratif>();                    
    liste_obstacle = new ArrayList<Obstacle>();                   
    liste_item = new ArrayList<Item>();                           
    liste_enemy = new ArrayList<Enemy>();                         
    liste_porte = new ArrayList<Obstacle>();  
    liste_friend = new ArrayList<Friend>();
    liste_intermittent = new ArrayList<Intermittent>();
    this.liste_biome = new ArrayList<Biome>();
    this.constructAll(carteData);
    this.carteName = new String(carteName);
    this.LocalPlayer = this.player;
  }
  
  /* construire les elements de la carte */
  /* soit par la grille */
  /* soit par les elements de la carteData */
  private void constructAll(DataCarte carteData) {
  	constructEncoding(carteData);
    constructGrille(carteData);
    constructElementFromGrille();
    constructElements(carteData);
    constructPlayer(carteData);
    constructPorte();
  }
  
  /* separate the doors from obstacle */
  private void constructPorte() {
    List<String> porte_skin = List.<String>of("DOOR", "GATE", "HOUSE", "TOWER");
    for (var obstacle : this.liste_obstacle) {
      for (var skin : porte_skin) {
        if (skin.equals(obstacle.getSkin())) {
          this.liste_porte.add(obstacle);
        }
      }
    }
    for (var porte : this.liste_porte) {
      this.liste_obstacle.remove(porte);
    }
  } 
  
  /* creer size */
  private void constructSize(DataCarte carteData) {
    var x = Integer.parseInt(carteData.getSizeX());
    var y = Integer.parseInt(carteData.getSizeY());
    this.size.put(x, y);
  }
  
  /* creer l'encoding */
  private void constructEncoding(DataCarte carteData) {
    var map = carteData.getEncodings(); 
    for (var key : map.keySet()) {
      this.encodings.put(key, map.get(key));
    }
  }
  
  /* creer la grille */
  private void constructGrille(DataCarte carteData) {
    String s = carteData.getData().replace("\n", "");
    char[] tab = new char[this.size.getX() * this.size.getY()];
    s.getChars(0, s.length(), tab, 0);
    for (int j = 0; j < this.size.getY(); j++) {
      for (int  i = 0; i < this.size.getX(); i++) {
        this.grille[j][i] = Character.toString(tab[j * this.size.getX() + i]);
      }
    }
  }
  
  /* creer quelque elements apartir de la grille */
  private void constructElementFromGrille() {
    for (int i = 0; i < this.size.getX(); i++) {
      for (int j = 0; j < this.size.getY(); j++) {
        var key = this.grille[j][i];
        var skin = this.encodings.get(key);
        var position = new Couple(i, j);
        var zone = new Zone(position, new Couple(0, 0));
        this.constructGrilleElement(skin, position, zone);
      }
    }
  }
  
  private void constructGrilleElement(String skin, Couple position, Zone zone) {
    if (skin == null) {
      return;
    }
    if (Skins.decoratifs.contains(skin)) {
      var element = new Decoratif("NoN", skin, position);
      this.liste_decore.add(element);
    } else if (Skins.obstacles.contains(skin)) {
      var element = new Obstacle("NoN", skin, position, "NoN|NoN", "NoN");
      this.liste_obstacle.add(element);
    } else if (Skins.items.contains(skin)) {
      var element = new Item("NoN", skin, position, 0);
      this.liste_item.add(element);
    } else if (Skins.intermittents.contains(skin)) {
      var element = new Intermittent(skin, position);
      this.liste_intermittent.add(element);
    } else if (Skins.biomes.contains(skin)) {
      var element = new Biome(skin, zone, Flow.NORTH); // by default North
      this.liste_biome.add(element);
    }
  }
  
  /* construct elements */
  private void constructElements(DataCarte carteData) {
    var elements = carteData.getElements();
    for (var element : elements) {
      if (!element.get("kind").equals("NoN")) {
        // construct element using kind
        switch (element.get("kind")) {
          case "obstacle" -> {this.constructElementObstacle(element);}
          case "item" -> {this.constructElementItem(element);}
          case "enemy" -> {this.constructElementEnemy(element);}
          case "friend" -> {this.constructElementFriend(element);}
          default -> {}
        }
      } else {
        // construct element using skin
        this.constructElementBySkin(element);
      }
    }
  }
  
  private void constructElementBySkin(Map<String, String> element) {
    var skin = element.get("skin");
    var zone = DataCarte.convertStringToZone(element.get("zone"));
    var flow = DataCarte.convertStringToFlow(element.get("flow"));
    if (Skins.biomes.contains(skin)) {
      var biome = new Biome(skin, zone, flow);
      this.liste_biome.add(biome);
    }
  }
  
  private void constructElementObstacle(Map<String, String> element) {
    String name = element.get("name");  // optionnel
    String skin = element.get("skin");
    Couple position = DataCarte.convertStringToPosition(element.get("position"));
    String locked = element.get("locked");
    String teleport = element.get("teleport");
    this.liste_obstacle.add(new Obstacle(name, skin, position, locked, teleport));
  }
  
  private void constructElementItem(Map<String, String> element) {
    String name = element.get("name");  // optionnel
    String skin = element.get("skin");
    Couple position = DataCarte.convertStringToPosition(element.get("position"));
    int damage = DataCarte.convertStringToInt(element.get("damage"));
    this.liste_item.add(new Item(name, skin, position, damage));
  }

  private void constructElementEnemy(Map<String, String> element) {
    String name = element.get("name");  // optionnel
    String skin = element.get("skin");
    Couple position = DataCarte.convertStringToPosition(element.get("position"));
    int health = DataCarte.convertStringToInt(element.get("health"));
    Zone zone = DataCarte.convertStringToZone(element.get("zone"));
    String behavior = element.get("behavior");
    int damage = DataCarte.convertStringToInt(element.get("damage"));
    this.liste_enemy.add(new Enemy(name, skin, position, health, zone, behavior, damage));
  }
  
  private void constructElementFriend(Map<String, String> element) {
    String name = element.get("name");  // optionnel
    String skin = element.get("skin");
    Couple position = DataCarte.convertStringToPosition(element.get("position"));
    String text = element.get("text");
    this.liste_friend.add(new Friend(name, skin, position, text));
  }
  
  /* creer un player */
  private void constructPlayer(DataCarte carteData) {
    for (var element : carteData.getElements()) {
      if (element.get("player").equals("true")) {
        String name = element.get("name");
        String skin = element.get("skin");
        Couple position = DataCarte.convertStringToPosition(element.get("position"));
        int health = DataCarte.convertStringToInt(element.get("health"));
        String[] trade = element.get("trade").split("\\|");
        this.player = new Player(name, skin, position, health, trade);
      }
    }
  }
  
  /* UPDATE */
  public void update() {
    this.updateIntermittent();
    this.updateEnemy();
    this.updateFriend();
    this.updatePorte();
  }
  
  private void updateIntermittent() {
    for (var intermittent : liste_intermittent) {
      intermittent.update();
    }
  }
  
  private void updateEnemy() {
    for (var enemy : this.liste_enemy) {
      enemy.update(this.liste_obstacle);
    }
  }
  
  private void updateFriend() {
    for (var friend : this.liste_friend) {
      friend.update(this.player);
    }
  }
  
  private void updatePorte() {
    for (var porte : this.liste_porte) {
      if (porte.checkTeleport(player)) {
        this.switchToAnotherMap = true;
        this.nextMap = porte.getTeleport();
        break;
      }
    }
  }
  
  /* FONCTIONS */
  
  public void initSwitchToAnotherMap() {
    this.switchToAnotherMap = false;
  }
  
  public void initNextMap() {
    this.nextMap = null;
  }
  
  public Item getItemInPosition(int x, int y) {
    for (var item : this.liste_item) {
      if (item.getPosition().equals(new Couple(x, y))) {
        this.removeItemFromListItems(item);
        return item;
      }
    }
    return null;
  }
  
  public Enemy getEnemyInPosition(int x, int y) {
    for (var enemy : this.liste_enemy) {
      if (enemy.getPosition().equals(new Couple(x, y))) {
        return enemy;
      }
    }
    return null;
  }
  
  public Obstacle getPorteInPosition(int x, int y) {
    for (var porte : this.liste_porte) {
      if (porte.getPosition().equals(new Couple(x, y))) {
        return porte;
      }
    }
    return null;
  }
  
  private void removeItemFromListItems(Item item) {
    this.liste_item.remove(item);
  }
  
  /* getteur */
  public Couple getSize() {return this.size;}
  public Map<String, String> getEncodings() {return this.encodings;}
  public String[][] getGrille() {return this.grille;}
  
  public List<Decoratif> getListeDecore() {return this.liste_decore;}
  public List<Intermittent> getListeIntermittent() {return this.liste_intermittent;}
  public List<Obstacle> getListeObstacle() {return this.liste_obstacle;}
  public List<Obstacle> getListePorte() {return this.liste_porte;}
  public List<Item> getListeItem() {return this.liste_item;}
  public List<Enemy> getListeEnemy() {return this.liste_enemy;}
  public List<Friend> getListeFriend() {return this.liste_friend;}
  public List<Biome> getListeBiome() {return this.liste_biome;}
  public Player getPlayer() {return this.player;}
  public String getCarteName() {return this.carteName;}
  public Player getLocalPlayer() {return this.LocalPlayer;}
  public boolean getSwitchToAnotherMap() {return this.switchToAnotherMap;}
  public String getNextMap() {return this.nextMap;}
}
