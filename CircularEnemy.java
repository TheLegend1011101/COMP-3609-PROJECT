import java.awt.Graphics2D;
import java.awt.Color;

public class CircularEnemy extends Enemy {
    CircularEnemy(int startX, int startY, Level level){
        super(startX, startY, level);
    }

    @Override
    public void update() {
        angle += angularSpeed; // Increment the angle
        x = (int) (centerX + radius * Math.cos(angle));
        y = (int) (centerY + radius * Math.sin(angle));
    }

    // You might need to override isOffScreen() depending on your game's logic
    @Override
    public boolean isOffScreen() {
        // For circular movement, "off-screen" might need a different definition.
        // Consider if the center of the circle goes off-screen, or a boundary check.
        return false; // Placeholder
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getAngularSpeed() {
        return angularSpeed;
    }

    public void setAngularSpeed(double angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

    @Override
    public void setX(int x) {
        this.x = x; // Still need to update the double value
    }

    @Override
    public void setY(int y) {
        this.y = y; // Still need to update the double value
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.fillRect((int) x, (int) y, getWidth(), getHeight());
    }
}