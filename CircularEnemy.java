// import java.awt.Graphics2D;
// import java.awt.Color;

// public class CircularEnemy extends Enemy {
//     private double centerX, centerY; // Center of the circular path
//     private double radius;
//     private double angle; // Current angle in radians
//     private double angularSpeed = 0.05; // Adjust for faster/slower circular movement

//     public CircularEnemy(int startX, int startY, Level level, int circleCenterX, int circleCenterY, int circleRadius) {
//         super(startX, startY, level);
//         this.centerX = circleCenterX;
//         this.centerY = circleCenterY;
//         this.radius = circleRadius;

//         // Initialize the enemy's position on the circle
//         this.angle = Math.atan2(startY - centerY, startX - centerX);
//         this.x = (int) (circleCenterX + radius * Math.cos(angle));
//         this.y = (int) (circleCenterY + radius * Math.sin(angle));
//     }

//     @Override
//     public void update() {
//         angle += angularSpeed; // Increment the angle
//         x = (int) (centerX + radius * Math.cos(angle));
//         y = (int) (centerY + radius * Math.sin(angle));
//         shoot(); // Enemies can still shoot while moving in a circle
//     }

//     // You might need to override isOffScreen() depending on your game's logic
//     @Override
//     public boolean isOffScreen() {
//         // For circular movement, "off-screen" might need a different definition.
//         // Consider if the center of the circle goes off-screen by a certain margin,
//         // or if the enemy itself goes beyond certain boundaries.
//         int enemyWidth = getWidth();
//         int enemyHeight = getHeight();
//         int screenWidth = 800; // Example screen width
//         int screenHeight = 600; // Example screen height

//         // Check if any part of the circle's influence is still on screen
//         double circleLeft = centerX - radius - enemyWidth;
//         double circleRight = centerX + radius + enemyWidth;
//         double circleTop = centerY - radius - enemyHeight;
//         double circleBottom = centerY + radius + enemyHeight;

//         return circleRight < 0 || circleLeft > screenWidth || circleBottom < 0 || circleTop > screenHeight;
//     }

//     public double getCenterX() {
//         return centerX;
//     }

//     public void setCenterX(double centerX) {
//         this.centerX = centerX;
//     }

//     public double getCenterY() {
//         return centerY;
//     }

//     public void setCenterY(double centerY) {
//         this.centerY = centerY;
//     }

//     public double getRadius() {
//         return radius;
//     }

//     public void setRadius(double radius) {
//         this.radius = radius;
//     }

//     public double getAngularSpeed() {
//         return angularSpeed;
//     }

//     public void setAngularSpeed(double angularSpeed) {
//         this.angularSpeed = angularSpeed;
//     }

//     @Override
//     public void setX(int x) {
//         // While the position is primarily determined by the circle,
//         // you might still want to set it directly in some circumstances.
//         // Updating centerX and angle might be more appropriate for circular motion.
//         this.x = x;
//     }

//     @Override
//     public void setY(int y) {
//         this.y = y;
//     }

//     @Override
//     public int getX() {
//         return (int) x;
//     }

//     @Override
//     public int getY() {
//         return (int) y;
//     }

//     @Override
//     public void draw(Graphics2D g2) {
//         g2.setColor(Color.BLUE); // Different color for circular enemies
//         g2.fillRect((int) x, (int) y, getWidth(), getHeight());
//     }
// }

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