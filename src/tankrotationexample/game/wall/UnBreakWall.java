package tankrotationexample.game.wall;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakWall extends Wall {
 Rectangle bounds;

    public UnBreakWall(int x, int y, BufferedImage wallImage) {
        super(x,y,wallImage);
        this.bounds = new Rectangle(x,y,img.getWidth(),img.getHeight());
        gameObject.add(this);
    }
}