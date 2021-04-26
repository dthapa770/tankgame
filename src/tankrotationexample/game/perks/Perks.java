package tankrotationexample.game.perks;

import tankrotationexample.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Perks extends GameObject {
    Perks(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    public  void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img,x,y, null);
    }
    public void update() {
    }
}

