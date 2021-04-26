package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected BufferedImage img;
    protected Rectangle bounds;
    public static ArrayList<GameObject> gameObject = new ArrayList<>();


    public GameObject( int x, int y, BufferedImage img) {
        this.x =x;
        this.y=y;
        this.img =img;
        this.bounds =  new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public  void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, x, y, null);
    }
    public  void update() {
       this.bounds= new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public void updateBounds(){
        this.bounds = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
