import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
public class Bullet {
    private int x, y, width = 5, height = 10;  // Bullet dimensions
    private int speed = 10;

    public Bullet(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Create and return the bounding box
    }
    // Update the position of the bullet (move up)
    public void update() {
        y -= speed;
    }

    // Check if the bullet is off-screen
    public boolean isOffScreen() {
        return y < 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return 5;  // Bullet width
    }

    public int getHeight() {
        return 10;  // Bullet height
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);  // Set color for the bullet
        g2.fillRect(x, y, width, height);  // Draw the bullet as a rectangle (for simplicity)
    }
}
