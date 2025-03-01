package vue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;


import fr.umlv.zen5.ApplicationContext;
import objets.Biome;
import objets.Carte;
import objets.Enemy;
import objets.Friend;
import objets.Intermittent;
import objets.Item;
import objets.Obstacle;
import objets.Decoratif;
import objets.Player;
import objets.primitive.Couple;
import objets.primitive.Direction;


public class Draw {
  
  /* dessiner les  elements de la grille */
  public static void draw(ApplicationContext context, Carte carte, Player player, Map<String, Image> mapSkinToImage, float width, float height, float zoom) {
    var size = carte.getSize();
    //var encodings = carte.getEncodings();
    //var grille = carte.getGrille();
    
    float constX = width / size.getX();
    float constY = height / size.getY();
    
    List<Decoratif> liste_decore = carte.getListeDecore();
    List<Intermittent> liste_intermittent = carte.getListeIntermittent();
    List<Obstacle> liste_obstacle = carte.getListeObstacle();
    List<Obstacle> liste_porte = carte.getListePorte();
    List<Item> liste_item = carte.getListeItem();
    List<Enemy> liste_enemy = carte.getListeEnemy();
    List<Friend> liste_friend = carte.getListeFriend();
    List<Biome> liste_biome = carte.getListeBiome();
    
    context.renderFrame(graphics -> {
    	// 
      clearWindow(graphics, width, height);
      // fait appelle a toutes le methode pour dessiner les objets
      drawAll(graphics, size, player, width, height, constX, constY, zoom, mapSkinToImage, 
      		liste_decore, liste_intermittent, liste_obstacle, liste_porte, liste_item, 
      		liste_enemy, liste_friend, liste_biome
      		);
    });
  }
  
  private static void drawAll(Graphics2D graphics, Couple size, Player player, float width, float height, 
  		float constX, float constY, float zoom, Map<String, Image> mapSkinToImage,
  		List<Decoratif> liste_decore, List<Intermittent> liste_intermittent, List<Obstacle> liste_obstacle, 
  		List<Obstacle> liste_porte, List<Item> liste_item, List<Enemy> liste_enemy, 
  		List<Friend> liste_friend, List<Biome> liste_biome) {
  	drawDecore(graphics, size, liste_decore, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawIntermittent(graphics, size, liste_intermittent, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawBiome(graphics, size, liste_biome, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawObstacle(graphics, size, liste_obstacle, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawPorte(graphics, size, liste_porte, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawItem(graphics, size, liste_item, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawEnemy(graphics, size, liste_enemy, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawFriend(graphics, size, liste_friend, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawFriendInventaire(graphics, size, liste_friend, player, mapSkinToImage,width, height, constX, constY, zoom);
    drawTextFriend(graphics, size, liste_friend, width, height);//
    drawPlayer(graphics, size, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawPlayerHand(graphics, size, player, mapSkinToImage, width, height, constX, constY, zoom);
    drawPlayerDirection(graphics, size, player, width, height, constX, constY, zoom);
    drawPlayerHealthBare(graphics, size, player, width, height, constX, constY, zoom);
    drawPlayerInventaire(graphics, size, player, mapSkinToImage,width, height, constX, constY, zoom);
  }
  
  private static void drawFriendInventaire(Graphics2D graphics, Couple size, List<Friend> liste_friend, Player player, Map<String, Image> mapSkinToImage,float width, float height, float constX, float constY, float zoom) {
		for (var friend : liste_friend) {
	  	if (!friend.getShowInventaire()) {
	  	continue;
	  	}
	  	float x = friend.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2  + 1.0f;
	  	float y = friend.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2  + 1.0f;
	  	int i = 0;
	  	int j = 0;
	  	drawScreenInventaireFriend(graphics, friend, x, y, constX, constY, zoom);
	  	for (var item : friend.getInventaire()) {
	    	graphics.drawImage(mapSkinToImage.get(item.getSkin()), (int)((x + i) * constX * zoom), (int)((y + j) * constY * zoom), (int)(constX * zoom), (int)(constY * zoom), null);
	    	i = (i + 1) % 5;
	    	if (i == 0) {
	      	j++;
	    	}
	  	}
  	}
  }
  
  private static void drawScreenInventaireFriend(Graphics2D graphics, Friend friend, float x, float y, float constX, float constY, float zoom) {
	float maxY = (friend.getInventaire().size() / 5) + 1;
	graphics.setColor(Color.GRAY);
	graphics.fill(new Rectangle2D.Float((int)(x * constX * zoom), (int)(y * constY * zoom), (int)((5.0f) * constX * zoom), (int)(maxY * constY * zoom)));
  }
  
  
  private static void drawScreenInventaire(Graphics2D graphics, Player player, float x, float y, float constX, float constY, float zoom) {
    float maxY = (player.getInventaire().size() / 5) + 1;
    graphics.setColor(Color.ORANGE);
    graphics.fill(new Rectangle2D.Float((int)(x * constX * zoom), (int)(y * constY * zoom), (int)((5.0f) * constX * zoom), (int)(maxY * constY * zoom)));
  }
  
  private static void drawSelecteur(Graphics2D graphics, Player player, float x, float y, int sx, int sy, float constX, float constY, float zoom) {
    if (player.getSelecteur() < 0) {
      return;
    }
    graphics.setColor(Color.WHITE);
    graphics.fill(new Rectangle2D.Float((int)((x + sx) * constX * zoom), (int)((y + sy) * constY * zoom), (int)((1.0f) * constX * zoom), (int)((1.0f) * constY * zoom)));
  }
  
  private static void drawEnemy(Graphics2D graphics, Couple size, List<Enemy> liste_enemy, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var enemy : liste_enemy) {
      float x = enemy.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = enemy.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = enemy.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
      drawHealthBareEnemy(graphics, enemy, x, y, constX, constY, zoom);
    }
  }
  
  private static void drawTextFriend(Graphics2D graphics, Couple size, List<Friend> liste_friend, float width, float height) {
    //float constX = width / size.getX();
    //float constY = height / size.getY();
    for (var friend : liste_friend) {
      if (friend.getInStateOfTalk()) {
        var tabText = friend.getText().split("\n");
        int maxHeight = 30 * (tabText.length + 1);
        graphics.setColor(Color.YELLOW);
        graphics.fill(new Rectangle2D.Float(0, 0, width, maxHeight));
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < tabText.length; i++) {
          graphics.drawString(tabText[i], 30, 30 + i * 30);
        }
      }
    }
  }
  
  
  private static void drawFriend(Graphics2D graphics, Couple size, List<Friend> liste_friend, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var friend : liste_friend) {
      float x = friend.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = friend.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = friend.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
    }
  }
  
  private static void drawHealthBareEnemy(Graphics2D graphics, Enemy enemy, float x, float y, float constX, float constY, float zoom) {
    x += 0.0f;
    y -= 1.0f;
    graphics.setColor(Color.RED);
    graphics.fill(new Rectangle2D.Float(
        (int)(x * constX * zoom), (int)((y + 0.5f) * constY * zoom),
        (int)((1.0f) * constX * zoom), (int)((0.5f) * constY * zoom)
        ));
    graphics.setColor(Color.GREEN);
    graphics.fill(new Rectangle2D.Float(
        (int)(x * constX * zoom), (int)((y + 0.5f) * constY * zoom),
        (int)((1.0f * enemy.getHealth() / enemy.getMaxHealth()) * constX * zoom),
        (int)((0.5f) * constY * zoom)
        ));
    graphics.drawString("HP = " + enemy.getHealth() + "/" + enemy.getMaxHealth(), (int)(x * constX * zoom), (int)(y * constY * zoom));
  } 
  
  private static void drawItem(Graphics2D graphics, Couple size, List<Item> liste_item, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var item : liste_item) {
      float x = item.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = item.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = item.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
    }
  }
  
  private static void drawObstacle(Graphics2D graphics, Couple size, List<Obstacle> liste_obstacle, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var obstcale : liste_obstacle) {
      float x = obstcale.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = obstcale.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = obstcale.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
    }
  }
  
  private static void drawPorte(Graphics2D graphics, Couple size, List<Obstacle> liste_porte, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var porte : liste_porte) {
      float x = porte.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = porte.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = porte.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
    }
  }
  
  private static void drawDecore(Graphics2D graphics, Couple size, List<Decoratif> liste_decore, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var decore : liste_decore) {
      float x = decore.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = decore.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = decore.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
    }
  }
  
  private static void drawIntermittent(Graphics2D graphics, Couple size, List<Intermittent> liste_intermittent, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var intermittent : liste_intermittent) {
      if (!intermittent.getShowSkin()) {
        continue;
      }
      float x = intermittent.getPosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = intermittent.getPosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      var skin = intermittent.getSkin();
      graphics.drawImage(
          mapSkinToImage.get(skin), 
          (int)(x * constX * zoom), 
          (int)(y * constY * zoom), 
          (int)(constX * zoom), 
          (int)(constY * zoom), 
          null);
    }
  }
  
  private static void drawBiome(Graphics2D graphics, Couple size, List<Biome> liste_biome, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    for (var biome : liste_biome) {
      float x = biome.getZone().getZonePosition().getX() - player.getPosition().getX() + size.getX()/zoom/2;
      float y = biome.getZone().getZonePosition().getY() - player.getPosition().getY() + size.getY()/zoom/2;
      int u = biome.getZone().getZoneRegion().getX();
      int v = biome.getZone().getZoneRegion().getY();
      var skin = biome.getSkin();
      for (int i = 0; i <= u; i++) {
        for (int j = 0; j <= v; j++) {
          graphics.drawImage(
              mapSkinToImage.get(skin), 
              (int)((x + i) * constX * zoom), 
              (int)((y + j) * constY * zoom), 
              (int)(constX * zoom), 
              (int)(constY * zoom), 
              null);
        }
      }
      
    }
  }
  
  private static void drawPlayer(Graphics2D graphics, Couple size, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    var skin = player.getSkin();
    graphics.drawImage(
        mapSkinToImage.get(skin), 
        (int)((size.getX()/zoom/2) * constX * zoom), 
        (int)((size.getY()/zoom/2) * constY * zoom), 
        (int)(constX * zoom), 
        (int)(constY * zoom), 
        null);
  }
  
  private static void drawPlayerHand(Graphics2D graphics, Couple size, Player player, Map<String, Image> mapSkinToImage, float width, float height, float constX, float constY, float zoom) {
    if (player.getSelectedItem() == null) {
      return;
    }
    var skin = player.getSelectedItem().getSkin();
    graphics.drawImage(
        mapSkinToImage.get(skin), 
        (int)((size.getX()/zoom/2) * constX * zoom), 
        (int)((size.getY()/zoom/2) * constY * zoom), 
        (int)(constX * zoom), 
        (int)(constY * zoom), 
        null);
  }
  
  private static void drawPlayerDirection(Graphics2D graphics, Couple size, Player player, float width, float height, float constX, float constY, float zoom) {
    float x = size.getX() / zoom / 2;
    float y = size.getY() / zoom / 2;
    switch (player.getDirection()) {
      case Direction.UP   : y--; break;
      case Direction.DOWN : y++; break;
      case Direction.LEFT : x--; break;
      default : x++;
    }
    
    graphics.setColor(Color.YELLOW);
    graphics.fill(new Ellipse2D.Float((int)(x * constX * zoom), (int)(y * constY * zoom), (int)(0.2f * constX * zoom), (int)(0.2f * constY * zoom)));
    graphics.fill(new Ellipse2D.Float((int)((x + 0.8f) * constX * zoom), (int)(y * constY * zoom), (int)(0.2f * constX * zoom), (int)(0.2f * constY * zoom)));
    graphics.fill(new Ellipse2D.Float((int)(x * constX * zoom), (int)((y + 0.8f) * constY * zoom), (int)(0.2f * constX * zoom), (int)(0.2f * constY * zoom)));
    graphics.fill(new Ellipse2D.Float((int)((x + 0.8f) * constX * zoom), (int)((y + 0.8f) * constY * zoom), (int)(0.2f * constX * zoom), (int)(0.2f * constY * zoom)));
    graphics.fill(new Ellipse2D.Float((int)((x + 0.4f) * constX * zoom), (int)((y + 0.4f) * constY * zoom), (int)(0.2f * constX * zoom), (int)(0.2f * constY * zoom)));
  }
  
  private static void drawPlayerHealthBare(Graphics2D graphics, Couple size, Player player, float width, float height, float constX, float constY, float zoom) {
    float x = (size.getX() / zoom / 2) + 1.0f;
    float y = (size.getY() / zoom / 2) - 1.0f;
    graphics.setColor(Color.RED);
    graphics.fill(new Rectangle2D.Float(
        (int)(x * constX * zoom), (int)((y + 0.5f) * constY * zoom),
        (int)((1.0f) * constX * zoom), (int)((0.5f) * constY * zoom)
        ));
    graphics.setColor(Color.GREEN);
    graphics.fill(new Rectangle2D.Float(
        (int)(x * constX * zoom), (int)((y + 0.5f) * constY * zoom),
        (int)((1.0f * player.getHealth() / player.getMaxHealth()) * constX * zoom),
        (int)((0.5f) * constY * zoom)
        ));
    graphics.drawString("HP = " + player.getHealth() + "/" + player.getMaxHealth(), (int)(x * constX * zoom), (int)(y * constY * zoom));
  }
  
  private static void drawPlayerInventaire(Graphics2D graphics, Couple size, Player player, Map<String, Image> mapSkinToImage,float width, float height, float constX, float constY, float zoom) {
    if (!player.getShowInventaire()) {
      return;
    }
    float x = (size.getX() / zoom / 2) + 1.0f;
    float y = (size.getY() / zoom / 2) + 1.0f;
    int i = 0;
    int j = 0;
    int sx = player.getSelecteur() % 5;
    int sy = player.getSelecteur() / 5;
    drawScreenInventaire(graphics, player, x, y, constX, constY, zoom);
    drawSelecteur(graphics, player, x, y, sx, sy, constX, constY, zoom);
    for (var item : player.getInventaire()) {
      graphics.drawImage(mapSkinToImage.get(item.getSkin()), (int)((x + i) * constX * zoom), (int)((y + j) * constY * zoom), (int)(constX * zoom), (int)(constY * zoom), null);
      i = (i + 1) % 5;
      if (i == 0) {
        j++;
      }
    }
  }
  
  private static void clearWindow(Graphics2D graphics, float width, float height) {
    graphics.setColor(Color.DARK_GRAY);
    graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
  }
  
  
  
  
}
