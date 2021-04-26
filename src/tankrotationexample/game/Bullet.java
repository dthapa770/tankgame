package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.game.wall.BreakWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/*
public class Bullet extends GameObject {
    int x, y, vx, vy;
    float angle;
    int R = 7;
    BufferedImage bulletImage;
    Rectangle hitBox;
    Boolean alive;
    Tank tank;

    public Bullet( int x, int y, float angle, BufferedImage bulletImage) {
        super( x, y, bulletImage);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        alive = true;
        this.hitBox = new Rectangle(x, y, this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }

    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void checkBorder() { //might not be void???
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }

    }

    public Boolean isAlive() {
        return alive;
    }

    public void update() {
        moveForwards();
        updateBounds();
        checkCollision(this);
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bulletImage.getWidth() / 2.0, this.bulletImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage, rotation, null);
    }

    public void checkCollision(Bullet bullet) {
        GameObject obj;
        Rectangle bulletBound = bullet.getBounds();

        for (int i = 0; i < GameMap.gameObjects.size(); i++) {
            System.out.println(GameMap.gameObjects.size());
            obj =  GameMap.gameObjects.get(i);

            if (bulletBound.intersects(obj.getBounds()) && obj != tank) {
                if (obj instanceof BreakWall) {
                    GameMap.gameObjects.remove(obj);
                }
                if (obj instanceof Tank) {
                    ((Tank) obj).damage();
                }
                alive = false;
            }
        }
    }

    public Rectangle getBounds() {
        return this.hitBox;
    }

}
*/

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
