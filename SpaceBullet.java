import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
public class SpaceBullet extends Bullet {
  

    // private int x, y, width = 5, height = 10;  // Bullet dimensions
    // private int speed;
    // private BulletOwner owner;  // Owner of the bullet (player or enemy)
    // private int damage;

    public SpaceBullet(int startX, int startY, BulletOwner owner, int damage) {
        this.x = startX;
        this.y = startY;
        this.owner = owner;
        this.speed = (owner == BulletOwner.PLAYER) ? -15 : 5;
        this.damage = damage;  // Set the damage value
        if(owner == BulletOwner.PLAYER){
            image = ImageManager.loadBufferedImage("images/playerbullet.png"); // Load player bullet image
        }else{
            image = ImageManager.loadBufferedImage("images/enemybullet.png"); // Load enemy bullet image
        }
            // this.width = 5; // Example width for player bullet
            // this.height = 10; // Example height for player bullet
    }

    // Update the position of the bullet (move up)
    public void update() {
        y += speed;
    }

    // Check if the bullet is off-screen
    public boolean isOffScreen() {
        return y < 0 || y> 500;  // Assuming screen height is 500
    }



    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null); // Draw the bullet image at its current position
        // g2.setColor(Color.RED);  // Set color for the bullet
        // g2.fillRect(x, y, width, height);  // Draw the bullet as a rectangle (for simplicity)
    }


}
