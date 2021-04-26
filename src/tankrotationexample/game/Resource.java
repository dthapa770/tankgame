package tankrotationexample.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();
        try {
            Resource.resources.put("t1",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/tank1.png"))));
            Resource.resources.put("t2",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/tank2.png"))));
            Resource.resources.put("breakableWall",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/Wall1.gif"))));
            Resource.resources.put("unBreakableWall",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/Wall2.gif"))));
            Resource.resources.put("bullet",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/Shell.gif"))));
            Resource.resources.put("background", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/Background.png"))));
            Resource.resources.put("health",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/gheart.png"))));
            Resource.resources.put("life",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/Life.png"))));
            Resource.resources.put("speed",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("spritemap/speed.png"))));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }


    }

    public static BufferedImage getResourceImage(String key){
        return Resource.resources.get(key);
    }
}
