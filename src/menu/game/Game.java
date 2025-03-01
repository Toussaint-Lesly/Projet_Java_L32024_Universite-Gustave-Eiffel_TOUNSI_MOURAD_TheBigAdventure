package menu.game;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import objets.Carte;
import objets.Player;
import vue.Draw;

public class Game {
  public static void game(List<Carte> liste_carte, Map<String, Image> mapSkinToImage) throws IOException {  
    
    
    /* GAME */
    Application.run(Color.DARK_GRAY, context -> {
    	/* le joueur principale */
    	Player player = liste_carte.getFirst().getPlayer();
    	/* le carte principale */
    	Carte carte = liste_carte.getFirst();
      
      // GET THE SIZE OF THE SCREEN
      ScreenInfo screenInfo = context.getScreenInfo();
      final float width = screenInfo.getWidth();
      final float height = screenInfo.getHeight();
      
      double chronometre = 0.0; // chronometre 
      float zoom = 1.5f;	// le zoom initiale
      
      /* GAME LOOP */
      for(;;) {
      	/* Gestion des evenements */
        Event event = context.pollOrWaitEvent(10);
        if (event != null) {  // no event
        	Action action = event.getAction();
        	if (action == Action.KEY_PRESSED) {
        		var key = event.getKey();
        		if (key.equals(KeyboardKey.Q)) {
        			System.out.println("exit apps !");
        			context.exit(0);
        			return;
        		}
        		// zoom IN
        		if (key.equals(KeyboardKey.P)) {
        			zoom = Math.min(zoom + 0.2f, 4.0f);
        		}
        		// zoom OUT
        		if (key.equals(KeyboardKey.M)) {
        			zoom = Math.max(0.2f, zoom - 0.2f);
        		}
        	}
        }
        
        /* LOGIC */
        if (carte.getSwitchToAnotherMap() && carte.getNextMap() != null) {
          for (var crt : liste_carte) {
            if (crt.getCarteName().equals(carte.getNextMap())) {
              carte.initSwitchToAnotherMap();
              carte.initNextMap();
              carte = crt;
              player.putPosition(carte.getLocalPlayer().getPosition());
            }
          }
        }
        
        if (player.getDeath()) {
          System.out.println("exit apps !");
          context.exit(0);
          return;
        }
        
        /* UPDATE */
        player.update(event, carte);
        carte.update();
        
        /* DRAW */
        chronometre += 1.0 / 3.0;
        if (chronometre > 1.0) {
        	chronometre = 0.0;
          Draw.draw(context, carte, player, mapSkinToImage, width, height, zoom);
        }        
      }
      
    }); /* FIN */
    
  }
}
