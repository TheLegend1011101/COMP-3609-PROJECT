import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

public class SpaceLevel extends Level {

    
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
    private TileMap tileMap;
    private int offsetX = 0; // Horizontal scrolling offset
    private int offsetY = 0; // Vertical scrolling offset (if needed)
    private boolean gameWon = false;
    private long gameWonTime;
    private boolean showingVictoryText = false;
    private boolean deathSoundPlayed = false;
    private BufferedImage asteroidImage;
    private int collisionFrames = 20;
    private List<TileMap> maps;
    private BufferedImage fullHealth;
    private BufferedImage halfHealth;
    private BufferedImage noHealth;
    public SpaceLevel(int levelNumber, GamePanel gamePanel) {
        image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        this.asteroidImage = ImageManager.loadBufferedImage("images/big-a.png");
        this.fullHealth = ImageManager.loadBufferedImage("images/fullhealth.png");
        this.halfHealth = ImageManager.loadBufferedImage("images/halfhealth.png");
        this.noHealth = ImageManager.loadBufferedImage("images/nohealth.png");
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.showingCompletionText = false;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.maps = new ArrayList<>();
        try{
            maps.add(new TileMap("maps/level1.txt", 50, 50));
            maps.add(new TileMap("maps/level2.txt", 50, 50));
            maps.add(new TileMap("maps/level3.txt", 50, 50));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        this.gamePanel = gamePanel;
        initializeLevel();
        SoundManager.getInstance().playSound("background", true);
    }

    private void initializeLevel() {
        this.player = new SpacePlayer(300, 450, this);
        tileMap = maps.get(levelNumber-1);
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
            case 1:
                backgroundManager.setImages(new String[] {
                        "images/T_YellowBackground_Version4_Layer1.png",
                        "images/T_YellowBackground_Version4_Layer2.png",
                        "images/T_YellowBackground_Version4_Layer3.png",
                        "images/T_YellowBackground_Version4_Layer4.png"
                });
                break;
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
            spawnChasingEnemies(1);
            spawnSineWaveEnemies(50);
        } else if (levelNumber == 2) {
            spawnBezierEnemies(0);
            spawnSineWaveEnemies(50);
            spawnChasingEnemies(1);
            spawnSineWaveEnemies(-150);
        } else if (levelNumber == 3) {
            spawnBezierEnemies(0);
            spawnChasingEnemies(1);
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
        collisionFrames++;
        updatePlayerPosition(player);
        if (checkCollision(player) && collisionFrames > 20) {
            collisionFrames = 0; // Reset collision frames
            player.takeDamage(10); // Handle collision
        }
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
            if(enemy.getHealth() == 0)
                enemiesToRemove.add(enemy);
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

    // public boolean checkCollision(Player player) {
    //     int tileWidth = tileMap.getTileWidth();
    //     int tileHeight = tileMap.getTileHeight();
    
    //     int playerCol = (player.getX() + offsetX) / tileWidth;
    //     int playerRow = (player.getY() + offsetY) / tileHeight;
    
    //     int[][] map = tileMap.getMap();
    //     if (playerRow >= 0 && playerRow < map.length && playerCol >= 0 && playerCol < map[0].length) {
    //         return map[playerRow][playerCol] == 1; // Collision if tile contains an asteroid
    //     }
    //     return false;
    // }

    public boolean checkCollision(Player player) {
        int tileWidth = tileMap.getTileWidth();
        int tileHeight = tileMap.getTileHeight();
        int[][] map = tileMap.getMap();
        
        // Get player bounds in tile coordinates
        int leftTile = (player.getX() + offsetX) / tileWidth;
        int rightTile = (player.getX() + offsetX + player.getWidth() - 1) / tileWidth;
        int topTile = (player.getY() + offsetY) / tileHeight;
        int bottomTile = (player.getY() + offsetY + player.getHeight() - 1) / tileHeight;
        
        // Check all tiles the player overlaps with
        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                // Check bounds
                if (row >= 0 && row < map.length && col >= 0 && col < map[row].length) {
                    // Check if tile is solid (1 represents asteroid in this case)
                    if (map[row][col] == 1) {
                        return true;
                    }
                } else {
                    // Treat out-of-bounds as collision if needed
                    return true;
                }
            }
        }
        return false;
    }

    // private void checkBulletCollisions() {
    //     Iterator<Bullet> it = bullets.iterator();
    //     while (it.hasNext()) {
    //         Bullet bullet = it.next();

    //         if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
    //             Iterator<Enemy> eit = enemies.iterator();
    //             while (eit.hasNext()) {
    //                 Enemy enemy = eit.next();
    //                 if (bullet.getBounds().intersects(enemy.getBounds())) {
    //                     enemy.setHealth(enemy.getHealth() - bullet.getDamage());
    //                     it.remove();
    //                     if (enemy.getHealth() <= 0) {
    //                         eit.remove();
    //                     }
    //                     break;
    //                 }
    //             }
    //         } else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
    //             if (bullet.getBounds().intersects(player.getBounds())) {
    //                 player.takeDamage(bullet.getDamage());
    //                 SoundManager.getInstance().playSound("hit", false); // Play hit sound
    //                 it.remove();
    //             }
    //         }
    //     }
    // }

    private void checkBulletCollisions() {
        // Create a copy of bullets to iterate over
        List<Bullet> bulletsCopy = new ArrayList<>(bullets);
        
        for (Bullet bullet : bulletsCopy) {
            if (bullet.getOwner() == Bullet.BulletOwner.PLAYER) {
                // Create a copy of enemies to avoid concurrent modification
                List<Enemy> enemiesCopy = new ArrayList<>(enemies);
                
                for (Enemy enemy : enemiesCopy) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                        bullets.remove(bullet); // Safe because we're modifying the original list
                        
                        if (enemy.getHealth() <= 0) {
                            enemies.remove(enemy); // Safe because we're modifying the original list
                        }
                        break; // Exit enemy loop after first hit
                    }
                }
            } 
            else if (bullet.getOwner() == Bullet.BulletOwner.ENEMY) {
                if (bullet.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(bullet.getDamage());
                    bullets.remove(bullet); // Safe because we're modifying the original list
                    SoundManager.getInstance().playSound("hit", false);
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

    public void updatePlayerPosition(Player player) {
        offsetX = Math.max(0, player.getX() - 300); // Keep player centered horizontally
    }

    public void drawTileMap(Graphics2D g) {
        int[][] map = tileMap.getMap();
        int tileWidth = tileMap.getTileWidth();
        int tileHeight = tileMap.getTileHeight();
    
        int startCol = offsetX / tileWidth;
        int startRow = offsetY / tileHeight;
    
        int endCol = Math.min(startCol + (600 / tileWidth) + 1, map[0].length); // Assuming screen width = 600
        int endRow = Math.min(startRow + (500 / tileHeight) + 1, map.length);  // Assuming screen height = 500
    
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (map[row][col] == 1) { // Draw asteroid tile
                    g.drawImage(asteroidImage, (col * tileWidth) - offsetX, (row * tileHeight) - offsetY, tileWidth, tileHeight, null);
                    // g.setColor(Color.GRAY);
                    // g.fillRect((col * tileWidth) - offsetX, (row * tileHeight) - offsetY, tileWidth, tileHeight);
                }
            }
        }
    }

    // public void draw(Graphics2D g2) {
    //     Graphics2D g = (Graphics2D) image.getGraphics();
    //     g.clearRect(0, 0, image.getWidth(), image.getHeight());
    
    //     // if (showingVictoryText) {
    //     //     g.setColor(Color.BLACK);
    //     //     g.fillRect(0, 0, image.getWidth(), image.getHeight());
    //     // } else {
    //     //     backgroundManager.draw(g);
    //     // }
    //     backgroundManager.draw(g);
    
    //     // Draw player health in top right corner
    //     if(!showingVictoryText) {
    //     drawTileMap(g);
    //     }
    
    //     if (gameOver) {
    //         g.setColor(Color.RED);
    //         g.setFont(new Font("Arial", Font.BOLD, 36));
    //         String text = "GAME OVER";
    //         int width = g.getFontMetrics().stringWidth(text);
    //         g.drawString(text, (image.getWidth() - width) / 2, image.getHeight() / 2 - 50);
    
    //         g.setFont(new Font("Arial", Font.PLAIN, 24));
    //         String restart = "Press any key to restart";
    //         int restartWidth = g.getFontMetrics().stringWidth(restart);
    //         g.drawString(restart, (image.getWidth() - restartWidth) / 2, image.getHeight() / 2 + 20);
    //     } else if (showingVictoryText) {
    //         drawVictoryScreen(g);
    //     } else if (showingCompletionText) {
    //         g.setColor(Color.GREEN);
    //         g.setFont(new Font("Arial", Font.BOLD, 36));
    //         String text = "LEVEL " + levelNumber + " COMPLETED!";
    //         int width = g.getFontMetrics().stringWidth(text);
    //         g.drawString(text, (image.getWidth() - width) / 2, image.getHeight() / 2);
    
    //         g.setFont(new Font("Arial", Font.PLAIN, 24));
    //         String countdown = "Next level in: "
    //                 + ((5000 - (System.currentTimeMillis() - levelCompleteTime)) / 1000 + 1) + "s";
    //         int cwidth = g.getFontMetrics().stringWidth(countdown);
    //         g.drawString(countdown, (image.getWidth() - cwidth) / 2, image.getHeight() / 2 + 50);
    //     }
    
    //     if (!showingVictoryText) {
    //         // Create defensive copies of the collections
    //         List<Bullet> bulletsCopy = new ArrayList<>(bullets);
    //         List<Enemy> enemiesCopy = new ArrayList<>(enemies);
    //         List<PowerUp> powerUpsCopy = new ArrayList<>(powerUps);
    //         player.draw(g);
    //         bulletsCopy.forEach(b -> b.draw(g));
    //         enemiesCopy.forEach(e -> e.draw(g));
    //         powerUpsCopy.forEach(p -> p.draw(g));
    //     }

        
    //     // g.setColor(Color.WHITE);
    //     // g.setFont(new Font("Arial", Font.BOLD, 20));
    //     // String healthText = "Health: " + Math.max(0,player.getHealth());
    //     // int healthWidth = g.getFontMetrics().stringWidth(healthText);
    //     // g.drawString(healthText, image.getWidth() - healthWidth - 20, 30); // 20px from right, 30px from top
    //     int heartSize = 20; // Size of each heart in pixels
    //     int heartSpacing = 5; // Space between hearts
    //     int totalHearts = 5; // Number of hearts to display
    //     int healthPerHeart = 100 / totalHearts; // 20 health per heart

    //     // Calculate full hearts and partial heart percentage
    //     int fullHearts = player.getHealth() / healthPerHeart;
    //     float partialHeart = (player.getHealth() % healthPerHeart) / (float)healthPerHeart;

    //     // Draw hearts from right to left (so they appear anchored to the right)
    //     int startX = image.getWidth() - 30; // Start 30px from right edge

    //     for (int i = totalHearts - 1; i >= 0; i--) {
    //         int x = startX - (i * (heartSize + heartSpacing));
            
    //         // Draw empty heart outline
    //         g.setColor(Color.WHITE);
    //         g.drawOval(x, 10, heartSize, heartSize);
            
    //         // Fill heart if it should be full or partial
    //         if (i < fullHearts) {
    //             // Full heart
    //             g.setColor(Color.RED);
    //             g.fillOval(x, 10, heartSize, heartSize);
    //         } else if (i == fullHearts && partialHeart > 0) {
    //             // Partial heart
    //             g.setColor(Color.RED);
    //             int partialWidth = (int)(heartSize * partialHeart);
    //             g.fillOval(x, 10, partialWidth, heartSize);
    //         }
    //     }
    
    //     g2.drawImage(image, 0, 0, null);
    //     g.dispose();
    // }

    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
    
        backgroundManager.draw(g);
    
        if(!showingVictoryText) {
            drawTileMap(g);
        }
    
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
            List<Bullet> bulletsCopy = new ArrayList<>(bullets);
            List<Enemy> enemiesCopy = new ArrayList<>(enemies);
            List<PowerUp> powerUpsCopy = new ArrayList<>(powerUps);
            player.draw(g);
            bulletsCopy.forEach(b -> b.draw(g));
            enemiesCopy.forEach(e -> e.draw(g));
            powerUpsCopy.forEach(p -> p.draw(g));
        }
    
        // Draw hearts using images
        int heartSize = 30; // Size of each heart in pixels
        int heartSpacing = 5; // Space between hearts
        int totalHearts = 5; // Number of hearts to display
        int healthPerHeart = 100 / totalHearts; // 20 health per heart
    
        // Calculate full hearts and partial heart
        int fullHearts = player.getHealth() / healthPerHeart;
        int partialHeartHealth = player.getHealth() % healthPerHeart;
    
        // Draw hearts from right to left (so they appear anchored to the right)
        int startX = image.getWidth() - 30; // Start 30px from right edge
    
        for (int i = totalHearts - 1; i >= 0; i--) {
            int x = startX - (i * (heartSize + heartSpacing));
            
            if (i < fullHearts) {
                // Full heart
                g.drawImage(fullHealth, x, 10, heartSize, heartSize, null);
            } else if (i == fullHearts && partialHeartHealth > 0) {
                // Partial heart (half heart)
                if (partialHeartHealth >= healthPerHeart / 2) {
                    g.drawImage(halfHealth, x, 10, heartSize, heartSize, null);
                } else {
                    g.drawImage(noHealth, x, 10, heartSize, heartSize, null);
                }
            } else {
                // Empty heart
                g.drawImage(noHealth, x, 10, heartSize, heartSize, null);
            }
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

    // public void checkLevelCompletion() {
    //     if (!levelCompleted && enemies.isEmpty()) {
    //         levelCompleted = true;
    //         showingCompletionText = true;
    //         levelCompleteTime = System.currentTimeMillis();
    //     }
    // }

    public void checkLevelCompletion() {
        if (!levelCompleted && enemies.isEmpty()) {
            levelCompleted = true;
            
            // For final level (3), go straight to victory
            if (levelNumber >= 3) {
                gameWon = true;
                showingVictoryText = true;
                gameWonTime = System.currentTimeMillis();
                SoundManager.getInstance().stopSound("background");
                SoundManager.getInstance().playSound("victory", false);
            } 
            // For other levels, show completion text
            else {
                showingCompletionText = true;
                levelCompleteTime = System.currentTimeMillis();
            }
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

    // public void endLevel() {
    //     if (levelNumber >= 3) {
    //         gameWon = true;
    //         showingVictoryText = true;
    //         gameWonTime = System.currentTimeMillis();
    //         SoundManager.getInstance().stopSound("background");
    //         SoundManager.getInstance().playSound("victory", false);
    //         return;
    //     }

    //     enemies.clear();
    //     bullets.clear();
    //     powerUps.clear();
    //     levelNumber++;
    //     initializeLevel();
    //     levelCompleted = false;
    //     showingCompletionText = false;
    //     gamePanel.repaint();
    // }

    public void endLevel() {
        // Only proceed if not on final level (victory already handled)
        if (levelNumber >= 3) {
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

    public void spawnChasingEnemies(int count) {
        for (int i = 0; i < count; i++) {
            int startX = (int) (Math.random() * 600); // Random starting x position
            int startY = (int) (Math.random() * 200); // Random starting y position
            enemies.add(new ChasingEnemy(startX, startY, this));
        }
    }
}