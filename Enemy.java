import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

public class Enemy {
    protected int x, y;
    private int width = 40, height = 40;
    private int speed = 2;
    private int health; // Health of the enemy
    private int damage; // Damage dealt by the enemy
    private Level level;
    private long lastShotTime = 0; // Time of the last shot
    private List<Bullet> bullets = new ArrayList<>();
    private Image alienImage;

    public Enemy(int startX, int startY, Level level) {
        this(startX, startY);
        this.level = level;
    }

    public Enemy(int startX, int startY) {
        this.x = startX; // Set the starting x position
        this.y = startY; // Set the starting y position
        this.health = 30; // Default health value
        this.damage = 10; // Default damage value
        this.alienImage = ImageManager.loadImage("images/Alien.gif"); // Load the alien GIF
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Create and return the bounding box
    }

    // Move down and across
    public void update() {
        y += speed; // Move downward
        shoot();
    }

    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 2000) {
            bullets.add(new SpaceBullet(x + width / 2, y + height, Bullet.BulletOwner.ENEMY, damage)); // Create a new
                                                                                                       // bullet
            lastShotTime = currentTime;
        }
        level.addBullets(bullets);
    }

    // Check if the enemy is off-screen
    public boolean isOffScreen() {
        return y > 500; // Assuming screen height is 500
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHealth() {
        return health; // Return the health of the enemy
    }

    public void setHealth(int health) {
        this.health = health; // Set the health of the enemy
    }

    public int getDamage() {
        return damage; // Return the damage dealt by the enemy
    }

    public void draw(Graphics2D g2) {
        if (alienImage != null) {
            g2.drawImage(alienImage, x, y, width, height, null); // Draw the alien image
        } else {
            g2.setColor(Color.GREEN); // Set color for the enemy
            g2.fillRect(x, y, width, height); // Draw the enemy as a rectangle (for simplicity)
        }
    }
}