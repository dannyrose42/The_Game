package view;

import controller.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import model.GameFigure;
import static model.Marine.getImage;

public class GamePanel extends JPanel {

    public static final int PWIDTH = 1000; // size of the game panel
    public static final int PHEIGHT = 540;


    // off screen rendering
    private Graphics graphics;
    private Image dbImage = null;
    private Image bgImage;

    public GamePanel() {
        setBackground(Color.blue);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        bgImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + "Background.jpg");
    }

    public void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = dbImage.getGraphics();
            }
        }

        graphics.drawImage(bgImage , 0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT, null);

        Main.gameData.marine.render(graphics);
        
        synchronized (Main.gameData.enemys) {
            for (GameFigure f : Main.gameData.enemys) {
                f.render(graphics);
            }
        }
        synchronized (Main.gameData.bullets) {
            for (GameFigure b : Main.gameData.bullets) {
                b.render(graphics);
            }
        }

    }

    // use active rendering to put the buffered image on-screen
    public void printScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
