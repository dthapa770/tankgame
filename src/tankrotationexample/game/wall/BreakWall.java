package tankrotationexample.game.wall;

import tankrotationexample.game.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall {


    Rectangle bounds;

    public BreakWall(int x, int y, BufferedImage img) {
        super(x,y,img);
        this.bounds= new Rectangle(x,y, this.img.getWidth(), this.img.getHeight());
        gameObject.add(this);
    }
}
