// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.util.List;
// import java.util.ArrayList;
// import java.awt.image.BufferedImage;
// import java.awt.Rectangle;
// import java.awt.event.*;
// import java.util.Iterator;

// public class SpaceLevel extends Level {
//     private Player player;
//     private List<Enemy> enemies;
//     private List<Bullet> bullets;
//     private List<PowerUp> powerUps;  // List of power-ups in the level
//     private boolean levelCompleted;
//     // private BufferedImage image;  // Image for the level background (if needed)
//     private int levelNumber;

//     // Constructor
//     public SpaceLevel(int levelNumber) {
//         image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
//         this.levelNumber = levelNumber;
//         this.levelCompleted = false;
//         this.enemies = new ArrayList<>();
//         this.bullets = new ArrayList<>();
//         this.powerUps = new ArrayList<>();  // Initialize the power-ups list
//         initializeLevel();
//     }

//     // Initialize the level (e.g., spawn player, enemies, etc.)
//     private void initializeLevel() {
//         // Initialize the player, enemies, etc.
//         this.player = new SpacePlayer(300, 450, this);  // Example player starting position
//         spawnEnemies();
//         powerUps.add(new BulletUpPowerUp(100, 100));  // Example power-up spawn position
//         powerUps.add(new BulletUpPowerUp(200, 100));  // Example power-up spawn position
//     }

//     // Spawn enemies (you can modify this to read from a level file or generate enemies dynamically)
//     private void spawnEnemies() {
//         for (int i = 0; i < 4; i++) {
//             // enemies.add(new SineWaveEnemy(100 * (i + 1), 50));  // Example enemy spawn positions
//             enemies.add(new SineWaveEnemy(150 * i + 55, 50, this));
//         }
//     }

//     // Update the level (e.g., update player, enemies, bullets, etc.)
//     // public void update() {
//     //     if (levelCompleted) {
//     //         // Handle level completion (e.g., proceed to next level)
//     //         return;
//     //     }
//     //     checkCollisions();
//     //     player.update();  // Update player state

//     //     for (Bullet bullet : bullets) {
//     //         bullet.update();  // Update bullet positions
//     //         if(bullet.getOwner() == SpaceBullet.BulletOwner.PLAYER){
//     //             System.out.println(bullet.getSpeed());
//     //         }
//     //     }

//     //     for (Enemy enemy : enemies) {
//     //         enemy.update();  // Update enemy positions
//     //     }
//     //     for(PowerUp powerUp: powerUps){
//     //         powerUp.update();  // Update power-up positions
//     //     }

        
//     // }

//     public void update() {
//         if (levelCompleted) return;
    
//         player.update();  // Update player first
    
//         // Update bullets
//         Iterator<Bullet> bulletIterator = bullets.iterator();
//         while (bulletIterator.hasNext()) {
//             Bullet bullet = bulletIterator.next();
//             bullet.update();
    
//             if (bullet.getOwner() == SpaceBullet.BulletOwner.PLAYER) {
//                 System.out.println(bullet.getSpeed());
//             }
//         }
    
//         // Update enemies
//         Iterator<Enemy> enemyIterator = enemies.iterator();
//         while (enemyIterator.hasNext()) {
//             Enemy enemy = enemyIterator.next();
//             enemy.update();
//         }
    
//         // Update power-ups
//         Iterator<PowerUp> powerUpIterator = powerUps.iterator();
//         while (powerUpIterator.hasNext()) {
//             PowerUp powerUp = powerUpIterator.next();
//             powerUp.update();
//         }
    
//         // 🔥 Run collision detection **after all updates are done**
//         checkCollisions();
//     }

//     // Check collisions between bullets and enemies
//     private void checkCollisions() {
//         List<Enemy> enemiesToRemove = new ArrayList<>();
    
//         Iterator<Bullet> bulletIterator = bullets.iterator();
//         while (bulletIterator.hasNext()) {
//             Bullet bullet = bulletIterator.next();
    
//             // Player bullet hits enemy
//             if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
//                 for (Enemy enemy : enemies) {
//                     if (bullet.getBounds().intersects(enemy.getBounds())) {
//                         enemy.setHealth(enemy.getHealth() - bullet.getDamage());
//                         bulletIterator.remove();  // Safe removal
//                         if (enemy.getHealth() <= 0) {
//                             enemiesToRemove.add(enemy);  // Don't remove here!
//                         }
//                         break;  // One bullet hits only one enemy
//                     }
//                 }
//             }
    
//             // Enemy bullet hits player
//             else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
//                 if (bullet.getBounds().intersects(player.getBounds())) {
//                     player.setHealth(player.getHealth() - bullet.getDamage());
//                     bulletIterator.remove();  // Safe removal
//                 }
//             }
//         }
    
//         // Now safely remove enemies after loop
//         enemies.removeAll(enemiesToRemove);
    
//         // Power-up collisions
//         Iterator<PowerUp> powerUpIterator = powerUps.iterator();
//         while (powerUpIterator.hasNext()) {
//             PowerUp powerUp = powerUpIterator.next();
//             if (powerUp.getBounds().intersects(player.getBounds())) {
//                 if (powerUp instanceof BulletUpPowerUp) {
//                     player.setBulletsPerShot(player.getBulletsPerShot() + 1);
//                 } else if (powerUp instanceof BulletDownPowerUp) {
//                     player.setBulletsPerShot(Math.max(1, player.getBulletsPerShot() - 1));
//                 }
//                 powerUpIterator.remove();  // Safe removal
//             }
//         }
//     }
    

//     public List<Rectangle> getSolidTiles(){
//         return null;
//     }
//     // Draw the level
//     public void draw(Graphics2D g2) {
//         Graphics2D imageContext = (Graphics2D) image.getGraphics();
//         imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());
    
//         player.draw(imageContext);  // Draw player
    
//         // Draw bullets using iterator
//         Iterator<Bullet> bulletIterator = bullets.iterator();
//         while (bulletIterator.hasNext()) {
//             Bullet bullet = bulletIterator.next();
//             bullet.draw(imageContext);
//         }
    
//         // Draw enemies using iterator
//         Iterator<Enemy> enemyIterator = enemies.iterator();
//         while (enemyIterator.hasNext()) {
//             Enemy enemy = enemyIterator.next();
//             enemy.draw(imageContext);
//         }
    
//         // Draw power-ups using iterator
//         Iterator<PowerUp> powerUpIterator = powerUps.iterator();
//         while (powerUpIterator.hasNext()) {
//             PowerUp powerUp = powerUpIterator.next();
//             powerUp.draw(imageContext);
//         }
    
//         g2.drawImage(image, 0, 0, null);  // Draw the composed image
//         g2.dispose();
//     }
    

//     // Add a bullet to the level
//     public void addBullet(SpaceBullet bullet) {
//         bullets.add(bullet);
//     }

//     public Player getPlayer() {
//         return player;
//     }

//     // public void setMovingDirection(int direction, boolean isMoving) {
//     //     player.setMovingDirection(direction, isMoving);  // Set player movement direction
//     // }

//     // Level-specific logic to check if the level is completed
//     public void checkLevelCompletion() {
//         if (enemies.isEmpty()) {
//             levelCompleted = true;
//         }
//     }

//     public boolean isLevelCompleted() {
//         return levelCompleted;
//     }

//     public int getLevelNumber() {
//         return levelNumber;
//     }


//     public void handleKeyPress(KeyEvent e) {
//         int keyCode = e.getKeyCode();
//         player.handleKeyPress(e);
//         // if (keyCode == KeyEvent.VK_LEFT) {
//         //     player.setMovingLeft(true);
//         // }
//         // if (keyCode == KeyEvent.VK_RIGHT) {
//         //     player.setMovingRight(true);
//         // }
//         // if (keyCode == KeyEvent.VK_UP) {
//         //     player.setMovingUp(true);
//         // }
//         // if (keyCode == KeyEvent.VK_DOWN) {
//         //     player.setMovingDown(true);
//         // }
//         // if (keyCode == KeyEvent.VK_SPACE) {
//         //     for(Bullet bullet: player.shoot()){
//         //         addBullet(bullet);  // Add bullet to the level
//         //     }
//         // }
//     }

//     public void handleKeyRelease(KeyEvent e) {
//         int keyCode = e.getKeyCode();
//         player.handleKeyRelease(e);
//         // if (keyCode == KeyEvent.VK_LEFT) {
//         //     player.setMovingLeft(false);
//         // }
//         // if (keyCode == KeyEvent.VK_RIGHT) {
//         //     player.setMovingRight(false);
//         // }
//         // if (keyCode == KeyEvent.VK_UP) {
//         //     player.setMovingUp(false);
//         // }
//         // if (keyCode == KeyEvent.VK_DOWN) {
//         //     player.setMovingDown(false);
//         // }
//     }

//     public void addBullets(List<Bullet> bullets) {
//         this.bullets.addAll(bullets);  // Add bullets to the level's bullet list
//     }
// }


import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class SpaceLevel extends Level {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<PowerUp> powerUps;
    private boolean levelCompleted;
    private int levelNumber;

    public SpaceLevel(int levelNumber) {
        image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        initializeLevel();
    }

    private void initializeLevel() {
        this.player = new SpacePlayer(300, 450, this);
        spawnEnemies();
        powerUps.add(new BulletUpPowerUp(100, 100));
        powerUps.add(new BulletUpPowerUp(200, 100));
    }

    private void spawnEnemies() {
        for (int i = 0; i < 4; i++) {
            enemies.add(new SineWaveEnemy(150 * i + 55, 50, this));
        }
    }

    public void update() {
        if (levelCompleted) return;
    
        // Update all entities
        player.update();
        if(player.getBounds().intersects(enemies.get(0).getBounds())){
            System.out.println("Player collided with enemy!");
        }
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
            if (bullet.getX() < 0 || bullet.getX() > image.getWidth() ||
                bullet.getY() < 0 || bullet.getY() > image.getHeight()) {
                bulletIterator.remove();
            }
        }
    }

    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.update();
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
        List<Enemy> enemiesToRemove = new ArrayList<>();
        List<Bullet> bulletsToRemove = new ArrayList<>();
    
        for (Bullet bullet : bullets) {
            // System.out.println(bullet.getOwner());
            // Player bullet hits enemy
            if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        System.out.println("Player bullet hit enemy!");
                        enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                        bulletsToRemove.add(bullet);
                        
                        if (enemy.getHealth() <= 0) {
                            enemiesToRemove.add(enemy);
                        }
                        break;
                    }
                }
            }
            // Enemy bullet hits player
            else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
                if (bullet.getBounds().intersects(player.getBounds())) {
                    player.setHealth(player.getHealth() - bullet.getDamage());
                    bulletsToRemove.add(bullet);
                }
            }
        }
    
        // Perform removals after iteration
        enemies.removeAll(enemiesToRemove);
        bullets.removeAll(bulletsToRemove);
    }

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
            }
        }
        
        powerUps.removeAll(powerUpsToRemove);
    }

    public void draw(Graphics2D g2) {
        Graphics2D imageContext = (Graphics2D) image.getGraphics();
        imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());
    
        // Draw all entities
        player.draw(imageContext);
        for (Bullet bullet : bullets) {
            bullet.draw(imageContext);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(imageContext);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(imageContext);
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
        levelCompleted = enemies.isEmpty();
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void handleKeyPress(KeyEvent e) {
        player.handleKeyPress(e);
    }

    public void handleKeyRelease(KeyEvent e) {
        player.handleKeyRelease(e);
    }

    public List<Rectangle> getSolidTiles() {
        return null;
    }
}
