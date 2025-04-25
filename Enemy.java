import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import java.awt.Color;

public class Enemy {
    protected int x, y;
    private int width = 40, height = 40;
    private int speed = 2;
    protected int health; // Health of the enemy
    protected int damage; // Damage dealt by the enemy
    protected Level level;
    protected long lastShotTime = 0; // Time of the last shot
    protected List<Bullet> bullets = new ArrayList<>();
    private Animation alienAnimation; // Use Animation class
    protected BufferedImage image;;

    public Enemy(int startX, int startY, Level level) {
        this(startX, startY);
        this.level = level;
        loadAnimation(); // Initialize animation in the base class
    }

    public Enemy(int startX, int startY) {
        this.x = startX; // Set the starting x position
        this.y = startY; // Set the starting y position
        this.health = 30; // Default health value
        this.damage = 10; // Default damage value
        loadAnimation(); // Initialize animation in the base class
    }

    // This method should be overridden by subclasses to load their specific
    // animations
    protected void loadAnimation() {
        alienAnimation = new Animation(true); // Loop the animation
        // Load default animation frames (if any)
        Image defaultAlien1 = ImageManager.loadImage("images/space__0000_A1.png");
        Image defaultAlien2 = ImageManager.loadImage("images/space__0001_A2.png");
        long frameDuration = 150;
        if (defaultAlien1 != null && defaultAlien2 != null) {
            alienAnimation.addFrame(defaultAlien1, frameDuration);
            alienAnimation.addFrame(defaultAlien2, frameDuration);
            alienAnimation.start();
        } else {
            System.err.println("Warning: Could not load default alien animation frames.");
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Create and return the bounding box
    }

    // Move down and across
    public void update() {
        y += speed; // Move downward
        shoot();
        if (alienAnimation != null) {
            alienAnimation.update(); // Update the animation frame
        }
    }

    public void shoot() {
        bullets.clear(); // Clear the bullets list before adding new ones
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 2000) {
            bullets.add(new SpaceBullet(x + width / 2, y + height, Bullet.BulletOwner.ENEMY, damage)); // Create a new
                                                                                                       // bullet
            lastShotTime = currentTime;
        }
        if (level != null) {
            level.addBullets(bullets);
        }
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
        if (alienAnimation != null) {
            Image currentFrame = alienAnimation.getImage();
            if (currentFrame != null) {
                g2.drawImage(currentFrame, x, y, width, height, null);
            } else {
                // Fallback drawing if animation fails to load
                g2.setColor(Color.RED);
                g2.fillRect(x, y, width, height);
            }
        } else {
            // Fallback drawing if animation object is null
            g2.setColor(Color.BLUE);
            g2.fillRect(x, y, width, height);
        }
    }
}