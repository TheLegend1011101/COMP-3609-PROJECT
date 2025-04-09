import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.event.*;

public class SpaceLevel extends Level {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<PowerUp> powerUps;  // List of power-ups in the level
    private boolean levelCompleted;
    private BufferedImage image;  // Image for the level background (if needed)
    private int levelNumber;

    // Constructor
    public SpaceLevel(int levelNumber) {
        image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.powerUps = new ArrayList<>();  // Initialize the power-ups list
        initializeLevel();
    }

    // Initialize the level (e.g., spawn player, enemies, etc.)
    private void initializeLevel() {
        // Initialize the player, enemies, etc.
        this.player = new SpacePlayer(300, 450, this);  // Example player starting position
        spawnEnemies();
        powerUps.add(new BulletUpPowerUp(100, 100));  // Example power-up spawn position
        powerUps.add(new BulletUpPowerUp(200, 100));  // Example power-up spawn position
    }

    // Spawn enemies (you can modify this to read from a level file or generate enemies dynamically)
    private void spawnEnemies() {
        for (int i = 0; i < 4; i++) {
            // enemies.add(new SineWaveEnemy(100 * (i + 1), 50));  // Example enemy spawn positions
            enemies.add(new SineWaveEnemy(150 * i + 55, 50));
        }
    }

    // Update the level (e.g., update player, enemies, bullets, etc.)
    public void update() {
        if (levelCompleted) {
            // Handle level completion (e.g., proceed to next level)
            return;
        }
        checkCollisions();
        player.update();  // Update player state

        for (Bullet bullet : bullets) {
            bullet.update();  // Update bullet positions
        }

        for (Enemy enemy : enemies) {
            enemy.update();  // Update enemy positions
        }
        for(PowerUp powerUp: powerUps){
            powerUp.update();  // Update power-up positions
        }

        
    }

    // Check collisions between bullets and enemies
    private void checkCollisions() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        List<PowerUp> powerUpsToRemove = new ArrayList<>();  // List to store power-ups to remove

        // Check for collisions
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.getBounds().intersects(enemy.getBounds()) && bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
                    bulletsToRemove.add(bullet);
                    if(enemy.getHealth() <= 0){
                        enemiesToRemove.add(enemy);
                    // You can add scoring, sounds, or other effects here
                }
            }
            if(bullet.getBounds().intersects(player.getBounds()) && bullet.getOwner() == Bullet.BulletOwner.ENEMY){
                bulletsToRemove.add(bullet);
                // Handle player damage or game over logic here
            }
        }
        }
        for(PowerUp powerUp: powerUps){
            // System.out.println("Player bounds: " + player.getBounds());
            // System.out.println("PowerUp bounds: " + powerUp.getBounds());
            if((powerUp.getBounds()).intersects(player.getBounds())){
                // System.out.println("PowerUp collected!");
                powerUpsToRemove.add(powerUp);
                if(powerUp instanceof BulletUpPowerUp){
                    player.setBulletsPerShot(player.getBulletsPerShot() + 1);  // Increase bullets per shot
                } else if(powerUp instanceof BulletDownPowerUp){
                    player.setBulletsPerShot(player.getBulletsPerShot() - 1);  // Increase player health
                }
                // Handle power-up collection logic here
            }
        }

        // Remove the bullets and enemies that collided
        
    
    bullets.removeAll(bulletsToRemove);
    enemies.removeAll(enemiesToRemove);
    powerUps.removeAll(powerUpsToRemove);  // Remove collected power-ups
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
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(imageContext);  // Draw power-ups
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
        player.handleKeyPress(e);
        // if (keyCode == KeyEvent.VK_LEFT) {
        //     player.setMovingLeft(true);
        // }
        // if (keyCode == KeyEvent.VK_RIGHT) {
        //     player.setMovingRight(true);
        // }
        // if (keyCode == KeyEvent.VK_UP) {
        //     player.setMovingUp(true);
        // }
        // if (keyCode == KeyEvent.VK_DOWN) {
        //     player.setMovingDown(true);
        // }
        // if (keyCode == KeyEvent.VK_SPACE) {
        //     for(Bullet bullet: player.shoot()){
        //         addBullet(bullet);  // Add bullet to the level
        //     }
        // }
    }

    public void handleKeyRelease(KeyEvent e) {
        int keyCode = e.getKeyCode();
        player.handleKeyRelease(e);
        // if (keyCode == KeyEvent.VK_LEFT) {
        //     player.setMovingLeft(false);
        // }
        // if (keyCode == KeyEvent.VK_RIGHT) {
        //     player.setMovingRight(false);
        // }
        // if (keyCode == KeyEvent.VK_UP) {
        //     player.setMovingUp(false);
        // }
        // if (keyCode == KeyEvent.VK_DOWN) {
        //     player.setMovingDown(false);
        // }
    }

    public void addBullets(List<Bullet> bullets) {
        this.bullets.addAll(bullets);  // Add bullets to the level's bullet list
    }
}
