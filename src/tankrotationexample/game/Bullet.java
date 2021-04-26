package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.game.wall.BreakWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class Bullet extends GameObject {

    private final int R= 4;
    protected int bx;
    protected int by;
    protected int angle;
    boolean alive;
    private static BufferedImage bullet;
    private Rectangle bound = new Rectangle(this.x, this.y, this.bullet.getWidth(), this.bullet.getHeight());

    Tank tank;

    static {
        bullet = Resource.getResourceImage("bullet");
    }

    Bullet(Tank tank, int x, int y, int angle){
        super(x,y, bullet);
        this.angle = angle;
        alive = true;
        this.tank = tank;
    }

    public boolean isAlive() {
        return alive;
    }

    public void update() {
        bx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        by = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += bx;
        y += by;
        checkCollision(this);
        updateBounds();

    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bullet.getWidth() / 2.0, this.bullet.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bullet, rotation, null);

    }

    public void checkCollision(Bullet bullet){
        GameObject obj;
        Rectangle tbound = bullet.getBounds();
        for (int i = 0; i< TRE.gameObjects.size(); i++){
            obj = TRE.gameObjects.get(i);
            if (tbound.intersects(obj.getBounds()) && obj != tank){
                if(obj instanceof BreakWall) {
                    TRE.gameObjects.remove(obj);
                }
                if(obj instanceof Tank){
                    ((Tank) obj).takeDamage();
                }
                alive = false;
            }
        }
    }

    public Rectangle getBounds(){
        return this.bound;
    }

    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, bullet.getWidth(), bullet.getHeight());
    }
}
