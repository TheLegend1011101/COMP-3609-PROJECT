import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class ProjectileBullet extends Bullet {
    private double timeElapsed;
    private int initialVelocityX;
    private int initialVelocityY;
    private int startX, startY;
    private boolean active;

    public ProjectileBullet(int x, int y, BulletOwner owner, int damage, int direction) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.owner = owner;
        this.damage = damage;
        this.timeElapsed = 0;
        this.active = true;
        
        // Adjust initial velocities based on direction
        this.initialVelocityX = (direction == 2) ? 15 : -15; // Right or left
        this.initialVelocityY = -25; // Upwards

        image = ImageManager.loadBufferedImage("images/enemybullet.png"); // Load projectile bullet image
    }

    @Override
    public void update() {
        if (!active) return;

        timeElapsed += 0.5;
        
        int deltaX = (int)(initialVelocityX * timeElapsed);
        int deltaY = (int)(initialVelocityY * timeElapsed + 4.9 * timeElapsed * timeElapsed);
        
        x = startX + deltaX;
        y = startY + deltaY;
        
        // Deactivate if below screen
        if (y > 500) { // Assuming screen height is 500
            active = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!active) return;
        g2.drawImage(image, x, y, width, height, null); // Draw the bullet image at its current position
        // g2.setColor(Color.ORANGE); // Different color for projectile bullets
        // g2.fill(new Ellipse2D.Double(x, y, width, height));
    }


    public boolean isActive() {
        return active;
    }
}