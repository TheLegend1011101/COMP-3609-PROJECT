
// // import java.awt.Graphics2D;
// // import java.util.List;
// // import java.util.ArrayList;
// // import java.util.Iterator;
// // import java.awt.image.BufferedImage;
// // import java.awt.Rectangle;
// // import java.awt.event.KeyEvent;
// // import java.awt.Color;
// // import java.awt.Font;

// // public class SpaceLevel extends Level {
// //     private Player player;
// //     private List<Enemy> enemies;
// //     private List<Bullet> bullets;
// //     private List<PowerUp> powerUps;
// //     private boolean levelCompleted;
// //     private int levelNumber;
// //     private GamePanel gamePanel;
// //     private long levelCompleteTime; // Time when level was completed
// //     private boolean showingCompletionText; // Flag for showing completion text
// //     private boolean gameOver;
// //     private boolean waitingForRestart;
// //     private BackgroundManager backgroundManager;
// //     private long gameOverTime;
// //     private BufferedImage image;

// //     public SpaceLevel(int levelNumber, GamePanel gamePanel) {
// //         image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
// //         this.levelNumber = levelNumber;
// //         this.levelCompleted = false;
// //         this.showingCompletionText = false;
// //         this.enemies = new ArrayList<>();
// //         this.bullets = new ArrayList<>();
// //         this.powerUps = new ArrayList<>();
// //         this.gamePanel = gamePanel; // Initialize gamePanel
// //         initializeLevel();
// //         SoundManager.getInstance().playSound("background", true);
// //     }

// //     private void initializeLevel() {
// //         this.player = new SpacePlayer(300, 450, this);
// //         setupBackground(); // New method for background setup
// //         spawnEnemies();
// //         powerUps.add(new BulletUpPowerUp(100, 100));
// //         powerUps.add(new BulletDownPowerUp(200, 100));
// //     }

// //     private void setupBackground() {
// //         // Initialize with default speed (1)
// //         backgroundManager = new BackgroundManager(gamePanel, 1);

// //         // Level-specific background settings
// //         switch(levelNumber) {
// //             case 1:
// //                 // Default background (already set)
// //                 break;

// //             case 2:
// //                 String[] redBackgroundImages = {
// //                     "images/T_RedBackground_Version4_Layer1.png",
// //                     "images/T_RedBackground_Version4_Layer2.png",
// //                     "images/T_RedBackground_Version4_Layer3.png",
// //                     "images/T_RedBackground_Version4_Layer4.png"
// //                 };
// //                 backgroundManager.setImages(redBackgroundImages);
// //                 // backgroundManager.setSpeed(2); // Faster speed for level 2 if desired
// //                 break;

// //             case 3:
// //                 // Example for level 3 - could use different images
// //                 // String[] blueBackgroundImages = {
// //                 //     "images/T_BlueBackground_Layer1.png",
// //                 //     "images/T_BlueBackground_Layer2.png",
// //                 //     "images/T_BlueBackground_Layer3.png"
// //                 // };
// //                 String[] purpleBackGroundImages = {
// //                     "images/T_PurpleBackground_Version4_Layer1.png",
// //                     "images/T_PurpleBackground_Version4_Layer2.png",
// //                     "images/T_PurpleBackground_Version4_Layer3.png",
// //                     "images/T_PurpleBackground_Version4_Layer4.png"
// //                 };
// //                 backgroundManager.setImages(purpleBackGroundImages);
// //                 break;

// //             default:
// //                 // Default background for any other levels
// //                 break;
// //         }
// //     }

// //     private void spawnEnemies() {
// //         if (levelNumber == 1) {
// //             // spawnAnimatedEnemies(50); // Spawn animated enemies
// //             spawnCircularEnemies(250);
// //             spawnSineWaveEnemies(50);
// //         } else if (levelNumber == 2) {
// //             spawnBezierEnemies(0);
// //             spawnSineWaveEnemies(50);
// //             spawnSineWaveEnemies(-150);
// //             spawnBezierEnemies(75); // Spawn Bezier enemies in level 2
// //         } else if (levelNumber == 3) {
// //             spawnBezierEnemies(0);
// //             spawnSineWaveEnemies(50);
// //             spawnSineWaveEnemies(-150);
// //             spawnCircularEnemies(250); // Spawn Circular enemies in level 3
// //         }
// //     }

// //     public void update() {
// //         if (gameOver && waitingForRestart) {
// //             return; // Stop all updates
// //         }
// //         if (levelCompleted) {
// //             // Check if we should proceed to next level after completion text
// //             if (showingCompletionText && System.currentTimeMillis() - levelCompleteTime >= 5000) {
// //                 endLevel();
// //             }
// //             return;
// //         }
// //         if (player.getHealth() <= 0 && !gameOver) {
// //             gameOver = true;
// //             waitingForRestart = true;
// //             gameOverTime = System.currentTimeMillis();
// //             return;
// //         }

// //         backgroundManager.moveDown();
// //         // Update all entities
// //         player.update();
// //         updateBullets();
// //         updateEnemies();
// //         updatePowerUps();

// //         // Check collisions
// //         checkCollisions();

// //         // Check level completion
// //         checkLevelCompletion();
// //     }

// //     private void updateBullets() {
// //         Iterator<Bullet> bulletIterator = bullets.iterator();
// //         while (bulletIterator.hasNext()) {
// //             Bullet bullet = bulletIterator.next();
// //             bullet.update();

// //             // Remove bullets that are out of bounds
// //             if (bullet.getY() < 0 || bullet.getY() > image.getHeight()) {
// //                 bulletIterator.remove();
// //             }
// //         }
// //     }

// //     private String getRemainingTimeText() {
// //         long elapsed = System.currentTimeMillis() - levelCompleteTime;
// //         int remaining = (int) ((5000 - elapsed) / 1000) + 1; // Round up
// //         return "Next level in: " + remaining + "s";
// //     }

// //     private void updateEnemies() {
// //         Iterator<Enemy> enemyIterator = enemies.iterator();
// //         while (enemyIterator.hasNext()) {
// //             Enemy enemy = enemyIterator.next();
// //             enemy.update(); // Update the enemy, including its animation

// //             // Remove enemies that exit the bottom of the screen and damage player
// //             if (enemy.getY() > image.getHeight()) {
// //                 enemyIterator.remove();
// //                 player.setHealth(player.getHealth() - 10); // Damage player when enemy escapes
// //                 // System.out.println("Enemy escaped! Player health: " + player.getHealth());

// //                 // Check if player died from the damage
// //                 if (player.getHealth() <= 0) {
// //                     // Handle player death (you might want to add game over logic here)
// //                 }
// //             }
// //         }
// //     }

// //     private void updatePowerUps() {
// //         Iterator<PowerUp> powerUpIterator = powerUps.iterator();
// //         while (powerUpIterator.hasNext()) {
// //             PowerUp powerUp = powerUpIterator.next();
// //             powerUp.update();

// //             // Remove power-ups that are out of bounds
// //             if (powerUp.getX() < 0 || powerUp.getX() > image.getWidth() ||
// //                     powerUp.getY() < 0 || powerUp.getY() > image.getHeight()) {
// //                 powerUpIterator.remove();
// //             }
// //         }
// //     }

// //     private void checkCollisions() {
// //         checkBulletCollisions();
// //         checkPowerUpCollisions();
// //     }
// //     // private void checkBulletCollisions() {
// //     //     Iterator<Bullet> bulletIterator = bullets.iterator();
// //     //     while (bulletIterator.hasNext()) {
// //     //         Bullet bullet = bulletIterator.next();

// //     //         if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
// //     //             Iterator<Enemy> enemyIterator = enemies.iterator();
// //     //             while (enemyIterator.hasNext()) {
// //     //                 Enemy enemy = enemyIterator.next();
// //     //                 if (bullet.getBounds().intersects(enemy.getBounds())) {
// //     //                     enemy.setHealth(enemy.getHealth() - bullet.getDamage());
// //     //                     bulletIterator.remove(); // Remove bullet immediately

// //     //                     if (enemy.getHealth() <= 0) {
// //     //                         enemyIterator.remove(); // Remove enemy immediately
// //     //                     }
// //     //                     break; // Exit enemy loop after first hit
// //     //                 }
// //     //             }
// //     //         } 
// //     //         else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
// //     //             if (bullet.getBounds().intersects(player.getBounds())) {
// //     //                 player.takeDamage(bullet.getDamage());
// //     //                 bulletIterator.remove(); // Remove bullet immediately
// //     //             }
// //     //         }
// //     //     }
// //     // }
// //     private void checkBulletCollisions() {
// //         // System.out.println("--- Checking collisions ---");
// //         // System.out.println("Bullets: " + bullets.size());
// //         // System.out.println("Enemies: " + enemies.size());

// //         List<Enemy> enemiesToRemove = new ArrayList<>();
// //         List<Bullet> bulletsToRemove = new ArrayList<>();

// //         for (Bullet bullet : bullets) {
// //             if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
// //                 for (Enemy enemy : enemies) {
// //                     if (bullet.getBounds().intersects(enemy.getBounds())) {
// //                         // System.out.println("Player bullet hit enemy!");
// //                         bulletsToRemove.add(bullet);
// //                         enemy.setHealth(enemy.getHealth() - bullet.getDamage());

// //                         if (enemy.getHealth() <= 0) {
// //                             enemiesToRemove.add(enemy);
// //                         }
// //                         break;
// //                     }
// //                 }
// //             } else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
// //                 if (bullet.getBounds().intersects(player.getBounds())) {
// //                     // System.out.println("ENEMY BULLET HIT PLAYER!");
// //                     bulletsToRemove.add(bullet);
// //                     player.takeDamage(bullet.getDamage());
// //                     break;
// //                 }
// //             }
// //         }

// //         // System.out.println("Bullets to remove: " + bulletsToRemove.size());
// //         bullets.removeAll(bulletsToRemove);
// //         enemies.removeAll(enemiesToRemove);
// //     }

// //     private void checkPowerUpCollisions() {
// //         List<PowerUp> powerUpsToRemove = new ArrayList<>();

// //         for (PowerUp powerUp : powerUps) {
// //             if (powerUp.getBounds().intersects(player.getBounds())) {
// //                 if (powerUp instanceof BulletUpPowerUp) {
// //                     player.setBulletsPerShot(player.getBulletsPerShot() + 1);
// //                 } else if (powerUp instanceof BulletDownPowerUp) {
// //                     player.setBulletsPerShot(Math.max(1, player.getBulletsPerShot() - 1));
// //                 }
// //                 powerUpsToRemove.add(powerUp);
// //                 System.out.println("Power-up collected! Bullets per shot: " + powerUp);
// //             }
// //         }

// //         powerUps.removeAll(powerUpsToRemove);
// //     }

// //     public void draw(Graphics2D g2) {
// //         Graphics2D imageContext = (Graphics2D) image.getGraphics();
// //         imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());
// //         backgroundManager.draw(imageContext);
// //         if (gameOver) {
// //             imageContext.setColor(Color.RED);
// //             imageContext.setFont(new Font("Arial", Font.BOLD, 36));
// //             String gameOverText = "GAME OVER";
// //             int textWidth = imageContext.getFontMetrics().stringWidth(gameOverText);
// //             imageContext.drawString(gameOverText, (image.getWidth() - textWidth) / 2, image.getHeight() / 2 - 50);

// //             imageContext.setFont(new Font("Arial", Font.PLAIN, 24));
// //             String restartText = "Press any key to restart";
// //             int restartWidth = imageContext.getFontMetrics().stringWidth(restartText);
// //             imageContext.drawString(restartText, (image.getWidth() - restartWidth) / 2, image.getHeight() / 2 + 20);
// //         }
// //         // Draw all entities
// //         player.draw(imageContext); // Pass imageContext to player.draw()
// //         for (Bullet bullet : bullets) {
// //             bullet.draw(imageContext);
// //         }
// //         for (Enemy enemy : enemies) {
// //             enemy.draw(imageContext);
// //         }
// //         for (PowerUp powerUp : powerUps) {
// //             powerUp.draw(imageContext);
// //         }

// //         // Draw level completion text if needed
// //         if (showingCompletionText) {
// //             // Draw "LEVEL COMPLETED" text
// //             imageContext.setColor(Color.GREEN);
// //             imageContext.setFont(new Font("Arial", Font.BOLD, 36));
// //             String completedText = "LEVEL " + levelNumber + " COMPLETED!";
// //             int textWidth = imageContext.getFontMetrics().stringWidth(completedText);
// //             imageContext.drawString(completedText, (image.getWidth() - textWidth) / 2, image.getHeight() / 2);

// //             // Draw countdown text below it
// //             imageContext.setFont(new Font("Arial", Font.PLAIN, 24));
// //             String countdownText = "Next level in: "
// //                     + ((5000 - (System.currentTimeMillis() - levelCompleteTime)) / 1000 + 1) + "s";
// //             int countdownWidth = imageContext.getFontMetrics().stringWidth(countdownText);
// //             imageContext.drawString(countdownText, (image.getWidth() - countdownWidth) / 2, image.getHeight() / 2 + 50);
// //         }

// //         g2.drawImage(image, 0, 0, null);
// //         imageContext.dispose();
// //     }

// //     public void addBullet(SpaceBullet bullet) {
// //         bullets.add(bullet);
// //     }

// //     public void addBullets(List<Bullet> bullets) {
// //         this.bullets.addAll(bullets);
// //     }

// //     public Player getPlayer() {
// //         return player;
// //     }

// //     public void checkLevelCompletion() {
// //         if (!levelCompleted && enemies.isEmpty()) {
// //             levelCompleted = true;
// //             showingCompletionText = true;
// //             levelCompleteTime = System.currentTimeMillis();
// //         }
// //     }

// //     public boolean isLevelCompleted() {
// //         return levelCompleted;
// //     }

// //     public int getLevelNumber() {
// //         return levelNumber;
// //     }

// //     public void handleKeyPress(KeyEvent e) {
// //         if (waitingForRestart) {
// //             restartGame();
// //             return;
// //         }
// //         player.handleKeyPress(e);
// //     }

// //     public void handleKeyRelease(KeyEvent e) {
// //         player.handleKeyRelease(e);
// //     }

// //     public List<Rectangle> getSolidTiles() {
// //         return null;
// //     }

// //     public void spawnSineWaveEnemies(int y) {
// //         for (int i = 0; i < 4; i++) {
// //             enemies.add(new SineWaveEnemy(150 * i + 55, y, this));
// //         }
// //     }

// //     public void spawnBezierEnemies(int y) {
// //         for (int i = 0; i < 3; i++) {
// //             enemies.add(new BezierEnemy(150 * i + 55, y, this));
// //         }
// //     }

// //     public void spawnCircularEnemies(int y) {
// //         int centerX = 300;  // Center of 600px wide screen
// //         int centerY = 250;  // Center of 500px tall screen
// //         int radius = 100;   // Circle radius

// //         // Create one circular enemy with spinning motion
// //         CircularEnemy enemy = new CircularEnemy(centerX, centerY, this, radius);
// //         enemy.setAngularSpeed(2); // Slightly slower than default
// //         enemies.add(enemy);
// //     }

// //     public void endLevel() {
// //         // Clear current level resources
// //         enemies.clear();
// //         bullets.clear();
// //         powerUps.clear();

// //         // Increment level number
// //         levelNumber++;

// //         // Re-initialize the level with new background
// //         initializeLevel();

// //         // Reset completion flags
// //         levelCompleted = false;
// //         showingCompletionText = false;

// //         // Notify GamePanel to update (if needed)
// //         gamePanel.repaint();
// //     }

// //     private void restartGame() {
// //         gameOver = false;
// //         waitingForRestart = false;
// //         enemies.clear();
// //         bullets.clear();
// //         powerUps.clear();
// //         initializeLevel();
// //         levelCompleted = false;
// //         showingCompletionText = false;
// //         levelNumber = 1;
// //     }
// // }

// import java.awt.Graphics2D;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.awt.image.BufferedImage;
// import java.awt.Rectangle;
// import java.awt.event.KeyEvent;
// import java.awt.Color;
// import java.awt.Font;

// public class SpaceLevel extends Level {
//     private Player player;
//     private List<Enemy> enemies;
//     private List<Bullet> bullets;
//     private List<PowerUp> powerUps;
//     private boolean levelCompleted;
//     private int levelNumber;
//     private GamePanel gamePanel;
//     private long levelCompleteTime;
//     private boolean showingCompletionText;
//     private boolean gameOver;
//     private boolean waitingForRestart;
//     private BackgroundManager backgroundManager;
//     private long gameOverTime;
//     private BufferedImage image;
//     private static final int MAX_POWERUPS_ON_SCREEN = 5;
//     // Victory state
//     private boolean gameWon = false;
//     private long gameWonTime;
//     private boolean showingVictoryText = false;

//     public SpaceLevel(int levelNumber, GamePanel gamePanel) {
//         image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
//         this.levelNumber = levelNumber;
//         this.levelCompleted = false;
//         this.showingCompletionText = false;
//         this.enemies = new ArrayList<>();
//         this.bullets = new ArrayList<>();
//         this.powerUps = new ArrayList<>();
//         this.gamePanel = gamePanel;
//         initializeLevel();
//         SoundManager.getInstance().playSound("background", true);
//     }

//     private void initializeLevel() {
//         this.player = new SpacePlayer(300, 450, this);
//         setupBackground();
//         spawnEnemies();
//         // powerUps.add(new BulletUpPowerUp(100, 100));
//         // powerUps.add(new BulletDownPowerUp(200, 100));
//     }

//     private void maybeSpawnPowerUp() {
//         // Don't spawn if we already have max power-ups
//         if (powerUps.size() >= MAX_POWERUPS_ON_SCREEN) {
//             return;
//         }

//         // Random chance to spawn (adjust probability as needed)
//         if (Math.random() < 0.05) { // 2% chance per frame
//             int x = (int)(Math.random() * (image.getWidth() - 50)) + 25; // Random x position (avoid edges)
//             int y = (int)(Math.random() * 100); // Spawn near top of screen

//             // 2:1 ratio favoring bullet-ups (66% chance)
//             PowerUp powerUp;
//             if (Math.random() < 0.66) {
//                 powerUp = new BulletUpPowerUp(x, y);
//             } else {
//                 powerUp = new BulletDownPowerUp(x, y);
//             }

//             powerUps.add(powerUp);
//         }
//     }

//     private void setupBackground() {
//         backgroundManager = new BackgroundManager(gamePanel, 1);

//         switch(levelNumber) {
//             case 1:
//                 break;
//             case 2:
//                 String[] redBackgroundImages = {
//                     "images/T_RedBackground_Version4_Layer1.png",
//                     "images/T_RedBackground_Version4_Layer2.png",
//                     "images/T_RedBackground_Version4_Layer3.png",
//                     "images/T_RedBackground_Version4_Layer4.png"
//                 };
//                 backgroundManager.setImages(redBackgroundImages);
//                 break;
//             case 3:
//                 String[] purpleBackGroundImages = {
//                     "images/T_PurpleBackground_Version4_Layer1.png",
//                     "images/T_PurpleBackground_Version4_Layer2.png",
//                     "images/T_PurpleBackground_Version4_Layer3.png",
//                     "images/T_PurpleBackground_Version4_Layer4.png"
//                 };
//                 backgroundManager.setImages(purpleBackGroundImages);
//                 break;
//             default:
//                 break;
//         }
//     }

//     private void spawnEnemies() {
//         if (levelNumber == 1) {
//             spawnCircularEnemies(250);
//             spawnSineWaveEnemies(50);
//         } else if (levelNumber == 2) {
//             spawnBezierEnemies(0);
//             spawnSineWaveEnemies(50);
//             spawnSineWaveEnemies(-150);
//             spawnBezierEnemies(75);
//         } else if (levelNumber == 3) {
//             spawnBezierEnemies(0);
//             spawnSineWaveEnemies(50);
//             spawnSineWaveEnemies(-150);
//             spawnCircularEnemies(250);
//         }
//     }

//     // public void update() {
//     //     if (gameWon) {
//     //         if (showingVictoryText && System.currentTimeMillis() - gameWonTime >= 8000) {
//     //             // gamePanel.returnToMenu();
//     //             restartGame();
//     //         }
//     //         return;
//     //     }

//     //     if (gameOver && waitingForRestart) {
//     //         return;
//     //     }
//     //     if (levelCompleted) {
//     //         if (showingCompletionText && System.currentTimeMillis() - levelCompleteTime >= 5000) {
//     //             endLevel();
//     //         }
//     //         return;
//     //     }
//     //     if (player.getHealth() <= 0 && !gameOver) {
//     //         gameOver = true;
//     //         waitingForRestart = true;
//     //         gameOverTime = System.currentTimeMillis();
//     //         return;
//     //     }

//     //     backgroundManager.moveDown();
//     //     player.update();
//     //     updateBullets();
//     //     updateEnemies();
//     //     updatePowerUps();
//     //     checkCollisions();
//     //     checkLevelCompletion();
//     // }

//     public void update() {
//         if (gameWon) {
//             if (showingVictoryText && System.currentTimeMillis() - gameWonTime >= 8000) {
//                 restartGame();
//             }
//             return;
//         }

//         // ... [keep all existing code at start of update] ...

//         backgroundManager.moveDown();
//         player.update();
//         updateBullets();
//         updateEnemies();
//         updatePowerUps();
//         maybeSpawnPowerUp(); // Add this line
//         checkCollisions();
//         checkLevelCompletion();
//     }

//     private void updateBullets() {
//         List<Bullet> bulletsToRemove = new ArrayList<>();
//         // Iterator<Bullet> bulletIterator = bullets.iterator();
//         // while (bulletIterator.hasNext()) {
//         //     Bullet bullet = bulletIterator.next();
//         //     bullet.update();
//         //     if (bullet.getY() < 0 || bullet.getY() > image.getHeight()) {
//         //         bulletIterator.remove();
//         //     }
//         // }
//         for (Bullet bullet : bullets) {
//             bullet.update();
//             if (bullet.getY() < 0 || bullet.getY() > image.getHeight()) {
//                 bulletsToRemove.add(bullet);
//             }
//         }
//         // apply changes
//         bullets.removeAll(bulletsToRemove);
//         // bullets.addAll(bulletsToAdd);
//         bulletsToRemove.clear();
//         // bulletsToAdd.clear();
//     }

//     private void updateEnemies() {
//         Iterator<Enemy> enemyIterator = enemies.iterator();
//         while (enemyIterator.hasNext()) {
//             Enemy enemy = enemyIterator.next();
//             enemy.update();
//             if (enemy.getY() > image.getHeight()) {
//                 enemyIterator.remove();
//                 player.setHealth(player.getHealth() - 10);
//                 if (player.getHealth() <= 0) {
//                     gameOver = true;
//                     waitingForRestart = true;
//                     gameOverTime = System.currentTimeMillis();
//                 }
//             }
//         }
//     }

//     private void updatePowerUps() {
//         Iterator<PowerUp> powerUpIterator = powerUps.iterator();
//         while (powerUpIterator.hasNext()) {
//             PowerUp powerUp = powerUpIterator.next();
//             powerUp.update();
//             if (powerUp.getX() < 0 || powerUp.getX() > image.getWidth() ||
//                     powerUp.getY() < 0 || powerUp.getY() > image.getHeight()) {
//                 powerUpIterator.remove();
//             }
//         }
//     }

//     private void checkCollisions() {
//         checkBulletCollisions();
//         checkPowerUpCollisions();
//     }

//     private void checkBulletCollisions() {
//         Iterator<Bullet> bulletIterator = bullets.iterator();
//         while (bulletIterator.hasNext()) {
//             Bullet bullet = bulletIterator.next();

//             if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
//                 Iterator<Enemy> enemyIterator = enemies.iterator();
//                 while (enemyIterator.hasNext()) {
//                     Enemy enemy = enemyIterator.next();
//                     if (bullet.getBounds().intersects(enemy.getBounds())) {
//                         enemy.setHealth(enemy.getHealth() - bullet.getDamage());
//                         bulletIterator.remove();
//                         if (enemy.getHealth() <= 0) {
//                             enemyIterator.remove();
//                         }
//                         break;
//                     }
//                 }
//             } 
//             else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
//                 if (bullet.getBounds().intersects(player.getBounds())) {
//                     player.takeDamage(bullet.getDamage());
//                     bulletIterator.remove();
//                 }
//             }
//         }
//     }

//     private void checkPowerUpCollisions() {
//         Iterator<PowerUp> powerUpIterator = powerUps.iterator();
//         while (powerUpIterator.hasNext()) {
//             PowerUp powerUp = powerUpIterator.next();
//             if (powerUp.getBounds().intersects(player.getBounds())) {
//                 if (powerUp instanceof BulletUpPowerUp) {
//                     player.setBulletsPerShot(player.getBulletsPerShot() + 1);
//                 } else if (powerUp instanceof BulletDownPowerUp) {
//                     player.setBulletsPerShot(Math.max(1, player.getBulletsPerShot() - 1));
//                 }
//                 powerUpIterator.remove();
//             }
//         }
//     }

//     private void drawVictoryScreen(Graphics2D g2) {
//         g2.setColor(new Color(255, 50, 50)); // Blood red color
//         g2.setFont(new Font("Arial", Font.BOLD, 42));
//         String victoryText = "GENOCIDE COMPLETE!";
//         int textWidth = g2.getFontMetrics().stringWidth(victoryText);
//         g2.drawString(victoryText, (image.getWidth() - textWidth)/2, image.getHeight()/2 - 60);

//         g2.setColor(Color.ORANGE);
//         g2.setFont(new Font("Arial", Font.PLAIN, 24));
//         String[] messages = {
//             "You've exterminated an entire alien civilization",
//             "Millions of lives ended by your hand",
//             "Was it worth it? Probably not.",
//             "But hey, at least you had fun!",
//             "Restarting in: " + (8000 - (System.currentTimeMillis() - gameWonTime))/1000 + "s"
//         };

//         for (int i = 0; i < messages.length; i++) {
//             int width = g2.getFontMetrics().stringWidth(messages[i]);
//             g2.drawString(messages[i], (image.getWidth() - width)/2, image.getHeight()/2 + i*30);
//         }
//     }

//     public void draw(Graphics2D g2) {
//         Graphics2D imageContext = (Graphics2D) image.getGraphics();
//         imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());

//         if (!showingVictoryText) {
//             backgroundManager.draw(imageContext);
//         } else {
//             imageContext.setColor(Color.BLACK);
//             imageContext.fillRect(0, 0, image.getWidth(), image.getHeight());
//         }

//         if (gameOver) {
//             imageContext.setColor(Color.RED);
//             imageContext.setFont(new Font("Arial", Font.BOLD, 36));
//             String gameOverText = "GAME OVER";
//             int textWidth = imageContext.getFontMetrics().stringWidth(gameOverText);
//             imageContext.drawString(gameOverText, (image.getWidth() - textWidth)/2, image.getHeight()/2 - 50);
//             imageContext.setFont(new Font("Arial", Font.PLAIN, 24));
//             String restartText = "Press any key to restart";
//             int restartWidth = imageContext.getFontMetrics().stringWidth(restartText);
//             imageContext.drawString(restartText, (image.getWidth() - restartWidth)/2, image.getHeight()/2 + 20);
//         }
//         else if (showingVictoryText) {
//             drawVictoryScreen(imageContext);
//         }
//         else if (showingCompletionText) {
//             imageContext.setColor(Color.GREEN);
//             imageContext.setFont(new Font("Arial", Font.BOLD, 36));
//             String completedText = "LEVEL " + levelNumber + " COMPLETED!";
//             int textWidth = imageContext.getFontMetrics().stringWidth(completedText);
//             imageContext.drawString(completedText, (image.getWidth() - textWidth)/2, image.getHeight()/2);
//             imageContext.setFont(new Font("Arial", Font.PLAIN, 24));
//             String countdownText = "Next level in: " + ((5000 - (System.currentTimeMillis() - levelCompleteTime))/1000 + 1) + "s";
//             int countdownWidth = imageContext.getFontMetrics().stringWidth(countdownText);
//             imageContext.drawString(countdownText, (image.getWidth() - countdownWidth)/2, image.getHeight()/2 + 50);
//         }

//         if (!showingVictoryText) {
//             player.draw(imageContext);
//             for (Bullet bullet : new ArrayList<>(bullets)) {
//                 bullet.draw(imageContext);
//             }
//             for (Enemy enemy : new ArrayList<>(enemies)) {
//                 enemy.draw(imageContext);
//             }
//             for (PowerUp powerUp : new ArrayList<>(powerUps)) {
//                 powerUp.draw(imageContext);
//             }
//         }

//         g2.drawImage(image, 0, 0, null);
//         imageContext.dispose();
//     }

//     public void addBullet(SpaceBullet bullet) {
//         bullets.add(bullet);
//     }

//     public void addBullets(List<Bullet> bullets) {
//         this.bullets.addAll(bullets);
//     }

//     public Player getPlayer() {
//         return player;
//     }

//     public void checkLevelCompletion() {
//         if (!levelCompleted && enemies.isEmpty()) {
//             levelCompleted = true;
//             showingCompletionText = true;
//             levelCompleteTime = System.currentTimeMillis();
//         }
//     }

//     public boolean isLevelCompleted() {
//         return levelCompleted;
//     }

//     public int getLevelNumber() {
//         return levelNumber;
//     }

//     public void handleKeyPress(KeyEvent e) {
//         if (waitingForRestart) {
//             restartGame();
//             return;
//         }
//         player.handleKeyPress(e);
//     }

//     public void handleKeyRelease(KeyEvent e) {
//         player.handleKeyRelease(e);
//     }

//     public List<Rectangle> getSolidTiles() {
//         return null;
//     }

//     public void spawnSineWaveEnemies(int y) {
//         for (int i = 0; i < 4; i++) {
//             enemies.add(new SineWaveEnemy(150 * i + 55, y, this));
//         }
//     }

//     public void spawnBezierEnemies(int y) {
//         for (int i = 0; i < 3; i++) {
//             enemies.add(new BezierEnemy(150 * i + 55, y, this));
//         }
//     }

//     public void spawnCircularEnemies(int y) {
//         int centerX = 300;
//         int centerY = 250;
//         int radius = 100;
//         CircularEnemy enemy = new CircularEnemy(centerX, centerY, this, radius);
//         enemy.setAngularSpeed(2);
//         enemies.add(enemy);
//     }

//     public void endLevel() {
//         if (levelNumber >= 3) {
//             gameWon = true;
//             showingVictoryText = true;
//             gameWonTime = System.currentTimeMillis();
//             SoundManager.getInstance().stopSound("background");
//             SoundManager.getInstance().playSound("victory", false);
//             return;
//         }

//         enemies.clear();
//         bullets.clear();
//         powerUps.clear();
//         levelNumber++;
//         initializeLevel();
//         levelCompleted = false;
//         showingCompletionText = false;
//         gamePanel.repaint();
//     }

//     private void restartGame() {
//         gameOver = false;
//         waitingForRestart = false;
//         levelNumber = 1;
//         enemies.clear();
//         bullets.clear();
//         powerUps.clear();
//         initializeLevel();
//         levelCompleted = false;
//         showingCompletionText = false;
//     }
// }
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
    private long levelCompleteTime;
    private boolean showingCompletionText;
    private boolean gameOver;
    private boolean waitingForRestart;
    private BackgroundManager backgroundManager;
    private long gameOverTime;
    private BufferedImage image;
    private static final int MAX_POWERUPS_ON_SCREEN = 5;

    private boolean gameWon = false;
    private long gameWonTime;
    private boolean showingVictoryText = false;
    private boolean deathSoundPlayed = false;

    public SpaceLevel(int levelNumber, GamePanel gamePanel) {
        image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.showingCompletionText = false;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.gamePanel = gamePanel;
        initializeLevel();
        SoundManager.getInstance().playSound("background", true);
    }

    private void initializeLevel() {
        this.player = new SpacePlayer(300, 450, this);
        setupBackground();
        spawnEnemies();
    }

    private void maybeSpawnPowerUp() {
        if (levelCompleted || gameOver || gameWon)
            return;

        if (powerUps.size() >= MAX_POWERUPS_ON_SCREEN)
            return;

        if (Math.random() < 0.02) {
            int x = (int) (Math.random() * (image.getWidth() - 50)) + 25;
            int y = (int) (Math.random() * 100);

            PowerUp powerUp = (Math.random() < 0.66)
                    ? new BulletUpPowerUp(x, y)
                    : new BulletDownPowerUp(x, y);

            powerUps.add(powerUp);
        }
    }

    private void setupBackground() {
        backgroundManager = new BackgroundManager(gamePanel, 1);

        switch (levelNumber) {
            case 2:
                backgroundManager.setImages(new String[] {
                        "images/T_RedBackground_Version4_Layer1.png",
                        "images/T_RedBackground_Version4_Layer2.png",
                        "images/T_RedBackground_Version4_Layer3.png",
                        "images/T_RedBackground_Version4_Layer4.png"
                });
                break;
            case 3:
                backgroundManager.setImages(new String[] {
                        "images/T_PurpleBackground_Version4_Layer1.png",
                        "images/T_PurpleBackground_Version4_Layer2.png",
                        "images/T_PurpleBackground_Version4_Layer3.png",
                        "images/T_PurpleBackground_Version4_Layer4.png"
                });
                break;
        }
    }

    private void spawnEnemies() {
        if (levelNumber == 1) {
            spawnCircularEnemies(250);
            spawnSineWaveEnemies(50);
        } else if (levelNumber == 2) {
            spawnBezierEnemies(0);
            spawnSineWaveEnemies(50);
            spawnSineWaveEnemies(-150);
            spawnBezierEnemies(75);
        } else if (levelNumber == 3) {
            spawnBezierEnemies(0);
            spawnSineWaveEnemies(50);
            spawnSineWaveEnemies(-150);
            spawnCircularEnemies(250);
        }
    }

    public void update() {
        if (gameWon) {
            if (showingVictoryText && System.currentTimeMillis() - gameWonTime >= 8000) {
                restartGame();
            }
            return;
        }

        if (gameOver && waitingForRestart)
            return;

        if (levelCompleted) {
            if (showingCompletionText && System.currentTimeMillis() - levelCompleteTime >= 5000) {
                endLevel();
            }
            return;
        }

        if (player.getHealth() <= 0 && !gameOver) {
            gameOver = true;
            waitingForRestart = true;
            gameOverTime = System.currentTimeMillis();
            if (!deathSoundPlayed) {
                SoundManager.getInstance().playSound("die", false); // Play die sound
                deathSoundPlayed = true;
                SoundManager.getInstance().stopSound("background"); // Stop background music on death
            }
            return;
        }

        backgroundManager.moveDown();
        player.update();
        updateBullets();
        updateEnemies();
        updatePowerUps();
        maybeSpawnPowerUp();
        checkCollisions();
        checkLevelCompletion();
    }

    private void updateBullets() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.getY() < 0 || bullet.getY() > image.getHeight()) {
                toRemove.add(bullet);
            }
        }
        bullets.removeAll(toRemove);
    }

    private void updateEnemies() {
        // Create a copy of the list to avoid ConcurrentModificationException
        List<Enemy> enemiesToRemove = new ArrayList<>();
        
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.getY() > image.getHeight()) {
                enemiesToRemove.add(enemy);
                player.setHealth(player.getHealth() - 10);
                if (player.getHealth() <= 0) {
                    gameOver = true;
                    waitingForRestart = true;
                    gameOverTime = System.currentTimeMillis();
                    if (!deathSoundPlayed) {
                        SoundManager.getInstance().playSound("die", false);
                        deathSoundPlayed = true;
                        SoundManager.getInstance().stopSound("background");
                    }
                }
            }
        }
        
        // Remove all enemies that need to be removed
        enemies.removeAll(enemiesToRemove);
    }

    private void updatePowerUps() {
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            p.update();
            if (p.getX() < 0 || p.getX() > image.getWidth() ||
                    p.getY() < 0 || p.getY() > image.getHeight()) {
                it.remove();
            }
        }
    }

    private void checkCollisions() {
        checkBulletCollisions();
        checkPowerUpCollisions();
    }

    private void checkBulletCollisions() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();

            if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
                Iterator<Enemy> eit = enemies.iterator();
                while (eit.hasNext()) {
                    Enemy enemy = eit.next();
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                        it.remove();
                        if (enemy.getHealth() <= 0) {
                            eit.remove();
                        }
                        break;
                    }
                }
            } else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
                if (bullet.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(bullet.getDamage());
                    SoundManager.getInstance().playSound("hit", false); // Play hit sound
                    it.remove();
                }
            }
        }
    }

    private void checkPowerUpCollisions() {
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            if (p.getBounds().intersects(player.getBounds())) {
                if (p instanceof BulletUpPowerUp) {
                    player.setBulletsPerShot(player.getBulletsPerShot() + 1);
                    SoundManager.getInstance().playSound("powerup", false); // Play power-up sound
                } else if (p instanceof BulletDownPowerUp) {
                    player.setBulletsPerShot(Math.max(1, player.getBulletsPerShot() - 1));
                    SoundManager.getInstance().playSound("powerdown", false); // Play power-down sound
                }
                it.remove();
            }
        }
    }

    private void drawVictoryScreen(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 42));
        String title = "GENOCIDE COMPLETE!";
        int width = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (image.getWidth() - width) / 2, image.getHeight() / 2 - 60);

        g2.setColor(Color.ORANGE);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        String[] lines = {
                "You've exterminated an entire alien civilization",
                "Millions of lives ended by your hand",
                "Was it worth it? Probably not.",
                "But hey, at least you had fun!",
                "Restarting in: " + (8000 - (System.currentTimeMillis() - gameWonTime)) / 1000 + "s"
        };
        for (int i = 0; i < lines.length; i++) {
            int lineWidth = g2.getFontMetrics().stringWidth(lines[i]);
            g2.drawString(lines[i], (image.getWidth() - lineWidth) / 2, image.getHeight() / 2 + i * 30);
        }
    }

    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
    
        if (showingVictoryText) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
        } else {
            backgroundManager.draw(g);
        }
    
        // Draw player health in top right corner
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String healthText = "Health: " + player.getHealth();
        int healthWidth = g.getFontMetrics().stringWidth(healthText);
        g.drawString(healthText, image.getWidth() - healthWidth - 20, 30); // 20px from right, 30px from top
    
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String text = "GAME OVER";
            int width = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (image.getWidth() - width) / 2, image.getHeight() / 2 - 50);
    
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String restart = "Press any key to restart";
            int restartWidth = g.getFontMetrics().stringWidth(restart);
            g.drawString(restart, (image.getWidth() - restartWidth) / 2, image.getHeight() / 2 + 20);
        } else if (showingVictoryText) {
            drawVictoryScreen(g);
        } else if (showingCompletionText) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String text = "LEVEL " + levelNumber + " COMPLETED!";
            int width = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (image.getWidth() - width) / 2, image.getHeight() / 2);
    
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String countdown = "Next level in: "
                    + ((5000 - (System.currentTimeMillis() - levelCompleteTime)) / 1000 + 1) + "s";
            int cwidth = g.getFontMetrics().stringWidth(countdown);
            g.drawString(countdown, (image.getWidth() - cwidth) / 2, image.getHeight() / 2 + 50);
        }
    
        if (!showingVictoryText) {
            // Create defensive copies of the collections
            List<Bullet> bulletsCopy = new ArrayList<>(bullets);
            List<Enemy> enemiesCopy = new ArrayList<>(enemies);
            List<PowerUp> powerUpsCopy = new ArrayList<>(powerUps);
    
            player.draw(g);
            bulletsCopy.forEach(b -> b.draw(g));
            enemiesCopy.forEach(e -> e.draw(g));
            powerUpsCopy.forEach(p -> p.draw(g));
        }
    
        g2.drawImage(image, 0, 0, null);
        g.dispose();
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
        CircularEnemy enemy = new CircularEnemy(300, 250, this, 100);
        enemy.setAngularSpeed(2);
        enemies.add(enemy);
    }

    public void endLevel() {
        if (levelNumber >= 3) {
            gameWon = true;
            showingVictoryText = true;
            gameWonTime = System.currentTimeMillis();
            SoundManager.getInstance().stopSound("background");
            SoundManager.getInstance().playSound("victory", false);
            return;
        }

        enemies.clear();
        bullets.clear();
        powerUps.clear();
        levelNumber++;
        initializeLevel();
        levelCompleted = false;
        showingCompletionText = false;
        gamePanel.repaint();
    }

    public void restartGame() {
        gameOver = false;
        waitingForRestart = false;
        levelNumber = 1;
        enemies.clear();
        bullets.clear();
        powerUps.clear();
        initializeLevel();
        gamePanel.setPaused(false);
        levelCompleted = false;
        showingCompletionText = false;
        gameWon = false;
        showingVictoryText = false;
        SoundManager.getInstance().playSound("background", true); // Restart background music
    }
}