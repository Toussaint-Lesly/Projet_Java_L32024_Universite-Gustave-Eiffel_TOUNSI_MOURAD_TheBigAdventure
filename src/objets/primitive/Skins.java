package objets.primitive;

import java.util.List;

/* pas encore fini */
/* CLASS DES CONSTANTE DU JEUX */
public class Skins {
  public static List<String> decoratifs = List.<String>of(
      "ALGAE", "CLOUD", "FLOWER", "FOLIAGE", "GRASS", "LADDER", "LILY", 
      "PLANK", "REED", "ROAD", "SPROUT", "TILE", "TRACK", "VINE"
      );
  
  public static List<String> intermittents = List.<String>of(
      "BUBBLE", "DUST"
      );
  
  public static List<String> obstacles = List.<String>of(
      "BED", "BOG", "BOMB", "BRICK", "CHAIR", "CLIFF", "DOOR", 
      "FENCE", "FORT", "GATE", "HEDGE", "HOUSE", "HUSK", "HUSKS", 
      "LOCK", "MONITOR", "PIANO", "PILLAR", "PIPE", "ROCK", "RUBBLE", 
      "SHELL", "SIGN", "SPIKE", "STATUE", "STUMP", "TABLE", "TOWER", 
      "TREE", "TREES", "WALL"
      );
  public static List<String> items = List.<String>of(
      "BOOK", "BOLT", "BOX", "CASH", "CLOCK", "COG", "CRYSTAL", "CUP", 
      "DRUM", "FLAG", "GEM", "GUITAR", "HIHAT", "KEY", "LAMP", "LEAF", 
      "MIRROR", "MOON", "ORB", "PANTS", "PAPER", "PLANET", "RING", "ROSE", 
      "SAX", "SCISSORS", "SEED", "SHIRT", "SHOVEL", "STAR", "STICK", "SUN", 
      "SWORD", "TRUMPET", "VASE",
      
      "BANANA", "BOBA", "BOTTLE", "BURGER", "CAKE", "CHEESE", "DONUT", "DRINK", 
      "EGG", "FRUIT", "FUNGUS", "FUNGI", "LOVE", "PIZZA", "POTATO", "PUMPKIN", "TURNIP"
      );
  public static List<String> nutritifs = List.<String>of(
      "BANANA", "BOBA", "BOTTLE", "BURGER", "CAKE", "CHEESE", "DONUT", "DRINK", 
      "EGG", "FRUIT", "FUNGUS", "FUNGI", "LOVE", "PIZZA", "POTATO", "PUMPKIN", "TURNIP"
  );
  public static List<String> frend_enemy = List.<String>of(
      "BABA", "BADBAD", "BAT", "BEE", "BIRD", "BUG", "BUNNY", "CAT", "CRAB", 
      "DOG", "FISH", "FOFO", "FROG", "GHOST", "IT", "JELLY", "JIJI", "KEKE", 
      "LIZARD", "ME", "MONSTER", "ROBOT", "SNAIL", "SKULL", "TEETH", "TURTLE", 
      "WORM"
      );
  public static List<String> player = List.<String>of(
      "BABA", "BADBAD", "FOFO", "IT"
      );
  public static List<String> biomes = List.<String>of(
      "ICE", "LAVA", "WATER"
      );
  
  /* liste de skin */
  public static List<String> allSkin = List.<String>of(
      "ALGAE", "CLOUD", "FLOWER", "FOLIAGE", "GRASS", "LADDER", "LILY", 
      "PLANK", "REED", "ROAD", "SPROUT", "TILE", "TRACK", "VINE",
      
      "BUBBLE", "DUST",
      
      "BED", "BOG", "BOMB", "BRICK", "CHAIR", "CLIFF", "DOOR", 
      "FENCE", "FORT", "GATE", "HEDGE", "HOUSE", "HUSK", "HUSKS", 
      "LOCK", "MONITOR", "PIANO", "PILLAR", "PIPE", "ROCK", "RUBBLE", 
      "SHELL", "SIGN", "SPIKE", "STATUE", "STUMP", "TABLE", "TOWER", 
      "TREE", "TREES", "WALL",
      
      "BOOK", "BOLT", "BOX", "CASH", "CLOCK", "COG", "CRYSTAL", "CUP", 
      "DRUM", "FLAG", "GEM", "GUITAR", "HIHAT", "KEY", "LAMP", "LEAF", 
      "MIRROR", "MOON", "ORB", "PANTS", "PAPER", "PLANET", "RING", "ROSE", 
      "SAX", "SCISSORS", "SEED", "SHIRT", "SHOVEL", "STAR", "STICK", "SUN", 
      "SWORD", "TRUMPET", "VASE",
      
      "BANANA", "BOBA", "BOTTLE", "BURGER", "CAKE", "CHEESE", "DONUT", "DRINK", 
      "EGG", "FRUIT", "FUNGUS", "FUNGI", "LOVE", "PIZZA", "POTATO", "PUMPKIN", "TURNIP",
      
      "BABA", "BADBAD", "BAT", "BEE", "BIRD", "BUG", "BUNNY", "CAT", "CRAB", 
      "DOG", "FISH", "FOFO", "FROG", "GHOST", "IT", "JELLY", "JIJI", "KEKE", 
      "LIZARD", "ME", "MONSTER", "ROBOT", "SNAIL", "SKULL", "TEETH", "TURTLE", 
      "WORM",
      
      "ICE", "LAVA", "WATER"
      );
  
  /* tableau de skin */
  public static String[] tabAllSkin = new String[] {
      "ALGAE", "CLOUD", "FLOWER", "FOLIAGE", "GRASS", "LADDER", "LILY", 
      "PLANK", "REED", "ROAD", "SPROUT", "TILE", "TRACK", "VINE",
      
      "BUBBLE", "DUST",
      
      "BED", "BOG", /*"BOMB",*/ "BRICK", "CHAIR", "CLIFF", "DOOR", 
      "FENCE", "FORT", "GATE", "HEDGE", "HOUSE", "HUSK", "HUSKS", 
      "LOCK", "MONITOR", "PIANO", "PILLAR", "PIPE", "ROCK", "RUBBLE", 
      /*"SHELL",*/ "SIGN", "SPIKE", "STATUE", "STUMP", "TABLE", "TOWER", 
      "TREE", "TREES", "WALL",
      
      "BOOK", "BOLT", "BOX", "CASH", "CLOCK", "COG", "CRYSTAL", "CUP", 
      "DRUM", "FLAG", "GEM", "GUITAR", "HIHAT", "KEY", "LAMP", "LEAF", 
      "MIRROR", "MOON", "ORB", "PANTS", "PAPER", "PLANET", "RING", "ROSE", 
      "SAX", "SCISSORS", "SEED", "SHIRT", "SHOVEL", "STAR", "STICK", "SUN", 
      "SWORD", "TRUMPET", "VASE",
      
      "BANANA", "BOBA", "BOTTLE", "BURGER", "CAKE", "CHEESE", /*"DONUT",*/ "DRINK", 
      "EGG", "FRUIT", "FUNGUS", "FUNGI", "LOVE", "PIZZA", "POTATO", "PUMPKIN", "TURNIP",
      
      "BABA", "BADBAD", "BAT", "BEE", "BIRD", "BUG", "BUNNY", "CAT", "CRAB", 
      "DOG", "FISH", "FOFO", "FROG", "GHOST", "IT", "JELLY", "JIJI", "KEKE", 
      "LIZARD", "ME", "MONSTER", "ROBOT", "SNAIL", "SKULL", "TEETH", "TURTLE", 
      "WORM",
      
      "ICE", "LAVA", "WATER"
  };
  
}
