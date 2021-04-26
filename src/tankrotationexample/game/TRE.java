/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.game.perks.Life;
import tankrotationexample.game.perks.Health;
import tankrotationexample.game.perks.SpeedUp;
import tankrotationexample.game.wall.BreakWall;
import tankrotationexample.game.wall.UnBreakWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {


    private BufferedImage world;
    public static BufferedImage bufferedImage;

    private Graphics2D buffer;
    private JFrame jFrame;
    private Launcher lf;
    private Tank t1;
    private Tank t2;
    static long tick = 0;

    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public TRE(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            while (true) {
                tick++;

                this.repaint();
                Thread.sleep(1000 / 144);
                GameObject.gameObject.forEach(GameObject::update);

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        try {

            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(TRE.class.getClassLoader().getResourceAsStream("maps/map1")));


            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCol = Integer.parseInt(mapInfo[0]);
            int numRow = Integer.parseInt(mapInfo[1]);

            for (int curRow = 0; curRow < numRow; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCol; curCol++) {
                    switch (mapInfo[curCol]) {
                        case "2":
                            gameObjects.add(new BreakWall(curCol * 30, curRow * 30, Resource.getResourceImage("breakableWall")));
                            break;
                        case "3":
                        case "9":
                            gameObjects.add(new UnBreakWall(curCol * 30, curRow * 30, Resource.getResourceImage("unBreakableWall")));
                            break;
                        case "4":
                            gameObjects.add(new Life(curCol * 30, curRow * 30, Resource.getResourceImage("life")));
                            break;
                        case "5":
                            gameObjects.add(new SpeedUp(curCol * 30, curRow * 30, Resource.getResourceImage("speed")));
                            break;
                        case "6":
                            gameObjects.add(new Health(curCol * 30, curRow * 30, Resource.getResourceImage("health")));
                            break;

                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(200, 200, 0, 0, 0, Resource.getResourceImage("t1"));
        t2 = new Tank(1900, 1600, 0, 0, 0, Resource.getResourceImage("t2"));


        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER);
        GameObject.gameObject.add(t1);
        GameObject.gameObject.add(t2);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        Graphics2D buffer = world.createGraphics();
       // buffer.setColor(Color.BLACK);
       // buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        buffer.drawImage(Resource.getResourceImage("background"), 0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT, null);

        // this.t1.drawImage(buffer);
        //  this.t2.drawImage(buffer);
        gameObjects.forEach(gameObjects -> gameObjects.drawImage(buffer));

        splitScreen(g2);

        AffineTransform minimap = AffineTransform.getTranslateInstance(GameConstants.SCREEN_HEIGHT / 1.35, GameConstants.SCREEN_WIDTH/2.9);
        minimap.scale(0.12, 0.12);
        g2.drawImage(world, minimap, null);

        healthAndLivesDisplay(g2);

        if (this.t1.isWon() || this.t2.isWon()) {
            this.lf.setFrame("end");
        }
    }

    public void healthAndLivesDisplay(Graphics g2) {

        g2.setFont(new Font("", Font.BOLD, 25));

        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t2.getLives(), GameConstants.GAME_SCREEN_WIDTH * 65 / 80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 2 Health: " + this.t2.getHealth(), GameConstants.GAME_SCREEN_WIDTH * 50 / 80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t2.getHealth() && i < 100; i++) {
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 50 / 80 + i, GameConstants.GAME_SCREEN_HEIGHT * 36 / 40, 60, 30);
        }

        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t1.getLives(), GameConstants.GAME_SCREEN_WIDTH * 27 / 80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 1 Health: " + this.t1.getHealth(), GameConstants.GAME_SCREEN_WIDTH * 12 / 80, GameConstants.GAME_SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t1.getHealth() && i < 100; i++) {
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 13 / 80 + i, GameConstants.GAME_SCREEN_HEIGHT * 36 / 40, 60, 30);
        }
    }

    public void splitScreen(Graphics g2) {

        BufferedImage leftHalf = world.getSubimage(checkBoundsX(t1), checkBoundsY(t1), GameConstants.SCREEN_WIDTH / 2, GameConstants.SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(checkBoundsX(t2), checkBoundsY(t2), GameConstants.SCREEN_WIDTH / 2, GameConstants.SCREEN_HEIGHT);
        g2.drawImage(leftHalf, 0, 0, null);
        g2.drawImage(rightHalf, (GameConstants.SCREEN_WIDTH / 2) + 4, 0, null);
    }

    private int checkBoundsX(Tank tank1) {
        int x = (int) (tank1.getX() - (GameConstants.WORLD_WIDTH/3.2));

        if (x < 0) {
            x = 0;
        } else if (x > GameConstants.WORLD_WIDTH - GameConstants.SCREEN_HEIGHT / 2) {
            x = GameConstants.WORLD_HEIGHT - GameConstants.SCREEN_HEIGHT / 2;
        }
        return x;
    }

    public int checkBoundsY(Tank tank2) {
        int y = tank2.getY()-10;
        if (y < 0) {
            y = 0;
        } else if (y > GameConstants.WORLD_WIDTH - GameConstants.SCREEN_HEIGHT) {
            y = GameConstants.WORLD_HEIGHT - GameConstants.SCREEN_HEIGHT;
        }
        return y;
    }

}
