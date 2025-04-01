import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.event.*;

public class Level {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private boolean levelCompleted;
    private BufferedImage image;  // Image for the level background (if needed)
    private int levelNumber;

    // Constructor
    public Level(int levelNumber) {
        image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        initializeLevel();
    }

    // Initialize the level (e.g., spawn player, enemies, etc.)
    private void initializeLevel() {
        // Initialize the player, enemies, etc.
        this.player = new Player();  // Example player starting position
        spawnEnemies();
    }

    // Spawn enemies (you can modify this to read from a level file or generate enemies dynamically)
    private void spawnEnemies() {
        for (int i = 0; i < 5; i++) {
            enemies.add(new BezierEnemy(100 * (i + 1), 50));  // Example enemy spawn positions
        }
    }

    // Update the level (e.g., update player, enemies, bullets, etc.)
    public void update() {
        if (levelCompleted) {
            // Handle level completion (e.g., proceed to next level)
            return;
        }

        player.update();  // Update player state

        for (Bullet bullet : bullets) {
            bullet.update();  // Update bullet positions
        }

        for (Enemy enemy : enemies) {
            enemy.update();  // Update enemy positions
        }

        checkCollisions();
    }

    // Check collisions between bullets and enemies
    private void checkCollisions() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();

        // Check for collisions
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    // You can add scoring, sounds, or other effects here
                }
            }
        }

        // Remove the bullets and enemies that collided
        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
    }

    // Draw the level
    public void draw(Graphics2D g2) {
        Graphics2D imageContext = (Graphics2D) image.getGraphics();
        imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());
        player.draw(imageContext);  // Draw player
        for (Bullet bullet : bullets) {
            bullet.draw(imageContext);  // Draw bullets
        }
        for (Enemy enemy : enemies) {
            enemy.draw(imageContext);  // Draw enemies
        }
        g2.drawImage(image, 0, 0, null);  // Draw the level background (if any)
        g2.dispose();
    }

    // Add a bullet to the level
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public Player getPlayer() {
        return player;
    }

    // public void setMovingDirection(int direction, boolean isMoving) {
    //     player.setMovingDirection(direction, isMoving);  // Set player movement direction
    // }

    // Level-specific logic to check if the level is completed
    public void checkLevelCompletion() {
        if (enemies.isEmpty()) {
            levelCompleted = true;
        }
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public int getLevelNumber() {
        return levelNumber;
    }


    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            player.setMovingLeft(true);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player.setMovingRight(true);
        }
        if (keyCode == KeyEvent.VK_UP) {
            player.setMovingUp(true);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            player.setMovingDown(true);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            player.shoot();
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            player.setMovingLeft(false);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player.setMovingRight(false);
        }
        if (keyCode == KeyEvent.VK_UP) {
            player.setMovingUp(false);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            player.setMovingDown(false);
        }
    }
}
