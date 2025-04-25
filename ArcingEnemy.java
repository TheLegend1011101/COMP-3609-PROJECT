// import java.awt.Graphics2D;
// import java.awt.Color;
// import java.util.ArrayList;
// import java.util.List;

// public class ArcingEnemy extends Enemy {

//     private long lastShotTime = 0;
//     private final long SHOOT_INTERVAL = 2000; // Adjust as needed
//     private List<ArcingBullet> arcingBullets = new ArrayList<>(); // Use ArcingBullet
//     private Level level;

//     public ArcingEnemy(int startX, int startY, Level level) {
//         super(startX, startY); // Call the constructor of the Enemy class
//         this.level = level;
//         this.setHealth(20); // Adjust health as needed.
//     }

//     @Override
//     public void update() {
//         super.update(); // Move like a regular enemy (e.g., downwards)
//         shootArcing();
//         // Update arcing bullets.  This might be better in Level, but we'll do it here for now.
//         for (ArcingBullet bullet : arcingBullets) {
//             bullet.update();
//         }
//     }

//     public void shootArcing() {
//         long currentTime = System.currentTimeMillis();
//         if (currentTime - lastShotTime >= SHOOT_INTERVAL) {
//             ArcingBullet bullet = new ArcingBullet(getX() + getWidth() / 2, getY() + getHeight(), Bullet.BulletOwner.ENEMY, getDamage());
//             arcingBullets.add(bullet);
//             lastShotTime = currentTime;
//             level.addArcingBullets(arcingBullets); // Add to Level's list
//         }
//     }

//     @Override
//     public void draw(Graphics2D g2) {
//         g2.setColor(Color.YELLOW); // Different color for arcing enemies
//         g2.fillRect(getX(), getY(), getWidth(), getHeight());
//         for (ArcingBullet bullet : arcingBullets) {
//             bullet.draw(g2);
//         }
//     }

    
// }

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArcingEnemy extends Enemy {

    private long lastShotTime = 0;
    private final long SHOOT_INTERVAL = 2000; // Adjust as needed
    private List<ArcingBullet> arcingBullets = new ArrayList<>(); // Use ArcingBullet
    private Level level;
    private Random random = new Random();

    public ArcingEnemy(int startX, int startY, Level level) {
        super(startX, startY); // Call the constructor of the Enemy class
        this.level = level;
        this.setHealth(20); // Adjust health as needed.
    }

    @Override
    public void update() {
        super.update(); // Move like a regular enemy (e.g., downwards)
        shootArcing();
        // Update arcing bullets.  This might be better in Level, but we'll do it here for now.
        for (ArcingBullet bullet : arcingBullets) {
            bullet.update();
        }
    }

    public void shootArcing() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= SHOOT_INTERVAL) {
            // Define initial velocities for the arcing bullet
            double initialVelX = (random.nextDouble() * 6) - 3; // Random horizontal velocity (-3 to 3)
            double initialVelY = 10 + (random.nextDouble() * 5);  // Random upward vertical velocity (10 to 15)

            ArcingBullet bullet = new ArcingBullet(getX() + getWidth() / 2, getY() + getHeight(), Bullet.BulletOwner.ENEMY, getDamage(), initialVelX, initialVelY);
            arcingBullets.add(bullet);
            lastShotTime = currentTime;
            level.addArcingBullets(arcingBullets); // Add to Level's list
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW); // Different color for arcing enemies
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
        for (ArcingBullet bullet : arcingBullets) {
            bullet.draw(g2);
        }
    }
}