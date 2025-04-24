import java.awt.Graphics2D;
import java.awt.Color;
public class PlatformBullet extends Bullet {
    private int x, y;
    private int speed;
    private int direction; // 1 for right, -1 for left

    public PlatformBullet(int x, int y, int direction, BulletOwner owner, int damage) {
        this.x = x;
        this.y = y;
        this.speed = 10;
        this.direction = direction;
        this.owner = owner;  // Set the owner of the bullet
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);  // Set color for the bullet
        g.fillRect(x, y, 5, 10);  // Draw the bullet as a rectangle (for simplicity)
    }

    public void update() {
        x += speed * direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
