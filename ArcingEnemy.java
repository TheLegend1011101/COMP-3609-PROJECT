import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ArcingEnemy extends Enemy {

    private long lastShotTime = 0;
    private final long SHOOT_INTERVAL = 2000; // Adjust as needed
    private List<ArcingBullet> arcingBullets = new ArrayList<>(); // Use ArcingBullet
    private Level level;

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
            ArcingBullet bullet = new ArcingBullet(getX() + getWidth() / 2, getY() + getHeight(), Bullet.BulletOwner.ENEMY, getDamage());
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

    // Inner class for the arcing projectile
    public static class ArcingBullet extends Bullet {
        private double angle = Math.PI / 4; // Initial angle (45 degrees upwards)
        private double velocity = 5;     // Initial velocity
        private double gravity = 0.5;   // Gravity affecting the bullet's path
        private double xVelocity;
        private double yVelocity;

        public ArcingBullet(int startX, int startY, BulletOwner owner, int damage) {
            this.x = startX;
            this.y = startY;
            this.owner = owner; // Set the owner of the bullet
            xVelocity = velocity * Math.cos(angle);
            yVelocity = velocity * Math.sin(angle);
        }

       
        @Override
        public void update() {
            x += xVelocity;
            y += yVelocity;
            yVelocity += gravity; // Apply gravity
        }

        @Override
        public void draw(Graphics2D g2) {
             g2.setColor(Color.ORANGE);
             g2.fillOval(x, y, 8, 8); // Draw as a small circle
        }
    }
}