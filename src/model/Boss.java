
package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import static model.Marine.getImage;
import view.GamePanel;

public class Boss extends GameFigure {
   
    private final int healthFactor = 3;
    private double dx = 4;
    private final double dy = 0;
    private int updateCount = 0;
    
    private final Image bossImage;
    public boolean movingLeft, movingRight;
    private final int bossWidth, bossHeight;
    private final Random random;
    
    public Boss(int x, int y, int w, int h){
        super(x, y, w);
        random = new Random();
        this.bossWidth = w;
        this.bossHeight = h;
        this.health = this.size * healthFactor;
        movingRight = movingLeft = false;
        String imagePath = System.getProperty("user.dir");
        // separator: Windows '\', Linux '/'
        String separator = System.getProperty("file.separator");

        // put images in 'ImagesFolder' folder, which is on the top level of
        // the NetBeans project folder.
        // Using "Files" tab of the NetBeans explorer window, right click on
        // the project folder name, and create a folder named "ImagesFolder"
        // You cannot see "ImagesFolder" folder in 'Project' tab, though
        //launcherImage = getImage(imagePath + separator + "images" + separator
        bossImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + "Boss.png");
    }

    public void resetHealth(){
        this.health = this.size * healthFactor;
    }
    
    public void shoot(){
        double targetX = Main.gameData.marine.x + Main.gameData.marine.size/2;
        double targetY = Main.gameData.marine.y;
        
        if(random.nextBoolean())
        Main.gameData.addEnemyBullet(super.x, super.y + this.bossHeight/2, targetX, targetY, Color.red);
        else
        Main.gameData.addEnemyBullet(super.x + super.size, super.y + this.bossHeight/2, targetX, targetY, Color.red);
    }
    
    
    
    @Override
    public void render(Graphics g) {
        
        if (dx >= 0)
        g.drawImage(bossImage, (int)super.x, (int)super.y, (int)this.bossWidth, (int)this.bossHeight, null);
        else  g.drawImage(bossImage, (int)super.x + (int)this.bossWidth, (int)super.y, -(int)this.bossWidth, (int)this.bossHeight, null);
        
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, (int)super.size, 2); 
        
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, (int)health/healthFactor, 2);
    }

    @Override
    public void update() {
       
        updateCount++;
        if(this.updateCount == 15){
            shoot();
            this.updateCount = 0;
        }
        
        super.x += dx;
        
        if (super.x + size > GamePanel.PWIDTH) {
            dx = -dx;
            super.x = GamePanel.PWIDTH - size;
        } 
        else if (super.x - size < 0) {
            dx = -dx;
            super.x = size;
        }
    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
       return new Rectangle2D.Double(super.x, super.y, this.bossWidth, this.bossHeight);
    }
}

