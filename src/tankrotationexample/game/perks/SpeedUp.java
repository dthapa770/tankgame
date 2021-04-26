package tankrotationexample.game.perks;

import tankrotationexample.game.perks.Perks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedUp  extends Perks {
    Rectangle bounds;
    public SpeedUp(int x, int y, BufferedImage img) {
        super(x, y, img);
        this.bounds = new Rectangle(x,y,img.getWidth(),img.getHeight());
        gameObject.add(this);
    }
}
