
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.File;

public class SpacePlayer extends Player {

    // private int width = 50, height = 50;
    // private int bulletsPerShot = 1;
    // private int damage = 10;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean flashing = false;
    private int flashTimer = 0;
    private int flashDuration = 20; // Number of frames to flash
    private BufferedImage image; // Image for the player
    private BufferedImage brightImage; // Bright image for flashing effect
    private String imagePath = "images/space__0006_Player.png"; // path to the image

    public SpacePlayer(int x, int y, Level level) {
        super(x, y, 50, 50, level); // Call the constructor of the Player class
        loadImage();
        // this.brightImage = brightenImage(image);
        // this.x = 300;
        // this.y = 450;
    }

    private void loadImage() {
        try {
            java.awt.Image tempImage = ImageManager.loadImage(imagePath); // Load using ImageManager
            if (tempImage == null) {
                System.out.println("Failed to load player image: " + imagePath);
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                g2d.setColor(Color.BLUE);
                g2d.fillRect(0, 0, width, height);
                g2d.dispose();
                return;
            }

            int tempWidth = tempImage.getWidth(null);
            int tempHeight = tempImage.getHeight(null);

            if (tempWidth <= 0 || tempHeight <= 0) {
                System.err.println("Error: Invalid image dimensions (width=" + tempWidth + ", height=" + tempHeight
                        + ") for " + imagePath);
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                g2d.setColor(Color.BLUE);
                g2d.fillRect(0, 0, width, height);
                g2d.dispose();
                return;
            }
            // Create a BufferedImage from the loaded image
            image = new BufferedImage(tempWidth, tempHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D bGr = image.createGraphics();
            bGr.drawImage(tempImage, 0, 0, null);
            bGr.dispose();

        } catch (Exception e) {
            System.err.println("Error loading player image: " + e.getMessage());
            e.printStackTrace();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.BLUE);
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();
        }
    }

    public void update() {
        if (movingLeft && x > 0)
            x -= dx;
        if (movingRight && x < 550)
            x += dx;
        if (movingUp && y > 0)
            y -= dy;
        if (movingDown && y < 500)
            y += dy;

        if (flashing) {
            flashTimer--;
            if (flashTimer <= 0) {
                flashing = false;
            }
        }
    }

    public boolean isFlashing() {
        return flashing; // Return true if the player is flashing
    }

    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, width, height, null);
        } else {
            g2.setColor(Color.BLUE);
            g2.fillRect(x, y, width, height);
        }

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
            float spacing = width / (float) (bulletsPerShot - 1);
            for (int i = 0; i < bulletsPerShot; i++) {
                int bulletX = (int) (x + i * spacing);
                bullets.add(new SpaceBullet(bulletX, y, SpaceBullet.BulletOwner.PLAYER, damage));
            }
        }
        level.addBullets(bullets); // Add bullets to the level's bullet list

        // Play the shooting sound
        SoundManager.getInstance().playSound("shoot", false); // Play the sound once
    }

    // public void setBulletsPerShot(int bulletsPerShot) {
    // this.bulletsPerShot = bulletsPerShot;
    // }
    // public int getBulletsPerShot() {
    // return bulletsPerShot;
    // }

    public void move(int direction) {
        System.out.println(1);
        switch (direction) {
            case 0: // Move left
                if (x > 0)
                    x -= dx;
                break;
            case 1: // Move right
                if (x < 550)
                    x += dx;
                break;
            case 2: // Move up
                if (y > 0)
                    y -= dy;
                break;
            case 3: // Move down
                if (y < 450)
                    y += dy;
                break;
        }
    }

    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("Key Pressed: " + keyCode); // Debugging line to check key presses

        // if(keyCode == KeyEvent.VK_LEFT){
        // move(0);
        // }
        // if(keyCode == KeyEvent.VK_RIGHT){
        // move(1);
        // }
        // if(keyCode == KeyEvent.VK_UP){
        // move(2);
        // }
        // if(keyCode == KeyEvent.VK_DOWN){
        // move(3);
        // }
        // if(keyCode == KeyEvent.VK_SPACE){
        // shoot(); // Call the shoot method when space is pressed
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
                shoot(); // Call the shoot method when space is pressed
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Create and return the bounding box
    }

    public void takeDamage(int amount) {
        health -= amount;
        System.out.println("Player took damage: " + amount); // Log the damage taken
        // Start flashing
        flashing = true;
        flashTimer = flashDuration;
    }
}