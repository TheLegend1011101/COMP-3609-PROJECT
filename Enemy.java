import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
public class Enemy {
    protected int x, y;
    private int width = 40, height = 40;
    private int speed = 2;
    private int health;  // Health of the enemy
    private int damage;  // Damage dealt by the enemy
    // public Enemy(int startX, int startY, int health, int damage) {
    //     this.health = health;  // Initialize health
    //     this.x = startX;
    //     this.y = startY;
    //     this.damage = damage;  // Initialize damage
    // }

    public Enemy(int startX, int startY) {
        this.x = startX;  // Set the starting x position
        this.y = startY;  // Set the starting y position
        this.health = 100;  // Default health value
        this.damage = 10;  // Default damage value
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Create and return the bounding box
    }
    // Move down and across
    public void update() {
        y += speed;  // Move downward
    }

    // Check if the enemy is off-screen
    public boolean isOffScreen() {
        return y > 500;  // Assuming screen height is 500
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHealth() {
        return health;  // Return the health of the enemy
    }

    public int getDamage() {
        return damage;  // Return the damage dealt by the enemy
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);  // Set color for the enemy
        g2.fillRect(x, y, width, height);  // Draw the enemy as a rectangle (for simplicity)
}
}
