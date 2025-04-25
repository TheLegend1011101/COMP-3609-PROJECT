import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class BulletDownPowerUp extends PowerUp {
    // private StripAnimation animation;
    public BulletDownPowerUp(int startX, int startY) {
        super(startX, startY);
        animation = new StripAnimation("images/bulletdown.png", 2, 300, width, height, true); // Example parameters
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
    }

    // @Override
    // public void draw(Graphics2D g2) {
    //     // Draw the power-up as a rectangle or an image
    //     g2.setColor(java.awt.Color.YELLOW); // Example color for the power-up
    //     g2.fillRect(x, y, width, height); // Draw the power-up as a rectangle
    //     // BufferedImage image = ImageLoader.loadImage("path/to/bullet_down_powerup.png"); // Load the image for the power-up
    //     // g2.drawImage(image, x, y, width, height, null); // Draw the power-up image at its current position
    // }
    
}
