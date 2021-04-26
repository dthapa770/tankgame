package tankrotationexample.menus;

import tankrotationexample.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JLabel start;
    private JButton exit;
    private Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("spritemap/end.png")));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }

        this.setBackground(Color.GREEN);
        this.setLayout(null);

        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD ,24));
        exit.setBounds(150,400,175,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


       // this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
