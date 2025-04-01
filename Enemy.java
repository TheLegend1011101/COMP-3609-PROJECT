import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
public class Enemy {
    private int x, y;
    private int width = 40, height = 40;
    private int speed = 2;

    public Enemy(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Create and return the bounding box
    }
    // Move down and across
    public void update() {
        y += speed;  // Move downward
    }

    // Check if the enemy is off-screen
    public boolean isOffScreen() {
        return y > 500;  // Assuming screen height is 500
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);  // Set color for the enemy
        g2.fillRect(x, y, width, height);  // Draw the enemy as a rectangle (for simplicity)
}
}
