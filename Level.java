import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class Level {
    protected BufferedImage image; // Image for the level background (if needed)
    protected List<Bullet> bullets = new ArrayList<>();
    protected List<ArcingEnemy.ArcingBullet> arcingBullets = new ArrayList<>();

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
        Iterator<ArcingEnemy.ArcingBullet> arcingBulletIterator = arcingBullets.iterator();
        while (arcingBulletIterator.hasNext()) {
            ArcingEnemy.ArcingBullet bullet = arcingBulletIterator.next();
            bullet.update();
            if (isArcingBulletOffScreen(bullet)) {
                arcingBulletIterator.remove();
            }
        }
    }

    public boolean isBulletOffScreen(Bullet bullet) {
        return bullet.getY() < -bullet.getHeight() ||
                bullet.getY() > screenHeight ||
                bullet.getX() < -bullet.getWidth() ||
                bullet.getX() > screenWidth;
    }

    public boolean isArcingBulletOffScreen(ArcingEnemy.ArcingBullet bullet) {
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
        for (ArcingEnemy.ArcingBullet bullet : arcingBullets) {
            bullet.draw(g);
        }
    }

    public void addBullets(List<Bullet> newBullets) {
        bullets.addAll(newBullets);
    }

    public void addArcingBullets(List<ArcingEnemy.ArcingBullet> newBullets) {
        arcingBullets.addAll(newBullets);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<ArcingEnemy.ArcingBullet> getArcingBullets() {
        return arcingBullets;
    }

    public abstract List<Rectangle> getSolidTiles();
    // public TileMap getTileMap() {
    // return tileMap;
    // }
}