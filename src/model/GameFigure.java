package model;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public abstract class GameFigure implements Collision {

    public double x; // for a faster access
    public double y;
    public double size;
    public double health;
    public Point2D.Double location;
    public boolean hit;

    public GameFigure(double x, double y, double size) {
        this.hit = false;
        this.x = x;
        this.y = y;
        this.size = size;
        this.location = new Point2D.Double(x - (size/2), y-(size/2));
    }
    
    public Point2D.Double getLocation(){
        return location;
    }

    
    public abstract void render(Graphics g);

    public abstract void update();

}
