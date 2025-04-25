// import java.awt.Rectangle;
// import java.util.ArrayList;
// import java.util.List;
// import java.awt.Graphics2D;
// import java.awt.Color;
// import java.awt.Image;

// public class Enemy {
//     protected int x, y;
//     private int width = 40, height = 40;
//     private int speed = 2;
//     private int health; // Health of the enemy
//     private int damage; // Damage dealt by the enemy
//     private Level level;
//     private long lastShotTime = 0; // Time of the last shot
//     private List<Bullet> bullets = new ArrayList<>();
//     private Image alienImage;

//     public Enemy(int startX, int startY, Level level) {
//         this(startX, startY);
//         this.level = level;
//     }

//     public Enemy(int startX, int startY) {
//         this.x = startX; // Set the starting x position
//         this.y = startY; // Set the starting y position
//         this.health = 30; // Default health value
//         this.damage = 10; // Default damage value
//         //this.alienImage = ImageManager.loadImage("imags/space_00.gif"); // Load the alien GIF
//     }

//     public Rectangle getBounds() {
//         return new Rectangle(x, y, width, height); // Create and return the bounding box
//     }

//     // Move down and across
//     public void update() {
//         y += speed; // Move downward
//         shoot();
//     }

//     public void shoot() {
//         long currentTime = System.currentTimeMillis();
//         if (currentTime - lastShotTime >= 2000) {
//             bullets.add(new SpaceBullet(x + width / 2, y + height, Bullet.BulletOwner.ENEMY, damage)); // Create a new
//                                                                                                        // bullet
//             lastShotTime = currentTime;
//         }
//         level.addBullets(bullets);
//     }

//     // Check if the enemy is off-screen
//     public boolean isOffScreen() {
//         return y > 500; // Assuming screen height is 500
//     }

//     public int getX() {
//         return x;
//     }

//     public void setX(int x) {
//         this.x = x;
//     }

//     public int getY() {
//         return y;
//     }

//     public void setY(int y) {
//         this.y = y;
//     }

//     public int getWidth() {
//         return width;
//     }

//     public int getHeight() {
//         return height;
//     }

//     public int getHealth() {
//         return health; // Return the health of the enemy
//     }

//     public void setHealth(int health) {
//         this.health = health; // Set the health of the enemy
//     }

//     public int getDamage() {
//         return damage; // Return the damage dealt by the enemy
//     }

//     public void draw(Graphics2D g2) {
//         if (alienImage != null) {
//             g2.drawImage(alienImage, x, y, width, height, null); // Draw the alien image
//         } else {
//             g2.setColor(Color.GREEN); // Set color for the enemy
//             g2.fillRect(x, y, width, height); // Draw the enemy as a rectangle (for simplicity)
//         }
//     }
// }

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
    private Animation alienAnimation; // Use Animation class

    public Enemy(int startX, int startY, Level level) {
        this(startX, startY);
        this.level = level;
        loadAnimation(); // Initialize animation
    }

    public Enemy(int startX, int startY) {
        this.x = startX; // Set the starting x position
        this.y = startY; // Set the starting y position
        this.health = 30; // Default health value
        this.damage = 10; // Default damage value
        loadAnimation(); // Initialize animation
    }

    private void loadAnimation() {
        alienAnimation = new Animation(true); // Loop the animation

        // Load the two alien PNG images
        Image alien1 = ImageManager.loadImage("images/space__0000_A1.png");
        Image alien2 = ImageManager.loadImage("images/space__0001_A2.png");

        // Add frames to the animation with a certain duration (milliseconds per frame)
        long frameDuration = 150; // Adjust for animation speed

        if (alien1 != null && alien2 != null) {
            alienAnimation.addFrame(alien1, frameDuration);
            alienAnimation.addFrame(alien2, frameDuration);
            alienAnimation.start(); // Start the animation
        } else {
            System.err.println("Error loading alien animation frames!");
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Create and return the bounding box
    }

    // Move down and across
    public void update() {
        y += speed; // Move downward
        shoot();
        alienAnimation.update(); // Update the animation frame
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
        Image currentFrame = alienAnimation.getImage();
        if (currentFrame != null) {
            g2.drawImage(currentFrame, x, y, width, height, null);
        } else {
            // Fallback drawing if animation fails to load
            g2.setColor(Color.MAGENTA);
            g2.fillRect(x, y, width, height);
        }
    }
}