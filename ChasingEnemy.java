import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ChasingEnemy extends Enemy {
    private int speed = 3; 

    public ChasingEnemy(int startX, int startY, Level level) {
        super(startX, startY, level);
        image = ImageManager.loadBufferedImage("images/chasing-enemy.png"); 
        health = 20; 
        damage = 20;
    }

    @Override
    public void update() {
        if (level == null || level.getPlayer() == null) return;

        
        Player player = level.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();

        
        if (x < playerX) x += speed;
        if (x > playerX) x -= speed;
        if (y < playerY) y += speed;
        if (y > playerY) y -= speed;

        
        if (getBounds().intersects(player.getBounds())) {
            player.takeDamage(damage); 
            setHealth(0);

        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, width, height, null); 
        } else {
            g2.setColor(java.awt.Color.RED); 
            g2.fillRect(x, y, width, height); 
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); 
    }
}