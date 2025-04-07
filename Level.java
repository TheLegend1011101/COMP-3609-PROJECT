import java.awt.Graphics2D;
import java.awt.event.*;
public abstract class Level {
    public void handleKeyPress(KeyEvent e) {
        // Handle key press events for the level
    }
    public void handleKeyRelease(KeyEvent e) {
        // Handle key release events for the level
    }

    public void update() {
        // Update the level state (e.g., move enemies, check collisions)
    }
    public void draw(Graphics2D g) {
        // Draw the level (e.g., player, enemies, bullets)
    }
}
