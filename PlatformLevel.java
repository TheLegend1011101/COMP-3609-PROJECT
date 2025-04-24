import java.util.*;
import java.awt.event.*;
import java.awt.Graphics2D;
public class PlatformLevel extends Level {
    private int levelNumber;
    private boolean levelCompleted;
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    // private List<Platform> platforms;

    public PlatformLevel(int levelNumber) {
        this.levelNumber = levelNumber;
        this.levelCompleted = false;
        this.player = new PlatformPlayer(200, 450, this); // Example player starting position
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        // this.platforms = new ArrayList<>();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    // public void addBullet(Bullet bullet) {
    //     bullets.add(bullet);
    // }

    public void addBullets(List<Bullet> bullets) {
        this.bullets.addAll(bullets);
    }

    public void handleKeyPress(KeyEvent e) {
        player.handleKeyPress(e);
        // int keyCode = e.getKeyCode();
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
    }

    public void handleKeyRelease(KeyEvent e) {
        player.handleKeyRelease(e);
        // int keyCode = e.getKeyCode();
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

    

    // public void addPlatform(Platform platform) {
    //     platforms.add(platform);
    // }

    public Player getPlayer() {
        return player;
    }

    public void draw(Graphics2D g) {
        player.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        // for (Platform platform : platforms) {
        //     platform.draw(g);
        // }
    }
    public void update() {
        player.update();
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        // for (Platform platform : platforms) {
        //     platform.update();
        // }
        // checkLevelCompletion();  // Check if the level is completed
    }
}
