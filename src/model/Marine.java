package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class Marine extends GameFigure {

    private final Image launcherImage;
    private int jumpHeight = 0;
    private int dy = -7;
    public boolean jump, movingLeft, movingRight;

    public Marine(int x, int y, int size) {
        super(x, y, size);
        this.health = 100;
        movingRight = jump = movingLeft = false;
        String imagePath = System.getProperty("user.dir");
        // separator: Windows '\', Linux '/'
        String separator = System.getProperty("file.separator");

        launcherImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + "Marine.png");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(launcherImage, (int) super.x, (int) super.y, (int) super.size, (int) super.size, null);

        g.setColor(Color.red);
        g.fillRect(3, GamePanel.PHEIGHT - 102, 10, 100);

        g.setColor(Color.green);
        g.fillRect(3, GamePanel.PHEIGHT - (int) this.health - 2, 10, (int) this.health);
    }

    @Override
    public void update() {
        if (movingLeft) {
            translate(-7, 0);
        }
        if (movingRight) {
            translate(7, 0);
        }
        if (jump) {
            jump();
        }
    }

    public void jump() {
        jumpHeight += dy;
        if (jumpHeight <= -100) {
            dy = 7;
        }
        if (jumpHeight == 0) {
            jump = false;
            dy = -7;
            super.y = GamePanel.PHEIGHT - 30;
            return;
        }
        super.y += dy;
    }

    public void translate(int dx, int dy) {
        if (super.x <= 0 && dx < 0) {
            dx = 0;
        }
        if (((super.x + 30) >= GamePanel.PWIDTH) && (dx > 0)) {
            dx = 0;
        }
        super.x += dx;
        super.y += dy;
    }

    public void resetMarine() {
        this.health = 100;
        jump = movingLeft = movingRight = false;
        jumpHeight = 0;
        jump = false;
        dy = -7;
        super.y = GamePanel.PHEIGHT - 30;
        super.x = GamePanel.PWIDTH/2;
    }

    public static Image getImage(String fileName) {
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }

    @Override
    public String toString() {
        return super.x + ", " + super.y;
    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y, super.size, super.size);
    }

}
