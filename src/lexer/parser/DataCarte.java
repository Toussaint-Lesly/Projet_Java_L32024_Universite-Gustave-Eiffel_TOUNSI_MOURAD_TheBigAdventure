package lexer.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import objets.primitive.Couple;
import objets.primitive.Flow;
import objets.primitive.Zone;

public class DataCarte {
  /* CHAMPS */
  private String sizeX;
  private String sizeY;
  private final HashMap<String, String> encodings;
  private String data;
  
  private final ArrayList<HashMap<String, String>> elements;
  
  /* CONSTRUCTEUR */
  public DataCarte() {
    this.data= "0";
    this.data= "0";
    this.encodings = new HashMap<String, String>();/* liste de couple de string */
    this.elements = new ArrayList<HashMap<String, String>>();
    this.data = new String("");
  }
  
  /* FONCTIONS */
  public void addSize(String x, String y) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    this.sizeX = x;
    this.sizeY = y;
  }
  
  public void addEncodings(String skin, String encodage) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(encodage);
    this.encodings.put(encodage, skin);
  }
  
  public void addData(String data) {
    Objects.requireNonNull(data);
    data = data.replaceAll("\n[ \t]*", "\n")
               .replace("\"", "")
               .replaceFirst("\n", "");
    this.data = data;
  }
  
  public void addElement() {
    this.elements.add(new HashMap<String, String>());
  }
  
  public void addAttribut(String attribut, String value) {
    Objects.requireNonNull(attribut);
    Objects.requireNonNull(value);
    
    this.elements.getLast().put(attribut, value);
  }
  
  /* DEFAULT VALUES */
  /* adding default value for non existant attribut of an element */
  public void addDefaultValue() {
    var tabAttribut = new String[] {"name", "skin", "player", "position", "health", 
        "kind", "zone", "behavior", "damage", "text", "steal", "trade", 
        "locked", "flow", "phantomized", "teleport"};
    var tabValue = new String[] {"NoN", "NoN", "false", "1|1", "0", 
        "NoN", "0|0|0|0", "NoN", "0", "NoN", "NoN", "NoN", 
        "NoN|NoN", "NoN", "false", "NoN"};
    for (var element: this.elements) {
      for (int i = 0; i < tabAttribut.length; i++) {
        if (element.get(tabAttribut[i]) == null) {
          element.put(tabAttribut[i], tabValue[i]);
        }
      }
    }
  }
  
  /* getteur */
  public String getSizeX() { return this.sizeX;}
  public String getSizeY() { return this.sizeY;}
  public String getData() { return this.data;}
  public Map<String, String> getEncodings() { return this.encodings;}
  public List<HashMap<String, String>> getElements() { return this.elements;}
  
  /* converteur */
  public static Couple convertStringToPosition(String s) {
    Objects.requireNonNull(s);
    var tab = s.split("\\|");
    var x = Integer.parseInt(tab[0]);
    var y = Integer.parseInt(tab[1]);
    return new Couple(x, y);
  }
  
  public static Zone convertStringToZone(String s) {
    Objects.requireNonNull(s);
    var tab = s.split("\\|");
    var x = Integer.parseInt(tab[0]);
    var y = Integer.parseInt(tab[1]);
    var u = Integer.parseInt(tab[2]);
    var v = Integer.parseInt(tab[3]);
    var U1 = new Couple(x, y);
    var U2 = new Couple(u, v);
    return new Zone(U1, U2);
  }
  
  public static int convertStringToInt(String s) {
    return Integer.parseInt(s);
  }
  
  public static Flow convertStringToFlow(String s) {
    Flow flow;
    switch (s) {
      case "NORTH" -> {flow = Flow.NORTH;}
      case "SOUTH" -> {flow = Flow.SOUTH;}
      case "EAST"  -> {flow = Flow.EAST;}
      default -> {flow = Flow.WEST;}
    }
    return flow;
  }
  
  
  
  /* affichage */
  public void affichage() {
    System.out.println("("+this.sizeX+","+this.sizeY+")");
    System.out.println(this.encodings);
    System.out.println(this.data);
    for (var element : this.elements) {
      System.out.println(element);
    }
  }
  
  
}
