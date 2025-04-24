import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;

public class SpacePlayer extends Player {

    // private int width = 50, height = 50;
    // private int bulletsPerShot = 1;
    // private int damage = 10;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    public SpacePlayer(int x, int y, Level level) {
        super(x, y, 50, 50, level); // Call the constructor of the Player class
        // this.x = 300;
        // this.y = 450; 
    }

    public void update() {
        if (movingLeft && x > 0) x -= dx;
        if (movingRight && x < 550) x += dx;
        if (movingUp && y > 0) y -= dy;
        if (movingDown && y < 500) y += dy;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRect(x, y, width, height);
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void shoot() {
            List<Bullet> bullets = new ArrayList<>();

    // Spread bullets evenly across the player's width
    if (bulletsPerShot == 1) {
        int bulletX = x + width / 2;
        bullets.add(new SpaceBullet(bulletX, y, SpaceBullet.BulletOwner.PLAYER, damage));
    } else {
        // System.out.println(1);
        float spacing = width / (float)(bulletsPerShot - 1);
        for (int i = 0; i < bulletsPerShot; i++) {
            int bulletX = (int)(x + i * spacing);
            bullets.add(new SpaceBullet(bulletX, y, SpaceBullet.BulletOwner.PLAYER, damage));
        }
        
    }
    level.addBullets(bullets);  // Add bullets to the level's bullet list

    }

    // public void setBulletsPerShot(int bulletsPerShot) {
    //     this.bulletsPerShot = bulletsPerShot;
    // }
    // public int getBulletsPerShot() {
    //     return bulletsPerShot;
    // }

    public void move(int direction){
        System.out.println(1);
        switch (direction) {
            case 0: // Move left
                if (x > 0) x -= dx;
                break;
            case 1: // Move right
                if (x < 550) x += dx;
                break;
            case 2: // Move up
                if (y > 0) y -= dy;
                break;
            case 3: // Move down
                if (y < 450) y += dy;
                break;
        }
    }
    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("Key Pressed: " + keyCode);  // Debugging line to check key presses

        // if(keyCode == KeyEvent.VK_LEFT){
        //     move(0);
        // }
        // if(keyCode == KeyEvent.VK_RIGHT){
        //     move(1);
        // }
        // if(keyCode == KeyEvent.VK_UP){
        //     move(2);
        // }
        // if(keyCode == KeyEvent.VK_DOWN){
        //     move(3);
        // }
        // if(keyCode == KeyEvent.VK_SPACE){
        //     shoot();  // Call the shoot method when space is pressed
        // }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                movingLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                movingRight = true;
                break;
            case KeyEvent.VK_UP:
                movingUp = true;
                break;
            case KeyEvent.VK_DOWN:
                movingDown = true;
                break;
            case KeyEvent.VK_SPACE:
                shoot();  // Call the shoot method when space is pressed
                break;
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                movingLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                movingRight = false;
                break;
            case KeyEvent.VK_UP:
                movingUp = false;
                break;
            case KeyEvent.VK_DOWN:
                movingDown = false;
                break;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Create and return the bounding box
    }
}

