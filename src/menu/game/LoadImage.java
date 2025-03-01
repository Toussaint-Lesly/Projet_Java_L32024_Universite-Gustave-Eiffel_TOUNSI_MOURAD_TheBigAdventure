package menu.game;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import objets.primitive.Skins;

public class LoadImage {
  
	public static Map<String, Image> loadImages(String path) {
    var mapNameImage = new HashMap<String, Image>();
    var userDir = System.getProperty("user.dir");
    Path pathDir = Path.of(userDir).resolve(path);
    File file;
    for (int i = 0; i < Skins.tabAllSkin.length; i++) {
      var pathIm = pathDir.resolve(Skins.tabAllSkin[i]);
      file = new File(pathIm.toString() + ".png");
      try {
        Image reader = ImageIO.read(file);
        mapNameImage.put(Skins.tabAllSkin[i], reader);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    return mapNameImage;
  }
  
}
