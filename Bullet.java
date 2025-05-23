import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class Bullet {
    protected int x, y; // Position of the bullet
    protected int speed; // Speed of the bullet
    protected int width = 30, height = 30; // Bullet dimensions
    protected BulletOwner owner; // Owner of the bullet (player or enemy)
    protected int damage; // Damage dealt by the bullet
    protected BufferedImage image;
    
    public int getSpeed() {
        return speed;
    }

    public enum BulletOwner {
        PLAYER,
        ENEMY
    }

    public abstract void update(); // Abstract method to update the bullet's position

    public abstract void draw(Graphics2D g); // Abstract method to draw the bullet

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return 5; // Bullet width
    }

    public int getHeight() {
        return 10; // Bullet height
    }

    // public int getSpeed() {
    // return speed; // Return the speed of the bullet
    // }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Create and return the bounding box
    }

    public BulletOwner getOwner() {
        return owner; // Return the owner of the bullet
    }

    
    public int getDamage() {
        return damage; // Return the damage of the bullet
    }
}
