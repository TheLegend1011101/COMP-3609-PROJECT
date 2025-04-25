import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class CircularEnemy extends Enemy {
    private int centerX, centerY; // Center of the circular path
    private int radius;
    private int degree;
    private int angularSpeed = 24; // Degrees per frame (adjust for speed)
    private boolean active = true;

    public CircularEnemy(int centerX, int centerY, Level level, int radius) {
        super(centerX + radius, centerY, level); // Start at right-most point of circle
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.degree = 0;
    }

    @Override
    public void update() {
        if (!active) return;
        
        // Update angle (clockwise motion)
        degree += angularSpeed;
        if (degree > 360) {
            degree = 0;
        }
        
        // Convert degrees to radians
        double radians = Math.toRadians(degree);
        
        // Calculate new position
        x = (int)(centerX + radius * Math.cos(radians));
        y = (int)(centerY + radius * Math.sin(radians));
        
        // Enemy can still shoot while moving
        shoot();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!active) return;
        
        // Draw enemy (using ellipse for circular appearance)
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Double(x, y, getWidth(), getHeight()));
        
        // Optional: Draw center point (for debugging)
        // g2.setColor(Color.GREEN);
        // g2.fill(new Ellipse2D.Double(centerX-5, centerY-5, 10, 10));
    }

    @Override
    public boolean isOffScreen() {
        // Check if entire circle area is off-screen
        int margin = radius + Math.max(getWidth(), getHeight());
        return (centerX + margin < 0) || 
               (centerX - margin > 600) || 
               (centerY + margin < 0) || 
               (centerY - margin > 500);
    }

    // Getters and setters
    public int getAngularSpeed() { return angularSpeed; }
    public void setAngularSpeed(int speed) { this.angularSpeed = speed; }
    public int getRadius() { return radius; }
    public void setRadius(int radius) { this.radius = radius; }
}