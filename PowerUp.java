import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
public class PowerUp {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int speed;

    PowerUp(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.width = 20; // Example width
        this.height = 20; // Example height
        this.speed = 5; // Example speed
    }

    public void update() {
        y += speed;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Create and return the bounding box
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW); // Example color
        g2.fillRect(x, y, width, height); // Draw the power-up as a rectangle
    }

    public int getX() {
        return x; // Get the x-coordinate
    }
    public int getY() {
        return y; // Get the y-coordinate
    }
    
}

