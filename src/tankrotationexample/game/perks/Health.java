package tankrotationexample.game.perks;

import tankrotationexample.game.perks.Perks;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Health extends Perks {
    Rectangle bounds;

    public Health(int x, int y, BufferedImage img) {
        super(x, y, img);
        this.bounds = new Rectangle(x,y,img.getWidth(),img.getHeight());
        gameObject.add(this);
    }
}
