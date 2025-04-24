import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.*;
public abstract class Level {
    public abstract void handleKeyPress(KeyEvent e);
    public abstract void handleKeyRelease(KeyEvent e);

    public void update() {
        // Update the level state (e.g., move enemies, check collisions)
    }
    public void draw(Graphics2D g) {
        // Draw the level (e.g., player, enemies, bullets)
    }
    public abstract void addBullets(List<Bullet> bullets);
}
