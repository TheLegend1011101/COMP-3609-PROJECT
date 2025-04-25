

import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

public class SpaceLevel extends Level {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<PowerUp> powerUps;
    private boolean levelCompleted;
    private int levelNumber;
    private GamePanel gamePanel;
    private long levelCompleteTime; // Time when level was completed
    private boolean showingCompletionText; // Flag for showing completion text
    private boolean gameOver;
    private boolean waitingForRestart;
    private BackgroundManager backgroundManager;
    private long gameOverTime;
    private BufferedImage image;

    public SpaceLevel(int levelNumber, GamePanel gamePanel) {
        image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.showingCompletionText = false;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.gamePanel = gamePanel; // Initialize gamePanel
        initializeLevel();
        SoundManager.getInstance().playSound("background", true);
    }

    private void initializeLevel() {
        this.player = new SpacePlayer(300, 450, this);
        setupBackground(); // New method for background setup
        spawnEnemies();
        powerUps.add(new BulletUpPowerUp(100, 100));
        powerUps.add(new BulletDownPowerUp(200, 100));
    }
    
    private void setupBackground() {
        // Initialize with default speed (1)
        backgroundManager = new BackgroundManager(gamePanel, 1);
        
        // Level-specific background settings
        switch(levelNumber) {
            case 1:
                // Default background (already set)
                break;
                
            case 2:
                String[] redBackgroundImages = {
                    "images/T_RedBackground_Version4_Layer1.png",
                    "images/T_RedBackground_Version4_Layer2.png",
                    "images/T_RedBackground_Version4_Layer3.png",
                    "images/T_RedBackground_Version4_Layer4.png"
                };
                backgroundManager.setImages(redBackgroundImages);
                // backgroundManager.setSpeed(2); // Faster speed for level 2 if desired
                break;
                
            case 3:
                // Example for level 3 - could use different images
                String[] blueBackgroundImages = {
                    "images/T_BlueBackground_Layer1.png",
                    "images/T_BlueBackground_Layer2.png",
                    "images/T_BlueBackground_Layer3.png"
                };
                backgroundManager.setImages(blueBackgroundImages);
                break;
                
            default:
                // Default background for any other levels
                break;
        }
    }

    private void spawnEnemies() {
        if (levelNumber == 1) {
            // spawnAnimatedEnemies(50); // Spawn animated enemies
            spawnBezierEnemies(0);
            spawnCircularEnemies(250);
            spawnSineWaveEnemies(50);
            spawnSineWaveEnemies(-150);
        } else if (levelNumber == 2) {
            spawnSineWaveEnemies(100);
            spawnBezierEnemies(75); // Spawn Bezier enemies in level 2
        } else if (levelNumber == 3) {
            spawnCircularEnemies(100); // Spawn Circular enemies in level 3
        }
    }

    public void update() {
        if (gameOver && waitingForRestart) {
            return; // Stop all updates
        }
        if (levelCompleted) {
            // Check if we should proceed to next level after completion text
            if (showingCompletionText && System.currentTimeMillis() - levelCompleteTime >= 5000) {
                endLevel();
            }
            return;
        }
        if (player.getHealth() <= 0 && !gameOver) {
            gameOver = true;
            waitingForRestart = true;
            gameOverTime = System.currentTimeMillis();
            return;
        }

        backgroundManager.moveDown();
        // Update all entities
        player.update();
        updateBullets();
        updateEnemies();
        updatePowerUps();

        // Check collisions
        checkCollisions();

        // Check level completion
        checkLevelCompletion();
    }

    private void updateBullets() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();

            // Remove bullets that are out of bounds
            if (bullet.getY() < 0 || bullet.getY() > image.getHeight()) {
                bulletIterator.remove();
            }
        }
    }

    private String getRemainingTimeText() {
        long elapsed = System.currentTimeMillis() - levelCompleteTime;
        int remaining = (int) ((5000 - elapsed) / 1000) + 1; // Round up
        return "Next level in: " + remaining + "s";
    }

    private void updateEnemies() {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.update(); // Update the enemy, including its animation

            // Remove enemies that exit the bottom of the screen and damage player
            if (enemy.getY() > image.getHeight()) {
                enemyIterator.remove();
                player.setHealth(player.getHealth() - 10); // Damage player when enemy escapes
                // System.out.println("Enemy escaped! Player health: " + player.getHealth());

                // Check if player died from the damage
                if (player.getHealth() <= 0) {
                    // Handle player death (you might want to add game over logic here)
                }
            }
        }
    }

    private void updatePowerUps() {
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update();

            // Remove power-ups that are out of bounds
            if (powerUp.getX() < 0 || powerUp.getX() > image.getWidth() ||
                    powerUp.getY() < 0 || powerUp.getY() > image.getHeight()) {
                powerUpIterator.remove();
            }
        }
    }

    private void checkCollisions() {
        checkBulletCollisions();
        checkPowerUpCollisions();
    }
    private void checkBulletCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            
            if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
                Iterator<Enemy> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                        bulletIterator.remove(); // Remove bullet immediately
                        
                        if (enemy.getHealth() <= 0) {
                            enemyIterator.remove(); // Remove enemy immediately
                        }
                        break; // Exit enemy loop after first hit
                    }
                }
            } 
            else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
                if (bullet.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(bullet.getDamage());
                    bulletIterator.remove(); // Remove bullet immediately
                }
            }
        }
    }
    // private void checkBulletCollisions() {
    //     // System.out.println("--- Checking collisions ---");
    //     // System.out.println("Bullets: " + bullets.size());
    //     // System.out.println("Enemies: " + enemies.size());
        
    //     List<Enemy> enemiesToRemove = new ArrayList<>();
    //     List<Bullet> bulletsToRemove = new ArrayList<>();

    //     for (Bullet bullet : bullets) {
    //         if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
    //             for (Enemy enemy : enemies) {
    //                 if (bullet.getBounds().intersects(enemy.getBounds())) {
    //                     // System.out.println("Player bullet hit enemy!");
    //                     bulletsToRemove.add(bullet);
    //                     enemy.setHealth(enemy.getHealth() - bullet.getDamage());

    //                     if (enemy.getHealth() <= 0) {
    //                         enemiesToRemove.add(enemy);
    //                     }
    //                     break;
    //                 }
    //             }
    //         } else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
    //             if (bullet.getBounds().intersects(player.getBounds())) {
    //                 // System.out.println("ENEMY BULLET HIT PLAYER!");
    //                 bulletsToRemove.add(bullet);
    //                 player.takeDamage(bullet.getDamage());
    //                 break;
    //             }
    //         }
    //     }
    
    //     // System.out.println("Bullets to remove: " + bulletsToRemove.size());
    //     bullets.removeAll(bulletsToRemove);
    //     enemies.removeAll(enemiesToRemove);
    // }

    private void checkPowerUpCollisions() {
        List<PowerUp> powerUpsToRemove = new ArrayList<>();

        for (PowerUp powerUp : powerUps) {
            if (powerUp.getBounds().intersects(player.getBounds())) {
                if (powerUp instanceof BulletUpPowerUp) {
                    player.setBulletsPerShot(player.getBulletsPerShot() + 1);
                } else if (powerUp instanceof BulletDownPowerUp) {
                    player.setBulletsPerShot(Math.max(1, player.getBulletsPerShot() - 1));
                }
                powerUpsToRemove.add(powerUp);
                System.out.println("Power-up collected! Bullets per shot: " + powerUp);
            }
        }

        powerUps.removeAll(powerUpsToRemove);
    }

    public void draw(Graphics2D g2) {
        Graphics2D imageContext = (Graphics2D) image.getGraphics();
        imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());
        backgroundManager.draw(imageContext);
        if (gameOver) {
            imageContext.setColor(Color.RED);
            imageContext.setFont(new Font("Arial", Font.BOLD, 36));
            String gameOverText = "GAME OVER";
            int textWidth = imageContext.getFontMetrics().stringWidth(gameOverText);
            imageContext.drawString(gameOverText, (image.getWidth() - textWidth) / 2, image.getHeight() / 2 - 50);

            imageContext.setFont(new Font("Arial", Font.PLAIN, 24));
            String restartText = "Press any key to restart";
            int restartWidth = imageContext.getFontMetrics().stringWidth(restartText);
            imageContext.drawString(restartText, (image.getWidth() - restartWidth) / 2, image.getHeight() / 2 + 20);
        }
        // Draw all entities
        player.draw(imageContext); // Pass imageContext to player.draw()
        for (Bullet bullet : bullets) {
            bullet.draw(imageContext);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(imageContext);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(imageContext);
        }

        // Draw level completion text if needed
        if (showingCompletionText) {
            // Draw "LEVEL COMPLETED" text
            imageContext.setColor(Color.GREEN);
            imageContext.setFont(new Font("Arial", Font.BOLD, 36));
            String completedText = "LEVEL " + levelNumber + " COMPLETED!";
            int textWidth = imageContext.getFontMetrics().stringWidth(completedText);
            imageContext.drawString(completedText, (image.getWidth() - textWidth) / 2, image.getHeight() / 2);

            // Draw countdown text below it
            imageContext.setFont(new Font("Arial", Font.PLAIN, 24));
            String countdownText = "Next level in: "
                    + ((5000 - (System.currentTimeMillis() - levelCompleteTime)) / 1000 + 1) + "s";
            int countdownWidth = imageContext.getFontMetrics().stringWidth(countdownText);
            imageContext.drawString(countdownText, (image.getWidth() - countdownWidth) / 2, image.getHeight() / 2 + 50);
        }

        g2.drawImage(image, 0, 0, null);
        imageContext.dispose();
    }

    public void addBullet(SpaceBullet bullet) {
        bullets.add(bullet);
    }

    public void addBullets(List<Bullet> bullets) {
        this.bullets.addAll(bullets);
    }

    public Player getPlayer() {
        return player;
    }

    public void checkLevelCompletion() {
        if (!levelCompleted && enemies.isEmpty()) {
            levelCompleted = true;
            showingCompletionText = true;
            levelCompleteTime = System.currentTimeMillis();
        }
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void handleKeyPress(KeyEvent e) {
        if (waitingForRestart) {
            restartGame();
            return;
        }
        player.handleKeyPress(e);
    }

    public void handleKeyRelease(KeyEvent e) {
        player.handleKeyRelease(e);
    }

    public List<Rectangle> getSolidTiles() {
        return null;
    }

    public void spawnSineWaveEnemies(int y) {
        for (int i = 0; i < 4; i++) {
            enemies.add(new SineWaveEnemy(150 * i + 55, y, this));
        }
    }

    public void spawnBezierEnemies(int y) {
        for (int i = 0; i < 3; i++) {
            enemies.add(new BezierEnemy(150 * i + 55, y, this));
        }
    }

    public void spawnCircularEnemies(int y) {
        int centerX = 300;  // Center of 600px wide screen
        int centerY = 250;  // Center of 500px tall screen
        int radius = 100;   // Circle radius
        
        // Create one circular enemy with spinning motion
        CircularEnemy enemy = new CircularEnemy(centerX, centerY, this, radius);
        enemy.setAngularSpeed(2); // Slightly slower than default
        enemies.add(enemy);
    }

    public void endLevel() {
        // Clear current level resources
        enemies.clear();
        bullets.clear();
        powerUps.clear();
        
        // Increment level number
        levelNumber++;
        
        // Re-initialize the level with new background
        initializeLevel();
        
        // Reset completion flags
        levelCompleted = false;
        showingCompletionText = false;
        
        // Notify GamePanel to update (if needed)
        gamePanel.repaint();
    }

    private void restartGame() {
        gameOver = false;
        waitingForRestart = false;
        enemies.clear();
        bullets.clear();
        powerUps.clear();
        initializeLevel();
        levelCompleted = false;
        showingCompletionText = false;
        levelNumber = 1;
    }
}