package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.game.perks.Life;
import tankrotationexample.game.perks.Health;
import tankrotationexample.game.perks.SpeedUp;
import tankrotationexample.game.wall.BreakWall;
import tankrotationexample.game.wall.UnBreakWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author anthony-pc
 */
/*
public class Tank extends GameObject {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;

    private  int R = 2;
    private final float ROTATIONSPEED = 3.0f;
    private int lives;
    private  int health;
private int damage;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
  //  private Rectangle bound = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
   // private Rectangle hitBox;
    private Rectangle bound;
    private Bullet b;
    private ArrayList<Bullet> ammo;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.ammo = new ArrayList<>();
        this.bound = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.health = 100;
        this.lives = 3;
        GameMap.gameObjects.add(this);

    }

    public Rectangle getBound() {
        return this.bound;
    }
    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    }

  public int getX() {
      return x; }

    public int getY() {
       return y;
    }



    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
           updateBounds();
        }
        if (this.DownPressed) {
            this.moveBackwards();
            updateBounds();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
            updateBounds();
        }
        if (this.RightPressed) {
            this.rotateRight();
            updateBounds();
        }

        if (this.ShootPressed && TRE.tick % 20 == 0) {

           Bullet b = new Bullet(x, y, angle, Resource.getResourceImage("bullet"));
           this.ammo.add(b);
        }
        this.ammo.forEach(Bullet::update);

//        for( int i =0; i<this.ammo.size();i++){
//            this.ammo.get(i).update();
//        }
        checkCollision(this);
    }

    public void checkCollision(Tank tank){
        GameObject obj;
        Rectangle tbound = tank.getBounds();
        for (int i = 0; i< GameMap.gameObjects.size(); i++){
            obj = GameMap.gameObjects.get(i);
            if (tbound.intersects(obj.getBounds())){
                System.out.println("working");
                handle(obj);
            }
        }
    }

    public void handle(GameObject obj){
        if (obj instanceof UnBreakWall){
            if (this.UpPressed){
                this.x -= vx;
                this.y -= vy;
            }
            if (this.DownPressed){
                this.x += vx;
                this.y += vy;
            }
        }
        if (obj instanceof BreakWall){
            if (this.UpPressed){
                this.x -= vx;
                this.y -= vy;
            }
            if (this.DownPressed){
                this.x += vx;
                this.y += vy;
            }
        }
        if (obj instanceof Health){
            if(health < 100) {
                this.health = 100;
                GameMap.gameObjects.remove(obj);
            }
        }
        if( obj instanceof SpeedUp){
            R = 4;
            GameMap.gameObjects.remove(obj);
        }
        if (obj instanceof Shield){
            this.lives += 1;
            GameMap.gameObjects.remove(obj);
        }
    }

    public int getHealth(){
        return this.health;
    }


    public void damage() {
        health -= damage;
        if(health <= 0){
            health = 100;
            lives -=1;
        }
    }

    public int getLives(){
        return this.lives;
    }

    public boolean isWon(){
        if(lives == 0){

            // System.exit(1);
            return true;
        }
        return false;
    }





    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        updateBounds();
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
        updateBounds();
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        updateBounds();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        updateBounds();
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.cyan);
        g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
        g2d.drawImage(this.img, rotation, null);
    }
}
*/

public class Tank extends GameObject{

    private int vx;
    private int vy;
    private int angle;
    private int health;
    private int lives;
    private int R = 2;
    public int damage = 5;
    private final int ROTATIONSPEED = 2;
    private float cooldown = 100;
    private float cooldownTimer = 0;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShotPressed;
    private Rectangle bound = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    private Bullet bullet;
    public ArrayList<Bullet> bulletsList = new ArrayList<>();

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {

        super(x,y, img);
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.health = 100;
        this.lives = 3;
       TRE.gameObjects.add(this);

    }

    void toggleUpPressed() { this.UpPressed = true; }

    void toggleDownPressed() { this.DownPressed = true; }

    void toggleRightPressed() { this.RightPressed = true; }

    void toggleLeftPressed() { this.LeftPressed = true; }

    void toggleShootPressed(){ this.ShotPressed = true; }

    void unToggleUpPressed() { this.UpPressed = false; }

    void unToggleDownPressed() { this.DownPressed = false; }

    void unToggleRightPressed() { this.RightPressed = false; }

    void unToggleLeftPressed() { this.LeftPressed = false; }

    void unToggleShootPressed(){ this.ShotPressed = false; }


    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
            updateBounds();
        }
        if (this.DownPressed) {
            this.moveBackwards();
            updateBounds();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
            updateBounds();

        }
        if (this.RightPressed) {
            this.rotateRight();
            updateBounds();
        }

        if (cooldownTimer < cooldown) {
            cooldownTimer += 1;
        }

        if (this.ShotPressed){
            this.shoot(bullet);
            updateBounds();
        }

        for (int x = 0; x < bulletsList.size(); x++) {
            if (bulletsList.get(x).isAlive()) {
                bulletsList.get(x).update();
            }
        }

        checkCollision(this);

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        updateBounds();
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
        updateBounds();
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        updateBounds();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        updateBounds();
    }

    private void shoot(Bullet bullet){
        if (cooldownTimer >= cooldown) {
            bulletsList.add(new Bullet(this,x,y,angle));
            cooldownTimer = 0;
        }
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_HEIGHT - 88) {
            x = GameConstants.WORLD_HEIGHT- 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + "angle=" + angle + Arrays.toString(bulletsList.toArray());
    }


    public int getHealth(){
        return health;
    }

    public void takeDamage() {
        health -= damage;
        if(health <= 0){
            health = 100;
            lives -=1;
        }
    }

    public int getLives(){
        return lives;
    }

    public boolean isWon(){
        if(lives == 0){
           return true;
        }
        return false;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int x = 0; x < bulletsList.size(); x++) {
            if (bulletsList.get(x).isAlive()) {
                bulletsList.get(x).drawImage(g2d);
            }
        }
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);
    }

    public Rectangle getBounds(){
        return this.bound;
    }

    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    }

    public void checkCollision(Tank tank){
        GameObject obj;
        Rectangle tbound = tank.getBounds();
        for (int i =0; i< TRE.gameObjects.size();i++){
            obj = TRE.gameObjects.get(i);
            if (tbound.intersects(obj.getBounds())){

                handle(obj);
            }
        }
    }

    public void handle(GameObject obj){
        if (obj instanceof UnBreakWall || obj instanceof BreakWall){
            if (this.UpPressed){
                this.x -= vx;
                this.y -= vy;
            }
            if (this.DownPressed){
                this.x += vx;
                this.y += vy;
            }
        }
        if (obj instanceof Health){
          this.health=100;
          TRE.gameObjects.remove(obj);

        }
        if( obj instanceof SpeedUp){
            R = 4;
            TRE.gameObjects.remove(obj);
        }
        if (obj instanceof Life){
            this.lives += 1;
            TRE.gameObjects.remove(obj);
        }
    }
}

