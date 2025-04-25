import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
public abstract class Player {
    protected int x, y;
    protected int width, height;
    protected int dx, dy;
    protected Level level;
    protected int bulletsPerShot = 1;
    protected int health = 100;
    protected int damage = 10;  // Damage dealt by the player
    // protected int speed;
    Player(int x, int y, int width, int height, Level level) {
        this.x = x;
        this.y = y;
        dx = 5;
        dy = 5;
        this.width = width;
        this.height = height;
        this.level = level;
    }
    public abstract void update();

    public abstract void draw(Graphics2D g2);

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Create and return the bounding box
    }

    public void setBulletsPerShot(int bullets) {
        if(bullets < 1) {
            bullets = 1;  // Ensure at least one bullet per shot
        }
        if(bullets > 3) {
            bullets = 3;  // Limit to a maximum of 10 bullets per shot
        }
        this.bulletsPerShot = bullets;  // Set the number of bullets per shot
    }
    public int getBulletsPerShot() {
        return bulletsPerShot;  // Get the number of bullets per shot
    }

    public abstract void handleKeyPress(KeyEvent e);
    public abstract void handleKeyRelease(KeyEvent e);
    public int getHealth() {
        return health;  // Get the player's health
    }
    public void setHealth(int health) {
        this.health = health;  // Set the player's health
    }

    public void takeDamage(int damage) {
        this.health -= damage;  // Reduce health by the damage taken
        if (this.health < 0) {
            this.health = 0;  // Ensure health doesn't go below zero
        }
    }
}
