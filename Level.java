import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class Level {
    protected BufferedImage image; // Image for the level background (if needed)
    protected List<Bullet> bullets = new ArrayList<>();
    protected List<ArcingBullet> arcingBullets = new ArrayList<>();
    protected List<CircularEnemy> circularEnemies = new ArrayList<>(); // Add list for circular enemies
    protected List<SineWaveEnemy> sineWaveEnemies = new ArrayList<>(); // Add list for sine wave enemies
    // protected List<ShootingEnemy> shootingEnemies = new ArrayList<>(); // Add
    // list for shooting enemies

    public abstract void handleKeyPress(KeyEvent e);

    public abstract void handleKeyRelease(KeyEvent e);

    protected int screenWidth = 800;
    protected int screenHeight = 600;

    public void update() {
        // Update regular bullets and remove off-screen ones
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if (isBulletOffScreen(bullet)) {
                bulletIterator.remove();
            }
        }

        // Update arcing bullets and remove off-screen ones
        Iterator<ArcingBullet> arcingBulletIterator = arcingBullets.iterator();
        while (arcingBulletIterator.hasNext()) {
            ArcingBullet bullet = arcingBulletIterator.next();
            bullet.update();
            if (isArcingBulletOffScreen(bullet)) {
                arcingBulletIterator.remove();
            }
        }

        // Update circular enemies
        for (CircularEnemy enemy : circularEnemies) {
            enemy.update();
        }

        // Update sine wave enemies
        for (SineWaveEnemy enemy : sineWaveEnemies) {
            enemy.update();
        }

        // Update shooting enemies
        // for (ShootingEnemy enemy : shootingEnemies) {
        // enemy.update();
        // }
    }

    public boolean isBulletOffScreen(Bullet bullet) {
        return bullet.getY() < -bullet.getHeight() ||
                bullet.getY() > screenHeight ||
                bullet.getX() < -bullet.getWidth() ||
                bullet.getX() > screenWidth;
    }

    public boolean isArcingBulletOffScreen(ArcingBullet bullet) {
        return bullet.getY() < -bullet.getHeight() ||
                bullet.getY() > screenHeight ||
                bullet.getX() < -bullet.getWidth() ||
                bullet.getX() > screenWidth;
    }

    public void draw(Graphics2D g) {
        // Draw the level (e.g., player, enemies, bullets)
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        for (ArcingBullet bullet : arcingBullets) {
            bullet.draw(g);
        }
        for (CircularEnemy enemy : circularEnemies) {
            enemy.draw(g);
        }
        for (SineWaveEnemy enemy : sineWaveEnemies) {
            enemy.draw(g);
        }
        // for (ShootingEnemy enemy : shootingEnemies) {
        // enemy.draw(g);
        // }
    }

    public void addBullets(List<Bullet> newBullets) {
        bullets.addAll(newBullets);
    }

    public void addArcingBullets(List<ArcingBullet> newBullets) {
        arcingBullets.addAll(newBullets);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<ArcingBullet> getArcingBullets() {
        return arcingBullets;
    }

    public void addCircularEnemy(CircularEnemy enemy) {
        circularEnemies.add(enemy);
    }

    public List<CircularEnemy> getCircularEnemies() {
        return circularEnemies;
    }

    public void addSineWaveEnemy(SineWaveEnemy enemy) {
        sineWaveEnemies.add(enemy);
    }

    public List<SineWaveEnemy> getSineWaveEnemies() {
        return sineWaveEnemies;
    }

    // public void addShootingEnemy(ShootingEnemy enemy) {
    // shootingEnemies.add(enemy);
    // }

    // public List<ShootingEnemy> getShootingEnemies() {
    // return shootingEnemies;
    // }

    public abstract List<Rectangle> getSolidTiles();
    // public TileMap getTileMap() {
    // return tileMap;
    // }
}